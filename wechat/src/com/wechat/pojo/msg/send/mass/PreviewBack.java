package com.wechat.pojo.msg.send.mass;

public class PreviewBack {

	private int errcode;
	private String errmsg;
	private long msg_id;
	
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
	public long getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(long msg_id) {
		this.msg_id = msg_id;
	}
}
