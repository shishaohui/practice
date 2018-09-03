package com.ssh.service.practice.enums;

import com.ssh.service.practice.common.commonenums.AbstractCodeConverter;
import com.ssh.service.practice.common.commonenums.AbstractIdConverter;
import com.ssh.service.practice.common.commonenums.AbstractValueConverter;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum TemplateType implements CommonEnum<TemplateType> {

	MEDICAL(0,1,"MEDICAL","病历单"),
	SURGERY(0,3,"SURGERY","门诊手术记录"),
	CONTRACT(0,2,"CONTRACT","合同");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	TemplateType(Integer id, Integer value, String code, String name) {
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

	public static TemplateType valueOf(Integer value){
		return CommonEnum.valueOf(TemplateType.class, value);
	}

	public static TemplateType fromString(String str){
		return CommonEnum.fromString(TemplateType.class, str.trim());
	}

	@MappedTypes(TemplateType.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsId extends AbstractIdConverter<TemplateType> {
		public AsId() {
			super(TemplateType.class);
		}
	}

	@MappedTypes(TemplateType.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsValue extends AbstractValueConverter<TemplateType> {

		public AsValue() {
			super(TemplateType.class);
		}
	}

	@MappedTypes(TemplateType.class)
	@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR}, includeNullJdbcType = true)
	public static class AsCode extends AbstractCodeConverter<TemplateType> {

		public AsCode() {
			super(TemplateType.class);
		}
	}
}
