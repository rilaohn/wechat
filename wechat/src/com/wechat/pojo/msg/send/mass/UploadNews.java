package com.wechat.pojo.msg.send.mass;

import java.util.List;

public class UploadNews {

	private List<UploadArticle> articles;

	public List<UploadArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<UploadArticle> articles) {
		this.articles = articles;
	}
}
