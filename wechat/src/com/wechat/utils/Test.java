package com.wechat.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wechat.corp.pojo.auth.login.Department;
import com.wechat.corp.pojo.res.manager.SetAgentBack;
import com.wechat.pojo.menu.MenuBack;
import com.wechat.pojo.token.ShortUrl;

/**
 * 类名：Test.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月12日 下午2:35:01 <br>
 * 发布版本：V <br>
 */
public class Test {
	private static String appId = "wxe5e79628254bb7f7";
	private static String secret = "7e6f8fb31f2af6af97b5f3bff9bd8a95";
	private static String token = "EmergencyUnlockingPlatformToken";
	static Map<String, Data> datas;

	public static void main(String[] args) {
		TestUtil util = new TestUtil(appId, secret, false, token, "", false);
		String rUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe5e79628254bb7f7&redirect_uri=http://lrc.natappvip.cc/social-service/h5/unlock.html&response_type=code&scope=snsapi_userinfo&state=unlock#wechat_redirect";
		
		ShortUrl url = null;
		try {
			String uurl = new String(rUrl.getBytes(), "UTF-8");
			url = util.getShortUrl(uurl);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println(url.getShort_url());
		util.getAndDownEternalQrcode(url.getShort_url(), "C:\\Users\\admin\\Desktop\\qrcodetest\\redirectUnlock");
	}
}
