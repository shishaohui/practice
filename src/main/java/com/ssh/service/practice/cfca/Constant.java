package com.ssh.service.practice.cfca;

public class Constant {
    public static final String CHARSET_UTF8 = "UTF-8";

    public static final String MEM_PFX_FILE_PASSWORD = "888888";

	//商户应该换成自己的证书
    public static final String MEM_PFX_FILE_NAME = "100001.pfx";

	public static final String TRUST_CER_NAME = "CFCA_ACS_TEST_OCA31.cer";

	public static final String MEM_PFX_FILE_PATH = Constant.class.getClassLoader().getResource(Constant.MEM_PFX_FILE_NAME).getPath();

	public static final String TRUST_CER_PATH = Constant.class.getClassLoader().getResource(Constant.TRUST_CER_NAME).getPath();


}
