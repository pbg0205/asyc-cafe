package com.cooper;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class WaitingBoard<T> {
    private final Queue<T> waitingQueue;

    public WaitingBoard(Queue<T> waitingQueue) {
        this.waitingQueue = waitingQueue;
    }

    public boolean add(T order) {
        return waitingQueue.add(order);
    }

    public T poll() {
        return waitingQueue.poll();
    }

    public boolean addAll(Collection<T > singleOrders) {
        return waitingQueue.addAll(singleOrders);
    }

    public List<T> getWaitingMenuList() {
        return waitingQueue.stream().toList();
    }

}
