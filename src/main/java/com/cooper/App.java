package com.cooper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
        ExecutorService orderExecutor = Executors.newSingleThreadExecutor();
        ExecutorService singleOrderExecutor = Executors.newSingleThreadExecutor();
        ExecutorService baristaExecutor = Executors.newFixedThreadPool(2, new BaristaThreadFactory());

        // 주문 입력 처리
        CompletableFuture<Void> orderFuture = CompletableFuture.runAsync(this::acceptOrder, orderExecutor);

        // 음료 주분 파싱
        CompletableFuture<Void> singleOrderFuture = CompletableFuture.runAsync(this::transformSingleOrder, singleOrderExecutor);

        // 주문 처리 및 커피 제조
        CompletableFuture<Void> processingFuture = CompletableFuture.runAsync(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                processOrders(baristaExecutor);
                try {
                    Thread.sleep(1000); // 1초마다 대기 주문 확인
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        // 종료 처리
        try {
            orderFuture.join();
        } finally {
            orderExecutor.shutdown();
            baristaExecutor.shutdown();
            try {
                if (!orderExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    orderExecutor.shutdownNow();
                }
                if (!baristaExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    baristaExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void acceptOrder() {
        while (true) {
            String input = cashier.readOrderInput();
            if (input.equals("exit")) {
                System.exit(0);
            }

            Order order = cashier.createOrder(input);
            orderWaitingBoard.add(order);
        }
    }

    private void processOrders(ExecutorService baristaExecutor) {
        SingleOrder singleOrder = singleOrderWaitingBoard.poll();
        if (singleOrder != null) {
            dashBoard.displaySingleOrderWaitingBoard(singleOrderWaitingBoard.getWaitingMenuList());

            CompletableFuture<Void> coffeeMakingTask = CompletableFuture.runAsync(() -> {
                dashBoard.displayStartMaking(singleOrder);
                barista.makeCoffee(singleOrder);
                dashBoard.displayComplete(singleOrder);
            }, baristaExecutor);
        }
    }

    private void transformSingleOrder() {
        while (true) {
            Order order = orderWaitingBoard.poll();
            if (order == null) {
                continue;
            }
            List<SingleOrder> singleOrders = manager.processOrder(order);
            singleOrderWaitingBoard.addAll(singleOrders);
        }
    }
}
