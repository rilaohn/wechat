package com.wechat.corp.pojo.auth.login;

/**
 * 类名：GetLoginUrl.java <br>
 * 描述：获取登录企业号官网的url返回的数据 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 上午9:50:55 <br>
 * 发布版本：V1.0 <br>
 */
public class GetLoginUrlBack {
	private int errcode;
	private String errmsg;
	private String login_url;
	private int expires_in;
	
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getLogin_url() {
		return login_url;
	}
	public void setLogin_url(String login_url) {
		this.login_url = login_url;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}

