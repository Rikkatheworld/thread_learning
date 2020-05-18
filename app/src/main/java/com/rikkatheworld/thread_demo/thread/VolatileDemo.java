package com.rikkatheworld.thread_demo.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile 保证了数据可见性, 不保证原子性，它是轻量的synchronized
 * 对于基本类型的赋值可以做到原子性
 * 对于复杂对象，或者基本类型的自增减不行。
 */
public class VolatileDemo {

    private volatile int count = 0;

    /**
     * 不能保证操作成功，因为 count++ ->  count = count + 1 ，这实际上是两个操作
     */
    private void countAdd() {
        count++;
    }

    /**
     * 可以保证数据安全
     */
    private void setCount(int count) {
        this.count = count;
    }

    /**
     * 可以保证原子性
     */
    private int getCount(int count) {
        return count;
    }

    /**
     * 如果需要保证count自增的原子性，需要AtomicInteger修饰
     */
    private AtomicInteger autoCount = new AtomicInteger(0);

    private void autoCountAdd() {
        autoCount.addAndGet(1);
    }

    /**
     * 如果对一个对象加上volatile，如果对它的字段赋值，并不能保证原子性
     * 它只能保证 user = otherUser 这样是安全的 或者 getUser
     * 但是 user.setName(name)是不安全的，因为它不能保证它的属性
     */
//    private volatile User user;
//
//    private void setName(String name) {
//        user.setName(name);
//    }
}
