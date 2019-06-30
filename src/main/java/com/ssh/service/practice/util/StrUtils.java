package com.ssh.service.practice.util;

/**
 * 1、如果已知支付串的当前字符不是大于0的数字字符，则复制该字符于新字符串中
 * 2、如果是一个数字字符，且它之后没有后续字符，则简单地将它复制到新字符串中
 * 3、如果当前字符是一个大于0的数字字符，并且还有后续字符，
 * 设该数字字符的面值为n，则将它的后续字符（包括后续字符是一个数字字符）重复复制n+1到新字符串中
 * 4、若已知支付串的当前字符是下划线'_'，则将当前支付串变更为'|UL'
 * 5、以上述一次变更为一组，在不同组之间另插入一个下划线'_'用于分割
 *
 * 例如 24ab_2t2 ---->   444_aaaaa_a_b_|UL_ttt_t_2
 * * */
public class StrUtils {
	private static String data = "24ab_2t";
	private static String decode(String str){
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {

		}
		return null;
	}
}
