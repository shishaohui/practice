package com.ssh.service.practice.digibig;

import com.ssh.service.practice.util.DomainUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldInfo<T> {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private Field field;
	private Method getter;
	private Method setter;

	public FieldInfo() {
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Method getGetter() {
		return this.getter;
	}

	public void setGetter(Method getter) {
		this.getter = getter;
	}

	public Method getSetter() {
		return this.setter;
	}

	public void setSetter(Method setter) {
		this.setter = setter;
	}

	public static <X> FieldInfo<X> extractField(Class<X> clazz, String fieldName) {
		String getterName = "get" + DomainUtil.upperCaseFirst(fieldName);
		String setterName = "set" + DomainUtil.upperCaseFirst(fieldName);
		Method setter = null;

		Method getter;
		try {
			getter = clazz.getMethod(getterName);
			Method[] methods = clazz.getMethods();

			for(int i = 0; i < methods.length; ++i) {
				if (methods[i].getName().equals(setterName)) {
					setter = methods[i];
					break;
				}
			}
		} catch (NoSuchMethodException var9) {
			throw new RuntimeException(var9);
		}

		Field field;
		try {
			field = getter.getDeclaringClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException var8) {
			throw new RuntimeException(var8);
		}

		FieldInfo<X> result = new FieldInfo();
		result.setField(field);
		result.setGetter(getter);
		result.setSetter(setter);
		return result;
	}
}
