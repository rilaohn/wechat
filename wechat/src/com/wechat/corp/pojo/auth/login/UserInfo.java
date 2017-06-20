package com.wechat.corp.pojo.auth.login;

/**
 * 类名：UserInfo.java <br>
 * 描述：登录人员的信息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月13日 下午6:26:38 <br>
 * 发布版本：V1.0 <br>
 */
public class UserInfo {
	private String userid; 
	private String name; 
	private String avatar; 
	private String email;
	
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	} 
}

