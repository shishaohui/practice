package com.ssh.service.practice.exception;

public class BusinessException extends RuntimeException {
	public BusinessException(){
		super();
	}

	public BusinessException(String message){
		super(message);
	}

	public BusinessException(String message, Throwable ex) {
		super(message,ex);
	}

}
