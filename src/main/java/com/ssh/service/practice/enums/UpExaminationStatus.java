package com.ssh.service.practice.enums;

import com.ssh.service.practice.common.commonenums.AbstractCodeConverter;
import com.ssh.service.practice.common.commonenums.AbstractIdConverter;
import com.ssh.service.practice.common.commonenums.AbstractValueConverter;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum UpExaminationStatus implements CommonEnum<UpExaminationStatus> {
	NEW(0,1,"NEW","新建"),
	FINISH(0,2,"FINISH","已完成");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	UpExaminationStatus(Integer id, Integer value, String code, String name) {
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

	public static UpExaminationStatus valueOf(Integer value){
		return CommonEnum.valueOf(UpExaminationStatus.class, value);
	}

	public static UpExaminationStatus fromString(String str){
		return CommonEnum.fromString(UpExaminationStatus.class, str.trim());
	}

	@MappedTypes(UpExaminationStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsId extends AbstractIdConverter<UpExaminationStatus> {
		public AsId() {
			super(UpExaminationStatus.class);
		}
	}

	@MappedTypes(UpExaminationStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsValue extends AbstractValueConverter<UpExaminationStatus> {

		public AsValue() {
			super(UpExaminationStatus.class);
		}
	}

	@MappedTypes(UpExaminationStatus.class)
	@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR}, includeNullJdbcType = true)
	public static class AsCode extends AbstractCodeConverter<UpExaminationStatus> {

		public AsCode() {
			super(UpExaminationStatus.class);
		}
	}
}
