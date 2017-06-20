package com.wechat.pojo.menu;

/**
 * 类名：SecondaryButton <br>
 * 描述：二级按钮 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class SubButton extends BaseType {
	
	private BaseType[] sub_button;

	public BaseType[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(BaseType[] sub_button) {
		this.sub_button = sub_button;
	}
}
