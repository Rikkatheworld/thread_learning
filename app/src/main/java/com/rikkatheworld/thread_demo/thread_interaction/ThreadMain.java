package com.rikkatheworld.thread_demo.thread_interaction;

/**
 * {@link ThreadLocal}：每个线程都有自己的内存区域，而ThreadLocal就是给每一个线程分配了属于它自己的内存空间
 */
public class ThreadMain {
    public static void main(String[] args) {
//        runThreadInteractionDemo();
        runWaitDemo();
    }

    private static void runWaitDemo() {
        new WaitDemo().runTest();
    }

    private static void runThreadInteractionDemo() {
        new ThreadInteractionDemo().runTest();
    }

}
