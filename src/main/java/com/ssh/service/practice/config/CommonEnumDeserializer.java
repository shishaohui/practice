package com.ssh.service.practice.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ssh.service.practice.common.commonenums.CommonEnum;
import java.io.IOException;
import java.lang.reflect.Method;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反序列化器  将二进制转换为对象
 */
public class CommonEnumDeserializer<X extends CommonEnum> extends JsonDeserializer<X> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Method method;
	private Class<X> clazz;

	public CommonEnumDeserializer(Class<X> clazz) {
		Method target;
		try {
			target = clazz.getMethod("fromString", String.class);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		this.method = target;
	}


	@Override
	public X deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		//有可能是文件，有可能object，如果是object，则去顺序读取
		//注意，这里并无法确定具体的枚举类
		logger.trace("开始进行序列化操作");
		String token = "";
		String id = "";
		String code = "";
		String value = "";
		if (p.hasToken(JsonToken.START_OBJECT)) {
			logger.trace("检测到是object输入，开始处理");
			//读取id,value,code
			boolean flag = true;
			String tokenName, tokenValue;
			//用于处理{}的情况
			p.nextToken();
			if (p.hasToken(JsonToken.END_OBJECT)) {
				logger.trace("没有有效内容，终止处理");
				flag = false;
			}
			logger.trace("开始进行循环解析");
			while (flag) {
				tokenName = p.getText();
				tokenValue = p.nextTextValue();
				this.logger.trace("当前值 {} {}", tokenName, tokenValue);
				switch (tokenName) {
					case "id":
						id = tokenValue;
						break;
					case "code":
						code = tokenValue;
						break;
					case "value":
						value = tokenValue;
						break;
					default:
						break;
				}
				p.nextToken();
				if (p.hasToken(JsonToken.END_OBJECT)) {
					flag = false;
				}
			}
			if (StringUtils.isNotBlank(code)) {
				token = code;
			} else if (StringUtils.isNotBlank(value)) {
				token = value;
			} else if (StringUtils.isNotBlank(id)) {
				token = id;
			}
		} else if (p.hasToken(JsonToken.VALUE_STRING) || p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
			//按字符串进和解析处理
			token = p.getValueAsString();
		}
		logger.trace("取得有效token为 {}", token);
		if (StringUtils.isBlank(token)) {
			logger.trace("最终值为空，返回空值");
			return null;
		} else {
			logger.trace("使用fromstring方法处理");
			try {
				return (X) method.invoke(null, token);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

	}
}
