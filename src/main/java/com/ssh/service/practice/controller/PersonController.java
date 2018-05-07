package com.ssh.service.practice.controller;

import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.common.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PersonController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/get")
	public HttpResult get(@RequestParam("age") Integer age,@RequestParam("name") String name) {
		Assert.isTrue(!StringUtils.isEmpty(name),"name 不能为空!");

		return new HttpResult(HttpStatus.OK, "成功");
	}
}
