package com.wechat.corp.pojo.msg.send;

public class CorpSendMsgBack {
	private int errcode;
	private String errmsg;
	private String invaliduser;
	private String invalidparty;
	private String invalidtag;
	
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
	public String getInvaliduser() {
		return invaliduser;
	}
	public void setInvaliduser(String invaliduser) {
		this.invaliduser = invaliduser;
	}
	public String getInvalidparty() {
		return invalidparty;
	}
	public void setInvalidparty(String invalidparty) {
		this.invalidparty = invalidparty;
	}
	public String getInvalidtag() {
		return invalidtag;
	}
	public void setInvalidtag(String invalidtag) {
		this.invalidtag = invalidtag;
	}
}
