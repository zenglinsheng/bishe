package com.zls.bishe.common;

import com.zls.bishe.exception.BusException;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Threads {

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4 , makeThreadName("default-executor-thread"));

    private static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(makeThreadName("default-scheduled-thread"));

    public static ThreadFactory makeThreadName(final String name) {
        return new ThreadFactory() {
            private AtomicLong count = new AtomicLong(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("%s-%d", name, count.incrementAndGet()));
            }
        };
    }

    public static Future<?> dispatch(Runnable runnable) {
        return executorService.submit(runnable);
    }

    public static <T> Future<T> dispatch(Callable<T> callable) {
        return executorService.submit(callable);
    }

    public static void delay(final Runnable runnable, long delaySecond) {
        scheduledExecutorService.schedule(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                runnable.run();
                return null;
            }
        }, delaySecond, TimeUnit.SECONDS);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return executorService.invokeAll(tasks);
    }

    public static <T> T get(Future<T> future) {
        try {
            return future.get();
        }catch (Exception e) {
            throw new BusException(e);
        }
    }

    public static void delay(Runnable runnable) {
    }
}
