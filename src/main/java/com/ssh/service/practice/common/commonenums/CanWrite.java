package com.ssh.service.practice.common.commonenums;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum CanWrite implements CommonEnum<CanWrite> {

	NONE(0, 0, "NONE", "无法更改"),
	INTERNAL(0, 1, "INTERNAL", "内部服务可以更改"),
	EXTERNAL(0, 2, "EXTERNAL", "外部调用可以更改"),
	ALL(0, 3, "ALL", "不进行权限控制");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	CanWrite(Integer id, Integer value, String code, String name) {
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

	public static CanWrite valueOf(Integer value){
		return CommonEnum.valueOf(CanWrite.class, value);
	}

	public static CanWrite fromString(String str){
		return CommonEnum.fromString(CanWrite.class, str.trim());
	}

	@MappedTypes(CanWrite.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsId extends AbstractIdConverter<CanWrite> {
		public AsId() {
			super(CanWrite.class);
		}
	}

	@MappedTypes(CanWrite.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsValue extends AbstractValueConverter<CanWrite> {

		public AsValue() {
			super(CanWrite.class);
		}
	}

	@MappedTypes(CanWrite.class)
	@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR}, includeNullJdbcType = true)
	public static class AsCode extends AbstractCodeConverter<CanWrite> {

		public AsCode() {
			super(CanWrite.class);
		}
	}
}
