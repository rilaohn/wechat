package com.wechat.pojo.token;

/**
 * 类名：Token.java <br>
 * 描述：Token Bean <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月12日 上午11:20:01 <br>
 * 发布版本：V1.0 <br>
 */
public class Token {

	private String token;
	private String encodingAESKey;
	private boolean encrypt;
	
	public Token(String token, String encodingAESKey, boolean encrypt) {
		this.token = token;
		this.encodingAESKey = encodingAESKey;
		this.encrypt = encrypt;
	}
	
	public Token() {
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEncodingAESKey() {
		return encodingAESKey;
	}
	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}
	public boolean isEncrypt() {
		return encrypt;
	}
	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
}

