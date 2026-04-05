package ru.vtv.hw.practical.javabeans;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TWO;
import static org.junit.jupiter.api.Assertions.*;
import static ru.vtv.hw.practical.javabeans.CoffeeStrength.LIGHT;
import static ru.vtv.hw.practical.javabeans.CoffeeStrength.STRONG;
import static ru.vtv.hw.practical.javabeans.PastryFeature.ARTISANAL;
import static ru.vtv.hw.practical.javabeans.Size.*;
import static ru.vtv.hw.practical.javabeans.TeaType.BLACK;
import static ru.vtv.hw.practical.javabeans.TeaType.GREEN;

@Slf4j
public class CoffeeShopTest {
    private CoffeeShop coffeeShop;

    private static final String STRONG_COFFEE_NAME = "Крепкий кофе";
    private static final String BLACK_TEA_NAME = "Чёрный чай";
    private static final String CINNAMON_BUN_NAME = "Булочка с корицей";
    private static final String GREEN_TEA_NAME = "Зелёный чай";

    private static final BigDecimal STRONG_COFFEE_PRICE = BigDecimal.valueOf(350);
    private static final BigDecimal BLACK_TEA_PRICE = BigDecimal.valueOf(300);
    private static final BigDecimal CINNAMON_BUN_PRICE = BigDecimal.valueOf(250);

    private static final MenuItem STRONG_COFFEE = Coffee.builder()
            .name(STRONG_COFFEE_NAME)
            .price(STRONG_COFFEE_PRICE)
            .strength(STRONG)
            .build();
    private static final MenuItem BLACK_TEA = Tea.builder()
            .name(BLACK_TEA_NAME)
            .price(BLACK_TEA_PRICE)
            .type(BLACK)
            .build();
    private static final MenuItem CINNAMON_BUN = Pastry.builder()
            .name(CINNAMON_BUN_NAME)
            .price(CINNAMON_BUN_PRICE)
            .feature(ARTISANAL)
            .build();
    private static final MenuItem GREEN_TEA = Tea.builder()
            .name(GREEN_TEA_NAME)
            .price(BigDecimal.valueOf(300))
            .type(GREEN)
            .build();

    @BeforeEach
    void initialSetup() {
        coffeeShop = new CoffeeShop();

        coffeeShop.addItem(STRONG_COFFEE);
        coffeeShop.addItem(BLACK_TEA);
        coffeeShop.addItem(CINNAMON_BUN);
    }

    @Test
    void addItem_ValidItem_ShouldAddToMenu() {
        coffeeShop.addItem(GREEN_TEA);

        assertTrue(coffeeShop.getMenu().containsKey(GREEN_TEA_NAME));
        assertEquals(GREEN_TEA, coffeeShop.getMenu().get(GREEN_TEA_NAME));
    }

    @Test
    void addItem_NullItem_ShouldThrowException() {
        var exception = assertThrows(MenuItemException.class,
                () -> coffeeShop.addItem(null),
                "Добавление пустого пункта меню должно вызывать ошибку!");
        assertTrue(exception.getMessage().contains("Impossible to add empty MenuItem!"));
    }

    @Test
    void addItem_NullName_ShouldThrowException() {
        var invalidItem = Coffee.builder().name(null).build();

        var exception = assertThrows(MenuItemException.class,
                () -> coffeeShop.addItem(invalidItem),
                "Добавление пункта меню с пустым именем - должно вызывать ошибку!");
        assertTrue(exception.getMessage().contains("Impossible to process MenuItem without name!"));
    }

    @Test
    void addItem_EmptyName_ShouldThrowException() {
        var invalidItem = Coffee.builder().name("").build();

        var exception = assertThrows(MenuItemException.class,
                () -> coffeeShop.addItem(invalidItem),
                "Добавление пункта меню с пустым именем - должно вызывать ошибку!");
        assertTrue(exception.getMessage().contains("Impossible to process MenuItem without name!"));
    }

    @Test
    void addItem_PriceIsNull_ShouldThrowException() {
        var invalidItem = Coffee.builder()
                .name("Дешёвый кофе")
                .price(null)
                .strength(LIGHT)
                .build();

        var exception = assertThrows(MenuItemException.class,
                () -> coffeeShop.addItem(invalidItem),
                "Добавление пункта меню с ценой = 0 должно вызывать ошибку!");
        assertTrue(exception.getMessage().contains("Invalid price for menuItem"));
    }

    @Test
    void addItem_PriceEqualToZero_ShouldThrowException() {
        var invalidItem = Coffee.builder()
                .name("Дешёвый кофе")
                .price(BigDecimal.ZERO)
                .strength(LIGHT)
                .build();

        var exception = assertThrows(MenuItemException.class,
                () -> coffeeShop.addItem(invalidItem),
                "Добавление пункта меню с ценой = 0 должно вызывать ошибку!");
        assertTrue(exception.getMessage().contains("Invalid price for menuItem"));
    }

    @Test
    void addItem_PriceLessThanZero_ShouldThrowException() {
        var invalidItem = Coffee.builder()
                .name("Дешёвый кофе")
                .price(BigDecimal.valueOf(-10))
                .strength(LIGHT)
                .build();

        var exception = assertThrows(MenuItemException.class,
                () -> coffeeShop.addItem(invalidItem),
                "Добавление пункта меню с ценой < 0 должно вызывать ошибку!");
        assertTrue(exception.getMessage().contains("Invalid price for menuItem"));
    }

    @Test
    void removeItem_ValidName_ShouldRemoveFromMenu() {
        coffeeShop.removeItem(STRONG_COFFEE);

        assertFalse(coffeeShop.getMenu().containsKey(STRONG_COFFEE_NAME));
    }

