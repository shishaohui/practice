package com.ssh.service.practice.security.base64;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class testMD5 {

	private static final String src = "ssh security MD5";
	private static final String key = "5mwyizsMwZD8WwmOgneNipS8b2omU";

	public static void main(String[] args) {
		jdkMd5();
		ccMd5();
	}

	private static void jdkMd5(){
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] digestBytes = messageDigest.digest((src + "&" + key).getBytes());
			//第一种转换16进制的方式
			StringBuilder sb = new StringBuilder();
			for (byte item : digestBytes) {
				sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
			}
			System.out.println("转换为16进制==="+sb.toString());
			//第二种转换为16进制的方法
			System.out.println("Hex(16进制) MD5签名:" + Hex.encodeHexString(digestBytes));
			System.out.println("Base64 MD5签名:" + Base64.encodeBase64String(digestBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void ccMd5(){
		String md5Hex = DigestUtils.md5Hex((src + "&" + key));
		System.out.println("ccMD5 hex:"+md5Hex);
	}
}
