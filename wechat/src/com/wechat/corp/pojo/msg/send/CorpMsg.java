package com.wechat.corp.pojo.msg.send;

/**
 * 类名：CorpMsg.java <br>
 * 描述：企业消息基础 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午2:37:28 <br>
 * 发布版本：V <br>
 */
public class CorpMsg {
	private String touser;
	private String toparty;
	private String totag;
	private String msgtype;
	private int agentid;
	private int safe;
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getToparty() {
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getTotag() {
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public int getAgentid() {
		return agentid;
	}
	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}
	public int getSafe() {
		return safe;
	}
	public void setSafe(int safe) {
		this.safe = safe;
	}
}

