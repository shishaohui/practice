package com.ssh.service.practice.enums;

import com.ssh.service.practice.common.commonenums.AbstractCodeConverter;
import com.ssh.service.practice.common.commonenums.AbstractIdConverter;
import com.ssh.service.practice.common.commonenums.AbstractValueConverter;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum Sex implements CommonEnum<Sex> {

	MAN(0,1,"MAN","男"),
	WOMAN(0,2,"WOMAN","女");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	Sex(Integer id, Integer value, String code, String name) {
		this.id = id;
		this.value =value;
		this.code = code;
		this.name = name;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public static Sex valueOf(Integer value){
		return CommonEnum.valueOf(Sex.class, value);
	}

	public static Sex fromString(String str){
		return CommonEnum.fromString(Sex.class, str.trim());
	}

	@MappedTypes(Sex.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsId extends AbstractIdConverter<Sex> {
		public AsId() {
			super(Sex.class);
		}
	}

	@MappedTypes(Sex.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsValue extends AbstractValueConverter<Sex> {

		public AsValue() {
			super(Sex.class);
		}
	}

	@MappedTypes(Sex.class)
	@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR}, includeNullJdbcType = true)
	public static class AsCode extends AbstractCodeConverter<Sex> {

		public AsCode() {
			super(Sex.class);
		}
	}
}
