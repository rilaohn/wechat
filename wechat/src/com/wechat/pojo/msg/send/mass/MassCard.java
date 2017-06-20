package com.wechat.pojo.msg.send.mass;

public class MassCard extends MassMsg{
	private WXCard wxcard;

	public WXCard getWxcard() {
		return wxcard;
	}

	public void setWxcard(WXCard wxcard) {
		this.wxcard = wxcard;
	}
}
