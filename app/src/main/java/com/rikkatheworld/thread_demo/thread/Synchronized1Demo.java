package com.rikkatheworld.thread_demo.thread;

/**
 * 两个线程同时for循环对x、y同时赋值, 在赋值过程中会判断x和y是否相等
 * <p>
 * 因为CPU时间片的关系，两个线程的工作时间应该是一样的，理论上不会出现不相等的情况
 * 但是有种情况会导致：
 * CPU切换线程并不是按方法执行完没有去切，它在代码中也可以切，例如
 * (1) A: x = 9  ---切线程   此时主线程 x = 9 y = 8
 * (2) B: ... x = 12, y =12 --切线程  此时主线程 x = 12 y = 12
 * (2) A: y = 9  此时主线程 x = 12, y = 9 导致x != y
 * 导致的原因是线程切换是可能会出现在代码间，我们控制不了
 * <p>
 * 第一层synchronized理解：
 * 假如此时给 count()加上 synchronized修饰， 那么在第(1)步A线程还没有执行完方法，没有释放锁，就切线程，第(2)步B线程拿到count方法后，它发现该方法被A锁住，执行不了，就还给A线程
 */
public class Synchronized1Demo implements TestDemo {
    private int x = 0;
    private int y = 0;

    private void count(int newValue) {
        x = newValue;
        y = newValue;
        if (x != y) {
            System.out.println("x:" + x + " y:" + y);
        }
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1e9; i++) {
                    count(i);
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1e9; i++) {
                    count(i);
                }
            }
        }.start();
    }
}
