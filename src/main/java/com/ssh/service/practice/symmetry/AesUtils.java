package com.ssh.service.practice.symmetry;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES加密
 */
public class AesUtils {
    public static final String data = "i am student";
    public static final String key = "12345678";

    private static void aesEncode() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(key.getBytes());

        kg.init(secureRandom);
        SecretKeySpec secretKeySpec = new SecretKeySpec(kg.generateKey().getEncoded(),"AES");

        //加密算法/工作模式/填充方式/
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        String encodeBase64Data = Base64.encodeBase64String(cipher.doFinal(data.getBytes()));

        System.out.println("加密后=============:"+encodeBase64Data);



        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        String decodeData = new String(cipher.doFinal(Base64.decodeBase64(encodeBase64Data)));
        System.out.println("解密后数据==========:"+decodeData);
    }

    public static void main(String[] args) throws Exception {
        aesEncode();
    }
}
