package com.wechat.pojo.msg.resp;


/**
* 类名: Video <br>
* 描述: 视频model <br>
* 开发人员：廖日辰 <br>
* 创建时间：Mar 03, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class Video {

	// 媒体文件id
    private String MediaId;
    // 视频消息的标题
    private String Title;
    // 	视频消息的描述
    private String Description;
    
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
}
