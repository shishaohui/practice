package com.ssh.service.practice.util;

import com.ssh.service.practice.common.commonenums.CanWrite;
import com.ssh.service.practice.domain.NotQueryParameter;
import com.ssh.service.practice.domain.WriteControl;
import com.ssh.service.practice.domain.WriteOnlyOnce;
import com.ssh.service.practice.validation.Key;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

@Component
public class DomainUtil {

		Logger logger = LoggerFactory.getLogger(this.getClass());

		private ConcurrentHashMap<Class, List<PropertyUpdater>> updateCache = new ConcurrentHashMap<>();
		private ConcurrentHashMap<Class, List<PropertyReader>> readCache = new ConcurrentHashMap<>();

		private final EntityManager entityManager;

		public DomainUtil(EntityManager entityManager) {
			this.entityManager=entityManager;
		}


		public <T> T clearAsNew( T data)
		{
			return this.clear(data,  CanWrite.ALL);
		}


		public <T> T clearFields(T data,String...fieldNames)
		{
			List<? extends PropertyUpdater> updaters = this.getCachedUpdaterConfig(data.getClass());
			PropertyUpdater updater;
			for (int i = 0; i < updaters.size(); i++) {
				updater= updaters.get(i);
				if (ArrayUtils.contains(fieldNames,updater.getField().getName())) {
					updater.setNullForce(data);
				}
			}
			return data;
		}

		public <T> T keepFields(T data,String...fieldNames)
		{
			List<? extends PropertyUpdater> updaters = this.getCachedUpdaterConfig(data.getClass());
			PropertyUpdater updater;

			for (int i = 0; i < updaters.size(); i++) {
				updater= updaters.get(i);
				if ( !ArrayUtils.contains(fieldNames,updater.getField().getName())) {
					updater.setNullForce(data);
				}
			}
			return data;
		}

		public <T> T clear( T data, CanWrite...control)
		{
			List<? extends PropertyUpdater> updaters = this.getCachedUpdaterConfig(data.getClass());
			PropertyUpdater updater;
			int controlValue=this.getControlValue(control);
			for (int i = 0; i < updaters.size(); i++) {
				//外部有写权限的，才可以写，否则都清空
				updater= updaters.get(i);
				updater.clear(data,controlValue);
			}
			return data;
		}

		private int getControlValue(CanWrite...control)
		{
			int result=0;
			if (Objects.isNull(control) || control.length==0) {
				return result;
			}
			for (int i = 0; i <control.length ; i++) {
				if (Objects.nonNull(control[i])) {
					result=result|control[i].getValue();
				}
			}
			return result;
		}

		//对空值不进行处理
		@SuppressWarnings("unchecked")
		public <T> T updateSelective(T current, T data, CanWrite...control) {
			return this.updateSelective(current, data,  (o,p) -> Objects.nonNull(p), control);
		}

		public <T> T updateSelective(T current, T data) {
			return this.updateSelective(current, data,  (o,p) -> Objects.nonNull(p), CanWrite.EXTERNAL);
		}

		@SuppressWarnings("unchecked")
		public <T> T updateSelective(T current, T data, BiPredicate<Object,Object> predicate,CanWrite...control) {

			if (Objects.isNull(current) || Objects.isNull(data)  || Objects.isNull(predicate)) {
				return null;
			}
			List<? extends PropertyUpdater> updaters = this.getCachedUpdaterConfig(data.getClass());
			//非空的
			PropertyUpdater updater;
			int controlValue=this.getControlValue(control);
//        logger.trace("control value{}",controlValue);
			for (int i = 0; i < updaters.size(); i++) {
				updater=updaters.get(i);

				updater.updateSelective(current, data,predicate,controlValue);
			}
			return current;
		}

		public <T> T setNull(T current,List<String> nullColumns) {
			return this.setNull(current, nullColumns, CanWrite.EXTERNAL);
		}

		@SuppressWarnings("unchecked")
		public <T> T setNull(T current, List<String> nullColumns, CanWrite...control) {
			if (Objects.isNull(current)) {
				return null;
			}
			List<? extends PropertyUpdater> updaters = this.getCachedUpdaterConfig(current.getClass());
			//非空的
			PropertyUpdater updater;
			int controlValue=this.getControlValue(control);
//        logger.trace("control value{}",controlValue);
			for (int i = 0; i < updaters.size(); i++) {
				updater=updaters.get(i);
				if (updater.getField().getAnnotation(Version.class) != null
					|| updater.getField().getAnnotation(Id.class) != null
					|| updater.getField().getAnnotation(NotNull.class) != null
					|| updater.getField().getAnnotation(Key.class) != null
					|| updater.getField().getAnnotation(CreationTimestamp.class) != null
					|| updater.getField().getAnnotation(UpdateTimestamp.class) != null) {
					continue;
				}
				if(nullColumns.contains(updater.getField().getName())){
					updater.setNull(current, controlValue);
				}
			}
			return current;
		}


