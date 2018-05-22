package com.ssh.service.practice.service;

import com.ssh.service.practice.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

@Service
public class TestService {

	@Autowired
	PlatformTransactionManager transactionManager;
	@Autowired
	StudentService studentService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 测试service内部调用 事物生效
	 * @param student
	 */
	public void testAopContext(Student student){
		((TestService) AopContext.currentProxy()).save(student);
	}

	public void testTransaction(Student student){
		TransactionStatus status = null;
		try{
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			status = this.transactionManager.getTransaction(def);
			this.save(student);
		}catch(Exception e){
			//异常事物回滚
			logger.debug(" transaction rollback! ");
			this.transactionManager.rollback(status);
		}finally{
			//提交事物
			logger.debug("事物 commit");
			this.transactionManager.commit(status);
		}

	}

	@Transactional
	public void save(Student student){
		studentService.add(student);
		Assert.isTrue(student.getId()>0,"id不能为空且必须大于0");
		throw new IllegalArgumentException("test");
	}
}
