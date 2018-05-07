/*
 * @(#)WebConfig.java   17/10/09
 *
 * Copyright (c) 2017 北京数多信息科技有限公司
 * 仅限公司内部使用，未经授权，不得分发、复制、使用
 */


package com.ssh.service.practice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ForkJoinPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableAspectJAutoProxy
public class WebConfig implements WebMvcConfigurer {

	Logger logger = LoggerFactory.getLogger(this.getClass());


	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new CommonEnumConverter());
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	}


	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer
			.favorPathExtension(false)
			.favorParameter(false)
			.ignoreAcceptHeader(true)
			.defaultContentType(MediaType.ALL);
		logger.debug("配置content negotiation");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		AntPathMatcher matcher = new AntPathMatcher();
		matcher.setCaseSensitive(false);
		configurer.setPathMatcher(matcher);
		logger.debug("配置url路径匹配大小写不敏感");
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
	}


	@Override
	public Validator getValidator() {
		return null;
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		return null;
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (int i = 0; i < converters.size(); i++) {
			logger.trace(converters.get(i).toString());
			if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) converters.get(i);
				//配置反序列化参数
				ObjectMapper mapper = converter.getObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);//这个会是什么效果？如果允许的话，会当做ordinal来处理？是否会覆盖creator
				mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
				mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
				mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
				mapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, true);
				//配置序列化参数
				mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, true);

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
				mapper.setDateFormat(simpleDateFormat);

				mapper.setLocale(Locale.ENGLISH);
				mapper.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
				logger.trace("检测到{}/{}并已重新配置", converter.toString(), mapper.toString());
			}
			//如果需要使用xml格式，则需要另行配置MessageConverter，且添加jar包，详见demo
		}
	}


	//提供一个线程池，这个线程池可以用于并发执行任务
	@Bean
	public ForkJoinPool forkJoinPool() {
		//使用默认的的
		return ForkJoinPool.commonPool();
	}

}

