package com.ssh.service.practice.digibig;

public final class Names {

//    public final static String TOKEN = "APPID_{0}_TOKEN";//浏览器端存放的cookie名称

	public final static String HEADER_AUTH = "X-DIGI-AUTH";//token
//    public final static String HEADER_TOKEN = "X-TOKEN";//token
//    public final static String HEADER_FROM_OUTSIDE = "X-FROM-OUTSIDE";//token
//    public final static String HEADER_FROM_API = "X-FROM-API";//token
//    public final static String HEADER_USER = "X-USER";//当前请求包含登陆用户信息

//    public final static String HEADER_STAFF_ID = "X-STAFF-ID";//当前请求包含所属租户信息
//    public final static String HEADER_CUSTOMER_ID = "X-CUSTOMER-ID";//当前请求包含所属租户信息
//    public final static String HEADER_TENANT = "X-TENANT";//当前请求包含所属租户信息

//    public final static String HEADER_APP = "X-APP";//当前请求包含所属登陆应用信息

//    public final static String HEADER_I_TENANT = "X-I-TENANT";//当前请求包含已授权租户信息

//    public final static String X_TENANT_HOST = "X-TENANT-HOST";

//    public final static String X_PROTOCOL = "X-PROTOCOL";

//    public final static String HEADER_I_APP = "X-I-APP";//当前请求包含已授权应用信息

//    public final static String HEADER_USER_AUTHORITIES = "X-USER-AUTHORITIES";//当前请求包含已授权用户的权限信息

//    public final static String HEADER_TENANT_AUTHORITIES = "X-TENANT-AUTHORITIES";//当前请求包含已授权租户的权限信息

//    public final static String HEADER_IS_LOGGED = "X-IS-LOGGED";//是否已登录的标志


	public final static String HEADER_TMP_FLAG = "X-TMP-FLAG";
	public final static String REQUEST_ATTRIBUTE_AUTH_CONTEXT = "request.attribute.tenant.auth";
	public final static String REQUEST_ATTRIBUTE_JOIN_CASCADE="request.attribute.join.cascade";
	//请求 auth 秘钥
	public final static String REQUEST_ATTRIBUTE_SECRET="request.attribute.secret";
	//请求 session 常量
	public final static String REQUEST_ATTRIBUTE_FINAL="request.attribute.final";


	public final static String HTTP_COOKIE = "Cookie";

	public final static String HEADER_ORIGINAL_PORT = "DIGI-PORT";
	public final static String HEADER_ORIGINAL_URL = "DIGI-URL";

	public final static String DATA_CONSTRAIN_DOMAIN = "request.attribute.auth.data.constrain.domain";
	public final static String DATA_CONSTRAIN_TENANT = "request.attribute.auth.data.constrain.tenant";
	public final static String DATA_CONSTRAIN_APP = "request.attribute.auth.data.constrain.app";
	public final static String DATA_CONSTRAIN_USER = "request.attribute.auth.data.constrain.user";

	//    public final static String HEADER_JOIN_REQUEST = "DIGI-JOIN-REQUEST";
	public final static String JOIN_KEYS = "com.digibig.join.keys";
	public final static String API_RESULT = "com.digibig.api.response";

	public final static String SESSION_KEY_AUTH = "auth-data";

	public final static String HYSTRIX_KEY_REQUEST = "digibig.feign.hystrix.key.request";
	public final static String HYSTRIX_KEY_FROM_EXTERNAL = "digibig.feign.hystrix.key.source";
}
