package com.cooper;

public class Barista {

    public Barista() {
    }

    public void makeCoffee(SingleOrder singleOrder) {
        singleOrder.inProgress();

        try {
            Thread.sleep(singleOrder.getDrinkMenu().getPreparationSeconds().toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        singleOrder.complete();
    }
}
