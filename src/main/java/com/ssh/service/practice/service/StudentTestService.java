package com.ssh.service.practice.service;

import com.ssh.service.practice.digibig.AbstractService;
import com.ssh.service.practice.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentTestService extends AbstractService<Student> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public StudentTestService() {
		super(Student.class);
	}

	@Override
	protected void postGet(Student data) {
		logger.debug("postGet 执行");
	}
}
