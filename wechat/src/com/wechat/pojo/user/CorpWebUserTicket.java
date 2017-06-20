package com.wechat.pojo.user;

/**
 * 类名：CorpWebUserInfo.java <br>
 * 描述：企业号网页授权的用户票据 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月11日 下午4:36:05 <br>
 * 发布版本：V1.0 <br>
 */
public class CorpWebUserTicket {
	private String UserId;
	private String OpenId;
	private String DeviceId;
	private String user_ticket;
	private int expires_in;
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getOpenId() {
		return OpenId;
	}
	public void setOpenId(String openId) {
		OpenId = openId;
	}
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	public String getUser_ticket() {
		return user_ticket;
	}
	public void setUser_ticket(String user_ticket) {
		this.user_ticket = user_ticket;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}

