package com.rikkatheworld.thread_demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * ui线程：死循环，所以ui线程不会结束，大的死循环不会导致卡顿，只有里面小的死循环才会导致主线程卡顿
 *
 * 死锁定义：两个线程互相持有对方的moniter，同时又在等待对方的moniter，就会产生死锁
 *
 */
public class Main {
    public static void main(String[] args) {
//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();
//        runSynchronized1Demo();
//        runSynchronized2Demo();
        runSynchronized3Demo();
    }

    private static void runSynchronized3Demo() {
        new Synchronized3Demo().runTest();
    }

    private static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    private static void runSynchronized1Demo() {
        new Synchronized1Demo().runTest();
    }


    /**
     * 相当于有返回值的Runnable
     * 通过Future来获取Callable的返回结果
     * {@link Future.get()}是一个异步方法，所以代码上看起来是没有阻塞的，其实在get的时就是阻塞的，它一直在轮询等待任务完成。
     */
    private static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Finished!";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);
        try {
            String result = future.get();
            System.out.println("result:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 最常用的一种方法，创建ExecutorService
     * {@link ExecutorService.shutdown()} 保守型结束，如果有任务正在执行或者有任务排上号，则不会结束线程
     * {@link ExecutorService.shutdownNow()} 激进型的结束，强制结束线程
     * newCachedThreadPool: 带缓存的线程池，里面线程不固定大小，线程不用自己创建、回收线程。
     * newSingleThreadExecutor: 只有一个线程的执行器
     * newFixedThreadPool: 固定线程池，自动回收，适用于批量操作。
     * newScheduledThreadPool: 指定某个时间执行线程
     * <p>
     * {@link ThreadPoolExecutor(初始线程大小, 最大线程大小, 线程存活（保持时间）, 等待时间单位, java.util.concurrent.BlockingQueue(阻塞队列))} 可以用来自定义线程池
     */
    private static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started");
            }
        };
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
    }

    //线程工厂 对初始化比较方便
    private static void threadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            int count = 0;

            @Override
            public Thread newThread(Runnable r) {
                //统一线程的初始化
                count++;
                return new Thread(r, "Thread-" + count);
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "started!");
            }
        };
        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread1 = factory.newThread(runnable);
        thread1.start();
    }

    private static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started!");
            }
        };
        thread.start();
    }


}