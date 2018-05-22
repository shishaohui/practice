package com.ssh.service.practice.service;

import com.ssh.service.practice.domain.Student;
import com.ssh.service.practice.repository.StudentRepository;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class StudentService {

	@Autowired
	EntityManager entityManager;
	@Autowired
	StudentRepository repository;
	@Autowired
	PlatformTransactionManager transactionManager;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public Student add(Student student){
		Assert.isTrue(Objects.nonNull(student),"学生信息为空！");
		repository.saveAndFlush(student);
		((StudentService) AopContext.currentProxy()).update(student);
		return student;
	}

	public Student get(Integer id){
		Assert.isTrue(id>0,"id不能为空且必须大于0");
		Student student = repository.getOne(id);
		Assert.isTrue(Objects.nonNull(student),"查询信息不存在");
		entityManager.detach(student);
		return student;
	}

	@Async
	@Transactional
	public Student update(Student student) {
		Assert.isTrue(student.getId()>0,"id不能为空且必须大于0");
		return repository.saveAndFlush(student);
	}


}
