package com.ssh.service.practice.enums;

import com.ssh.service.practice.common.commonenums.AbstractCodeConverter;
import com.ssh.service.practice.common.commonenums.AbstractIdConverter;
import com.ssh.service.practice.common.commonenums.AbstractValueConverter;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

public enum  PayStatus implements CommonEnum<PayStatus> {
	unpaid(0,1,"unpaid","未支付"),
	paid(0,2,"paid","已支付");

	private Integer id;
	private Integer value;
	private String code;
	private String name;

	PayStatus(Integer id, Integer value, String code, String name) {
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

	public static PayStatus valueOf(Integer value){
		return CommonEnum.valueOf(PayStatus.class, value);
	}

	public static PayStatus fromString(String str){
		return CommonEnum.fromString(PayStatus.class, str.trim());
	}

	@MappedTypes(PayStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsId extends AbstractIdConverter<PayStatus> {
		public AsId() {
			super(PayStatus.class);
		}
	}

	@MappedTypes(PayStatus.class)
	@MappedJdbcTypes(value = JdbcType.INTEGER, includeNullJdbcType = true)
	public static class AsValue extends AbstractValueConverter<PayStatus> {

		public AsValue() {
			super(PayStatus.class);
		}
	}

	@MappedTypes(PayStatus.class)
	@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.CHAR}, includeNullJdbcType = true)
	public static class AsCode extends AbstractCodeConverter<PayStatus> {

		public AsCode() {
			super(PayStatus.class);
		}
	}
}
