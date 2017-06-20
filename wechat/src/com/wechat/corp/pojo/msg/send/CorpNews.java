package com.wechat.corp.pojo.msg.send;

import com.wechat.pojo.msg.send.kf.News;

/**
 * 类名：CorpNews.java <br>
 * 描述：企业新闻消息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午2:48:32 <br>
 * 发布版本：V1.0 <br>
 */
public class CorpNews extends CorpMsg {
	private News news;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
}

