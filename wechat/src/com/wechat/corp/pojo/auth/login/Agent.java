package com.wechat.corp.pojo.auth.login;

/**
 * 类名：AgentidAndAuthType.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月13日 下午6:23:11 <br>
 * 发布版本：V <br>
 */
public class Agent {
	private int agentid;
	private int auth_type;
	
	public int getAgentid() {
		return agentid;
	}
	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}
	public int getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(int auth_type) {
		this.auth_type = auth_type;
	}
}

