package com.cooper;

public class SingleOrder {

    private final DrinkMenu drinkMenu;
    private OrderStatus orderStatus;

    public SingleOrder(DrinkMenu drinkMenu, OrderStatus orderStatus) {
        this.drinkMenu = drinkMenu;
        this.orderStatus = orderStatus;
    }

    public DrinkMenu getDrinkMenu() {
        return drinkMenu;
    }

    public void complete() {
        this.orderStatus =OrderStatus.COMPLETED;
    }

    public void inProgress() {
        this.orderStatus = OrderStatus.IN_PROGRESS;
    }
}
