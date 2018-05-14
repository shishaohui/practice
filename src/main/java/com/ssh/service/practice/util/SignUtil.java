package com.ssh.service.practice.util;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SignUtil {
	public Map getDecryptData(String encryptedData,String digestKey,String iv){
		encryptedData = "/Sfi3kp1c/BSU4WtGGP2EbskOkbbLSsWJckrLwYKZZAnWYMOeF+KpJ3x3EB7OnM37gJrX9sCIY2Y1KjVldudeVx5qT6HQRNuOPnIxyk7Ck"
			+ "+viZfgxvD57Qfzf4uTiAqC89uh97h9uaCgjr0ptsykX56s0IHba2d1uaRl0nINhmvah6VN1ySp/Bibw8EDGnEW70yRwTtXF9G0Du4P2elxevTY8+I7neta++pvl+DsMJNVBexdSwb/Jq8LokCm8VFrUMGUTxOy8CX/r0GJVPQQr9URf3A2mubz016wnkjPmGLW7pDByUkORF9uNWBDGS7r/a8GCZJKDp/0++T+LkANQabkf+2sAF8H6Cfn9/kJ1El1mqKatOZBboHh0WS39PCvmaGBQtXCJJ3kUEO7+Vq7QGoim3ruLwRl8FJZr9CPe6zYTYmyfA7RDEX0+xCE3BD0cS4SbfnIn9dMNOekBi0b9Q==";
		iv = "a8FJE+rqjk2Rq2rhe9ilmQ==";
		digestKey = "q7goyzc/nniAvrr1FIDZpg==";
		Map map = new HashMap();
		try {
			byte[] resultByte  = decrypt(decodeBase64(encryptedData),decodeBase64(digestKey),decodeBase64(iv));
			if(null != resultByte && resultByte.length > 0){
				String userInfo = new String(resultByte, "UTF-8");
				map.put("status", "1");
				map.put("msg", "解密成功");
				map.put("userInfo", userInfo);
			}else{
				map.put("status", "0");
				map.put("msg", "解密失败");
			}
		}catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean initialized = false;

	public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");

			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//生成iv
	public static AlgorithmParameters generateIV(byte[] iv) throws Exception{
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}

	public static void main(String[] args){
		SignUtil util = new SignUtil();
		util.getDecryptData(null,null,null);
	}

}
