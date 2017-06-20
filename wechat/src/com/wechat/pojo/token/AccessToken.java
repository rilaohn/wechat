package com.wechat.pojo.token;

/**
 * 类名：AccessToken <br>
 * 描述：微信授权接口调用凭证 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14， 2017 <br>
 * 发布版本：1.00 <br>
 */
public class AccessToken {

	// 公众号的全局唯一票据
	private String access_token;
	// 凭证有效时间，单位：秒
	private int expires_in;
	
	public AccessToken() {
		access_token = "";
		expires_in = 0;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
