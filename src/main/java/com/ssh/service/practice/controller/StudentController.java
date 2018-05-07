package com.ssh.service.practice.controller;

import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.domain.Student;
import com.ssh.service.practice.service.StudentService;
import com.ssh.service.practice.validation.TransParam;
import javax.transaction.SystemException;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/student", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping(value = "/add")
	public HttpResult<Student> add(@Validated(TransParam.class) @RequestBody Student student) {
		return HttpResult.OK(studentService.add(student));
	}

	@GetMapping(value = "/get")
	public HttpResult<Student> get(@NotNull @RequestParam("id") Integer id) {
		return HttpResult.OK(studentService.get(id));
	}

	@PostMapping(value = "/update")
	public HttpResult<Student> update(@RequestBody Student student) {
		return HttpResult.OK(studentService.update(student));
	}

	@PostMapping(value = "/test")
	public HttpResult<Student> test(@Validated(TransParam.class) @RequestBody Student student) throws SystemException {
		studentService.test(student);
		return HttpResult.OK(student);
	}

}
