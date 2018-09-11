package com.ssh.service.practice.digibig;

import com.ssh.service.practice.digibig.enums.ConditionOperation;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class CrossConditionHelper {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public CrossConditionHelper() {
	}


	public <DOMAIN, CROSS,RESULT> Predicate asPredicate(AbstractCrossFieldCondition<CROSS, RESULT> condition, Class<DOMAIN> clazz, EntityManager entityManager, CriteriaBuilder builder, Root<DOMAIN> root) {
		Params<DOMAIN,CROSS> params = new Params<>(clazz, entityManager, builder, root,condition.getOnName());
		return asPredicate(condition, params);
	}

	public <DOMAIN, CROSS,RESULT> Predicate asPredicate(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
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
			case IS_NULL:
				return this.isNull(condition, params);
			case NOT_NULL:
				return this.notNull(condition, params);
			default:
				throw new IllegalArgumentException("不支持的AbstractCrossFieldCondition类型");
		}
	}

	public <T> List<Predicate> getJoinConstrain(Class<T> clazz, EntityManager entityManager, CriteriaBuilder builder, Root<T> root)
	{

		List<Predicate> predicates = new ArrayList<>();
		predicates.addAll(byType(Names.DATA_CONSTRAIN_TENANT, clazz, entityManager, builder, root));
		predicates.addAll(byType(Names.DATA_CONSTRAIN_APP, clazz, entityManager, builder, root));
		predicates.addAll(byType(Names.DATA_CONSTRAIN_USER, clazz, entityManager, builder, root));
		return  predicates;

	}

	private <DOMAIN,CROSS> List<Predicate> byType(String key, Class<DOMAIN> clazz, EntityManager entityManager, CriteriaBuilder builder, Root<DOMAIN> root)
	{
		if (Objects.isNull(RequestContextHolder.getRequestAttributes())) return Collections.emptyList();
		Object raw=RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
		if (Objects.isNull(raw)) return Collections.emptyList();

		List<AbstractCrossFieldCondition<CROSS, Object>> conditions;
		conditions=(List<AbstractCrossFieldCondition<CROSS, Object>>)raw ;
		List<Predicate> predicates = new ArrayList<>();
		if ( conditions.size()>0)
		{
			Params<DOMAIN,CROSS> params = new Params<>(clazz, entityManager, builder, root,conditions.get(0).getOnName());
			conditions.stream().forEach(c->{
				predicates.add(this.<DOMAIN,CROSS,Object>asPredicate(c, params));
			});
		}
		return predicates;
	}

	class Params<T,R>{
		private Class<T> clazz;
		private EntityManager entityManager;
		private CriteriaBuilder builder;
		private Root<T> root;
		private Join<T,R> join;
		public Params(Class<T> clazz,EntityManager entityManager, CriteriaBuilder builder, Root<T> root,String onName)
		{
			this.clazz=clazz;
			this.entityManager=entityManager;
			this.builder=builder;
			this.root=root;
			this.join= root.join(onName);//默认inner join吧,todo
		}
		public Class<T> getClazz() {
			return clazz;
		}

		public EntityManager getEntityManager() {
			return entityManager;
		}

		public CriteriaBuilder getBuilder() {
			return builder;
		}

		public Root<T> getRoot() {
			return root;
		}

		public<X> Path<X> getField(String name)
		{
			return this.join.<X>get(name);
		}

		public Path<? extends Number> getNumberField(String name)
		{
			return this.join.get(name);
		}
	}


	private <DOMAIN, CROSS,RESULT> Predicate equal(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.EQUAL) && condition.getData().size() == 1))
		{
			throw new IllegalArgumentException();
		}
		Predicate predicate = params.getBuilder().equal(params.getRoot().get(condition.getField()), condition.getData().get(0));
		return predicate;
	}
	private <DOMAIN, CROSS,RESULT> Predicate notEqual(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.NOT_EQUAL) && condition.getData().size() == 1))
		{
			throw new IllegalArgumentException();
		}
		Predicate predicate = params.getBuilder().notEqual(params.getRoot().get(condition.getField()), condition.getData().get(0));
		return predicate;
	}
	private <DOMAIN, CROSS,RESULT> Predicate in(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.IN) && condition.getData().size()> 0))
		{
			throw new IllegalArgumentException();
		}
		Collection par=(Collection)condition.getData().get(0);
		Predicate predicate = params.getRoot().get(condition.getField()).in(par.toArray());//不转ok么？
		return predicate;
	}

	private <DOMAIN, CROSS,RESULT> Predicate notIn(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.IN) && condition.getData().size()> 0))
		{
			throw new IllegalArgumentException();
		}
		Collection par=(Collection)condition.getData().get(0);
		Predicate predicate = params.getBuilder().not(params.getRoot().get(condition.getField()).in(par.toArray())); //不转ok么
		return predicate;
	}

	private <DOMAIN, CROSS,RESULT> Predicate ge(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.IN) && condition.getData().size()==1))
		{
			throw new IllegalArgumentException();
		}
		Predicate predicate = params.getBuilder().ge(params.getNumberField(condition.getField()), (Number) condition.getData().get(0));
		return predicate;
	}

	private <DOMAIN, CROSS,RESULT> Predicate gt(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.IN) && condition.getData().size()==1))
		{
			throw new IllegalArgumentException();
		}
		Predicate predicate = params.getBuilder().gt(params.getNumberField(condition.getField()), (Number) condition.getData().get(0));
		return predicate;
	}

	private <DOMAIN, CROSS,RESULT> Predicate lt(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.IN) && condition.getData().size()==1))
		{
			throw new IllegalArgumentException();
		}
		Predicate predicate = params.getBuilder().lt(params.getNumberField(condition.getField()), (Number) condition.getData().get(0));
		return predicate;
	}

	private <DOMAIN, CROSS,RESULT> Predicate le(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.IN) && condition.getData().size()==1))
		{
			throw new IllegalArgumentException();
		}
		Predicate predicate = params.getBuilder().le(params.getNumberField(condition.getField()), (Number) condition.getData().get(0));
		return predicate;
	}
	private <DOMAIN, CROSS,RESULT> Predicate between(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.IN) && condition.getData().size()==2))
		{
			throw new IllegalArgumentException();
		}
		AbstractCrossFieldCondition<CROSS, Comparable> wc = (AbstractCrossFieldCondition<CROSS, Comparable>) condition;
		Predicate predicate = params.getBuilder().between(params.getField(wc.getField()), wc.getData().get(0), wc.getData().get(1));
		return predicate;
	}

	private <DOMAIN, CROSS,RESULT> Predicate isNull(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.IS_NULL) && condition.getData().size()==1))
		{
			throw new IllegalArgumentException();
		}
		AbstractCrossFieldCondition<CROSS, Comparable> wc = (AbstractCrossFieldCondition<CROSS, Comparable>) condition;
		Predicate predicate = params.getBuilder().isNull(params.getField(wc.getField()));
		return predicate;
	}

	private <DOMAIN, CROSS,RESULT> Predicate notNull(AbstractCrossFieldCondition<CROSS, RESULT> condition, Params<DOMAIN,CROSS> params) {
		//要求有一个参数
		//不做类型检查
		if (!(condition.getOperation().in(ConditionOperation.NOT_NULL) && condition.getData().size()==1))
		{
			throw new IllegalArgumentException();
		}
		AbstractCrossFieldCondition<CROSS, Comparable> wc = (AbstractCrossFieldCondition<CROSS, Comparable>) condition;
		Predicate predicate = params.getBuilder().isNotNull(params.getField(wc.getField()));
		return predicate;
	}
}
