package com.wechat.pojo.menu;

/**
 * 类名：MediaId <br>
 * 描述：下发消息（除文本消息） <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class MediaId extends BaseType {

	private String type = "media_id";
	private String sub_button = "[]";
	private String media_id;
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
}
