package com.rikkatheworld.thread_demo.thread_interaction;

import com.rikkatheworld.thread_demo.thread.TestDemo;

/**
 * sleep / wait 的区别:
 * (1) wait线程需要 notify()/notifyAll() 来唤醒, sleep在指定时候线程唤醒
 * (2) wait是Object方法，  sleep是Thread方法， 这是因为调用notify的线程和调用wait线程不是同一个，所以得用共用的方法
 * (3) wait/notify/notifyAll 必须要放在 synchronized修饰的方法里，这是因为他们修改的是共享资源，如果不是共享资源那也没有必要使用wait/notify了
 *
 * 下面的例子，是有一个线程先打印字符串，再开一个线程初始化字符串，因为一开始字符串是空的，所以打印为null
 * 除了sleep方法外，还可以使用 wait/notify
 * 在打印的时候，先while循环判断字符串是否为null，如果为null，则调用wait()挂起线程
 * 在初始化成功后，调用 notify()唤醒正在等待线程池的第一个线程，或则调用 notifyAll() 来唤醒所有等待的线程
 *
 *
 * {@link Thread.join()}: 可以将一个线程插在自己线程间。 假如 A线程里面调用了B.join() 它就会经历：
 * （1）A执行，调用B.join()
 * （2）B执行，执行wait()
 * （3）B结束，执行notify()
 * （4）A唤醒，继续执行下面代码
 *
 *
 * {@link Thread.yield()}： 因为线程是由优先级的，如果一个线程调用了 yield()，那么他就会调用wait，唤醒等待线程池的下一个优先级更高的线程
 * 调用 yield()的线程会插入到后面去，但是插入到哪个具体的位置，是由操作系统来决定的，程序本身是不知道的。
 */
public class WaitDemo implements TestDemo {
    private String sharedString;

    private synchronized void initString() {
        sharedString = "rikka";
        notify();   // 也可以 notifyAll()
    }

    private synchronized void printString() {
        while (sharedString == null) {   // 不能写成if，因为不确定是被什么原因唤醒的
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("String:" + sharedString);
    }

    @Override
    public void runTest() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printString();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initString();
            }
        });
        thread2.start();
    }
}
