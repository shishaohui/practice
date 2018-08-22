package com.ssh.service.practice.common.commonenums;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractValueConverter<T extends CommonEnum> extends BaseTypeHandler<T> implements AttributeConverter<T, Integer> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Class<T> clazz;
	private Method methodOfValue;
	private Method methodFromString;

	public AbstractValueConverter(Class<T> clazz) {
		this.clazz = clazz;
		try {
			this.methodOfValue = clazz.getDeclaredMethod("valueOf", Integer.class);
			this.methodFromString = clazz.getDeclaredMethod("fromString", String.class);
		} catch (Exception e) {
			logger.error("AbstractValueConverter初始化异常", e);
		}
	}


	public Integer convertToDatabaseColumn(T attribute) {
		return Objects.isNull(attribute) ? null : attribute.getValue();
	}

	public T convertToEntityAttribute(Integer dbData) {
		try {
			return Objects.isNull(dbData) ? null : (T) this.methodOfValue.invoke(null, dbData);
		} catch (Exception e) {
			this.logger.error("由DBValue转为目标对象异常", e);
			return null;
		}
	}

	public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getValue());
	}


	public T getNullableResult(ResultSet rs, String column) {
		try {
			Object value = rs.getObject(column);
			return Objects.isNull(value) ? null : (T) this.methodOfValue.invoke(null, (Integer) value);
		} catch (Exception var4) {
			this.logger.error("由DBValue 转为目标对象 异常", var4);
			return null;
		}
	}

	public T getNullableResult(ResultSet rs, int columnIndex) {
		try {
			Object value = rs.getObject(columnIndex);
			return Objects.isNull(value) ? null : (T) this.methodOfValue.invoke(null, (Integer) value);
		} catch (Exception var4) {
			this.logger.error("由DBValue 转为目标对象异常", var4);
			return null;
		}
	}

	public T getNullableResult(CallableStatement cs, int columnIndex) {
		try {
			Object value = cs.getObject(columnIndex);
			return Objects.isNull(value) ? null : (T)this.methodOfValue.invoke(null, (Integer)value);
		} catch (Exception var4) {
			this.logger.error("由DBValue转为目标对象失败", var4);
			return null;
		}
	}
}
