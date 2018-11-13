package com.ssh.service.practice.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncTaskExecutePool implements AsyncConfigurer {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//设置最大线程数
		executor.setMaxPoolSize(50);
		//设置核心线程数
		executor.setCorePoolSize(10);
		//线程池所使用的缓存队列，当缓存队列满了 才会新建新的线程 否则优先使用核心线程
		executor.setQueueCapacity(10);
		executor.setKeepAliveSeconds(300);
		executor.setThreadNamePrefix("sshExecutor-");
		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
/*		!-- AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常 -->
		<!-- CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度 -->
		<!-- DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行 -->
		<!-- DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行 -->*/
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		//初始化线程
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {
			@Override
			public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
				logger.error("==========================" + arg0.getMessage() + "=======================", arg0);
				logger.error("exception method:" + arg1.getName());
			}
		};
	}
}
