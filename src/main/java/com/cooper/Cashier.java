package com.cooper;

import java.util.Scanner;

public class Cashier {

    private final Scanner scanner = new Scanner(System.in);

    public String readOrderInput() {
        return scanner.nextLine().trim();
    }

    public Order createOrder(final String orderInput) {
        String[] orderParams = orderInput.split(":");
        int drinkId = Integer.parseInt(orderParams[0]);
        int orderQuantity = Integer.parseInt(orderParams[1]);
        return new Order(DrinkMenu.valueOf(drinkId), orderQuantity);
    }
}
