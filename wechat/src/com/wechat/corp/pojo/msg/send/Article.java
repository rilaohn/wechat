package com.wechat.corp.pojo.msg.send;

/**
 * 类名：Article.java <br>
 * 描述：文章 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午4:41:30 <br>
 * 发布版本：V1.0 <br>
 */
public class Article {
	private String title;
	private String thumb_media_id;
	private String author;
	private String content_source_url;
	private String content;
	private String digest;
	private String show_cover_pic;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent_source_url() {
		return content_source_url;
	}
	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getShow_cover_pic() {
		return show_cover_pic;
	}
	public void setShow_cover_pic(String show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
}

