package com.wechat.pojo.msg.send.mass;

public class MassCard2List extends MassMsg2List{
	private WXCard wxcard;

	public WXCard getWxcard() {
		return wxcard;
	}

	public void setWxcard(WXCard wxcard) {
		this.wxcard = wxcard;
	}
}
