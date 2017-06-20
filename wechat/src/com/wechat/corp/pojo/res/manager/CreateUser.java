package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：CreateUser.java <br>
 * 描述：创建成员 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午2:46:28 <br>
 * 发布版本：V1.0 <br>
 */
public class CreateUser {

	private String userid;
	private String name;
	private List<Integer> department;
	private String position;
	private String mobile;
	private String gender;
	private String email;
	private String weixinid;
	private String avatar_mediaid;
	private ExtAttr extattr;
	
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public String getAvatar_mediaid() {
		return avatar_mediaid;
	}
	public void setAvatar_mediaid(String avatar_mediaid) {
		this.avatar_mediaid = avatar_mediaid;
	}
	public ExtAttr getExtattr() {
		return extattr;
	}
	public void setExtattr(ExtAttr extattr) {
		this.extattr = extattr;
	}
}

