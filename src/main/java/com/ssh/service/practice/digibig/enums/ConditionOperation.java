/*
 * Copyright 2017 - 数多科技
 *
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。
 * 本内容为保密信息，仅限本公司内部使用。
 * 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.ssh.service.practice.digibig.enums;

import com.ssh.service.practice.common.commonenums.AbstractCodeConverter;
import com.ssh.service.practice.common.commonenums.AbstractIdConverter;
import com.ssh.service.practice.common.commonenums.AbstractValueConverter;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import com.ssh.service.practice.enums.ElectiveCourseStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum ConditionOperation implements CommonEnum<ConditionOperation> {

	EQUAL(Integer.valueOf(0), Integer.valueOf(1), "EQUAL", "EQUAL"),
	NOT_EQUAL(Integer.valueOf(0), Integer.valueOf(2), "NOT_EQUAL", "NOT EQUAL"),
	IN(Integer.valueOf(0), Integer.valueOf(3), "IN", "IN"),
	NOT_IN(Integer.valueOf(0), Integer.valueOf(4), "NOT_IN", "NOT IN"),
	GT(Integer.valueOf(0), Integer.valueOf(5), "GT", "GT"),
	GE(Integer.valueOf(0), Integer.valueOf(6), "GE", "GE"),
	LT(Integer.valueOf(0), Integer.valueOf(7), "LT", "LT"),
	LE(Integer.valueOf(0), Integer.valueOf(8), "LE", "LE"),
	BETWEEN(Integer.valueOf(0), Integer.valueOf(9), "BETWEEN", "BETWEEN"),
	LIKE(Integer.valueOf(0), Integer.valueOf(10), "LIKE", "LIKE"),
	NOT_LIKE(Integer.valueOf(0), Integer.valueOf(11), "NOT_LIKE", "NOT LIKE"),
	IS_NULL(Integer.valueOf(0), Integer.valueOf(12), "IS_NULL", "IS NULL"),
	NOT_NULL(Integer.valueOf(0), Integer.valueOf(13), "NOT_NULL", "NOT NULL");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	ConditionOperation(Integer id, Integer value, String code, String name) {
		this.id = id;
		this.value = value;
		this.code = code;
		this.name = name;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

	@Override
	public String getCode() {
		return this.code;
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