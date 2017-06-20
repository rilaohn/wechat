package com.wechat.corp.pojo.upload;

import com.wechat.corp.pojo.msg.send.MPNews;

/**
 * 类名：UpdateMPNewsPost.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 下午5:20:09 <br>
 * 发布版本：V <br>
 */
public class UpdateMPNewsPost {
	private String media_id;
	private MPNews mpnews;
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public MPNews getMpnews() {
		return mpnews;
	}
	public void setMpnews(MPNews mpnews) {
		this.mpnews = mpnews;
	}
}

