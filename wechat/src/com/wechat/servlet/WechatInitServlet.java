package com.wechat.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.InitSingleton;
import com.wechat.utils.InitUtil;

/**
 * 类名：WechatInitServlet <br>
 * 描述：微信初始化Servlet <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */

public class WechatInitServlet extends HttpServlet{

	private InitUtil initUtil;
	private Map<String, InitData> datas;
	private String initData;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		datas = new HashMap<String, InitData>();
		
		initData = config.getInitParameter("InitData");
//		System.out.println("--- initdata : " + initData);
		String[] split1 = initData.split(";");
		for (int i = 0; i < split1.length; i++) {
			String[] split2 = split1[i].split(",");
			InitData data = new InitData();
			data.setFlag(split2[0]);
			data.setId(split2[1]);
			data.setSecret(split2[2]);
			data.setCorp(Boolean.parseBoolean(split2[3]));
			datas.put(split2[0], data);
		}
		InitSingleton.get().setDatas(datas);
		initUtil = new InitUtil(InitSingleton.get().getDatas());
		super.init();
	}
}
