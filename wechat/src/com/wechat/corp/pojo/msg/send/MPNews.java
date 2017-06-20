package com.wechat.corp.pojo.msg.send;

import java.util.List;

/**
 * 类名：MPNews.java <br>
 * 描述：站内图文 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午4:44:50 <br>
 * 发布版本：V1.0 <br>
 */
public class MPNews {
	private List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}

