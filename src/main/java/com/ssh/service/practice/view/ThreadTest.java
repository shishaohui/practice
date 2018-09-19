package com.ssh.service.practice.view;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ThreadTest {

	@Transactional
	public void test() {
		try {
			System.out.println("service内部事务,等待5秒");
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				Thread thread = Thread.currentThread();
				System.out.println("transfer after transaction commit...");
			}
		});
	}
}
