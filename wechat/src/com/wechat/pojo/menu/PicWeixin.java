package com.wechat.pojo.menu;

/**
 * 类名：PicWeixin <br>
 * 描述：弹出微信相册发图器 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class PicWeixin extends BaseType {

	private String type = "pic_weixin";
	private String sub_button = "[]";
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
