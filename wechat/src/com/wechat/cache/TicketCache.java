package com.wechat.cache;

import com.wechat.pojo.token.JSApiTicket;

public class TicketCache {
	private String key;
	private JSApiTicket jsApiTicket;
	private long saveTime;

	public TicketCache() {}

	public TicketCache(String key, JSApiTicket jsApiTicket, long saveTime) {
		this.key = key;
		this.jsApiTicket = jsApiTicket;
		this.saveTime = saveTime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public JSApiTicket getJsApiTicket() {
		return jsApiTicket;
	}

	public void setJsApiTicket(JSApiTicket jsApiTicket) {
		this.jsApiTicket = jsApiTicket;
	}

	public long getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(long saveTime) {
		this.saveTime = saveTime;
	}
}
