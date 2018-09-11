package com.ssh.service.practice.digibig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryBuilder<T> {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private EntityManager entityManager;
	private CriteriaBuilder builder;
	private Class<T> clazz;
	private FieldConditionHelper fieldConditionHelper;
	private CrossConditionHelper crossConditionHelper;

	public QueryBuilder(EntityManager entityManager, Class<T> clazz, FieldConditionHelper fieldConditionHelper, CrossConditionHelper crossConditionHelper) {
		this.entityManager = entityManager;
		this.builder = this.entityManager.getCriteriaBuilder();
		this.clazz = clazz;
		this.fieldConditionHelper = fieldConditionHelper;
		this.crossConditionHelper = crossConditionHelper;
	}

	public Query<T, T, T> loadOne() {
		Query<T, T, T> result = new Query();
		result.setEntityManager(this.entityManager);
		result.setGenerator(this::generateLoadOne);
		return result;
	}

	public Query<T, T, T> generateLoadOne(Query<T, T, T> query, Map<String, Object> data) {
		Query<T, T, T> result = query.clone();
		CriteriaQuery<T> cq = this.builder.createQuery(this.clazz);
		Root<T> root = cq.from(this.clazz);
		cq.select(root);
		result.setRoot(root);
		List<Predicate> predicates = new ArrayList();
		predicates.addAll(this.fieldConditionHelper.getDomainConstrain(this.clazz, this.entityManager, this.builder, root));
		predicates.addAll(this.crossConditionHelper.getJoinConstrain(this.clazz, this.entityManager, this.builder, root));
		predicates.addAll(result.getPredicate(data));
		if (predicates.size() > 0) {
			cq = cq.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
		}

		result.setQuery(cq);
		result.setExecute(result::executeSingle);
		return result;
	}

	public Query<T, T, List<T>> loadAll() {
		Query<T, T, List<T>> result = new Query();
		result.setEntityManager(this.entityManager);
		result.setGenerator(this::generateLoadAll);
		return result;
	}

	public Query<T, T, List<T>> generateLoadAll(Query<T, T, List<T>> query, Map<String, Object> data) {
		Query<T, T, List<T>> result = query.clone();
		CriteriaQuery<T> cq = this.builder.createQuery(this.clazz);
		Root<T> root = cq.from(this.clazz);
		cq.select(root);
		result.setRoot(root);
		List<Predicate> predicates = new ArrayList();
		predicates.addAll(this.fieldConditionHelper.getDomainConstrain(this.clazz, this.entityManager, this.builder, root));
		predicates.addAll(this.crossConditionHelper.getJoinConstrain(this.clazz, this.entityManager, this.builder, root));
		predicates.addAll(result.getPredicate(data));
		if (predicates.size() > 0) {
			cq = cq.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
		}

		result.setQuery(cq);
		result.setExecute(result::executeList);
		return result;
	}

	public Query<T, Long, Long> count() {
		Query<T, Long, Long> result = new Query();
		result.setEntityManager(this.entityManager);
		result.setGenerator(this::generateCount);
		return result;
	}

	public Query<T, Long, Long> generateCount(Query<T, Long, Long> query, Map<String, Object> data) {
		Query<T, Long, Long> result = query.clone();
		CriteriaQuery<Long> cq = this.builder.createQuery(Long.class);
		Root<T> root = cq.from(this.clazz);
		cq.select(this.builder.count(root));
		result.setRoot(root);
		List<Predicate> predicates = new ArrayList();
		predicates.addAll(this.fieldConditionHelper.getDomainConstrain(this.clazz, this.entityManager, this.builder, root));
		predicates.addAll(this.crossConditionHelper.getJoinConstrain(this.clazz, this.entityManager, this.builder, root));
		predicates.addAll(result.getPredicate(data));
		if (predicates.size() > 0) {
			cq = cq.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
		}

		result.setQuery(cq);
		result.setExecute(result::executeSingle);
		return result;
	}

	public Update<T> disable() {
		Update<T> result = new Update();
		result.setEntityManager(this.entityManager);
		result.setGenerator(this::generateDisable);
		return result;
	}

	public Update<T> generateDisable(Update<T> query,Map<String,Object> data)
	{
		Update<T> result = query.clone();//不复制root 和query
		CriteriaUpdate<T> cq = builder.createCriteriaUpdate(this.clazz);
		Root<T> root = cq.from(clazz);
		//添加predict
		result.setRoot(root);
		List<Predicate> predicates = result.getPredicate(data);
		if (predicates.size()>0)
		{
			cq=cq.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		cq.set(root.<Integer>get("enabledId"), root.get("id"));
		cq.set(root.get("enabled"), false);
		result.setQuery(cq);
		return result;
	}
}
