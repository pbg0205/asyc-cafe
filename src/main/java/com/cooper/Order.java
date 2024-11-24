package com.cooper;

import java.util.List;
import java.util.stream.IntStream;

public class Order {
    private final DrinkMenu drinkMenu;
    private final int quantity;

    public Order(DrinkMenu drinkMenu, int quantity) {
        this.drinkMenu = drinkMenu;
        this.quantity = quantity;
    }

    public List<SingleOrder> getSingleOrder() {
        return IntStream.range(0, quantity)
                .mapToObj(i -> new SingleOrder(drinkMenu, OrderStatus.WAITING))
                .toList();
    }

    @Override
    public String toString() {
        return "Order{" +
                "drinkType=" + drinkMenu +
                ", quantity=" + quantity +
                '}';
    }
}
