package com.wechat.pojo.token;

import java.util.Map;

public class InitSingleton {
	
	private Map<String, InitData> datas;
	
	private static InitSingleton IS;
	private InitSingleton() {}
	
	public static InitSingleton get() {
		if (IS == null)
			IS = new InitSingleton();
		return IS;
	}

	public Map<String, InitData> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, InitData> datas) {
		this.datas = datas;
	}
}
