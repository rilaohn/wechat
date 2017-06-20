package com.wechat.corp.pojo.res.manager;

/**
 * 类名：CrateTagBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午4:49:26 <br>
 * 发布版本：V <br>
 */
public class CreateTagBack {
	private int errcode;
	private String errmsg;
	private int tagid;
	
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
	public int getTagid() {
		return tagid;
	}
	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
}

