package com.wechat.pojo.msg.send.kf;

import com.wechat.pojo.msg.send.mass.Media;

public class KFSendVoice extends KFSend {

	private Media voice;

	public Media getVoice() {
		return voice;
	}

	public void setVoice(Media voice) {
		this.voice = voice;
	}
}
