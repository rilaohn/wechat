package com.wechat.pojo.msg.event;

public class TemplateStatusBack extends BaseEvent {

	private long MsgID;
	private String Status;
	
	public long getMsgID() {
		return MsgID;
	}
	public void setMsgID(long msgID) {
		MsgID = msgID;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
}
