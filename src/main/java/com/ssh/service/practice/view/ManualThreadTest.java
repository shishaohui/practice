package com.ssh.service.practice.view;

import com.ssh.service.practice.exception.BusinessException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 手动开启一个新事物
 */
@Service
public class ManualThreadTest {

	@Autowired
	PlatformTransactionManager transactionManager;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void test() {
		TransactionStatus status = null;
		try {
			DefaultTransactionDefinition transaction = new DefaultTransactionDefinition();
			transaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			status = transactionManager.getTransaction(transaction);
			Thread.sleep(5000L);
			System.out.println("service内部事务,等待5秒");
		} catch (Exception e) {
			if (Objects.nonNull(status)) {
				this.rollBack(status);
			}
			throw new BusinessException("异常", e);
		}
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				Thread thread = Thread.currentThread();
				System.out.println("transfer after transaction commit...");
			}
		});

		this.commit(status);
	}

	private void rollBack(TransactionStatus status) {
		if (Objects.isNull(status)) {
			logger.warn("发现一个空事务");
			return;
		}
		if (status.isCompleted()) {
			return;
		}
		transactionManager.rollback(status);
		logger.debug("transaction rollback");
	}

	private void commit(TransactionStatus status) {
		if (Objects.isNull(status)) {
			logger.warn("发现一个空事务");
			return;
		}
		if (status.isCompleted()) {
			return;
		}
		transactionManager.commit(status);
		logger.debug("transaction commit");
	}
}
