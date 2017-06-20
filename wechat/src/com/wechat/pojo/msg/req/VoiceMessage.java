package com.wechat.pojo.msg.req;

/**
* 类名: BaseMessage <br>
* 描述: 请求消息的基类 <br>
* 开发人员： 廖日辰 <br>
* 创建时间： Mar 03, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class VoiceMessage extends BaseMessage {

	// 媒体ID
    private String MediaId;
    // 语音格式
    private String Format;
    // 语音识别结果，使用UTF8编码 客户端缓存
    private String Recognition;
    
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
}
