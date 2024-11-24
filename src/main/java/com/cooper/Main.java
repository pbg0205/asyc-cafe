package com.cooper;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        // 필요한 컴포넌트들 초기화
        DashBoard dashBoard = new DashBoard();
        Barista barista = new Barista();
        Cashier cashier = new Cashier();
        Manager manager = new Manager();
        
        // 대기열 생성
        Queue<Order> orderQueue = new LinkedBlockingQueue<>();
        Queue<SingleOrder> singleOrderQueue = new LinkedBlockingQueue<>();
        
        // WaitingBoard 초기화
        WaitingBoard<Order> orderWaitingBoard = new WaitingBoard<>(orderQueue);
        WaitingBoard<SingleOrder> singleOrderWaitingBoard = new WaitingBoard<>(singleOrderQueue);

        // App 생성 및 실행
        App app = new App(
            cashier,
            orderWaitingBoard,
            singleOrderWaitingBoard,
            manager,
            dashBoard,
            barista
        );

        // 메뉴 안내 출력
        System.out.println("=== 메뉴 ===");
        System.out.println("1. 아메리카노 (3초)");
        System.out.println("2. 카페라떼 (5초)");
        System.out.println("3. 프라푸치노 (10초)");
        System.out.println("주문 형식: [메뉴번호]:[수량] (예: 1:2)");
        System.out.println("종료: exit");
        System.out.println("==========\n");

        // 애플리케이션 실행
        app.run();
    }
}
