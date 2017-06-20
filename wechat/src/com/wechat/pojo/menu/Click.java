package com.wechat.pojo.menu;

/**
 * 类名：Click <br>
 * 描述：点击推事件 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class Click extends BaseType {
	
	private String type = "click";
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
