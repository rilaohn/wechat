package com.wechat.corp.pojo.res.manager;

/**
 * 类名：BatchSyncResult.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 上午10:26:45 <br>
 * 发布版本：V <br>
 */
public class BatchSyncResult {
	private int action;
	private String userid;
	private int partyid;
	private int errcode;
	private String errmsg;
	
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getPartyid() {
		return partyid;
	}
	public void setPartyid(int partyid) {
		this.partyid = partyid;
	}
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
}

