package com.wechat.cache;

import com.wechat.pojo.token.AccessToken;

public class TokenCache {
	private String key;
	private AccessToken accessToken;
	private long saveTime;

	public TokenCache(String key, AccessToken accessToken, long saveTime) {
		this.key = key;
		this.accessToken = accessToken;
		this.saveTime = saveTime;
	}

	public TokenCache() {}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public long getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(long saveTime) {
		this.saveTime = saveTime;
	}
}