		public <T> CriteriaQuery<Long> createCountQuery(T example, Class<T> clazz) {

			if (Objects.isNull(example)) {
				return null;
			}
			CriteriaBuilder builder=this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> query=builder.createQuery(Long.class);
			Root<T> root= query.from(clazz);
			query.select(builder.count(root));

			List<Predicate> predicates = this.generatePredicate(example, root);

			if (Objects.isNull(predicates) || predicates.size()==0) {
				return null;
			}

			return query.where(predicates.toArray(new Predicate[predicates.size()]));

		}




		public <T> List<Predicate> generatePredicate(T example,Root<T> root)
		{

			List<PropertyReader> readers=getCachedReaderConfig(example.getClass());
			List<Predicate> predicates = new ArrayList<>();
			for (int i = 0; i <readers.size() ; i++) {
				predicates.add(readers.get(i).getPredicate(root,  example));

			}
			return predicates.stream().filter(Objects::nonNull).collect(Collectors.toList());
		}





		public List<BeanFieldInfo> getBeanFields(Class clazz)
		{
			List<BeanFieldInfo> fields = new ArrayList<>();
			Method[] methods=clazz.getMethods();
			Method getter=null;
			Method setter=null;
			for (int i = 0; i < methods.length; i++) {
				//需要先判断，是否是一个updateter
				Method method = methods[i];
				if (method.getName().length() <= 3) {
					continue;
				}
				if (!method.getName().startsWith("get")) {
					continue;
				}
				getter = method;
				String fieldName = method.getName().replaceFirst("^get", "");

				setter = Arrays.stream(methods).filter(o -> o.getName().equals("set".concat(fieldName))).findFirst().orElse(null);

				if (Objects.isNull(setter)) {
					continue;
				}
				if (getter.getParameterCount() > 0 || setter.getParameterCount() != 1) {
					continue;
				}
				if (!getter.getReturnType().isAssignableFrom(setter.getParameterTypes()[0])) {
					//可以使用子类进行赋值
					continue;
				}
				Field field = null;
				try {
					field = getter.getDeclaringClass().getDeclaredField(DomainUtil.lowerCaseFirst(fieldName));
				} catch (NoSuchFieldException ex) {
//                logger.warn("getter/setter未检查到匹配的field{}",field.getName());
					continue;
				}
				fields.add(new BeanFieldInfo(field,getter,setter));
			}
			return fields;
		}




		public  <T> List<Predicate> generatePredicate(T firstData, T secondData,Root<T> root)
		{
			List<PropertyReader> readers=getCachedReaderConfig(firstData.getClass());
			List<Predicate> predicates = new ArrayList<>();
			for (int i = 0; i <readers.size() ; i++) {
				predicates.add(readers.get(i).getPredicate(root, firstData, secondData));

			}
			return predicates.stream().filter(Objects::nonNull).collect(Collectors.toList());
		}


		private List<PropertyUpdater> getCachedUpdaterConfig(Class clazz) {
			List<PropertyUpdater> result = this.updateCache.get(clazz);
			if (Objects.isNull(result) ) {
				result = this.initializeUpdater(clazz);
				this.updateCache.put(clazz,result);
			}
			return result;
		}

		private List<PropertyReader> getCachedReaderConfig(Class clazz) {
			List<PropertyReader> result = this.readCache.get(clazz);
			if (Objects.isNull(result) ) {
				result = this.initializeReader(clazz);
				this.readCache.put(clazz,result);
			}
			return result;
		}


