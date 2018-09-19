package com.ssh.service.practice.controller;

import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.view.ManualThreadTest;
import com.ssh.service.practice.view.ThreadTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/thread", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ThreadTestController {

	@Autowired
	ThreadTest threadTest;

	@Autowired
	ManualThreadTest manualThreadTest;

	@Transactional
	@GetMapping(value = "/after/commit")
	public HttpResult<Void> afterCommit() {
		threadTest.test();
		System.out.println("controller线程");
		return new HttpResult<>(200, "ok");
	}

	@GetMapping(value = "/manual/transaction")
	public HttpResult<Void> manualTransaction() {
		manualThreadTest.test();
		System.out.println("controller外部事务");
		return new HttpResult<>(200, "ok");
	}
}
