package com.wechat.pojo.msg.req;

/**
 * 类名：LocationSelectEvent <br>
 * 描述：本地选择事件 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class LocationSelectEvent extends EventMessage {

	// 发送的位置信息
	private LocationInfo SendLocationInfo;

	public LocationInfo getSendLocationInfo() {
		return SendLocationInfo;
	}

	public void setSendLocationInfo(LocationInfo sendLocationInfo) {
		SendLocationInfo = sendLocationInfo;
	}
}
