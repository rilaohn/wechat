package com.wechat.pojo.msg.send.kf;

/**
 * 发送图文消息（点击跳转到外链）
 * @author Etertops
 *
 */
public class KFSendNewsBy extends KFSendBy {

	private News news;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
}
