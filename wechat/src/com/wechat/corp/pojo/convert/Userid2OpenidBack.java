package com.wechat.corp.pojo.convert;

/**
 * 类名：Userid2OpenidBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月13日 下午5:06:21 <br>
 * 发布版本：V <br>
 */
public class Userid2OpenidBack {
	private int errcode;
	private String errmsg;
	private String openid;
	private String appid;
	
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
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
}

