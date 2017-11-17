package com.pay.alipay;

/**
 * 支付宝支付接口相关参数配置
 * 
 * @author 周光兵
 *
 */
public class AlipayParamConfig {
	/**
	 * 支付宝应用ID
	 */
	public static final String APPID = "";
	/**
	 * 支付宝公钥（非应用公钥）
	 */
	public static final String ALIPAY_PUBLIC_KEY = "";
	/**
	 * 商户私钥
	 */
	public static final String RSA2_PRIVATE = "";
	/**
	 * 字符编码
	 */
	public static final String CHARSET = "UTF-8";

	public static final String URL_PAY_SUCCESS_NOTIFY = ""; // 支付成功后微信回调地址，不能携带参数
	
} // end public class AlipayParamConfig