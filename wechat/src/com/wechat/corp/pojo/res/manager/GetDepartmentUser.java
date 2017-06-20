package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：GetDepartmentUser.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午4:07:52 <br>
 * 发布版本：V <br>
 */
public class GetDepartmentUser {
	private int errcode;
	private String errmsg;
	private List<DepartmentUser> userlist;
	
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
	public List<DepartmentUser> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<DepartmentUser> userlist) {
		this.userlist = userlist;
	}
}

