package com.ssh.service.practice.digibig;

import com.ssh.service.practice.digibig.enums.ConditionOperation;
import java.util.ArrayList;
import java.util.List;

public class FieldCondition<T, R> implements Condition<T, R> {
	private Class<T> clazz;
	private String field;
	private ConditionOperation operation;
	private List<R> data = new ArrayList(2);

	public FieldCondition() {
	}

	public Class<T> getClazz() {
		return this.clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public ConditionOperation getOperation() {
		return this.operation;
	}

	public void setOperation(ConditionOperation operation) {
		this.operation = operation;
	}

	public List<R> getData() {
		return this.data;
	}

	public void setData(List<R> data) {
		this.data = data;
	}

	public void append(R par) {
		this.data.add(par);
	}
}
