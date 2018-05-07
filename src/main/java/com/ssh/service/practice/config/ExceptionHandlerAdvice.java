package com.ssh.service.practice.config;

import com.ssh.service.practice.common.HttpResult;
import com.ssh.service.practice.common.HttpStatus;
import com.ssh.service.practice.exception.BusinessException;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller层的异常捕获处理
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	//处理非法参数的异常
	@ResponseBody
	@ExceptionHandler({IllegalArgumentException.class})
	public HttpResult webErrorHandler(IllegalArgumentException e){
		logger.info(e.getMessage(),e);
		return new HttpResult(HttpStatus.PARAM_ERROR, "error:"+ e.getMessage());
	}

	//处理不符合业务逻辑 抛出异常
	@ResponseBody
	@ExceptionHandler({BusinessException.class})
	public HttpResult businessErrorHandler(BusinessException e){
		logger.info(e.getMessage(),e);
		return new HttpResult(HttpStatus.SERVER_ERROR, "error:"+ e.getMessage());
	}

	//处理所有未知异常
	@ResponseBody
	@ExceptionHandler({Exception.class})
	public HttpResult exceptionHandler(Exception e){
		logger.info(e.getMessage(),e);
		return new HttpResult(HttpStatus.SERVER_ERROR, "服务器内部错误！"+e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler({ValidationException.class})
	public HttpResult validationHandler(ValidationException e) {
		logger.info(e.getMessage(),e);
		return new HttpResult(HttpStatus.PARAM_ERROR, "参数验证错误！"+e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public HttpResult methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
		logger.info(e.getMessage(),e);
		return new HttpResult(HttpStatus.PARAM_ERROR, "参数验证错误！"+e.getMessage());
	}
}
