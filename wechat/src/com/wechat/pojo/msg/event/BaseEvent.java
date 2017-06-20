package com.wechat.pojo.msg.event;

import com.wechat.pojo.msg.req.OriginMessage;

/**
* 类名: BaseEvent <br>
* 描述: 事件的基类 <br>
* 开发人员：廖日辰 <br>
* 创建时间：Mar 14, 2017 <br>
* 发布版本：V1.00  <br>
 */
public class BaseEvent extends OriginMessage {

    // 事件类型
    private String Event;
    
	
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
}