		private  List<PropertyReader> initializeReader(Class clazz)
		{

			Method[] methods=clazz.getMethods();

			List<PropertyReader> result = new ArrayList<>();
			Method getter=null;
			List<BeanFieldInfo> fieldInfos = getBeanFields(clazz);

			for (int i = 0; i <fieldInfos.size() ; i++) {

				BeanFieldInfo info = fieldInfos.get(i);

				PropertyReader reader = new PropertyReader();
				if (!isQuerableField(info.getField())) {
					continue;
				}
				reader.setField(info.getField());
				try {
					getter = clazz.getMethod(getGetterName(info.getField().getName()));
				} catch (Exception ex) { }

				if (Objects.isNull(getter) ) {
					//任意一个没有，则不进行注册
					continue;
				}

				reader.setGetter(getter);
				reader.setCriteriaBuilder(this.entityManager.getCriteriaBuilder());
				reader.setQuerable(true);
				NotQueryParameter noQuery=info.getField().getAnnotation(NotQueryParameter.class);
				if (Objects.nonNull(noQuery)) {
					reader.setQuerable(false);
				}
				//不是枚举值，且不能转为comparable是不可以查询的
				if (!Comparable.class.isAssignableFrom(reader.getField().getType()) && !reader.getField().getType().isEnum()) {
					reader.setQuerable(false);
				}
				result.add(reader);
			}
			return result;
		}


		private boolean isQuerableField(Field field)
		{
			if (field.getAnnotation(Transient.class)!=null) {
				return false;
			}
			if (field.getAnnotation(org.springframework.data.annotation.Transient.class)!=null) {
				return false;
			}
			return field.getType().isEnum() || field.getType().isAssignableFrom(String.class) || Comparable.class.isAssignableFrom(field.getType());
		}



		private  List<PropertyUpdater> initializeUpdater(Class clazz)  {

			List<BeanFieldInfo> fieldInfos = getBeanFields(clazz);
			List<PropertyUpdater> result = new ArrayList<>();

			for (int i = 0; i < fieldInfos.size(); i++) {

				BeanFieldInfo info = fieldInfos.get(i);

				//getter setter都有了，
				PropertyUpdater updater = new PropertyUpdater();
				updater.setField(info.getField());
				updater.setSetter(info.getSetter());
				updater.setGetter(info.getGetter());
				//强假设，getter方法必须与filed声明在同一个类上

				WriteControl writeAccess=info.getField().getAnnotation(WriteControl.class);

				//最多标记32种状态，足够了
				if (Objects.nonNull(writeAccess) ) {
					updater.setControl(CanWrite.NONE.getValue());
					for (int j = 0; j <writeAccess.allow().length ; j++) {
						if (Objects.nonNull(writeAccess.allow()[j])) {
							updater.setControl(updater.getControl() | writeAccess.allow()[j].getValue());
						}
					}
				}
				else
				{
					//如果没有设置，则认为是所有权限都可以更改
					updater.setControl(CanWrite.ALL.getValue());
				}

				if (info.getField().getAnnotation(Version.class) != null
					|| info.getField().getAnnotation(Id.class) != null
					|| info.getField().getAnnotation(CreationTimestamp.class) != null
					|| info.getField().getAnnotation(UpdateTimestamp.class) != null) {
					//这几个注解，任何时候都是不允许更改的
					updater.setControl(CanWrite.NONE.getValue());
				}

				if (info.getField().getAnnotation(WriteOnlyOnce.class) != null) {
					updater.setWriteOnlyOnce(true);
				}
				else
				{
					updater.setWriteOnlyOnce(false);
				}
				result.add(updater);
			}
			return result;
		}


		private static String getGetterName(String fieldName) {

			return "get".concat(DomainUtil.upperCaseFirst(fieldName));
		}

		private static String getSetterName(String fieldName) {

			return "set".concat(DomainUtil.upperCaseFirst(fieldName));
		}


		public static String lowerCaseFirst(String value) {

			Assert.isTrue(StringUtils.isNotBlank(value), "名称不能为空");
			for (char i = 65; i <= 90; i++) {
				if (value.startsWith(Character.valueOf(i).toString())) {
					if (value.length() == 1) {
						value = Character.valueOf((char) (i + 32)).toString();
					} else {
						value = Character.valueOf((char) (i + 32)).toString().concat(value.substring(1, value.length())) ;
					}
				}
			}
			return value;
		}

		public static String upperCaseFirst(String value) {

			Assert.isTrue(StringUtils.isNotBlank(value), "名称不能为空");
			for (char i = 97; i <= 122; i++) {
				if (value.startsWith(Character.valueOf(i).toString())) {
					if (value.length() == 1) {
						value = Character.valueOf((char) (i - 32)).toString();
					} else {
						value = Character.valueOf((char) (i - 32)).toString().concat(value.substring(1, value.length()));
					}
				}
			}

			return value;
		}

		public static Method getGetter(Class clazz, String fieldName) {
			return ClassUtils.getMethod(clazz, "get".concat(upperCaseFirst(fieldName)));
		}

		public static Method getSetter(Class clazz, String fieldName) {
			return ClassUtils.getMethod(clazz, "set".concat(upperCaseFirst(fieldName)));
		}




