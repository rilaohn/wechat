package com.wechat.corp.pojo.auth.login;

import java.util.List;

/**
 * 类名：AuthInfo.java <br>
 * 描述：该管理员拥有的通讯录权限 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月13日 下午6:16:33 <br>
 * 发布版本：V <br>
 */
public class AuthInfo {
	private List<Department> department;

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
	}
}

