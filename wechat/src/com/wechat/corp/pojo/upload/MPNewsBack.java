package com.wechat.corp.pojo.upload;

/**
 * 类名：MPNewsBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 下午6:19:47 <br>
 * 发布版本：V <br>
 */
public class MPNewsBack {
	private String media_id;
	private MPNewsPost content;
	private long update_time;
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public MPNewsPost getContent() {
		return content;
	}
	public void setContent(MPNewsPost content) {
		this.content = content;
	}
	public long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}
}

