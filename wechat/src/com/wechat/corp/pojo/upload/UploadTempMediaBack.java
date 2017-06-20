package com.wechat.corp.pojo.upload;

/**
 * 类名：UploadTempMediaBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 下午3:47:22 <br>
 * 发布版本：V <br>
 */
public class UploadTempMediaBack {
	private String type;
	private String media_id;
	private String created_at;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}

