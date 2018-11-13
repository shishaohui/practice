package com.ssh.service.practice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	AsyncExecutor executor;


	public void host(){
		logger.info("主线程开始时间={}",System.currentTimeMillis());
		for (int i = 1;i<=40;i++) {
			executor.executor(i);
		}
		logger.info("主线程结束时间={}",System.currentTimeMillis());
	}

}
