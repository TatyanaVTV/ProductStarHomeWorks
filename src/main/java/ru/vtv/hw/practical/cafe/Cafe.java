package ru.vtv.hw.practical.cafe;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Cafe {
    private final List<MenuItem> orders = new ArrayList<>();
    private static final AtomicInteger ORDER_COUNTER = new AtomicInteger(0);
    public static int MAX_ORDERS = 10;

    public synchronized void addOrder(MenuItem item) {
        if (ORDER_COUNTER.get() >= MAX_ORDERS) {
            return;
        }

        orders.add(item);
        ORDER_COUNTER.incrementAndGet();
        log.info("Повар приготовил {}", item.getRusName());
        notifyAll();
    }

    public synchronized MenuItem takeOrder() {
        while (orders.isEmpty() && ORDER_COUNTER.get() < MAX_ORDERS) {
            try {
                log.info("Еды нет, посетитель {} ждет", Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        if (!orders.isEmpty()) {
            var dish = orders.removeFirst();
            var action = dish.isDrink() ? "выпил" : "съел";
            log.info("Посетитель {} {} {}", Thread.currentThread().getName(), action, dish.getRusName());
            return dish;
        }
        return null;
    }

    public boolean isFinished() {
        return ORDER_COUNTER.get() >= MAX_ORDERS;
    }
}
