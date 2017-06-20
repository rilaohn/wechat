package com.wechat.pojo.token;

/**
 * 类名：WechatData.java <br>
 * 描述：微信数据bean <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月12日 下午3:44:40 <br>
 * 发布版本：V <br>
 */
public class WechatData {

	private Token token;
	private InitData initData;
	
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public InitData getInitData() {
		return initData;
	}
	public void setInitData(InitData initData) {
		this.initData = initData;
	}
}

