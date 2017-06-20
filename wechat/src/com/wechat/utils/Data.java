package com.wechat.utils;

/**
 * 类名：Data.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月12日 下午2:35:56 <br>
 * 发布版本：V <br>
 */
public class Data {
	private String id;
	private String secret;
	private boolean isCorp;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public boolean isCorp() {
		return isCorp;
	}
	public void setCorp(boolean isCorp) {
		this.isCorp = isCorp;
	}
}

