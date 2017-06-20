package com.wechat.corp.pojo.msg.send;

/**
 * 类名：CorpMPNews.java <br>
 * 描述：企业微信图文消息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午2:50:05 <br>
 * 发布版本：V1.0 <br>
 */
public class CorpMPNews extends CorpMsg {
	private MPNews mpnews;

	public MPNews getMpnews() {
		return mpnews;
	}

	public void setMpnews(MPNews mpnews) {
		this.mpnews = mpnews;
	}
}

