package com.ssh.service.practice.common;

import lombok.Data;

@Data
public class HttpResult<T> {
	private Integer code;
	private String desc;
	private T data;

	public HttpResult(Integer code,String desc,T data) {
		this.code = code;
		this.desc = desc;
		this.data = data;
	}

	public HttpResult(Integer code,String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static <X> HttpResult<X> OK(X data) {
		return new HttpResult<>(HttpStatus.OK, "成功",data);
	}
}
