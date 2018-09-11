package com.ssh.service.practice.util;

import com.ssh.service.practice.common.commonenums.CanWrite;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DomainUtil {

	public <T> T clear(T data, CanWrite... control) {
		return null;
	}

	public static String lowerCaseFirst(String value) {
		Assert.isTrue(StringUtils.isNotBlank(value), "名称不能为空!");
		for (char i = 'A'; i <= 'Z'; i++) {
			if (value.startsWith(String.valueOf(i))) {
				if (value.length() == 1) {
					value = String.valueOf((char)(i + 32));
				} else {
					value = String.valueOf((char)(i + 32)).concat(value.substring(1, value.length()));
				}
			}
		}
		return value;
	}

	public static String upperCaseFirst(String value) {
		Assert.isTrue(StringUtils.isNotBlank(value), "名称不能为空");

		for (char i = 'a'; i <= 'z'; ++i) {
			if (value.startsWith(String.valueOf(i))) {
				if (value.length() == 1) {
					value = String.valueOf((char)(i-32));
				} else {
					value = String.valueOf((char)(i - 32)).concat(value.substring(1, value.length()));
				}
			}
		}

		return value;
	}
}
