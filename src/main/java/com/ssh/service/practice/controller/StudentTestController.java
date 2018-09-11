package com.ssh.service.practice.controller;

import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.domain.Student;
import com.ssh.service.practice.service.StudentTestService;
import com.ssh.service.practice.validation.TransParam;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/student/test", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class StudentTestController {

	@Autowired
	StudentTestService service;

	@PostMapping(value = "/add")
	public HttpResult<Student> add(@Validated(TransParam.class) @RequestBody Student student) {
		return HttpResult.OK(service.add(student));
	}

	@GetMapping(value = "/get")
	public HttpResult<Student> get(@NotNull @RequestParam("id") Integer id) {
		Student student = service.get(id);
		return HttpResult.OK(student);
	}
}
