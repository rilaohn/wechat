package com.wechat.corp.pojo.res.manager;

/**
 * 类名：BatchCallback.java <br>
 * 描述：企业通讯录更新回调给自己后台 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 上午9:19:54 <br>
 * 发布版本：V <br>
 */
public class BatchSyncCallback {
	private String url;
	private String token;
	private String encodingaeskey;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEncodingaeskey() {
		return encodingaeskey;
	}
	public void setEncodingaeskey(String encodingaeskey) {
		this.encodingaeskey = encodingaeskey;
	}
}