		@SuppressWarnings("uncheck")
		private static class PropertyReader
		{
			Logger logger = LoggerFactory.getLogger(this.getClass());
			private Field field;
			private Method getter;

			private CriteriaBuilder criteriaBuilder;
			private boolean isQuerable;


			public CriteriaBuilder getCriteriaBuilder() {
				return criteriaBuilder;
			}

			public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
				this.criteriaBuilder = criteriaBuilder;
			}

			public Field getField() {
				return field;
			}

			public void setField(Field field) {
				this.field = field;
			}

			public Method getGetter() {
				return getter;
			}

			public void setGetter(Method getter) {
				this.getter = getter;
			}

			public boolean isQuerable() {
				return isQuerable;
			}

			public void setQuerable(boolean querable) {
				isQuerable = querable;
			}

			public<T>  Predicate getPredicate(Root<T> root,  T example) {


				if (Objects.isNull(example) ) {

					return null;
				}
				//不是可查询字段
				if (!this.isQuerable) {
					return null;
				}

				try {
					Object value= this.getter.invoke(example);
					if (Objects.isNull(value) ) {
						return null;
					}

					return this.criteriaBuilder.equal(root.get(this.field.getName()),value);

				} catch (IllegalAccessException ex) {
					logger.error("message", ex);
				} catch (InvocationTargetException ex) {
					logger.error("message", ex);
				}

				return null;

			}


