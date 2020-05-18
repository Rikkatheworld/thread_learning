package com.rikkatheworld.thread_demo.thread;

/**
 * 现在程序同时要改x、y和name
 * 如果我们把 synchronized都加在方法上 count、minus、setName，其实是实现了对象锁，这会产生一个现象：
 * A线程在访问count方法，  B线程想要访问setName, 但是此时对象已经被A线程锁住，导致B线程访问不了setName
 * 这是Java的保护设计，它的目的保护资源，所以这么设计是没有问题的，但是对于开发者来说比较困惑，因为 count和setName访问的是不同的资源，这样的话效率不是很低么
 * <p>
 * 那么我们可以在方法里面加锁
 * private void count(int newValue) {
 * synchronized (this) {
 * x = newValue;
 * y = newValue;
 * }
 * }
 * 它和 private synchronized void count().. 的效果是一样的，因为锁的本质是一个用于监控线程的moniter, 如果不设置的话就是默认的锁
 * <p>
 * 所以我们可以用不同的锁来锁住不同的方法的代码块，
 * 假如  count和minus用锁1来监视，  setName用锁2来监视
 * 这样当一个线程在使用count时，它持有锁1, 而另外一个线程想要访问setName时，它并不用考虑锁1，它只用判断锁2有没有被被的线程占用就行了。
 */
public class Synchronized3Demo implements TestDemo {
    private int x = 0;
    private int y = 0;
    private String name;
    private final Object object1 = new Object();
    private final Object object2 = new Object();

    private void count(int newValue) {
        synchronized (object1) {
            x = newValue;
            y = newValue;
        }
    }

    private void minus(int delta) {
        synchronized (object1) {
            x -= delta;
            y -= delta;
        }
    }

    private void setName(String newName) {
        synchronized (object2) {
            name = newName;
        }
    }

    @Override
    public void runTest() {

    }
}
