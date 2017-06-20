package com.wechat.pojo.msg.resp;

import java.util.List;

/**
* 类名: NewsMessage <br>
* 描述: 文本消息 <br>
* 开发人员：廖日辰 <br>
* 创建时间：Mar 03, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class NewsMsg extends BaseMsg {

	// 图文消息个数，限制为10条以内
    private int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
