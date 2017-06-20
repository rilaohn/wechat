package com.wechat.corp.pojo.convert;

/**
 * 类名：Userid2Openid.java <br>
 * 描述：企业号userid转换成openid接口 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月13日 下午5:03:45 <br>
 * 发布版本：V1.0 <br>
 */
public class Userid2OpenidPost {
	private String userid;
	private int agentid;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getAgentid() {
		return agentid;
	}
	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}
}

