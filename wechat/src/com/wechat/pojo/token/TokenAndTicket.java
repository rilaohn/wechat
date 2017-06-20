package com.wechat.pojo.token;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名：TokenAndTicket.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月12日 下午4:13:24 <br>
 * 发布版本：V <br>
 */
public class TokenAndTicket {
	private Map<String, AccessToken> tokenMap;
	private Map<String, JSApiTicket> ticketMap;
	
	private static TokenAndTicket TAT;
	private TokenAndTicket() {
		tokenMap = new HashMap<String, AccessToken>();
		ticketMap = new HashMap<String, JSApiTicket>();
	}
	
	public static TokenAndTicket get() {
		if (TAT == null) 
			TAT = new TokenAndTicket();
		return TAT;
	}
	
	public Map<String, AccessToken> getTokenMap() {
		return tokenMap;
	}
	public void setTokenMap(Map<String, AccessToken> tokenMap) {
		this.tokenMap = tokenMap;
	}
	public Map<String, JSApiTicket> getTicketMap() {
		return ticketMap;
	}
	public void setTicketMap(Map<String, JSApiTicket> ticketMap) {
		this.ticketMap = ticketMap;
	}
}

