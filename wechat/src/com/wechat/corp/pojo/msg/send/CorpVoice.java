package com.wechat.corp.pojo.msg.send;

import com.wechat.pojo.msg.send.mass.Media;

/**
 * 类名：CorpVoice.java <br>
 * 描述：企业语音消息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午2:44:09 <br>
 * 发布版本：V1.0 <br>
 */
public class CorpVoice extends CorpMsg {

	private Media voice;

	public Media getVoice() {
		return voice;
	}

	public void setVoice(Media voice) {
		this.voice = voice;
	}
}

