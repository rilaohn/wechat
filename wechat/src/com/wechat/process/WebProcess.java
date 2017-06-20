package com.wechat.process;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.TokenAndTicket;
import com.wechat.pojo.token.WebAccessToken;
import com.wechat.pojo.user.CorpWebUserInfo;
import com.wechat.pojo.user.CorpWebUserTicket;
import com.wechat.pojo.user.Signature;
import com.wechat.pojo.user.WebUserInfo;
import com.wechat.utils.AdvancedUtil;
import com.wechat.utils.JSSign;

/**
 * 类名：WebProcess.java <br>
 * 描述：网页处理 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月11日 下午2:38:50 <br>
 * 发布版本：V1.0 <br>
 */
public class WebProcess {

	private InitData data;

	public WebProcess(InitData data) {
		this.data = data;
	}

	/**
	 * 获取WebAccessToken
	 * @param code			获取的微信code
	 * @return				WebAccessToken对象
	 * @throws Exception	失败抛出异常
	 */
	public WebAccessToken getWebAccessToken(String code) throws Exception {
		return AdvancedUtil.getWebAccessToken(data.getId(), data.getSecret(), code);
	}

	/**
	 * 获取WebAccessToken
	 * @param request		HttpServletRequest请求
	 * @return				WebAccessToken对象
	 * @throws Exception	失败抛出异常
	 */
	public WebAccessToken getWebAccessToken(HttpServletRequest request) throws Exception {
		String code = request.getParameter("code");
		if (code != null && code.length() > 0)
			return AdvancedUtil.getWebAccessToken(data.getId(), data.getSecret(), code);
		return null;
	}

	/**
	 * 获取web授权的用户信息
	 * @param accessToken	webAccessToken
	 * @param openId		用户的openID
	 * @return				WebUserInfo对象
	 * @throws Exception	失败抛出异常
	 */
	public WebUserInfo getWebUserInfo(String accessToken, String openId) throws Exception {
		return AdvancedUtil.getWebUserInfo(accessToken, openId);
	}

	/**
	 * 微信重定向到JSSDK页面
	 * @param request		HttpServletRequest请求
	 * @param response		HttpServletResponse请求
	 * @param redirectUrl	填写重定向网页，暨Servlet地址（web授权时的REDIRECT_URI）
	 * @param targetUrl		重定向的目标网页，将把用户信息，appId，timestamp，nonceStr，signature
	 * @param ticket		通过code获取的jsapi_icket
	 * @throws Exception	失败抛出异常
	 */
	public void redirect2JSSdk(HttpServletRequest request, HttpServletResponse response,
			String redirectUrl, String targetUrl, String ticket)
			throws Exception {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String tempUrl = redirectUrl + "?code=" + code + "&state=" + state;
		WebAccessToken wat = getWebAccessToken(code);
		JSSign sign = new JSSign();
		Map<String, String> congifMap = sign.sign(ticket, tempUrl);
		WebUserInfo wui = AdvancedUtil.getWebUserInfo(wat.getAccess_token(), wat.getOpenid());
		request.setAttribute("appId", data.getId());
		request.setAttribute("timestamp", congifMap.get("timestamp"));
		request.setAttribute("nonceStr", congifMap.get("nonceStr"));
		request.setAttribute("signature", congifMap.get("signature"));
		request.setAttribute("openid", wui.getOpenid());

		request.getRequestDispatcher(targetUrl).forward(request, response);
	}

	/**
	 * 获取网页授权url
	 * @param id			公证号的APPID或者企业号的CorpID
	 * @param redirectUri	重定向网页（建议填处理改业务的servlet地址）请使用urlencode对链接进行处理
	 * @param scope			应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @param state			重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @return				OAUTH2的url字符串
	 */
	public String getOauth2Url(String id, String redirectUri, String scope, String state) {
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + id
				+ "&redirect_uri=" + redirectUri + "&response_type=code&scope=" + scope
				+ "&state=" + state + "#wechat_redirect";
	}

	/**
	 * 企业获取web用户信息
	 * @param accessToken	调用接口凭证
	 * @param code			通过成员授权获取到的code，每次成员授权带上的code将不一样，code只能使用一次，10分钟未被使用自动过期
	 * @return				CorpWebUserInfo对象
	 */
	public CorpWebUserTicket getCorpWebUserInfo(String accessToken, String code) {
		return AdvancedUtil.getCorpWebUserTicket(accessToken, code);
	}
	
	// FIXME  一下的方法没有加入到MyWechat类里面
	
	/**
	 * 获取web授权的用户信息
	 * @param code	获取的微信code
	 * @return		WebUserInfo对象
	 */
	public WebUserInfo getWebUserInfo(String code) {
		WebUserInfo wui = null;
		try {
			WebAccessToken wat = getWebAccessToken(code);
			wui = getWebUserInfo(wat.getAccess_token(), wat.getOpenid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wui;
	}
	
	/**
	 * 获取使用微信JSSDK的初始化差数
	 * @param url	通过请求地址加上code和state的url	
	 * @return	map对象
	 */
	public Map<String, String> getJSSDKConfig(String url) {
		JSSign sign = new JSSign();
		String ticket = TokenAndTicket.get().getTicketMap().get(data.getFlag()).getTicket();
		Map<String, String> map = sign.sign(ticket, url);
		map.put("appId", data.getId());
		return map;
	}
	
	/**
	 * 获取使用微信JSSDK的初始化差数
	 * @param url		通过请求地址加上code和state的url	
	 * @param code		用户code
	 * @return			Signature对象
	 */
	public Signature getJSSDKConfig(String url, String code) {
		Signature signature = new Signature();
		try {
			WebAccessToken wat = getWebAccessToken(code);
			Map<String, String> map = getJSSDKConfig(url);
			signature.setAppId(data.getId());
			signature.setOpenId(wat.getOpenid());
			signature.setNonce(map.get("nonceStr"));
			signature.setSignature(map.get("signature"));
			signature.setTimestamp(map.get("timestamp"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signature;
	}
	
	/**
	 * 获取使用微信JSSDK的初始化参数
	 * @param url		通过请求地址加上code和state的url
	 * @return			Signature对象
	 */
	public Signature getJSSDKConfigOutOpenId(String url) {
		Signature signature = new Signature();
		try {
			Map<String, String> map = getJSSDKConfig(url);
			signature.setAppId(data.getId());
			signature.setNonce(map.get("nonceStr"));
			signature.setSignature(map.get("signature"));
			signature.setTimestamp(map.get("timestamp"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signature;
	}
	
	// FIXME 获取企业号网页用户数据
	/**
	 * 获取企业号web页面snsapi_nase信息
	 * @param code
	 * @return CorpWebUserTicket对象
	 */
	public CorpWebUserTicket getCorpSnsapiBase(String code) {
		CorpWebUserTicket ticket = AdvancedUtil.getCorpWebUserTicket(getAccessToken(), code);
		return ticket;
	}
	
	/**
	 * 获取企业号web页面snsapi_info信息
	 * @param code
	 * @return CorpWebUserInfo对象
	 */
	public CorpWebUserInfo getCorpSnsapiInfo(String code) {
		CorpWebUserTicket ticket = getCorpSnsapiBase(code);
		CorpWebUserInfo info = AdvancedUtil.getCorpWebUserInfo(getAccessToken(), ticket.getUser_ticket());
		return info;
	}
	
	// FIXME 下面是私有方法
	private String getAccessToken() {
		return TokenAndTicket.get().getTokenMap().get(data.getFlag()).getAccess_token();
	}
}
