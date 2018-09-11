package com.ssh.service.practice.digibig;

import com.ssh.service.practice.digibig.enums.ConditionOperation;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCrossFieldCondition<T,R> implements Condition<T,R> {

	private Class<T> clazz;
	private String onName;
	private String field;
	private ConditionOperation operation;
	private List<R> data;
	public AbstractCrossFieldCondition() {
		this.data = new ArrayList<>(2);
	}

	public AbstractCrossFieldCondition(Class<T> clazz) {
		this.clazz=clazz;
	}
	@Override
	public Class<T> getClazz() {
		return clazz;
	}
	@Override
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public String getOnName() {
		return onName;
	}

	public void setOnName(String onName) {
		this.onName = onName;
	}
	@Override
	public ConditionOperation getOperation() {
		return operation;
	}
	@Override
	public void setOperation(ConditionOperation operation) {
		this.operation = operation;
	}

	@Override
	public List<R> getData() {
		return data;
	}

	@Override
	public void setData(List<R> data) {
		this.data = data;
	}


	@Override
	public void append(R par) {
		this.data.add(par);
	}
	@Override
	public String getField() {
		return field;
	}
	@Override
	public void setField(String field) {
		this.field = field;
	}

}
