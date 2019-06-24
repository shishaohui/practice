package com.ssh.service.practice.view;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedToLock {
	Lock lock = new ReentrantLock();
	public synchronized void method1() {
		System.out.println("这是一个Synchronized锁");
	}

	public void method2(){
		lock.lock();
		try{
			System.out.println("这是一个Lock锁");
		}catch(Exception e){
			throw e;
		}finally{
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		SynchronizedToLock s = new SynchronizedToLock();
		s.method2();
		s.method1();

	}
}
