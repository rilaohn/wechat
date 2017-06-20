package com.wechat.pojo.token;

/**
 * 类名：JSApiTicket <br>
 * 描述：公众号用于调用微信JS接口的临时票据 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14， 2017 <br>
 * 发布版本：1.00 <br>
 */
public class JSApiTicket {

	// 返回的错误代码 0表示正确
	private int errcode;
	// 返回的错误信息 “OK”表示通过
	private String errmsg;
	// 临时票据ticket
	private String ticket;
	// 临时票据ticket的有效时间
	private int expires_in;
	
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
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
