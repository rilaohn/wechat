package com.wechat.corp.pojo.res.manager;

/**
 * 类名：CreateDepartmentPost.java <br>
 * 描述：创建部门时POST的数据 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 上午11:35:05 <br>
 * 发布版本：V1.0 <br>
 */
public class Department {
	private String name; 
	private int parentid;
	private int order;
	private int id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

