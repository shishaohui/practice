package com.ssh.service.practice.controller;

import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.domain.Student;
import com.ssh.service.practice.service.TestService;
import com.ssh.service.practice.validation.TransParam;
import javax.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

	@Autowired
	TestService testService;

	//aop暴露代理对象 实现service内部调用方法 事物管理
	@PostMapping(value = "/test/aop")
	public HttpResult<Student> testAopContext(@Validated(TransParam.class) @RequestBody Student student) throws SystemException {
		testService.testAopContext(student);
		return HttpResult.OK(student);
	}

	//手动新建事务 实现service内部调用方法 事物管理
	@PostMapping(value = "/test/transaction")
	public HttpResult<Student> testTransaction(@Validated(TransParam.class) @RequestBody Student student) throws SystemException {
		testService.testTransaction(student);
		return HttpResult.OK(student);
	}

}
