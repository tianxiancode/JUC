package com.mashibing.juc.c_028_interview.A1B2C3;


public class T03_00_sync_wait_notify {

    private static volatile boolean t2Started = false;

    //private static CountDownLatch latch = new C(1);

    public static void main(String[] args) {
        final Object o = new Object();



        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        new Thread(()->{
            //latch.await();

            synchronized (o) {

                while(!t2Started) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //

                for(char c : aI) {
                    System.out.print(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                o.notify();
            }
        }, "t1").start();

        new Thread(()->{

            synchronized (o) {
                //latch.countDown()
                for(char c : aC) {
                    System.out.print(c);
                    t2Started = true;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t2").start();
    }
}


