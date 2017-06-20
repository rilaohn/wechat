package com.wechat.corp.pojo.auth.login;

/**
 * 类名：RedirectLoginInfo.java <br>
 * 描述：登录跳转的票据信息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月13日 下午6:15:10 <br>
 * 发布版本：V1.0 <br>
 */
public class RedirectLoginInfo {
	private String login_ticket;
	private int expires_in;
	
	public String getLogin_ticket() {
		return login_ticket;
	}
	public void setLogin_ticket(String login_ticket) {
		this.login_ticket = login_ticket;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}

