package com.ssh.service.practice.security.base64;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class testAES {

	private static String src = "ssh security aes";
	private static String keyAex = "1wBZv8yg+8K75gAi5XckvLGBpSAWlQ=";

	public static void main(String[] args) {
		jdkAES();
	}

	public static void jdkAES(){
		try {
			//获取key
			KeyGenerator generator = getKey(keyAex);
			SecretKey secretKey = generator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();

			//key转换
			Key key = new SecretKeySpec(keyBytes, "AES");

			//加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE,key);

			//base64加密 new String会有乱码
			String encode = Base64.encodeBase64String(cipher.doFinal(src.getBytes()));
			System.out.println("aes encode====="+ encode);
			//JTYIFCatkSsum+5mwyizsMwZD8WwmOgneNipS8b2omU=

			cipher.init(Cipher.DECRYPT_MODE,key);
			byte[] decodeBytes = cipher.doFinal(Base64.decodeBase64(encode));
			System.out.println("decode========="+new String(decodeBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static KeyGenerator getKey(String strKey) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes("UTF-8"));
			generator.init(128, secureRandom);
			return generator;
		} catch (Exception e) {
			throw new RuntimeException(" 初始化密钥出现异常 ");
		}
	}
}
