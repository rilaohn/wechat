package com.wechat.pojo.menu;

/**
 * 类名：Matchrule.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月11日 上午9:40:26 <br>
 * 发布版本：V <br>
 */
public class Matchrule {

	private String group_id;
	private String sex;
	private String country;
	private String province;
	private String city;
	private String client_platform_type;
	private String language;
	
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getClient_platform_type() {
		return client_platform_type;
	}
	public void setClient_platform_type(String client_platform_type) {
		this.client_platform_type = client_platform_type;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}

