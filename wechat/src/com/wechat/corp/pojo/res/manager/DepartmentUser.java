package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：DepartmentUser.java <br>
 * 描述：部门成员 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午4:06:27 <br>
 * 发布版本：V <br>
 */
public class DepartmentUser {
	private String userid;
	private String name;
	private List<Integer> department;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getDepartment() {
		return department;
	}
	public void setDepartment(List<Integer> department) {
		this.department = department;
	}
}

