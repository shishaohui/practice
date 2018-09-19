package com.ssh.service.practice.digibig;

import com.ssh.service.practice.common.commonenums.CanWrite;
import com.ssh.service.practice.domain.Message;
import com.ssh.service.practice.util.DomainUtil;
import com.ssh.service.practice.validation.New;
import com.ssh.service.practice.validation.Update;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.hibernate.jpa.criteria.predicate.InPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

public abstract class AbstractPracticeService<T> {

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

	public AbstractPracticeService(Class<T> targetClass) {
		this(targetClass, "id");
	}

	public AbstractPracticeService(Class<T> targetClass, String targetKeyName) {
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
		T result = getByQuery(buidSelectQuery(this.idMap(id)));
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

	private Map<String, Object> idMap(Object data) {
		Map<String, Object> para = new HashMap<>();
		if (Objects.nonNull(data)) {
			para.put("id", data);
		}
		return para;
	}

	@Transactional
	public List<T> list(T example) {
		return this.list(example, false);
	}

	@Transactional
	public List<T> list(T example, boolean failOnNull) {
		Map<String, Object> map = new HashMap<>();
		objectToMap(example, map);
		List<T> dataList = this.listByQuery(this.buidSelectQuery(map));
		if (CollectionUtils.isEmpty(dataList)) {
			if (failOnNull) {
				this.message.getClass();
				throw new IllegalArgumentException("目标对象不存在");
			} else {
				return null;
			}
		}
		return dataList;
	}

	@Transactional
	public T updateSelective(T data) {
		T current = this.get(getKeyValue(data),true);
		current = domainUtil.updateSelective(current, data);
		preUpdate(current);
		this.validate(current, Update.class);
		data = this.saveAndGet(current, this::postUpdate);
		return data;
	}

	@Transactional
	public Integer delete(Integer id){
		List<Integer> ids = new ArrayList<>();
		ids.add(id);
		CriteriaUpdate<T> query = this.buidDeleteQuery(ids);
		Integer result = this.updateByquery(query);

		return result;
	}

	@Transactional
	public Integer delete(List<Integer> ids){
		CriteriaUpdate<T> query = this.buidDeleteQuery(ids);
		return this.updateByquery(query);
	}

	//获取对象主键id值
	private Integer getKeyValue(T data){
		Field field = null;
		Integer id = 0;
		try {
			field = data.getClass().getDeclaredField("id");
			field.setAccessible(true);
			id= (Integer) field.get(data);
		} catch (Exception e) {
			throw new IllegalArgumentException("获取id异常", e);
		}
		return id;
	};

	protected void postUpdate(T data) {

	}

	protected void preUpdate(T data){

	}

	protected void postGet(T data) {

	}

	@Transactional
	private void flushAndDetach(T data) {
		if (Objects.nonNull(data)) {
			this.entityManager.flush();
			this.entityManager.detach(data);
		}
	}

	//校验
	private void validate(T data, Class... groups) {
		Set<ConstraintViolation<T>> violations = validator.validate(data, groups);
		if (violations.size() > 0) {
			throw new ConstraintViolationException(message.MESSAGE_CONSTRAIN_VIOLATION, violations);
		}
	}

	//持久化数据并重新获取最新数据
	@Transactional
	private T saveAndGet(T data, Consumer<T> post) {
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

	/**
	 * 将对象转换为map 属性名作为key 值作为value 过滤空值
	 * @param obj
	 * @param data
	 */
	private void objectToMap(T obj, Map<String, Object> data) {
		if (Objects.isNull(obj)) {
			return;
		}
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			String name = field.getName();
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("对象转换map异常", e);
			}
			if (Objects.nonNull(value)) {
				data.put(name, value);
			}
		}
	}

	//创建查询query
	private CriteriaQuery<T> buidSelectQuery(Map<String, Object> data) {
		data.put("enabled", true);
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = builder.createQuery(this.targetClass);
		Root root = cq.from(this.targetClass);
		List<Predicate> predicates = new ArrayList<>();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			predicates.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
		}
		if (!CollectionUtils.isEmpty(predicates)) {
			cq.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		return cq;
	}

	/**
	 * 创建逻辑删除的query 根据id或者ids更新enabled和enabledId
	 * @param ids
	 * @return
	 */
	private CriteriaUpdate<T> buidDeleteQuery(Collection<Integer> ids) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<T> cq = builder.createCriteriaUpdate(this.targetClass);
		Root<T> root = cq.from(this.targetClass);
		Expression expression = root.get("id");
		Predicate predicate = expression.in(ids);
		cq.where(predicate);
		cq.set(root.<Integer>get("enabledId"), root.<Integer>get("id"));
		cq.set(root.get("enabled"), false);

		return cq;
	}

	/**
	 * 查询多个结果
	 * @param query
	 * @return
	 */
	private List<T> listByQuery(CriteriaQuery<T> query) {
		if (Objects.isNull(query)) {
			return null;
		}
		List<T> dataList = entityManager.createQuery(query).getResultList();
		dataList.forEach(o -> {
			this.postGet(o);
			this.flushAndDetach(o);
		});
		return dataList;
	}

	/**
	 * 查询单个结果
	 * @param query
	 * @return
	 */
	private T getByQuery(CriteriaQuery<T> query) {
		if (Objects.isNull(query)) {
			return null;
		}
		//getSingleResult 如果返回结果为空 则会抛异常 NoResultException:No entity found for query
//		T data = entityManager.createQuery(query).getSingleResult();
		List<T> dataList = entityManager.createQuery(query).getResultList();
		T data = CollectionUtils.isEmpty(dataList) ? null: dataList.get(0);
		if(Objects.nonNull(data)){
			this.postGet(data);
			this.flushAndDetach(data);
		}
		return data;
	}

	/**
	 * 执行update语句，返回更新的条数 （只用于单个批量的逻辑删除）
	 * @param query
	 * @return
	 */
	private Integer updateByquery(CriteriaUpdate<T> query) {
		if (Objects.isNull(query)) {
			return null;
		}

		return entityManager.createQuery(query).executeUpdate();
	}
}
