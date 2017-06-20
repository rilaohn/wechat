package com.wechat.pojo.menu;

/**
 * 类名：View <br>
 * 描述：跳转URL <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class View extends BaseType {

	private String type = "view";
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
