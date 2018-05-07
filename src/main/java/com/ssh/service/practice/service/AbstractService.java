package com.ssh.service.practice.service;

import com.ssh.service.practice.common.commonenums.CanWrite;
import com.ssh.service.practice.domain.Message;
import com.ssh.service.practice.validation.New;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

public abstract class AbstractService<T> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;
	@Autowired
	Validator validator;

	protected final Class<T> targetClass;

	protected final String targetClassName;

	protected final Message message;

	public AbstractService(Class<T> targetClass) {

		this(targetClass,"id");
	}

	public AbstractService(Class<T> targetClass, String targetKeyName) {
		this.targetClass = targetClass;
		this.targetClassName = ClassUtils.getShortName(targetClass);
		this.message = new Message(this.targetClassName);
	}

	protected void preAdd(T data) {

	}

	protected void postAdd(T data) {

	}

	public T add(T data) {
		return add(data, CanWrite.EXTERNAL);
	}

	@Transactional
	public T add(T data,CanWrite... right) {
		if(Objects.isNull(data)){
			return null;
		}
		preAdd(data);
		validate(data, New.class);
		saveAndGet(data, this::postAdd);
		return data;
	}

	protected void validate(T data,Class... groups) {
		Set<ConstraintViolation<T>> violations=  validator.validate(data,groups);
		if(violations.size()>0){
			throw new ConstraintViolationException(message.MESSAGE_CONSTRAIN_VIOLATION,violations);
		}
	}

	@Transactional
	protected T saveAndGet(T data, Consumer<T> post) {
		if(Objects.isNull(data)){
			return null;
		}
		entityManager.persist(data);
		entityManager.flush();
		entityManager.refresh(data);
		post.accept(data);
		entityManager.persist(data);
		entityManager.flush();
		entityManager.refresh(data);
		return data;
	}
}
