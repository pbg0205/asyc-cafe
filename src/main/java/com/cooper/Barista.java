package com.cooper;

public class Barista {

    public Barista() {
    }

    public void makeCoffee(SingleOrder singleOrder, DashBoard dashBoard) {
        singleOrder.inProgress();
        dashBoard.displayStartMaking(singleOrder);

        try {
            Thread.sleep(singleOrder.getDrinkMenu().getPreparationSeconds().toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        singleOrder.complete();
        dashBoard.displayComplete(singleOrder);
    }
}
