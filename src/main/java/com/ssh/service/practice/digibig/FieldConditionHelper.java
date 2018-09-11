package com.ssh.service.practice.digibig;

import com.alibaba.fastjson.JSON;
import com.ssh.service.practice.digibig.enums.ConditionOperation;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class FieldConditionHelper {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public FieldConditionHelper() {
	}

	public <X, Y> Predicate asPredicate(FieldCondition<X, Y> condition, EntityManager entityManager, CriteriaBuilder builder, Root<X> root) {
		FieldConditionHelper.Params<X> params = new FieldConditionHelper.Params(condition.getClazz(), entityManager, builder, root);
		return this.asPredicate(condition, params);
	}

	public <X, Y> Predicate asPredicate(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		Assert.notNull(condition, "condition不能为空");
		switch (condition.getOperation()) {
			case EQUAL:
				return this.equal(condition, params);
			case NOT_EQUAL:
				return this.notEqual(condition, params);
			case IN:
				return this.in(condition, params);
			case NOT_IN:
				return this.notIn(condition, params);
			case GT:
				return this.gt(condition, params);
			case GE:
				return this.ge(condition, params);
			case LT:
				return this.lt(condition, params);
			case LE:
				return this.le(condition, params);
			case BETWEEN:
				return this.between(condition, params);
			case LIKE:
				return this.like(condition, params);
			case NOT_LIKE:
				return this.notLike(condition, params);
			case IS_NULL:
				return this.isNull(condition, params);
			case NOT_NULL:
				return this.notNull(condition, params);
			default:
				throw new IllegalArgumentException("不支持的Condition类型");
		}
	}

	public <T> List<Predicate> getDomainConstrain(Class<T> clazz, EntityManager entityManager, CriteriaBuilder builder, Root<T> root) {
		FieldConditionHelper.Params params = new FieldConditionHelper.Params(clazz, entityManager, builder, root);

		try {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if (Objects.isNull(requestAttributes)) {
				return Collections.emptyList();
			} else {
				Map<Class, ?> config = (Map) requestAttributes.getAttribute("request.attribute.auth.data.constrain.domain", 0);
				if (Objects.isNull(config)) {
					return Collections.emptyList();
				} else {
					List<FieldCondition<T, ?>> conditions = (List) config.get(clazz);
					List<Predicate> predicates = new ArrayList();
					this.logger.trace("开始取得predicate {}", Objects.isNull(conditions) ? null : conditions.size());
					conditions.stream().forEach((c) -> {
						this.logger.trace("循环condition {}", JSON.toJSONString(c));
						predicates.add(this.asPredicate(c, params));
					});
					return predicates;
				}
			}
		} catch (NullPointerException var10) {
			this.logger.debug("空数据，无返回");
			throw new RuntimeException(var10);
		}
	}

	private <X, Y> Predicate equal(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.EQUAL}) && condition.getData().size() == 1) {
			Predicate predicate = params.getBuilder().equal(params.getRoot().get(condition.getField()), condition.getData().get(0));
			return predicate;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate notEqual(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.NOT_EQUAL}) && condition.getData().size() == 1) {
			Predicate predicate = params.getBuilder().notEqual(params.getRoot().get(condition.getField()), condition.getData().get(0));
			return predicate;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate in(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.IN}) && condition.getData().size() > 0) {
			Collection par = (Collection) condition.getData().get(0);
			Predicate predicate = params.getRoot().get(condition.getField()).in(par.toArray());
			return predicate;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate notIn(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.NOT_IN}) && condition.getData().size() > 0) {
			Collection par = (Collection) condition.getData().get(0);
			Predicate predicate = params.getBuilder().not(params.getRoot().get(condition.getField()).in(par.toArray()));
			return predicate;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate ge(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.GE}) && condition.getData().size() == 1) {
			return condition.getData().get(0) instanceof Number ? params.getBuilder().ge(params.getNumberField(condition.getField()), (Number) condition.getData().get
				(0))
				: params.getBuilder().greaterThanOrEqualTo(params.getComparableField(condition.getField()), (Comparable) condition.getData().get(0));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate gt(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.GT}) && condition.getData().size() == 1) {
			return condition.getData().get(0) instanceof Number ? params.getBuilder().gt(params.getNumberField(condition.getField()), (Number) condition.getData().get
				(0))
				: params.getBuilder().greaterThan(params.getComparableField(condition.getField()), (Comparable) condition.getData().get(0));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate lt(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.LT}) && condition.getData().size() == 1) {
			return condition.getData().get(0) instanceof Number ? params.getBuilder().lt(params.getNumberField(condition.getField()), (Number) condition.getData().get
				(0))
				: params.getBuilder().lessThan(params.getComparableField(condition.getField()), (Comparable) condition.getData().get(0));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate le(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.LE}) && condition.getData().size() == 1) {
			return condition.getData().get(0) instanceof Number ? params.getBuilder().le(params.getNumberField(condition.getField()), (Number) condition.getData().get
				(0))
				: params.getBuilder().lessThanOrEqualTo(params.getComparableField(condition.getField()), (Comparable) condition.getData().get(0));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate between(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.BETWEEN}) && condition.getData().size() == 2) {
			Predicate predicate = params.getBuilder()
				.between(params.getField(condition.getField()), (Comparable) condition.getData().get(0), (Comparable) condition.getData().get(1));
			return predicate;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate like(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.LIKE}) && condition.getData().size() == 1) {
			return params.builder.like(params.getField(condition.getField()), (String) condition.getData().get(0));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate notLike(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (condition.getOperation().in(new ConditionOperation[]{ConditionOperation.LIKE}) && condition.getData().size() == 1) {
			return params.builder.notLike(params.getField(condition.getField()), (String) condition.getData().get(0));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private <X, Y> Predicate isNull(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (!condition.getOperation().in(new ConditionOperation[]{ConditionOperation.IS_NULL})) {
			throw new IllegalArgumentException();
		} else {
			return params.builder.isNull(params.getField(condition.getField()));
		}
	}

	private <X, Y> Predicate notNull(FieldCondition<X, Y> condition, FieldConditionHelper.Params<X> params) {
		if (!condition.getOperation().in(new ConditionOperation[]{ConditionOperation.NOT_NULL})) {
			throw new IllegalArgumentException();
		} else {
			return params.builder.isNotNull(params.getField(condition.getField()));
		}
	}

	private class Params<T> {

		private Class<T> clazz;
		private EntityManager entityManager;
		private CriteriaBuilder builder;
		private Root<T> root;

		public Params(Class<T> clazz, EntityManager entityManager, CriteriaBuilder builder, Root<T> root) {
			this.clazz = clazz;
			this.entityManager = entityManager;
			this.builder = builder;
			this.root = root;
		}

		public Class<T> getClazz() {
			return this.clazz;
		}

		public EntityManager getEntityManager() {
			return this.entityManager;
		}

		public CriteriaBuilder getBuilder() {
			return this.builder;
		}

		public Root<T> getRoot() {
			return this.root;
		}

		public <X> Path<X> getField(String name) {
			return this.root.get(name);
		}

		public Path<? extends Number> getNumberField(String name) {
			return this.root.get(name);
		}

		public Path<? extends Comparable> getComparableField(String name) {
			return this.root.get(name);
		}
	}
}
