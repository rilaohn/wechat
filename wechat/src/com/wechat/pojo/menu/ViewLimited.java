package com.wechat.pojo.menu;

/**
 * 类名：ViewLimited <br>
 * 描述：跳转图文消息URL <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class ViewLimited extends BaseType {

	private String type = "view_limited";
	private String sub_button = "[]";
	private String media_id;
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
}
