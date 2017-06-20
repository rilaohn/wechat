package com.wechat.corp.pojo.res.manager;

/**
 * 类名：CreateDepartmentPost.java <br>
 * 描述：创建部门时POST的数据 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 上午11:35:05 <br>
 * 发布版本：V1.0 <br>
 */
public class CreateDepartmentBack {
	 
	private int errcode;
	private String errmsg;
	private int id;
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

