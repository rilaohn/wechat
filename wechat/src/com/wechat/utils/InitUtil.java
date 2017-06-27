package com.wechat.utils;

import static com.wechat.utils.C.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.wechat.pojo.token.AccessToken;
import com.wechat.pojo.token.GetAddressList;
import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.JSApiTicket;
import com.wechat.pojo.token.TokenAndTicket;
import com.wechat.pojo.token.WechatIP;

/**
 * 类名：InitUtil.java <br>
 * 描述：初始化工具 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 上午9:06:56 <br>
 * 发布版本：V1.0 <br>
 */
public class InitUtil {

	private Map<String, InitData> datas;
	private Gson gson;

	/**
	 * 初始化
	 * 
	 * @param datas
	 *            Map&lt;String, InitData&gt;对象
	 */
	public InitUtil(Map<String, InitData> datas) {
		this.datas = datas;
		gson = new Gson();
		for (Map.Entry<String, InitData> entry : this.datas.entrySet()) {
			aotuGetAccessToken(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 获取公众号唯一凭证AccessToken
	 * @param flag			初始化标志
	 * @param data			InitData对象
	 * @return accessToken AccessToken对象
	 */
	@SuppressWarnings("unused")
	public AccessToken getAccessToken(String flag, InitData data) {
		String urlStr = "";
		if (!data.isCorp()) {
			urlStr = ACCESS_TOKEN_URL.replace("APPID", data.getId()).replace("APPSECRET", data.getSecret());
		} else {
			urlStr = CORP_ACCESSTOKEN.replace("id=id", "id=" + data.getId()).replace("secret=secrect",
					"secret=" + data.getSecret());
		}
		String jsonStr = CommonUtil.httpsRequest(urlStr, "GET", null);
		Gson gson = new Gson();
		AccessToken accessToken = gson.fromJson(jsonStr, AccessToken.class);
		int Expires_in = accessToken.getExpires_in();
		if (accessToken == null) {
			try {
				throw new Exception("获取AccessToken失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
//			if (!getAddress(data.isCorp(), accessToken.getAccess_token())) {
//				getAccessToken(flag, data);
//			}
		}
//		Map<String, AccessToken> tokenMap = TokenAndTicket.get().getTokenMap();
		if (TokenAndTicket.get().getTokenMap().containsKey(flag))
			TokenAndTicket.get().getTokenMap().remove(flag);
		TokenAndTicket.get().getTokenMap().put(flag, accessToken);
//		TokenAndTicket.get().setTokenMap(tokenMap);
		AccessToken token = TokenAndTicket.get().getTokenMap().get(flag);
		return token;
	}

	/**
	 * 获取调用微信JS接口的临时票据
	 * @param flag			初始化标志
	 * @param url			请求地址
	 * @return jsApiTicket AccessToken对象
	 */
	public JSApiTicket getJSAPITicket(String flag, String url) {
		JSApiTicket jsApiTicket = null;
		try {
			AccessToken accessToken = TokenAndTicket.get().getTokenMap().get(flag);
			String token = accessToken.getAccess_token();
			jsApiTicket = AdvancedUtil.getJSApiTicket(url, token);
			Map<String, JSApiTicket> map = TokenAndTicket.get().getTicketMap();
			if (map.containsKey(flag))
				map.remove(flag);
			map.put(flag, jsApiTicket);
			TokenAndTicket.get().setTicketMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsApiTicket;
	}

	/**
	 * 获取微信ip地址
	 * @param isCorp		是否企业号
	 * @param accessToken	accesstoken
	 */
	public boolean getAddress(boolean isCorp, String accessToken) {
		String url = null;
		if (isCorp) {
			url = GET_CORP_WECHAT_ADDRESS_LIST.replace("ACCESS_TOKEN", accessToken);
		} else {
			url = GET_WECHAT_ADDRESS_LIST.replace("ACCESS_TOKEN", accessToken);
		}
		String json = CommonUtil.httpsRequest(url, GET, null);

		// System.out.println(json);
		JSONObject object = new JSONObject(json);
		if (object.has("errcode")) {
//			String wstr = isCorp ? "企业号" : "公证号";
//			try {
//				throw new Exception(wstr + "问题;" + object.toString() + "。accessToken:" + accessToken);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			return false;
		} else {
			GetAddressList list = gson.fromJson(json, GetAddressList.class);
			if (isCorp) {
				if (WechatIP.get().getCorpIPList().equals(json))
					return true;
				WechatIP.get().setCorpIPList(json);
				WechatIP.get().setCorpList(list.getIp_list());
			} else {
				if (WechatIP.get().getIpList().equals(json))
					return true;
				WechatIP.get().setIpList(json);
				WechatIP.get().setList(list.getIp_list());
			}
			CharSequence sequence = "/";
			List<String> sublist = new ArrayList<String>();
			for (String ip : WechatIP.get().getList()) {
				if (ip.contains(sequence))
					sublist.add(ip);
			}
			for (String ip : WechatIP.get().getCorpList()) {
				if (ip.contains(sequence))
					sublist.add(ip);
			}
			WechatIP.get().setSublist(sublist);
		}
		return true;
	}

	/**
	 * 自动获取AccessToken，和jsapiticket<br>
	 * 没7000秒自动获取AccessToken和jsapiticket
	 */
	private void aotuGetAccessToken(final String flag, final InitData data) {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						AccessToken accessToken = getAccessToken(flag, data);
						String url = null;
						if (data.isCorp()) {
							url = CORP_JSAPI_TICKET;
						} else {
							url = JSAPI_TICKET_URL;
						}
						while (TokenAndTicket.get().getTokenMap().get(flag) == null || TokenAndTicket.get().getTokenMap().get(flag).getAccess_token() == null) {
							TimeUnit.MILLISECONDS.sleep(200);
						}
				
						getJSAPITicket(flag, url);
						System.out.println(flag + "的accesstoken是：" + TokenAndTicket.get().getTokenMap().get(flag).getAccess_token());
						System.out.println(flag + "的jsapiticket是：" + TokenAndTicket.get().getTicketMap().get(flag).getTicket());
						if (data.isCorp()) {
							System.out.println("企业号：" + accessToken.getExpires_in() + "+1秒后刷新");
							TimeUnit.SECONDS.sleep(accessToken.getExpires_in() + 1);
						} else {
							System.out.println("公众号：" + accessToken.getExpires_in() + "-100秒后刷新");
							TimeUnit.SECONDS.sleep(Math.abs(accessToken.getExpires_in() - 100));
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
