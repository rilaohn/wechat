package com.wechat.pojo.msg.send.mass;

import com.wechat.pojo.msg.send.kf.Text;

public class MassText2List extends MassMsg2List {

	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
}
