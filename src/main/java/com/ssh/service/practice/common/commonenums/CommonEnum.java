package com.ssh.service.practice.common.commonenums;

import java.util.Objects;
import org.apache.commons.lang.math.NumberUtils;

public interface CommonEnum<T extends Enum & CommonEnum> {

	Integer getValue();

	Integer getId();

	String getCode();

	String getName();

	/**
	 * 优先按照value匹配，再按照id匹配
	 * @param clazz
	 * @param value
	 * @param <X>
	 * @return
	 */
	static <X extends Enum & CommonEnum> X valueOf(Class<X> clazz, Integer value) {
		X[] values = clazz.getEnumConstants();
		X target = null;
		for (X x : values) {
			if(x.getValue().equals(value)){
				target = x;
				break;
			}
		}
		if(Objects.isNull(target)){
			for (X x : values) {
				if(x.getId().equals(value)){
					target = x;
					break;
				}
			}
		}
		return target;
	}

	/**
	 * 将字符串转换为枚举 首先按照key值转换 然后按照code转换 最后按照name转换
	 * @param clazz
	 * @param str
	 * @param <X>
	 * @return
	 */
	static <X extends Enum & CommonEnum> X fromString(Class<X> clazz, String str) {
		X target = null;
		//按照key转换
		try{
			target = (X) Enum.valueOf(clazz, str);
		}catch(Exception e){
		    ;
		}

		if(Objects.isNull(target)){
			X[] values = clazz.getEnumConstants();
			//按照code转换
			for (X x : values) {
				if(x.getCode().equals(str)){
					target = x;
					break;
				}
			}
			//按照name转换
			if(Objects.isNull(target)){
				for (X x : values) {
					if(x.getName().equals(str)){
						target = x;
						break;
					}
				}
			}
			if(Objects.isNull(target) && NumberUtils.isNumber(str)) {
				target = valueOf(clazz, Integer.parseInt(str));
			}
		}
		if(Objects.isNull(target)){
			throw new IllegalArgumentException("No enum constant " + clazz.getCanonicalName() + "." + str);
		}

		return target;
	}

	default boolean in(T... items) {
		if(Objects.nonNull(items) && items.length > 0){
			for (T t : items) {
				if(this.equals(t)){
					return true;
				}
			}
		}
		return false;
	}
}
