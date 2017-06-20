package com.wechat.pojo.menu;

/**
 * 类名：Menu <br>
 * 描述：菜单bean <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class Menu {

	private BaseType[] button;
	
	private Matchrule matchrule;

	public BaseType[] getButton() {
		return button;
	}

	public void setButton(BaseType[] button) {
		this.button = button;
	}

	public Matchrule getMatchrule() {
		return matchrule;
	}

	public void setMatchrule(Matchrule matchrule) {
		this.matchrule = matchrule;
	}
}
