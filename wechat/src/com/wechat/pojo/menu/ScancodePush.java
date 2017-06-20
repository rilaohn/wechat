package com.wechat.pojo.menu;

/**
 * 类名：ScancodePush <br>
 * 描述：扫码推事件 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class ScancodePush extends BaseType {

	private String type = "scancode_push";
	private String sub_button = "[]";
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
