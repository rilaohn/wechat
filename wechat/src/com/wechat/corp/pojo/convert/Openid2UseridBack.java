package com.wechat.corp.pojo.convert;

/**
 * 类名：Openid2UseridBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月13日 下午5:08:43 <br>
 * 发布版本：V <br>
 */
public class Openid2UseridBack {

	private int errcode;
	private String errmsg;
	private String userid;
	
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}

