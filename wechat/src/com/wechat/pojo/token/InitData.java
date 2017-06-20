package com.wechat.pojo.token;

public class InitData {
	
	private String id;
	private String secret;
	private String flag;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
