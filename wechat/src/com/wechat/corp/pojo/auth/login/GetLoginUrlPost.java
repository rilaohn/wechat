package com.wechat.corp.pojo.auth.login;

/**
 * 类名：GetLoginUrl.java <br>
 * 描述：获取登录企业号官网的url的post数据 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 上午9:50:55 <br>
 * 发布版本：V1.0 <br>
 */
public class GetLoginUrlPost {
	private String login_ticket;
	private String target;
	private int agentid;
	
	public String getLogin_ticket() {
		return login_ticket;
	}
	public void setLogin_ticket(String login_ticket) {
		this.login_ticket = login_ticket;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public int getAgentid() {
		return agentid;
	}
	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}
}

