package ru.vtv.hw.practical.javabeans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.javabeans.MenuItemException.*;
import static ru.vtv.hw.practical.javabeans.OrderException.invalidCountForOrder;
import static ru.vtv.hw.practical.javabeans.OrderException.itemsNotFromMenu;
import static ru.vtv.hw.practical.javabeans.Size.MEDIUM;

@Slf4j
public class CoffeeShop {
    private final Map<String, MenuItem> menuItemMap = new ConcurrentHashMap<>();

    public Map<String, MenuItem> getMenu() {
        return Map.copyOf(menuItemMap);
    }

    public void addItem(MenuItem menuItem) {
        if (isNull(menuItem)) throw noItemToAdd();
        if (isNull(menuItem.getName()) || menuItem.getName().isBlank()) throw emptyName();

        var price = menuItem.getPrice();
        if (isNull(price) || price.compareTo(ZERO) <= 0) throw priceIsNotValid(menuItem);

        menuItemMap.put(menuItem.getName(), menuItem);
    }

    public void removeItem(MenuItem menuItem) {
        if (isNull(menuItem)) throw itemNotFound();
        if (isNull(menuItem.getName()) || menuItem.getName().isBlank()) throw emptyName();
        if (!menuItemMap.containsKey(menuItem.getName())) throw itemNotFound();

        menuItemMap.remove(menuItem.getName());
    }

    public BigDecimal addOrder(MenuItem menuItem, Size size, int count) {
        if (!menuItemMap.containsValue(menuItem)) throw itemsNotFromMenu();
        if (count <= 0) throw invalidCountForOrder(count);
        if (isNull(size)) size = MEDIUM;

        var order = new Order(menuItem, size, count);

        var priceMultiplier = ONE;

        if (order.item instanceof Preparable) {
            priceMultiplier = BigDecimal.valueOf(order.size.getMultiplier());
        }

        var price = order.item.getPrice().multiply(priceMultiplier);
        var orderPrice = price.multiply(BigDecimal.valueOf(order.count));

        if (menuItem instanceof Preparable) ((Preparable) menuItem).prepare();

        log.debug("Order added: {}", order);
        CoffeeShopStats.addOrder(orderPrice);
        return orderPrice;
    }

    @AllArgsConstructor
    private static class Order {
        private MenuItem item;
        private Size size;
        private int count;
    }

    public static class CoffeeShopStats {
        @Getter
        private static int totalOrders = 0;

        @Getter
        private static BigDecimal totalRevenue = ZERO;

        public static void addOrder(BigDecimal price) {
            totalOrders++;
            totalRevenue = totalRevenue.add(price);
            log.debug(getStats());
        }

        public static void reset() {
            totalOrders = 0;
            totalRevenue = BigDecimal.ZERO;
        }

        public static String getStats() {
            return format("Total orders: %d%nTotal revenue: %s", totalOrders, totalRevenue);
        }
    }
}