    @Test
    void removeItem_NullItem_ShouldThrowException() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> coffeeShop.removeItem(null),
                "Удаление пустого пункта меню должно вызывать ошибку");
        assertTrue(exception.getMessage().contains("MenuItem not found!"));
    }

    @Test
    void removeItem_NullName_ShouldThrowException() {
        var invalidItem = Coffee.builder().name(null).build();

        var exception = assertThrows(IllegalArgumentException.class,
                () -> coffeeShop.removeItem(invalidItem),
                "Удаление пункта меню с пустым именем должно вызывать ошибку");
        assertTrue(exception.getMessage().contains("Impossible to process MenuItem without name!"));
    }

    @Test
    void removeItem_EmptyName_ShouldThrowException() {
        var invalidItem = Coffee.builder().name("").build();

        var exception = assertThrows(IllegalArgumentException.class,
                () -> coffeeShop.removeItem(invalidItem),
                "Удаление пункта меню с пустым именем должно вызывать ошибку");
        assertTrue(exception.getMessage().contains("Impossible to process MenuItem without name!"));
    }

    @Test
    void removeItem_NonExistentName_ShouldThrowException() {
        var exception = assertThrows(MenuItemException.class,
                () -> coffeeShop.removeItem(GREEN_TEA),
                "Удаление несуществующего пункта должно вызывать ошибку!");
        assertTrue(exception.getMessage().contains("MenuItem not found!"));
    }

    @Test
    void addOrder_ValidPreparableWithSize_ShouldCalculateCorrectPrice() {
        var size = SMALL;
        var count = 2;

        var expectedPrice = STRONG_COFFEE_PRICE
                .multiply(BigDecimal.valueOf(size.getMultiplier()))
                .multiply(BigDecimal.valueOf(count));

        var actualPrice = coffeeShop.addOrder(STRONG_COFFEE, size, count);
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void addOrder_ValidPastry_ShouldCalculateCorrectPrice() {
        var count = 3;

        var expectedPrice = CINNAMON_BUN_PRICE.multiply(BigDecimal.valueOf(count));

        var actualPrice =coffeeShop.addOrder(CINNAMON_BUN, null, count);
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void addOrder_PreparableWithNullSize_ShouldUseMediumSize() {
        var actualPrice = coffeeShop.addOrder(BLACK_TEA, null, 1);
        assertEquals(0, BLACK_TEA_PRICE.compareTo(actualPrice));
    }
    @Test
    void addOrder_ItemNotInMenu_ShouldThrowException() {
        var exception = assertThrows(OrderException.class,
                () -> coffeeShop.addOrder(GREEN_TEA, MEDIUM, 1));
        assertEquals("Order contains items not existing in menu!", exception.getMessage());
    }

    @Test
    void addOrder_ZeroCount_ShouldThrowException() {
        var exception = assertThrows(OrderException.class,
                () -> coffeeShop.addOrder(STRONG_COFFEE, MEDIUM, 0));
        assertEquals("Invalid count for order: 0!", exception.getMessage());
    }

    @Test
    void addOrder_NegativeCount_ShouldThrowException() {
        var exception = assertThrows(OrderException.class,
                () -> coffeeShop.addOrder(STRONG_COFFEE, MEDIUM, -1));
        assertEquals("Invalid count for order: -1!", exception.getMessage());
    }

    @Test
    void addOrder_LargeSize_ShouldApplyCorrectMultiplier() {
        CoffeeShop.CoffeeShopStats.reset();

        var size = LARGE;
        var count = 1;

        var expectedPrice = BLACK_TEA_PRICE
                .multiply(BigDecimal.valueOf(size.getMultiplier()));

        var actualPrice = coffeeShop.addOrder(BLACK_TEA, size, count);
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void addOrder_MultipleOrders_ShouldAccumulateTotal() {
        CoffeeShop.CoffeeShopStats.reset();

        coffeeShop.addOrder(STRONG_COFFEE, SMALL, 2);
        var firstOrderExpectedPrice = STRONG_COFFEE_PRICE
                .multiply(BigDecimal.valueOf(SMALL.getMultiplier())
                .multiply(TWO));

        coffeeShop.addOrder(CINNAMON_BUN, null, 1);
        var secondOrderExpectedPrice = CINNAMON_BUN_PRICE.multiply(ONE);

        coffeeShop.addOrder(BLACK_TEA, LARGE, 1);
        var thirdOrderExpectedPrice = BLACK_TEA_PRICE
                .multiply(BigDecimal.valueOf(LARGE.getMultiplier())
                .multiply(ONE));

        var expectedTotal = firstOrderExpectedPrice
                .add(secondOrderExpectedPrice)
                .add(thirdOrderExpectedPrice);

        assertEquals(3, CoffeeShop.CoffeeShopStats.getTotalOrders());
        assertEquals(0, expectedTotal.compareTo(CoffeeShop.CoffeeShopStats.getTotalRevenue()));
    }

    @Test
    void addOrder_SameItemMultipleTimes_ShouldAccumulateCorrectly() {
        CoffeeShop.CoffeeShopStats.reset();

        coffeeShop.addOrder(STRONG_COFFEE, MEDIUM, 1);
        coffeeShop.addOrder(STRONG_COFFEE, MEDIUM, 2);

        var expectedTotal = STRONG_COFFEE_PRICE.multiply(BigDecimal.valueOf(3));
        assertEquals(2, CoffeeShop.CoffeeShopStats.getTotalOrders());
        assertEquals(0, expectedTotal.compareTo(CoffeeShop.CoffeeShopStats.getTotalRevenue()));
    }
}
