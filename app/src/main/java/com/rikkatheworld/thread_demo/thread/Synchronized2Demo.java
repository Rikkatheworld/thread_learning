package com.rikkatheworld.thread_demo.thread;

/**
 * 两个线程同时for循环对x自增，每个线程执行1e9次，那么理论上总会有一个线程打印x会是 2e9，但实际上， 基本不可能出现这个情况
 * <p>
 * 跟demo1一样，由于线程切换是不定位置的，而且操作系统在执行机器码时对x++其实并不是一行语句，而是：
 * count() {
 * int tmp = x + 1;  // 1
 * x = tmp;       // 2
 * }
 * <p>
 * 所以就会出现 ：
 * A： tmp = 10 + 1    切换线程，此时 x=10，A线程tmp=11
 * B： tmp=10+1=11, x=11  切换线程，此时x=11
 * A： x = 11       这个时候，A的赋值其实就是重复了
 * <p>
 * 所以在count没有加 synchronized 修饰的情况下， 会出现多处重复赋值的地方
 */
public class Synchronized2Demo implements TestDemo {
    int x = 0;

    private void count() {
        x++;
    }

    @Override
    public void runTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count();
                }
                System.out.println("final x form 1:" + x);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count();
                }
                System.out.println("final x form 2:" + x);
            }
        }).start();
    }
}
