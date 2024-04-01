
package com.mycompany.pr1_14;

public class Pr1_14 {
    private static int currentThread = 0;
    private static final Object monitor = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Worker(0));
        Thread t2 = new Thread(new Worker(1));
        Thread t3 = new Thread(new Worker(2));

        t1.start();
        t2.start();
        t3.start();
    }

    static class Worker implements Runnable {
        private int id;

        public Worker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized(monitor) {
                        while (currentThread != id) {
                            monitor.wait();
                        }
                        System.out.println("Thread-" + id);
                        currentThread = (currentThread + 1) % 3;
                        monitor.notifyAll();
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}