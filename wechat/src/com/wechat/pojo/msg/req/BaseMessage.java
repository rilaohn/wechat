package com.wechat.pojo.msg.req;

/**
* 类名: BaseMessage <br>
* 描述: 请求消息的基类 <br>
* 开发人员： 廖日辰 <br>
* 创建时间： Mar 03, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class BaseMessage extends OriginMessage {
	
    private long MsgId;

	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
}
