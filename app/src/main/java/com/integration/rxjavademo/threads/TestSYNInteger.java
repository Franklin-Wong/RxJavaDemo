package com.integration.rxjavademo.threads;


import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/7/28 - 12:02
 * @Description com.integration.rxjavademo
 */
public class TestSYNInteger {

    public final static ThreadPoolExecutor poolExecutor =
            new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES,
                    new LinkedBlockingDeque<>());


    public static void main(String[] args) {

        Worker worker = new Worker(1);
        for (int i = 0; i < 5; i++) {
            new Thread(worker).start();
        }

    }

    private static class Worker implements Runnable {

        private Integer mInteger;

        public Worker(Integer integer) {
            mInteger = integer;
        }

        @Override
        public void run() {

            synchronized (mInteger) {
                Thread thread = Thread.currentThread();
                mInteger++;
                System.out.println(thread.getName() + " --- i = " + mInteger + "--@"
                        + System.identityHashCode(mInteger));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread = " + thread.getName() + mInteger + "-@"
                        + System.identityHashCode(mInteger));


            }


        }
    }

}
