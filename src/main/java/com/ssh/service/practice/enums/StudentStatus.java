package com.ssh.service.practice.enums;

import com.ssh.service.practice.common.commonenums.AbstractCodeConverter;
import com.ssh.service.practice.common.commonenums.AbstractIdConverter;
import com.ssh.service.practice.common.commonenums.AbstractValueConverter;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum StudentStatus implements CommonEnum<StudentStatus> {

	IN_SCHOOL(0,1,"IN_SCHOOL","在校"),
	FINISH_SCHOOL(0,2,"FINISH_SCHOOL","毕业"),
	INDUSTRY(0,3,"INDUSTRY","辍业");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	StudentStatus(Integer id, Integer value, String code, String name) {
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

	public static StudentStatus valueOf(Integer value){
		return CommonEnum.valueOf(StudentStatus.class, value);
	}

	public static StudentStatus fromString(String str){
		return CommonEnum.fromString(StudentStatus.class, str.trim());
	}

	@MappedTypes(StudentStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsId extends AbstractIdConverter<StudentStatus> {
		public AsId() {
			super(StudentStatus.class);
		}
	}

	@MappedTypes(StudentStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsValue extends AbstractValueConverter<StudentStatus> {

		public AsValue() {
			super(StudentStatus.class);
		}
	}

	@MappedTypes(StudentStatus.class)
	@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR}, includeNullJdbcType = true)
	public static class AsCode extends AbstractCodeConverter<StudentStatus> {

		public AsCode() {
			super(StudentStatus.class);
		}
	}
}
