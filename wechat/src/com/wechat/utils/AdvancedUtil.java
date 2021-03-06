package com.wechat.utils;

import static com.wechat.utils.C.*;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.wechat.pojo.token.JSApiTicket;
import com.wechat.pojo.token.JSApiTicketSingleton;
import com.wechat.pojo.token.QRCodeResult;
import com.wechat.pojo.token.UserTicket;
import com.wechat.pojo.token.WebAccessToken;
import com.wechat.pojo.user.CorpWebUserInfo;
import com.wechat.pojo.user.CorpWebUserTicket;
import com.wechat.pojo.user.WebUserInfo;

/**
 * 类名：AdvancedUtil <br>
 * 描述：进阶包装工具 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class AdvancedUtil {

	/**
	 * 获取网页授权凭证
	 * @param appId				公众账号的唯一标识
	 * @param appSecret			公众账号的密钥
	 * @param code				用户授权返回的code
	 * @return WebAccessToken	WebAccessToken对象
	 * @throws Exception		获取失败抛出异常
	 */
	public static WebAccessToken getWebAccessToken(String appId, String appSecret, String code)
			throws Exception {

		WebAccessToken wat = new WebAccessToken();

		// 拼接请求地址
		String requestUrl = WEB_ACCESS_TOKEN_URL;
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		String jsonStr = CommonUtil.httpsRequest(requestUrl, "GET", null);

		JSONObject json = new JSONObject(jsonStr);
		if (!json.has("errcode")) {
			Gson gson = new Gson();
			wat = gson.fromJson(jsonStr, WebAccessToken.class);
		} else if (json.getInt("errcode") == 0 ) {
			Gson gson = new Gson();
			wat = gson.fromJson(jsonStr, WebAccessToken.class);
		} else {
			String errmsg = "获取网页AccessToken失败\n错误代码(errcode)：" + json.getInt("errcode")
					+ "， 错误消息(errmsg)：" + json.getString("errmsg") + "。";
			throw new Exception(errmsg);
		}

		return wat;
	}

	/**
	 * 获取JSApiTicket
	 * @param url				请求地址
	 * @param accessToken		公众号授权接口调用凭证（非网页accesstoken）
	 * @return					JSApiTicket对象
	 * @throws Exception		获取失败抛出异常
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static JSApiTicket getJSApiTicket(String url, String accessToken) throws Exception {

		JSApiTicket jsApiTicket = new JSApiTicket();
		// 拼接请求地址
		String urlStr = url.replace("ACCESS_TOKEN", accessToken);
		// 通过网页授权获取用户信息
		String jsonStr = CommonUtil.httpsRequest(urlStr, "GET", null);
//		System.out.println(jsonStr);
		JSONObject json = new JSONObject(jsonStr);
		int errcode = -1;
		errcode = json.getInt("errcode");
		if (errcode == 0) {
			jsApiTicket.setErrcode(errcode);

			String errmsg = json.getString("errmsg");
			jsApiTicket.setErrmsg(errmsg);

			String ticket = json.getString("ticket");
			jsApiTicket.setTicket(ticket);

			int expires_in = json.getInt("expires_in");
			jsApiTicket.setExpires_in(expires_in);
		} else {
			String errmsg = "获取网页JSApiTicket失败\n错误代码(errcode)：" + json.getInt("errcode")
					+ "， 错误消息(errmsg)：" + json.getString("errmsg") + "。";
			throw new Exception(errmsg);
		}

		return jsApiTicket;

	}

	/**
	 * 通过网页授权获取用户信息
	 * @param accessToken	网页授权接口调用凭证
	 * @param openId		用户标识
	 * @return WebUserInfo	WebUserInfo对象
	 * @throws Exception	如果获取失败就抛出异常
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static WebUserInfo getWebUserInfo(String accessToken, String openId)
			throws Exception {
		WebUserInfo webUserInfo = null;
		// 拼接请求地址
		String requestUrl = WEB_USER_INFO_URL.replace("ACCESS_TOKEN", accessToken)
				.replace("OPENID", openId);
		// 通过网页授权获取用户信息
		String jsonStr = CommonUtil.httpsRequest(requestUrl, "GET", null);
		JSONObject json = new JSONObject(jsonStr);
		if (!json.has("errcode")) {
			Gson gson = new Gson();
			webUserInfo = gson.fromJson(jsonStr, WebUserInfo.class);
		} else if (json.getInt("errcode") == 0 ) {
			Gson gson = new Gson();
			webUserInfo = gson.fromJson(jsonStr, WebUserInfo.class);
		} else {
			String errmsg = "获取网页AccessToken失败\n错误代码(errcode)：" + json.getInt("errcode")
					+ "， 错误消息(errmsg)：" + json.getString("errmsg") + "。";
			throw new Exception(errmsg);
		}
		return webUserInfo;
	}
	
	/**
	 * 获取用户信息JSON
	 * @param accessToken	网页授权接口调用凭证
	 * @param openId		用户标识
	 * @return userInfoJson	用户基本信息
	 */
	public static JSONObject getUserInfoJson(String accessToken, String openId){
		JSONObject userInfoJson = null;
		// 拼接请求地址
		String requestUrl = GET_USER_INFO_URL.replace("ACCESS_TOKEN", accessToken)
				.replace("OPENID", openId);
		// 通过网页授权获取用户信息
		String jsonStr = CommonUtil.httpsRequest(requestUrl, "GET", null);
		userInfoJson = new JSONObject(jsonStr);
		return userInfoJson;
	}
	
	/**
	 * 判断用户是否关注
	 * @param accessToken	网页授权接口调用凭证
	 * @param openId		用户标识
	 * @return	关注了返回true没有关注返回false
	 */
	public static boolean userSubscribe(String accessToken, String openId){
		JSONObject userInfoJson = getUserInfoJson(accessToken, openId);
		int subscribe = userInfoJson.getInt("subscribe");
		if (subscribe == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 企业获取web user ticket
	 * @param accessToken	调用接口凭证
	 * @param code			通过成员授权获取到的code，每次成员授权带上的code将不一样，code只能使用一次，10分钟未被使用自动过期
	 * @return				CorpWebUserInfo对象
	 */
	public static CorpWebUserTicket getCorpWebUserTicket(String accessToken, String code) {
		String url = CORP_WEB_USER_INFO.replace("ACCESS_TOKEN", accessToken).replace("CODE",
				code);
		CorpWebUserTicket cwi = null;
		String jsonStr = CommonUtil.httpsRequest(url, GET, null);
		JSONObject json = new JSONObject(jsonStr);
		if (!json.has("errcode")) {
			Gson gson = new Gson();
			cwi = gson.fromJson(jsonStr, CorpWebUserTicket.class);
		} else if (json.getInt("errcode") == 0 ) {
			Gson gson = new Gson();
			cwi = gson.fromJson(jsonStr, CorpWebUserTicket.class);
		} else {
			String errmsg = "获取网页user ticket失败\n错误代码(errcode)：" + json.getInt("errcode")
					+ "， 错误消息(errmsg)：" + json.getString("errmsg") + "。";
			try {
				throw new Exception(errmsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cwi;
	}
	
	/**
	 * 获取企业号网页用户信息
	 * @param accessToken	企业号的唯一凭证
	 * @param userTicket	企业号webticket
	 * @return	CorpWebUserInfo 实体类
	 */
	public static CorpWebUserInfo getCorpWebUserInfo(String accessToken, String userTicket) {
		Gson gson = new Gson();
		String url = CORP_WEB_USER_INFO_BY_TICKET.replace("ACCESS_TOKEN", accessToken);
		CorpWebUserInfo info = null;
		UserTicket ticket = new UserTicket();
		ticket.setUser_ticket(userTicket);
		try {
			String postData = gson.toJson(ticket);
			String jsonStr = CommonUtil.httpsRequest(url, POST, postData);
			JSONObject json = new JSONObject(jsonStr);
			if (!json.has("errcode")) {
				info = gson.fromJson(jsonStr, CorpWebUserInfo.class);
			} else if (json.getInt("errcode") == 0 ) {
				info = gson.fromJson(jsonStr, CorpWebUserInfo.class);
			} else {
				String errmsg = "企业号获取网页用户信息失败\n错误代码(errcode)：" + json.getInt("errcode")
						+ "， 错误消息(errmsg)：" + json.getString("errmsg") + "。";
				try {
					throw new Exception(errmsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
}
