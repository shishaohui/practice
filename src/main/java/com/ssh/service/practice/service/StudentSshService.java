package com.ssh.service.practice.service;

import com.ssh.service.practice.digibig.AbstractPracticeService;
import com.ssh.service.practice.domain.Student;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentSshService extends AbstractPracticeService<Student> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;

	@Autowired
	public StudentSshService() {
		super(Student.class);
	}

	@Override
	protected void postGet(Student data) {
		logger.debug("postGet 执行");
	}
}
