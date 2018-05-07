package com.ssh.service.practice.enums;

import com.ssh.service.practice.common.commonenums.AbstractCodeConverter;
import com.ssh.service.practice.common.commonenums.AbstractIdConverter;
import com.ssh.service.practice.common.commonenums.AbstractValueConverter;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum ElectiveCourseStatus implements CommonEnum<ElectiveCourseStatus>{
	NEW(0,1,"NEW","选修"),
	FINISH(0,2,"FINISH","完成"),
	FAIL(0,2,"FAIL","挂科");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	ElectiveCourseStatus(Integer id, Integer value, String code, String name) {
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

	public static ElectiveCourseStatus valueOf(Integer value){
		return CommonEnum.valueOf(ElectiveCourseStatus.class, value);
	}

	public static ElectiveCourseStatus fromString(String str){
		return CommonEnum.fromString(ElectiveCourseStatus.class, str.trim());
	}

	@MappedTypes(ElectiveCourseStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsId extends AbstractIdConverter<ElectiveCourseStatus> {
		public AsId() {
			super(ElectiveCourseStatus.class);
		}
	}

	@MappedTypes(ElectiveCourseStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsValue extends AbstractValueConverter<ElectiveCourseStatus> {

		public AsValue() {
			super(ElectiveCourseStatus.class);
		}
	}

	@MappedTypes(ElectiveCourseStatus.class)
	@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR}, includeNullJdbcType = true)
	public static class AsCode extends AbstractCodeConverter<ElectiveCourseStatus> {

		public AsCode() {
			super(ElectiveCourseStatus.class);
		}
	}
}
