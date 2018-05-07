package com.ssh.service.practice.config;

import com.ssh.service.practice.common.commonenums.CommonEnum;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

public class CommonEnumConverter implements GenericConverter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> pairs = new HashSet<>();
		pairs.add(new ConvertiblePair(Integer.class, CommonEnum.class));
		pairs.add(new ConvertiblePair(String.class, CommonEnum.class));
		return pairs;
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

		if (Objects.isNull(source)) {
			return null;
		}
		if (source instanceof String) {
			try {
				Method methodFromString = targetType.getType().getDeclaredMethod("fromString", String.class);
				return methodFromString.invoke(null, source);
			} catch (NoSuchMethodException e1) {
				logger.error("CommonEnum格式转换错误，目标枚举值未实现fromString(String)方法", e1);
			} catch (Exception e2) {
				logger.error("CommonEnum格式转换错误，fromString方法调用失败", e2);
			}
		} else if (source instanceof Integer) {
			try {
				Method methodOfValue = targetType.getType().getDeclaredMethod("valueOf", Integer.class);
				return methodOfValue.invoke(null, source);
			} catch (NoSuchMethodException e1) {
				logger.error("CommonEnum格式转换错误，valueOf(Integer)方法", e1);
			} catch (Exception e2) {
				logger.error("CommonEnum格式转换错误，valueOf", e2);
			}
		}
		//这个异常永远不会执行到
		throw new IllegalArgumentException("解析错误");
	}
}
