package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：AgentList.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 上午11:13:20 <br>
 * 发布版本：V <br>
 */
public class AgentList {
	private int errcode;
	private String errmsg;
	private List<AgentInfo> agentlist;
	
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
	public List<AgentInfo> getAgentlist() {
		return agentlist;
	}
	public void setAgentlist(List<AgentInfo> agentlist) {
		this.agentlist = agentlist;
	}
}

