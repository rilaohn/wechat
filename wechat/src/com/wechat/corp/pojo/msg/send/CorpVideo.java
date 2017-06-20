package com.wechat.corp.pojo.msg.send;

import com.wechat.pojo.msg.send.kf.Video;

/**
 * 类名：CorpVideo.java <br>
 * 描述：企业视频消息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午2:45:31 <br>
 * 发布版本：V1.0 <br>
 */
public class CorpVideo extends CorpMsg {
	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
}

