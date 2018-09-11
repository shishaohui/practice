package com.ssh.service.practice.digibig;

import com.ssh.service.practice.common.commonenums.CanWrite;
import com.ssh.service.practice.domain.Message;
import com.ssh.service.practice.util.DomainUtil;
import com.ssh.service.practice.validation.New;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public abstract class AbstractService<T> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;
	@Autowired
	Validator validator;
	@Autowired
	DomainUtil domainUtil;

	protected final Class<T> targetClass;
	protected final String targetClassName;
	protected final Message message;

	private Query<T, T, T> queryLoadOne;
	protected final FieldInfo<T> fieldId;
	private QueryBuilder<T> queryBuilder;
	@Autowired
	FieldConditionHelper fieldConditionHelper;
	@Autowired
	CrossConditionHelper crossConditionHelper;

	public AbstractService(Class<T> targetClass) {

		this(targetClass, "id");
	}

	public AbstractService(Class<T> targetClass, String targetKeyName) {
		this.targetClass = targetClass;
		this.targetClassName = ClassUtils.getShortName(targetClass);
		this.message = new Message(this.targetClassName);
		this.fieldId = FieldInfo.extractField(targetClass, targetKeyName);
	}

	@PostConstruct
	private void init(){
		queryBuilder = new QueryBuilder<>(this.entityManager, this.targetClass,
			this.fieldConditionHelper, this.crossConditionHelper);
		this.queryLoadOne = queryBuilder.loadOne();
	}

	protected void preAdd(T data) {

	}

	protected void postAdd(T data) {

	}

	public T add(T data) {
		return add(data, CanWrite.EXTERNAL);
	}

	@Transactional
	public T add(T data, CanWrite... right) {
		if (Objects.isNull(data)) {
			return null;
		}
		this.domainUtil.clear(data, right);
		preAdd(data);
		validate(data, New.class);
		saveAndGet(data, this::postAdd);
		return data;
	}

	@Transactional
	public Collection<T> add(Collection<T> data) {
		return this.add(data, CanWrite.EXTERNAL);
	}

	@Transactional
	public Collection<T> add(Collection<T> data, CanWrite... right) {
		if (Objects.nonNull(data) && data.size() > 0) {
			data = data.stream().filter(Objects::nonNull).collect(Collectors.toList());
			if (data.isEmpty()) {
				return new ArrayList<>();
			} else {
				List<T> result = new ArrayList<>();
				for (T item : data) {
					result.add(this.add(item, right));
				}
				return result;
			}
		} else {
			return new ArrayList<>();
		}
	}

	@Transactional
	public T get(Integer id) {
		return this.get(id, false);
	}

	@Transactional
	public T get(Integer id, boolean failOnNull) {
		Assert.isTrue(id > 0, "id必须大于0");
		T result = this.getInternal(this.idMap(id));
		if (Objects.isNull(result)) {
			if (failOnNull) {
				this.message.getClass();
				throw new IllegalArgumentException("目标对象不存在");
			} else {
				return null;
			}
		} else {
			return result;
		}
	}

	protected Map<String, Object> idMap(Object data) {
		Map<String, Object> para = new HashMap<>();
		if (Objects.nonNull(data)) {
			para.put(this.fieldId.getField().getName(), data);
		}
		return para;
	}

	protected T getInternal(Map<String, Object> query) {
		T result = this.queryLoadOne.generate(query)
									.getExecute()
									.apply(null, null);
		if (Objects.nonNull(result)) {
			this.postGet(result);
			this.flushAndDetach(result);
		}
		return result;
	}

	protected void postGet(T data) {

	}

	@Transactional
	protected void flushAndDetach(T data) {
		if (Objects.nonNull(data)) {
			this.entityManager.flush();
			this.entityManager.detach(data);
		}
	}


	protected void validate(T data, Class... groups) {
		Set<ConstraintViolation<T>> violations = validator.validate(data, groups);
		if (violations.size() > 0) {
			throw new ConstraintViolationException(message.MESSAGE_CONSTRAIN_VIOLATION, violations);
		}
	}

	@Transactional
	protected T saveAndGet(T data, Consumer<T> post) {
		if (Objects.isNull(data)) {
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
