package com.ssh.service.practice.service;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncExecutor {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Async
	public void executor(Integer a) {
		logger.info("当前时间={}，当前副线程={},处理数据={}",LocalDateTime.now(),Thread.currentThread().getId(),a);

		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			logger.error("异常，{}",e);
		}
	}
}
