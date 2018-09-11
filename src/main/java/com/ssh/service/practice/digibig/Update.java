package com.ssh.service.practice.digibig;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Update<T> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public Update() {
	}

	private EntityManager entityManager;

	private CriteriaUpdate<T> query;
	private Root<T> root;

	private BiFunction<Update<T>, Map<String, Object>, Update<T>> generator;


	public BiFunction<Update<T>, Map<String, Object>, Update<T>> getGenerator() {
		return generator;
	}

	public void setGenerator(BiFunction<Update<T>, Map<String, Object>, Update<T>> generator) {
		this.generator = generator;
	}

	public CriteriaUpdate<T> getQuery() {
		return query;
	}

	public void setQuery(CriteriaUpdate<T> query) {
		this.query = query;
	}

	public Update<T> generate(Map<String, Object> data) {
		return this.generator.apply(this, data);
	}


	public Root<T> getRoot() {
		return root;
	}

	public void setRoot(Root<T> root) {
		this.root = root;
	}


	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public int execute() {
		javax.persistence.Query query = this.entityManager.createQuery(this.query);
		return query.executeUpdate();
	}

	public List<Predicate> getPredicate(Map<String, Object> param) {
		return Query.predicates(this.entityManager, this.root, param);
	}

	public Update<T> clone() {
		Update<T> result = new Update<>();
		result.setEntityManager(this.entityManager);
		return result;
	}
//提供build功能
}