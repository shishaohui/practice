package com.ssh.service.practice.service;

import com.ssh.service.practice.digibig.AbstractService;
import com.ssh.service.practice.domain.Student;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class StudentTestService extends AbstractService<Student> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;

	@Autowired
	public StudentTestService() {
		super(Student.class);
	}

	@Override
	protected void postGet(Student data) {
		logger.debug("postGet 执行");
	}

	public Student getTest(Integer id){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(this.targetClass);
		Root root = cq.from(this.targetClass);
		List<Predicate> predicates = new ArrayList<>();
		Predicate predicate = builder.equal(root.get("id"),id);
		predicates.add(predicate);
		if(!CollectionUtils.isEmpty(predicates)){
			cq.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		Student student = entityManager.createQuery(cq).getSingleResult();
		System.out.println("student"+student);
		return student;
	}
}
