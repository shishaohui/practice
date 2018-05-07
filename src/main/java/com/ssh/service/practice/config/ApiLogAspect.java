package com.ssh.service.practice.config;


import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * aop的日志打印处理
 */
@Aspect
@Component
@Order(700)  //值越小拥有越高的权限
public class ApiLogAspect {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public ApiLogAspect() {
	}

	@Pointcut("execution(public * com.ssh..*.controller.*ontroller.*(..)) || execution(public * com.ssh..*.controller..*.*ontroller.*(..)) ")
	public void webLog(){
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable{
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(Objects.nonNull(attributes)){
			HttpServletRequest request = attributes.getRequest();
			logger.info("url:{}",request.getRequestURL().toString());
			logger.info("request method:{}",request.getMethod());
			logger.info("remote ip:{}",request.getRemoteAddr());
			logger.info("class method:{}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
			logger.info("request params:{}", Arrays.toString(joinPoint.getArgs()));
		}
	}

	@AfterReturning(returning = "ret",pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable{
		logger.info("response:{}",ret);
	}

	@Around("webLog()")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		long startTime = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		long endTime = System.currentTimeMillis();
		logger.info("{} execute time :{}ms",proceedingJoinPoint.getSignature(),endTime-startTime);
		return result;
	}
}
