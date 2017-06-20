package com.wechat.corp.pojo.upload;

/**
 * 类名：MaterialCount.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 下午5:33:05 <br>
 * 发布版本：V <br>
 */
public class MaterialCount {
	private int errcode;
	private String errmsg;
	private int total_count;
	private int image_count;
	private int voice_count;
	private int video_count;
	private int file_count;
	private int mpnews_count;
	
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getImage_count() {
		return image_count;
	}
	public void setImage_count(int image_count) {
		this.image_count = image_count;
	}
	public int getVoice_count() {
		return voice_count;
	}
	public void setVoice_count(int voice_count) {
		this.voice_count = voice_count;
	}
	public int getVideo_count() {
		return video_count;
	}
	public void setVideo_count(int video_count) {
		this.video_count = video_count;
	}
	public int getFile_count() {
		return file_count;
	}
	public void setFile_count(int file_count) {
		this.file_count = file_count;
	}
	public int getMpnews_count() {
		return mpnews_count;
	}
	public void setMpnews_count(int mpnews_count) {
		this.mpnews_count = mpnews_count;
	}
}

