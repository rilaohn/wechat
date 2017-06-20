package com.wechat.pojo.msg.send.mass;

import com.wechat.pojo.msg.send.kf.WXCard;

public class PreviewCard extends PreviewMsg {

	private WXCard wxcard;

	public WXCard getWxcard() {
		return wxcard;
	}

	public void setWxcard(WXCard wxcard) {
		this.wxcard = wxcard;
	}
}
