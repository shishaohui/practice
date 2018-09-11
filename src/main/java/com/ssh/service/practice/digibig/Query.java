package com.ssh.service.practice.digibig;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query<T, R, Z> {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	private EntityManager entityManager;
	private CriteriaQuery<R> query;
	private Root<T> root;
	private BiFunction<Integer, Integer, Z> execute;
	private BiFunction<Query<T, R, Z>, Map<String, Object>, Query<T, R, Z>> generator;

	public Query() {
	}

	public BiFunction<Query<T, R, Z>, Map<String, Object>, Query<T, R, Z>> getGenerator() {
		return this.generator;
	}

	public void setGenerator(BiFunction<Query<T, R, Z>, Map<String, Object>, Query<T, R, Z>> generator) {
		this.generator = generator;
	}

	public Query<T, R, Z> generate(Map<String, Object> data) {
		return (Query)this.generator.apply(this, data);
	}

	public Root<T> getRoot() {
		return this.root;
	}

	public void setRoot(Root<T> root) {
		this.root = root;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public CriteriaQuery<R> getQuery() {
		return this.query;
	}

	public void setQuery(CriteriaQuery<R> query) {
		this.query = query;
	}

	public BiFunction<Integer, Integer, Z> getExecute() {
		return this.execute;
	}

	public void setExecute(BiFunction<Integer, Integer, Z> execute) {
		this.execute = execute;
	}

	public R executeSingle(Integer offset, Integer limit) {
		TypedQuery<R> query = this.entityManager.createQuery(this.query);
		if (Objects.nonNull(offset)) {
			query.setFirstResult(offset.intValue());
		}

		if (Objects.nonNull(limit)) {
			query.setMaxResults(limit.intValue());
		}

		try {
			return query.getSingleResult();
		} catch (NoResultException var5) {
			return null;
		}
	}

	public List<R> executeList(Integer offset, Integer limit) {
		TypedQuery<R> query = this.entityManager.createQuery(this.query);
		if (Objects.nonNull(offset)) {
			query.setFirstResult(offset.intValue());
		}

		if (Objects.nonNull(limit)) {
			query.setMaxResults(limit.intValue());
		}

		try {
			return query.getResultList();
		} catch (NoResultException var5) {
			return new ArrayList();
		}
	}

	public List<Predicate> getPredicate(Map<String, Object> param) {
		return predicates(this.entityManager, this.root, param);
	}

	public static <X> List<Predicate> predicates(EntityManager entityManager, Root<X> root, Map<String, Object> param) {
		if (Objects.isNull(param)) {
			return Collections.emptyList();
		} else {
			List<Predicate> predicates = new ArrayList();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();

			try {
				Iterator var5 = param.entrySet().iterator();

				while(var5.hasNext()) {
					Entry<String, Object> entry = (Entry)var5.next();
					Object value = entry.getValue();
					if (!Objects.isNull(value)) {
						if (value instanceof Collection) {
							Collection col = (Collection)value;
							if (col.size() != 0) {
								Expression expression = root.get((String)entry.getKey());
								Predicate predicate = expression.in(col.toArray());
								predicates.add(predicate);
							}
						} else {
							Predicate predicate = builder.equal(root.get((String)entry.getKey()), value);
							predicates.add(predicate);
						}
					}
				}

				return predicates;
			} catch (Exception var11) {
				throw new RuntimeException(var11);
			}
		}
	}

	public Query<T, R, Z> clone() {
		Query<T, R, Z> result = new Query();
		result.setEntityManager(this.entityManager);
		return result;
	}
}
