package com.rikkatheworld.thread_demo.thread_interaction;

import com.rikkatheworld.thread_demo.thread.TestDemo;

/**
 * {@link Thread.stop()}  使用stop来终止线程，它的做法跟断电一样，直接掐断线程。所以它的结果是不可靠的，因为计算结果不可靠，所以弃用
 * {@link Thread.interrupt()} 使用interrupt来打断线程，它标记线程为中断状态。 它会在一个合适的位置终止自己， 它是一种温和版本的中断线程
 * 不过他需要配合  isInterrupted()一起使用:
 * run() {
 * if(Thread.interrupted()){  // 或者 if(isInterrupted)
 * return;
 * }
 * ...
 * }
 * 所以 interrupt必须配合使用。
 * 所以也可以看出 InterruptedException这个异常， 比如在子线程sleep的时候，外部线程可能会调用子线程的interrupt，这个时候子线程就会抛出 InterruptedException异常
 * 所以需要在InterruptedException做收尾的工作，如果不做的话，它的终止状态就变成false了，子线程就会继续执行不会被杀掉（因为一般情况下这个异常处理里面我们不会做任何操作)
 */
public class ThreadInteractionDemo implements TestDemo {
    @Override
    public void runTest() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1e9; i++) {
                    if (isInterrupted()) {
                        return;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  // 如果外面调用了interrupt，子线程这个时候还在sleep，那么就会抛出这个异常
                        //如果这里不return，那么线程状态的interrupted就置为false了
                        return;
                    }
                    System.out.println("number:" + i);
                }
            }
        };
        thread.start();
        try {
            Thread.sleep(500);  // 主线程睡500ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
