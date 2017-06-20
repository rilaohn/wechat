package com.wechat.pojo.msg.send.mass;

import com.wechat.pojo.msg.send.kf.Text;

public class PreviewText extends PreviewMsg {
	
	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
}
