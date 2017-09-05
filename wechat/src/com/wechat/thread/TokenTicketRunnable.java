package com.wechat.thread;

import com.google.gson.Gson;
import com.wechat.pojo.token.*;
import com.wechat.utils.AdvancedUtil;
import com.wechat.utils.CommonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.wechat.utils.C.*;

public class TokenTicketRunnable implements Runnable {

	private String appId;
	private String secret;
	private boolean isCorp;
	private Gson gson;
	private String tokenURL;
	private String ticketURL;
	private String WXSAURL;
	private String typeStr;

	public TokenTicketRunnable(String appId, String secret, boolean isCorp) {
		this.appId = appId;
		this.secret = secret;
		this.isCorp = isCorp;
		if (isCorp)
			typeStr = "企业号";
		else
			typeStr = "公众号";
		gson = new Gson();
	}


	/**
	 * 获取公众号唯一凭证AccessToken
	 * @return accessToken AccessToken对象
	 */
	@SuppressWarnings("unused")
	public AccessToken getAccessToken() {
		if (!isCorp) {
			tokenURL = ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", secret);
		} else {
			tokenURL = CORP_ACCESSTOKEN.replace("id=id", "id=" + appId).replace("secret=secrect", "secret=" + secret);
		}
		String jsonStr = CommonUtil.httpsRequest(tokenURL, "GET", null);
		AccessToken accessToken = gson.fromJson(jsonStr, AccessToken.class);
		int Expires_in = accessToken.getExpires_in();
		if (accessToken == null) {
			try {
				throw new Exception("获取AccessToken失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (TokenAndTicket.get().getTokenMap().containsKey(appId))
			TokenAndTicket.get().getTokenMap().remove(appId);
		TokenAndTicket.get().getTokenMap().put(appId, accessToken);
		return getATFS(appId);
	}

	/**
	 * 获取调用微信JS接口的临时票据
	 * @return jsApiTicket AccessToken对象
	 */
	public JSApiTicket getJSAPITicket() {
		JSApiTicket jsApiTicket = null;
		try {
			AccessToken accessToken = getATFS(appId);
			String token = accessToken.getAccess_token();
			jsApiTicket = AdvancedUtil.getJSApiTicket(ticketURL, token);
			Map<String, JSApiTicket> map = TokenAndTicket.get().getTicketMap();
			if (TokenAndTicket.get().getTicketMap().containsKey(appId))
				TokenAndTicket.get().getTicketMap().remove(appId);
			TokenAndTicket.get().getTicketMap().put(appId, jsApiTicket);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return getJSAPITFS(appId);
		}
	}

	/**
	 * 获取微信ip地址
	 * @param accessToken	accesstoken
	 */
	public boolean getAddress(String accessToken) {
		if (isCorp) {
			WXSAURL = GET_CORP_WECHAT_ADDRESS_LIST.replace("ACCESS_TOKEN", accessToken);
		} else {
			WXSAURL = GET_WECHAT_ADDRESS_LIST.replace("ACCESS_TOKEN", accessToken);
		}
		String json = CommonUtil.httpsRequest(WXSAURL, GET, null);

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

	@Override
	public void run() {
		while (true) {
			try {
				AccessToken accessToken = getAccessToken();
				if (isCorp) {
					ticketURL = CORP_JSAPI_TICKET;
				} else {
					ticketURL = JSAPI_TICKET_URL;
				}
				while (getATFS(appId) == null || getATFS(appId).getAccess_token() == null) {
					TimeUnit.MILLISECONDS.sleep(200);
				}

				getJSAPITicket();
				while (getJSAPITFS(appId) == null || getJSAPITFS(appId).getTicket() == null) {
					TimeUnit.MILLISECONDS.sleep(100);
				}
				System.out.println(typeStr + appId + "的accesstoken是：" + getATFS(appId).getAccess_token());
				System.out.println(typeStr + appId + "的jsapiticket是：" + getJSAPITFS(appId).getTicket());
				if (isCorp) {
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

	private AccessToken getATFS(String flag){
		return TokenAndTicket.get().getTokenMap().get(flag);
	}

	private JSApiTicket getJSAPITFS(String flag){
		return TokenAndTicket.get().getTicketMap().get(flag);
	}
}
