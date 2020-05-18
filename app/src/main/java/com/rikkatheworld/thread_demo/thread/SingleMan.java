package com.rikkatheworld.thread_demo.thread;

public class SingleMan {
    private static SingleMan instance;

    private SingleMan() {

    }

    /**
     * 对于静态方法加锁，是加上 类本身的moniter
     */
    static SingleMan newInstance() {
        if (instance == null) {
            synchronized (SingleMan.class) {
                if (instance == null) {
                    instance = new SingleMan();
                }
            }
        }
        return instance;
    }
}
