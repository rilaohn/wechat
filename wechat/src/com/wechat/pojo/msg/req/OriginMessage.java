package com.wechat.pojo.msg.req;

/**
* 类名: OriginMessage <br>
* 描述: 请求消息的原始类 <br>
* 开发人员： 廖日辰 <br>
* 创建时间： Mar 06, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class OriginMessage {
	
	// 接收方帐号（收到的OpenID）
    private String ToUserName;
    // 开发者微信号
    private String FromUserName;
    // 消息创建时间 （整型）
    private long CreateTime;
    // 消息类型
    private String MsgType;
    // 应用代理ID
    private int AgentID;
	
    public int getAgentID() {
		return AgentID;
	}
	public void setAgentID(int agentID) {
		AgentID = agentID;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
    
}
