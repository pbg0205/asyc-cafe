package com.cooper;

public class DashBoard {

    public void displayStartMaking(SingleOrder singleOrder) {
        System.out.printf("[%s]가 [%s]을(를) 시작합니다.\n", Thread.currentThread().getName(),
                singleOrder.getDrinkMenu().getName());
    }

    public void displayComplete(SingleOrder singleOrder) {
        System.out.printf("[%s]가 [%s]을(를) 완료되었습니다.\n", Thread.currentThread().getName(),
                singleOrder.getDrinkMenu().getName());
    }
}
