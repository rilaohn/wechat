package com.wechat.pojo.menu;

/**
 * 类名：ScancodeWaitmsg <br>
 * 描述：扫码推事件且弹出“消息接收中”提示框 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class ScancodeWaitmsg extends BaseType {

	private String type = "scancode_waitmsg";
	private String sub_button = "[]";
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
