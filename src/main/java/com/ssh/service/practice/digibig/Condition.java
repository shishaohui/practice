package com.ssh.service.practice.digibig;

import com.ssh.service.practice.digibig.enums.ConditionOperation;
import java.util.List;

public interface Condition<T, R> {
	Class<T> getClazz();

	void setClazz(Class<T> var1);

	String getField();

	void setField(String var1);

	ConditionOperation getOperation();

	void setOperation(ConditionOperation var1);

	List<R> getData();

	void setData(List<R> var1);

	void append(R var1);
}
