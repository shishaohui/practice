package com.ssh.service.practice.controller;

import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.domain.Student;
import com.ssh.service.practice.service.StudentDigibigService;
import com.ssh.service.practice.service.StudentSshService;
import com.ssh.service.practice.validation.TransParam;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/student/test", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class StudentTestController {

	@Autowired
	StudentDigibigService service;
	@Autowired
	StudentSshService studentSshService;

	@PostMapping(value = "/add")
	public HttpResult<Student> add(@Validated(TransParam.class) @RequestBody Student student) {
		return HttpResult.OK(service.add(student));
	}

	@GetMapping(value = "/get")
	public HttpResult<Student> get(@NotNull @RequestParam("id") Integer id) {
		Student student = service.get(id);
		return HttpResult.OK(student);
	}

	@GetMapping(value = "/get/test")
	public HttpResult<Student> getTest(@NotNull @RequestParam("id") Integer id) {
		Student student = service.getTest(id);
		return HttpResult.OK(student);
	}

	@GetMapping(value = "/get/ssh")
	public HttpResult<Student> getSsh(@NotNull @RequestParam("id") Integer id) {
		Student student = studentSshService.get(id);
		return HttpResult.OK(student);
	}

	@PostMapping(value = "/list/ssh",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpResult<List> listSsh(@RequestBody Student data) {
		return HttpResult.OK(studentSshService.list(data));
	}

	@PostMapping(value = "/update/ssh",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpResult<Student> update(@RequestBody Student data) {
		return HttpResult.OK(studentSshService.updateSelective(data));
	}

	@RequestMapping(value = "/delete/ssh", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method = {RequestMethod.GET, RequestMethod.POST})
	public HttpResult<Integer> delete(@RequestParam(required = false) Integer id,@RequestParam(required = false) List<Integer> ids) {
		Integer result = 0;
		if (Objects.nonNull(id)) {
			result = studentSshService.delete(id);
		} else if(!CollectionUtils.isEmpty(ids)){
			result = studentSshService.delete(ids);
		}
		return HttpResult.OK(result);
	}
}
