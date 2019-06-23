package com.ssh.service.practice.cfca;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import org.apache.commons.codec.binary.Base64;

public class CfcaUtils {

	public static final String CERT_TYPE = "X.509";
	public static String alias = "{d9e03b97-c922-4fa7-8a44-f0b1b9ffb67f}";

	/**
	 * 获取keyStore
	 */
	private static KeyStore getKeyStore(String pfxPath, String pwd) throws Exception {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		FileInputStream in = new FileInputStream(pfxPath);
		ks.load(in, pwd.toCharArray());
		in.close();
		System.out.println("keystore type=:" + ks.getType());

		Enumeration enumeration = ks.aliases();
		if (enumeration.hasMoreElements())// we are readin just one certificate.
		{
			alias = (String) enumeration.nextElement();
		}
		return ks;
	}

	/**
	 * 对数据进行签名
	 */
	private static String sign(String data, String pfxPath, String pwd) throws Exception {
		KeyStore keyStore = getKeyStore(pfxPath, pwd);
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, pwd.toCharArray());
		System.out.println("privateKey====:" + privateKey);
		Signature signature = Signature.getInstance("sha256WithRSA");
		signature.initSign(privateKey);
		signature.update(data.getBytes());
		byte[] sign = signature.sign();
		return new String(Base64.encodeBase64(sign));
	}

	/**
	 * 公钥验证签名是否正确
	 */
	private static Boolean verify(String data, String sign, String pfxPath, String pwd) throws Exception {
		KeyStore keyStore = getKeyStore(pfxPath, pwd);
		Certificate cert = keyStore.getCertificate(alias);
		System.out.println("cert========:" + cert);
		PublicKey publicKey = cert.getPublicKey();
		Signature signature = Signature.getInstance("sha256WithRSA");
		signature.initVerify(publicKey);
		System.out.println("pfx-publicKey====:" + publicKey);
		signature.update(data.getBytes());
		return signature.verify(Base64.decodeBase64(sign.getBytes()));
	}

	private static Boolean verify(String data, String sign, String certificatePath) throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(certificatePath);
		Certificate certificate = factory.generateCertificate(in);
		in.close();
		PublicKey publicKey = certificate.getPublicKey();
		System.out.println("cert-publicKey====:" + publicKey);
		Signature signature = Signature.getInstance("sha256WithRSA");
		signature.initVerify(publicKey);
		signature.update(data.getBytes());
		return signature.verify(Base64.decodeBase64(sign.getBytes()));
	}


	private static PrivateKey getPrivateKey(String path, String pwd) throws Exception {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		FileInputStream in = new FileInputStream(path);
		ks.load(in, pwd.toCharArray());
		in.close();

		System.out.println("keystore type=" + ks.getType());
		Enumeration enumas = ks.aliases();
		String keyAlias = null;
		if (enumas.hasMoreElements())// we are readin just one certificate.
		{
			keyAlias = (String) enumas.nextElement();
			System.out.println("alias===[" + keyAlias + "]");
		}
		// Now once we know the alias, we could get the keys.
		System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
		PrivateKey priKey = (PrivateKey) ks.getKey(keyAlias, pwd.toCharArray());

		Certificate cert = ks.getCertificate(keyAlias);
		PublicKey pubKey = cert.getPublicKey();
		PublicKey publicKey = getCertificate(Constant.TRUST_CER_PATH).getPublicKey();
		System.out.println("pubKey==========" + pubKey);

		System.out.println("cer publicKey=======" + publicKey);
		return priKey;
	}

	/**
	 * @return Certificate 证书
	 */
	public static Certificate getCertificate(String certificatePath) throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance(CERT_TYPE);
		FileInputStream in = new FileInputStream(certificatePath);
		Certificate certificate = factory.generateCertificate(in);
		in.close();
		return certificate;

	}

	public static void main(String[] args) throws Exception {
		getPrivateKey(Constant.MEM_PFX_FILE_PATH, Constant.MEM_PFX_FILE_PASSWORD);
	}


}
