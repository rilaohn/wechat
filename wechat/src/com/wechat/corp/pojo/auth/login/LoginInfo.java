package com.wechat.corp.pojo.auth.login;

import java.util.List;

/**
 * 类名：LoginInfo.java <br>
 * 描述：企业号登录用户信息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 上午9:25:41 <br>
 * 发布版本：V <br>
 */
public class LoginInfo {
	private int usertype;
	private UserInfo user_info;
	private CorpInfo corp_info;
	private List<Agent> agent;
	private AuthInfo auth_info;
	private RedirectLoginInfo redirect_login_info;
	
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public UserInfo getUser_info() {
		return user_info;
	}
	public void setUser_info(UserInfo user_info) {
		this.user_info = user_info;
	}
	public CorpInfo getCorp_info() {
		return corp_info;
	}
	public void setCorp_info(CorpInfo corp_info) {
		this.corp_info = corp_info;
	}
	public List<Agent> getAgent() {
		return agent;
	}
	public void setAgent(List<Agent> agent) {
		this.agent = agent;
	}
	public AuthInfo getAuth_info() {
		return auth_info;
	}
	public void setAuth_info(AuthInfo auth_info) {
		this.auth_info = auth_info;
	}
	public RedirectLoginInfo getRedirect_login_info() {
		return redirect_login_info;
	}
	public void setRedirect_login_info(RedirectLoginInfo redirect_login_info) {
		this.redirect_login_info = redirect_login_info;
	}
}

