package com.cooper;

import java.util.List;
import java.util.Queue;

public class DashBoard {

    public synchronized void displayStartMaking(SingleOrder singleOrder) {
        System.out.printf("[%s]가 [%s]을(를) 시작합니다.\n", Thread.currentThread().getName(),
                singleOrder.getDrinkMenu().getName());
    }

    public synchronized void displayComplete(SingleOrder singleOrder) {
        System.out.printf("[%s]가 [%s]을(를) 완료했습니다.\n", Thread.currentThread().getName(),
                singleOrder.getDrinkMenu().getName());
    }

    public void displaySingleOrderWaitingBoard(List<SingleOrder> waitingQueue) {
        List<String> waitMenuList = waitingQueue.stream()
                .map(singleOrder -> String.valueOf(singleOrder.getDrinkMenu().getId()))
                .toList();
        System.out.printf("/%s/\n", String.join(",", waitMenuList));
    }
}
