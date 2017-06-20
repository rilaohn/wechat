package com.wechat.pojo.msg.req;

/**
* 类名: ViewEvent <br>
* 描述: 点击菜单跳转链接时的事件推送 <br>
* 开发人员： 廖日辰 <br>
* 创建时间： Mar 06, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class ViewEvent extends EventMessage {

	private String MenuID;

	public String getMenuID() {
		return MenuID;
	}

	public void setMenuID(String menuID) {
		MenuID = menuID;
	}
}
