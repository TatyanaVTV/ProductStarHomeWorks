package ru.vtv.hw.practical.cafe;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.Duration.ofSeconds;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.mockito.Mockito.*;
import static ru.vtv.hw.practical.cafe.Cafe.MAX_ORDERS;
import static ru.vtv.hw.practical.cafe.MenuItem.*;
import static ru.vtv.hw.practical.util.ReflectionUtils.getPrivateField;

@Execution(CONCURRENT)
@TestInstance(PER_CLASS)
public class CafeTest {
    private static final String ORDER_COUNTER_FIELD = "ORDER_COUNTER";
    private static final String ORDERS_FIELD = "orders";

    @SneakyThrows
    @Test
    void testChefPreparesExactlyMaxCountDishes() {
        var cafe = new Cafe();
        var chef = new Chef(cafe);
        var chefThread = new Thread(chef);

        chefThread.start();
        chefThread.join();

        AtomicInteger counter = getPrivateField(cafe, ORDER_COUNTER_FIELD);
        assertEquals(MAX_ORDERS, counter.get());
    }

    @SneakyThrows
    @Test
    void testCafeHasMaxDishesWhenFinished() {
        var cafe = new Cafe();
        var chef = new Chef(cafe);
        var chefThread = new Thread(chef);

        chefThread.start();
        chefThread.join();

        AtomicInteger counter = getPrivateField(cafe, ORDER_COUNTER_FIELD);
        assertEquals(MAX_ORDERS, counter.get());

        assertTrue(cafe.isFinished());
    }

    @Test
    void testChefDoesNotPrepareMoreThanMaxDishes() throws InterruptedException {
        var cafe = spy(new Cafe());

        var chef = new Chef(cafe);
        var chefThread = new Thread(chef);

        chefThread.start();
        chefThread.join();

        verify(cafe, times(MAX_ORDERS)).addOrder(any());
    }

    @Test
    void testChefNotifiesCustomersWhenAddingDishes() throws InterruptedException {
        var addOrderCalls  = new AtomicInteger(0);
        var cafe = new Cafe() {
            @Override
            public synchronized void addOrder(MenuItem item) {
                super.addOrder(item);
                addOrderCalls.getAndIncrement();
            }
        };
        var chef = new Chef(cafe);

        var chefThread = new Thread(chef);
        chefThread.start();

        Thread.sleep(ofSeconds(10));
        chefThread.interrupt();

        assertTrue(addOrderCalls.get() > 0, "Chef должен был вызвать addOrder хотя бы один раз");
    }

    @SneakyThrows
    @Test
    void testVisitorTakesAvailableDish() {
        var cafe = new Cafe();
        cafe.addOrder(TEA);

        var customer = new CafeCustomer(cafe, "1");
        var customerThread = new Thread(customer);

        customerThread.start();
        customerThread.join();

        List<MenuItem> orders = getPrivateField(cafe, ORDERS_FIELD);
        assertTrue(orders.isEmpty());
    }

    @SneakyThrows
    @Test
    void testVisitorWaitsForDish() {
        var cafe = new Cafe();
        var testDish = BURGER;

        List<MenuItem> orders = getPrivateField(cafe, ORDERS_FIELD);

        var barrier = new CyclicBarrier(2);
        var customer = new CafeCustomer(cafe, "2") {
            @Override
            public void run() {
                try {
                    barrier.await();
                    super.run();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        var customerThread = new Thread(customer);
        customerThread.start();

        cafe.addOrder(testDish);
        barrier.await(10, SECONDS);
        assertFalse(orders.isEmpty(), "Заказ не был добавлен в кафе");

        customerThread.join(ofSeconds(testDish.getSecondsToCook() * 2L));
        assertTrue(orders.isEmpty(), "Заказ не был обработан клиентом");
    }

    @Test
    void testMultipleVisitorsCompeteForDishes() throws InterruptedException {
        var cafe = new Cafe();

        cafe.addOrder(SOUP);
        cafe.addOrder(SALAD);

        var customerThreads = new ArrayList<Thread>();
        for (int i = 1; i <= 3; i++) {
            var customer = new CafeCustomer(cafe, String.valueOf(i));
            var thread = new Thread(customer);
            customerThreads.add(thread);
            thread.start();
        }

        for (Thread thread : customerThreads) {
            thread.join();
        }

        List<MenuItem> orders = getPrivateField(cafe, ORDERS_FIELD);
        assertEquals(0, orders.size(), "Все заказы должны быть получены посетителями.");
    }

    @Test
    void testVisitorExitsWhenAllDishesArePrepared() throws InterruptedException {
        var cafe = new Cafe();

        // Имитируем достижение лимита
        for (int i = 0; i < MAX_ORDERS; i++) {
            cafe.addOrder(SPARKLING_WATER);
        }

        var customer = new CafeCustomer(cafe, "3");
        var customerThread = new Thread(customer);

        customerThread.start();
        customerThread.join(ofSeconds((long) SPARKLING_WATER.getSecondsToCook() * MAX_ORDERS + 1));

        assertFalse(customerThread.isAlive());
    }

    @Test
    void testCafeSynchronization() throws InterruptedException {
        var cafe = new Cafe();
        var chef = new Chef(cafe);
        var customer1 = new CafeCustomer(cafe, "1");
        var customer2 = new CafeCustomer(cafe, "2");

        var chefThread = new Thread(chef);
        var c1Thread = new Thread(customer1);
        var c2Thread = new Thread(customer2);

        chefThread.start();
        c1Thread.start();
        c2Thread.start();

        chefThread.join();
        c1Thread.join();
        c2Thread.join();

        AtomicInteger counter = getPrivateField(cafe, ORDER_COUNTER_FIELD);
        assertEquals(MAX_ORDERS, counter.get());
        assertTrue(cafe.isFinished());
    }

    @Test
    void testCafeProperShutdown() throws InterruptedException {
        var cafe = new Cafe();
        var chef = new Chef(cafe);

        var customerThreads = new ArrayList<Thread>();
        for (int i = 1; i <= 3; i++) {
            var customer = new CafeCustomer(cafe, String.valueOf(i));
            var thread = new Thread(customer);
            customerThreads.add(thread);
            thread.start();
        }

        var chefThread = new Thread(chef);
        chefThread.start();

        chefThread.join();
        for (Thread thread : customerThreads) {
            thread.join(2000);
            assertFalse(thread.isAlive());
        }
    }

    @Test
    void testInterruptionHandling() throws InterruptedException {
        var cafe = new Cafe();
        var customer = new CafeCustomer(cafe, "4");
        var customerThread = new Thread(customer);

        customerThread.start();
        Thread.sleep(500);
        customerThread.interrupt();
        customerThread.join(1000);

        assertFalse(customerThread.isAlive());
    }
}
