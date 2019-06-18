package com.ssh.service.practice.view;

/**
 *
 */
public class SynchronizedTest implements Runnable {

	static SynchronizedTest instance = new SynchronizedTest();
	static int j = 0;
	@Override
	public void run() {
		synchronized (this) {
			System.out.println("当前线程开始==="+Thread.currentThread().getName());
			for (int i = 0; i < 1000000; i++) {
				j++;
			}
			System.out.println("当前线程结束==="+Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(instance);
		Thread thread2 = new Thread(instance);
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		System.out.println("j====="+j);

	}
}
