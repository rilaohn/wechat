package com.wechat.pojo.msg.req;

/**
 * 类名：PictureEvent <br>
 * 描述：图片事件 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class PictureEvent extends EventMessage {

	private SendPicsInformation SendPicsInfo;

	public SendPicsInformation getSendPicsInfo() {
		return SendPicsInfo;
	}

	public void setSendPicsInfo(SendPicsInformation sendPicsInfo) {
		SendPicsInfo = sendPicsInfo;
	}
}
