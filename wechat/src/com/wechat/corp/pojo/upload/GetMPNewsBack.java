package com.wechat.corp.pojo.upload;

import com.wechat.corp.pojo.msg.send.MPNews;

/**
 * 类名：GetNPNewsBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 下午5:08:29 <br>
 * 发布版本：V <br>
 */
public class GetMPNewsBack {
	private String type;
	private MPNews mpnews;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MPNews getMpnews() {
		return mpnews;
	}
	public void setMpnews(MPNews mpnews) {
		this.mpnews = mpnews;
	}
}

