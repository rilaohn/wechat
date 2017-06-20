package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：DepartmentList.java <br>
 * 描述：部门列表back <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午2:28:30 <br>
 * 发布版本：V <br>
 */
public class DepartmentList {

	private int errcode;
	private String errmsg;
	private List<Department> department;
	
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
	public List<Department> getDepartment() {
		return department;
	}
	public void setDepartment(List<Department> department) {
		this.department = department;
	}
}

