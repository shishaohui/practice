package com.ssh.service.practice.security.base64;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * jdk和cc的两种base64方式
 */
public class SshBase64 {

	private static String data = "ssh security base64";
	public static void main(String[] args) {
//		jdkBase64();
		commonsCodesBase64();
	}

	public static void jdkBase64(){
		BASE64Encoder encoder = new BASE64Encoder();
		String encode = encoder.encode(data.getBytes());
		System.out.println("加密字符串==="+encode);

		BASE64Decoder decoder = new BASE64Decoder();
		String decode = "";
		try {
			byte[] bytes = decoder.decodeBuffer(encode);
			decode = new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("解密字符串===="+decode);
	}

	public static void commonsCodesBase64(){
		byte[] bytes = Base64.encodeBase64(data.getBytes());
		System.out.println("encode======"+new String(bytes));
		byte[] decodeBase64 = Base64.decodeBase64(bytes);
		System.out.println("decode======"+new String(decodeBase64));
	}
}
