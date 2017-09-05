package com.wechat.cache;

import com.google.gson.Gson;
import com.wechat.pojo.token.AccessToken;
import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.JSApiTicket;
import com.wechat.utils.AdvancedUtil;
import com.wechat.utils.CommonUtil;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.wechat.utils.C.*;

public class WXCache {
	private ConcurrentHashMap<String, TokenCache> tokenMap;
	private ConcurrentHashMap<String, TicketCache> ticketMap;
	private static ConcurrentHashMap<String, InitData> initMap;

	private Gson gson;
	private String tokenURL;
	private String ticketURL;

	private static WXCache cache;

	private WXCache() {
		tokenURL = null;
		ticketURL = null;
		tokenMap = new ConcurrentHashMap<>();
		ticketMap = new ConcurrentHashMap<>();
		initMap = new ConcurrentHashMap<>();
		gson = new Gson();
	}

	/**
	 * 获取WXCache对象
	 * @param appId		公证号的appId
	 * @param secret	公证号的secret
	 * @param isCorp	是否是企业号
	 * @param flag		标志
	 * @return WXCache对象
	 */
	public static WXCache get(String appId, String secret, boolean isCorp, String flag){
		if (cache == null)
			cache = new WXCache();
		if (appId == null || appId.trim().length() == 0)
			throwException("AppId不能为空！");
		if (secret == null || secret.trim().length() == 0)
			throwException("AppSecret不能为空！");
		if (flag == null || flag.trim().length() == 0) {
			flag = appId + secret;
		}
		cache.putInitData(appId, secret, isCorp, flag);
		return cache;
	}

	private static void putInitData(String appId, String secret, boolean isCorp, String flag){
		InitData oriData = initMap.get(flag);
		if (oriData != null){
			if (!oriData.getId().equals(appId))
				throwException("同一个flag存在被不同的AppId使用！");
			if (!oriData.getSecret().equals(secret))
				throwException("同一个flag存在被不同的AppSecret使用！");
			if (!(oriData.isCorp() == isCorp))
				throwException("同一个flag存在被企业号和公众号同时使用！");
		}
		InitData initData = new InitData();
		initData.setId(appId);
		initData.setSecret(secret);
		initData.setCorp(isCorp);
		initData.setFlag(flag);
		initMap.put(flag, initData);
	}

	/**
	 * 获取AccessToken
	 * @param flag		标志
	 * @return 返回AccessToken对象
	 */
	public AccessToken getAccessToken(String flag){
		TokenCache token = tokenMap.get(flag);
		long nowtime = getNowTime();
		if (token == null || nowtime - token.getSaveTime() >= token.getAccessToken().getExpires_in()){
			AccessToken accessToken = reqAccessToken(flag);
			while (accessToken == null || accessToken.getAccess_token() == null){
				try {
					TimeUnit.MILLISECONDS.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			tokenMapPut(accessToken, flag);
		}
		return tokenMap.get(flag).getAccessToken();
	}

	/**
	 * 获取JSAPITicket
	 * @param flag		标志
	 * @return 返回JSApiTicket对象
	 */
	public JSApiTicket getJSApiTicket(String flag){
		TicketCache ticketCache = ticketMap.get(flag);
		AccessToken accessToken = getAccessToken(flag);
		long nowtime = getNowTime();
		if (ticketCache == null || nowtime - ticketCache.getSaveTime() >= ticketCache.getJsApiTicket().getExpires_in()){
			JSApiTicket jsApiTicket = reqJSAPITicket(accessToken.getAccess_token(), initMap.get(flag).isCorp());
			while (jsApiTicket == null || jsApiTicket.getTicket() == null){
				try {
					TimeUnit.MILLISECONDS.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ticketMapPut(jsApiTicket, flag);
		}
		return ticketMap.get(flag).getJsApiTicket();
	}

	private AccessToken reqAccessToken(String flag) {
		InitData data = initMap.get(flag);
		if (!data.isCorp()) {
			tokenURL = ACCESS_TOKEN_URL.replace("APPID", data.getId()).replace("APPSECRET", data.getSecret());
		} else {
			tokenURL = CORP_ACCESSTOKEN.replace("id=id", "id=" + data.getId()).replace("secret=secrect", "secret=" + data.getSecret());
		}
		String jsonStr = CommonUtil.httpsRequest(tokenURL, "GET", null);
		AccessToken accessToken = gson.fromJson(jsonStr, AccessToken.class);
		if (accessToken == null) {
			try {
				throw new Exception("获取AccessToken失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return accessToken;
	}

	private JSApiTicket reqJSAPITicket(String accessToken, boolean isCorp) {
		JSApiTicket jsApiTicket = null;
		if (isCorp) {
			ticketURL = CORP_JSAPI_TICKET;
		} else {
			ticketURL = JSAPI_TICKET_URL;
		}
		try {
			jsApiTicket = AdvancedUtil.getJSApiTicket(ticketURL, accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsApiTicket;
	}

	private long getNowTime(){
		Date date = new Date();
		return date.getTime() / 1000;
	}

	private void tokenMapPut(AccessToken accessToken, String flag){
		TokenCache token = new TokenCache();
		token.setKey(flag);
		token.setAccessToken(accessToken);
		token.setSaveTime(getNowTime());
		tokenMap.put(flag, token);
	}

	private void ticketMapPut(JSApiTicket jsApiTicket, String flag){
		TicketCache ticketCache = new TicketCache();
		ticketCache.setKey(flag);
		ticketCache.setJsApiTicket(jsApiTicket);
		ticketCache.setSaveTime(getNowTime());
		ticketMap.put(flag, ticketCache);
	}

	private static void throwException(String msg){
		try {
			throw new Exception(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
