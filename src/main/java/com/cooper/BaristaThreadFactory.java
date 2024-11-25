package com.cooper;

import java.util.concurrent.ThreadFactory;

public class BaristaThreadFactory implements ThreadFactory {

    private static final String NAME_PREFIX = "바리스타";
    private int threadCount = 0;

    @Override
    public Thread newThread(Runnable r) {
        threadCount++;
        String threadName = NAME_PREFIX + threadCount;
        Thread newThread = new Thread(r, threadName);
        return newThread;
    }
}
