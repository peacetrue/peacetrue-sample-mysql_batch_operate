package com.github.peacetrue.sample.mysql_batch_operate;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TimeUtils {

    /**
     * 记录方法执行耗时
     */
    public static <T> T recordTime(Supplier<T> supplier, Consumer<Long> timeConsumer) {
        long start = System.currentTimeMillis();
        T value = supplier.get();
        timeConsumer.accept(System.currentTimeMillis() - start);
        return value;
    }

    /**
     * @see #recordTime(Supplier, Consumer)
     */
    public static void recordTime(Runnable runnable, Consumer<Long> timeConsumer) {
        recordTime(() -> {
            runnable.run();
            return null;
        }, timeConsumer);
    }

    /**
     * @see #recordTime(Supplier, Consumer)
     */
    public static <T> T printTime(String message, Supplier<T> supplier) {
        return recordTime(supplier, (time) -> {
            System.out.printf("cost %s millis: %s", time, message);
            System.out.println();
        });
    }

    /**
     * @see #recordTime(Supplier, Consumer)
     */
    public static void printTime(String message, Runnable runnable) {
        printTime(message, () -> {
            runnable.run();
            return null;
        });
    }
}
