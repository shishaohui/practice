package com.ssh.service.practice.domain;

public class Message {

   //异常提示消息
	public final String MESSAGE_KEY_TYPE_NOT_SUPPORTED = "只支持Integer类型的主键";

	public final String MESSAGE_KEY_IS_NULL = "对象主键为空";

	public final String MESSAGE_TARGET_NOT_EXIST = "目标对象不存在";

	public final String MESSAGE_CONSTRAIN_VIOLATION;

	public final String MESSAGE_SWITCH_NOT_SUPPORTED;

	public final String MESSAGE_DUPLICATE_DATA = "包含重复数据";

	public final String MESSAGE_TENANT_ID_IS_NULL = "tenantId为空";

	public final String MESSAGE_FOREIGN_KEY_TYPE_NOT_SUPPORTED = "只支持Integer类型的外键约束";

	public final String MESSAGE_FOREIGN_KEY_IS_NULL = "对象的外键为空";

	public final String MESSAGE_LIST_HAS_NO_TARGET;

	public final String MESSAGE_ONLY_ONE_PARENT = "仅允许对同一个交类对象进行操作";

	public final String MESSAGE_PARENT_NOT_EXIST;

	private String targetClassName;

	public Message(String targetClassName) {
		this.targetClassName = targetClassName;
		this.MESSAGE_CONSTRAIN_VIOLATION = String.format("%s格式校验失败", this.targetClassName);
		this.MESSAGE_LIST_HAS_NO_TARGET = String.format("指定的List<%s>中不包指定的元素", this.targetClassName);
		this.MESSAGE_SWITCH_NOT_SUPPORTED = String.format("%s不支持Switch操作", this.targetClassName);
		this.MESSAGE_PARENT_NOT_EXIST = String.format("%s的上级对象不存在", this.targetClassName);
	}
}