			public<T>  Predicate getPredicate(Root<T> root,  T firstData, T secondData) {

				if (!firstData.getClass().equals(secondData.getClass())) {
					return null;
				}

				if (Objects.isNull(firstData) || Objects.isNull(secondData)) {

					return null;
				}
				//不是可查询字段
				if (this.isQuerable==false) {
					return null;
				}

				try {

					Object firstObj=this.getter.invoke(firstData);
					Object secondObj =this.getter.invoke(secondData);
					List<Object> data = new ArrayList<>();
					data.add(firstObj);
					data.add(secondObj);
					data = data.stream().filter(o -> Objects.nonNull(o)).collect(Collectors.toList());

					if (Objects.isNull(data) || data.size()==0) {
						return null;
					}

					if (this.field.getType().isEnum()) {
						//进行in合
						return root.get(this.field.getName()).in(data);
					}

					if (this.field.getType().isAssignableFrom(String.class)) {
						//按in处理
						return root.get(this.field.getName()).in(data);
					}

					if (Comparable.class.isAssignableFrom(this.field.getType())) {
						if (data.size()==1) {
							return this.criteriaBuilder.equal(root.get(this.field.getName()),data.get(0));
						}
						if (data.size()==2) {
							List<Comparable> comparableData = data.stream().map(o -> (Comparable) o).collect(Collectors.toList());

							comparableData.sort(Comparable::compareTo);
							return this.criteriaBuilder.between(root.get(this.field.getName()), comparableData.get(0), comparableData.get(1));
						}
					}


				} catch (IllegalAccessException ex) {
					logger.error("message", ex);
				} catch (InvocationTargetException ex) {
					logger.error("message", ex);
				}

				return null;

			}
		}

		private static class PropertyUpdater {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			private Field field;
			private Method getter;
			private Method setter;
			private Boolean writeOnlyOnce;
			private int control;

			public PropertyUpdater() {

			}


			public Field getField() {
				return field;
			}


			public Method getGetter() {
				return getter;
			}

			public Method getSetter() {
				return setter;
			}

			public Boolean getWriteOnlyOnce() {
				return writeOnlyOnce;
			}

			public void setField(Field field) {
				this.field = field;
			}


			public void setGetter(Method getter) {
				this.getter = getter;
			}


			public void setSetter(Method setter) {
				this.setter = setter;
			}

			public void setWriteOnlyOnce(Boolean writeOnlyOnce) {
				this.writeOnlyOnce = writeOnlyOnce;
			}

			public int getControl() {
				return control;
			}

			public void setControl(int control) {
				this.control = control;
			}


			public void updateSelective(Object current, Object data, BiPredicate<Object,Object> predicate,int right)  {
				try {


//                logger.trace("需要权限{}/{},实际权限{}",this.field.getName(),this.control,right);
					if ((this.control & right)==0) {
						//没有权限
//                    logger.trace("由于权限问题，字段{}#{}未被处理",Objects.isNull(current)?null: current.getClass(),this.field.getName());
						return;
					}
					//以下是有权限的,

					Object currentValue=getter.invoke(current);
					Object updateValue=getter.invoke(data);
//                logger.trace("predicate.test:{}",predicate.test(currentValue,updateValue));
					boolean flag=false;
					if (writeOnlyOnce) {

						if (Objects.isNull(currentValue) &&  predicate.test(currentValue,updateValue) ) {
//                        logger.trace("更新字段值{} 原值{} 新值 {}",this.field.getName(),currentValue,updateValue);
							flag=true;
							setter.invoke(current, qualifiedParam(updateValue));
						}
					} else if(predicate.test(currentValue,updateValue)) {
						//这里是有可能进行空赋值的，需要特殊处理
						//而且有可能是对基本类型进行空赋值
//                    logger.trace("更新字段值{} 原值{} 新值 {}",this.field.getName(),currentValue,updateValue);
						flag=true;
						setter.invoke(current, qualifiedParam(updateValue));
					}
					if (!flag) {
//                    logger.trace("由于不符合更新条件，字段{}没有被更新",this.field.getName());
					}
				} catch (IllegalAccessException e) {

					PropertyUpdater.this.logger.warn("updateSelective检测到未预期到的非法访问异常，更新可能未按预期进行");
				}
				catch (InvocationTargetException e) {
					PropertyUpdater.this.logger.warn("updateSelective检测到未预期到的方法调用异常，更新可能未按预期进行");
				}
			}


			public void setNull(Object current , int right)  {
				try {
					if ((this.control & right)==0) {
						return;
					}
					if (!writeOnlyOnce) {
						if (this.field.getType().isPrimitive()) {
							PropertyUpdater.this.logger.warn("setNull检测到非法的参数类型，更新可能未按预期进行");
							return;
						}

						setter.invoke(current,new Object[]{null});
					}

				} catch (IllegalAccessException e) {

					PropertyUpdater.this.logger.warn("setNull检测到未预期到的非法访问异常，更新可能未按预期进行");
				}
				catch (InvocationTargetException e) {
					PropertyUpdater.this.logger.warn("setNull检测到未预期到的方法调用异常，更新可能未按预期进行");
				}
			}

			//此方法的作用是，将当前字段的值设为空，不进行权限检查
			public void setNullForce(Object data)
			{
				try {
					setter.invoke(data, qualifiedNull());
				} catch (IllegalAccessException e) {

					PropertyUpdater.this.logger.warn("setNull检测到未预期到的非法访问异常，更新可能未按预期进行");
				} catch (InvocationTargetException e) {
					PropertyUpdater.this.logger.warn("setNull检测到未预期到的方法调用异常，更新可能未按预期进行");
				}
			}

			//这个方法是有歧义的，目前的作用是，如果没有权限则清空值，主要用于在add方法里，用于清理非法的输入值
			public void clear(Object data,int right) {
				try {
					if ((this.control & right)==0) {
						//如果字段的canwrite值为none，则此处无法清理
						setter.invoke(data, qualifiedNull());
					}
				} catch (IllegalAccessException e) {

					PropertyUpdater.this.logger.warn("setNull检测到未预期到的非法访问异常，更新可能未按预期进行");
				} catch (InvocationTargetException e) {
					PropertyUpdater.this.logger.warn("setNull检测到未预期到的方法调用异常，更新可能未按预期进行");
				}
			}


			private Object[] qualifiedNull() {
				if (this.field.getType().isPrimitive()) {
					return new Object[]{0};
				}
				else
				{
					return new Object[]{null};
				}
			}

			private Object[] qualifiedParam(Object value) {
				if (this.field.getType().isPrimitive()) {
					if (Objects.isNull(value)) {
						//以0作为初始值
						return new Object[]{0};
					}
					else
					{
						//自动拆装箱操作
						return new Object[]{value};
					}
				}
				else
				{
					if (Objects.isNull(value)) {
						return new Object[]{null};
					}
					else
					{
						return new Object[]{value};
					}
				}
			}


		}

		public static class BeanFieldInfo
		{
			private Field field;
			private Method getter;
			private Method setter;

			public BeanFieldInfo()
			{

			}
			public BeanFieldInfo(Field field,Method getter,Method setter)
			{
				this.field=field;
				this.getter=getter;
				this.setter=setter;
			}
			public Field getField() {
				return field;
			}

			public void setField(Field field) {
				this.field = field;
			}

			public Method getGetter() {
				return getter;
			}

			public void setGetter(Method getter) {
				this.getter = getter;
			}

			public Method getSetter() {
				return setter;
			}

			public void setSetter(Method setter) {
				this.setter = setter;
			}
		}
}
