package com.rikkatheworld.thread_demo.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Lock lock()/unlock() 可以保证修饰的地方不被别的线程访问，和 synchronized的作用是一样的
 * <p>
 * ReentrantReadWriteLock: 读写锁，
 * ReentrantReadWriteLock.ReadLock: 读锁，不能让别的线程写
 * ReentrantReadWriteLock.WriteLock： 写锁，不能别的线程写也不能别的线程读
 *
 * 使用读写锁的原因是，他比synchronized做的更加精细，区分了读/写操作，便于开发者更高效的开发程序，而 synchronized是读/写都加锁。
 */
public class LockDemo {

    private Lock lock = new ReentrantLock();

    /**
     * 如果在 lock() 和 unlock() 间出现了异常，那么线程将一直持有这个方法,不能被别的线程访问
     * 所以必须在这之间加 try-finally，在finally代码里面写 unlock()
     */
    private void reset() {
        lock.lock();
        try {
            // Do Everything
        } finally {
            lock.unlock();
        }
    }


    /**
     * 一个读写锁的例子:
     * 在 count()执行时， 别的线程既不可以读，也不可以写
     * 在 print()执行时， 别的线程不可以写资源，多个线程可以一起读资源
     */
    int x = 0;
    int y = 0;
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private void count(int newValue) {
        writeLock.lock();
        try {
            x = newValue;
            y = newValue;
        } finally {
            writeLock.unlock();
        }
    }

    private void print() {
        readLock.lock();
        try {
            System.out.println("values: " + "");
        } finally {
            readLock.unlock();
        }
    }


}
