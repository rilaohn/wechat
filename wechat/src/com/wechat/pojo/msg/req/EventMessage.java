package com.wechat.pojo.msg.req;

/**
* 类名: EventMessage <br>
* 描述: 事件消息的基类 <br>
* 开发人员： 廖日辰 <br>
* 创建时间： Mar 06, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class EventMessage extends OriginMessage {
	
	// 事件类型
	private String Event;
	// 事件KEY值，由开发者在创建菜单时设定
	private String EventKey;
	
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
