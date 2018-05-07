package com.ssh.service.practice.enums;

import com.ssh.service.practice.common.commonenums.AbstractCodeConverter;
import com.ssh.service.practice.common.commonenums.AbstractIdConverter;
import com.ssh.service.practice.common.commonenums.AbstractValueConverter;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum TeacherStatus implements CommonEnum<TeacherStatus> {

	IN_OFFICE(0,1,"IN_OFFICE","在职"),
	LEAVE_OFFICE(0,2,"LEAVE_OFFICE","离职"),
	HOLIDAY(0,3,"HOLIDAY","休假"),
	RETIRE(0,4,"RETIRE","退休");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	TeacherStatus(Integer id, Integer value, String code, String name) {
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

	public static TeacherStatus valueOf(Integer value){
		return CommonEnum.valueOf(TeacherStatus.class, value);
	}

	public static TeacherStatus fromString(String str){
		return CommonEnum.fromString(TeacherStatus.class, str.trim());
	}

	@MappedTypes(TeacherStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsId extends AbstractIdConverter<TeacherStatus> {
		public AsId() {
			super(TeacherStatus.class);
		}
	}

	@MappedTypes(TeacherStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsValue extends AbstractValueConverter<TeacherStatus> {

		public AsValue() {
			super(TeacherStatus.class);
		}
	}

	@MappedTypes(TeacherStatus.class)
	@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR}, includeNullJdbcType = true)
	public static class AsCode extends AbstractCodeConverter<TeacherStatus> {

		public AsCode() {
			super(TeacherStatus.class);
		}
	}
}
