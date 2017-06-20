package com.wechat.pojo.msg.send.kf;

import com.wechat.pojo.msg.send.mass.Media;

public class KFSendVoiceBy extends KFSendBy {

	private Media voice;

	public Media getVoice() {
		return voice;
	}

	public void setVoice(Media voice) {
		this.voice = voice;
	}
}
