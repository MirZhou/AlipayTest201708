package com.pay.alipay;

/**
 * 支付宝支付结果
 * 
 * @author 周光兵
 *
 */
public class PayResultInfo {
	private PayResultOrderInfo alipay_trade_app_pay_response;
	private String sign;
	private String sign_type;

	public PayResultOrderInfo getAlipay_trade_app_pay_response() {
		return alipay_trade_app_pay_response;
	}

	public void setAlipay_trade_app_pay_response(PayResultOrderInfo alipay_trade_app_pay_response) {
		this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	
} // end public class PayResultInfo