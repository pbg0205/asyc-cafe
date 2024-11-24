package com.cooper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    private final Cashier cashier;
    private final WaitingBoard<Order> orderWaitingBoard;
    private final WaitingBoard<SingleOrder> singleOrderWaitingBoard;
    private final Manager manager;
    private final DashBoard dashBoard;
    private final Barista barista;

    public App(
            Cashier cashier,
            WaitingBoard<Order> orderWaitingBoard,
            WaitingBoard<SingleOrder> singleOrderWaitingBoard,
            Manager manager,
            DashBoard dashBoard,
            Barista barista
    ) {
        this.cashier = cashier;
        this.orderWaitingBoard = orderWaitingBoard;
        this.singleOrderWaitingBoard = singleOrderWaitingBoard;
        this.manager = manager;
        this.dashBoard = dashBoard;
        this.barista = barista;
    }

    public void run() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ExecutorService orderInputExecutor = Executors.newSingleThreadExecutor();
        ExecutorService coffeeMakeExecutor = Executors.newFixedThreadPool(2, new BaristaThreadFactory());

        // 매니저가 1초마다 주문 확인
        scheduler.scheduleAtFixedRate(this::checkWaitingOrder, 0, 1, TimeUnit.SECONDS);
        orderInputExecutor.execute(this::acceptOrder);
        
        // 바리스타 2명이 동시에 커피 제조
        for (int i = 0; i < 2; i++) {
            coffeeMakeExecutor.execute(this::makeCoffee);
        }

        try {
            orderInputExecutor.shutdown();
            orderInputExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            scheduler.shutdown();
            coffeeMakeExecutor.shutdown();

            scheduler.awaitTermination(5, TimeUnit.SECONDS);
            coffeeMakeExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void makeCoffee() {
        while (true) {
            SingleOrder singleOrder = singleOrderWaitingBoard.poll();
            if (singleOrder != null) {
                barista.makeCoffee(singleOrder, dashBoard);
            }
            
            try {
                Thread.sleep(100); // 폴링 간격 조절
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void acceptOrder() {
        while (true) {
            String input = cashier.readOrderInput();
            if (input.equals("exit")) System.exit(0);

            Order order = cashier.createOrder(input);
            orderWaitingBoard.add(order);
        }
    }

    private void checkWaitingOrder() {
        Order order = orderWaitingBoard.poll();
        if (order != null) {
            List<SingleOrder> singleOrders = manager.processOrder(order);
            singleOrders.forEach(singleOrderWaitingBoard::add);
        }
    }
}
