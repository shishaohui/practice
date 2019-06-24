package com.ssh.service.practice.thread;

public class Actor extends Thread {
	public void run() {
		System.out.println(getName() + "是一个演员");
		int count = 0;
		boolean keepRunning = true;
		while (keepRunning) {
			System.out.println(getName() + "登台演出" + (++count));
			if(count%10 == 0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (count == 100) {
				keepRunning = false;
			}
		}
		System.out.println(getName() + "演出结束");
	}

	public static void main(String[] args) {
		Thread actor = new Actor();
		actor.setName("Mr.Thread");
		actor.start();

		Actress actressMs = new Actress();
		Thread actress = new Thread(actressMs,"Ms.Runnable");
		actress.start();
	}
}

//同一个文件可以有多个class类 但是只能有一个public类
class Actress implements Runnable {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+ "是一个演员");
		int count = 0;
		boolean keepRunning = true;
		while (keepRunning) {
			System.out.println(Thread.currentThread().getName() + "登台演出" + (++count));
			if(count%10 == 0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (count == 100) {
				keepRunning = false;
			}
		}
		System.out.println(Thread.currentThread().getName() + "演出结束");
	}
}

