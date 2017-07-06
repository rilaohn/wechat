package com.wechat.process;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wechat.pojo.token.AccessToken;
import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.JSApiTicket;
import com.wechat.pojo.token.QRCodeResult;
import com.wechat.pojo.token.ShortUrl;
import com.wechat.pojo.token.TokenAndTicket;
import com.wechat.pojo.token.WebAccessToken;
import com.wechat.pojo.user.CorpWebUserInfo;
import com.wechat.pojo.user.CorpWebUserTicket;
import com.wechat.pojo.user.Signature;
import com.wechat.pojo.user.WebUserInfo;
import com.wechat.utils.AdvancedUtil;
import com.wechat.utils.C;
import com.wechat.utils.CommonUtil;
import com.wechat.utils.DownFile;
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
	private AccessToken token;
	private JSApiTicket ticket;
	private Gson gson;

	public WebProcess(InitData data, AccessToken token, JSApiTicket ticket) {
		this.data = data;
		this.token = token;
		this.ticket = ticket;
		gson = new GsonBuilder().disableHtmlEscaping().create();
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
		String ticket = getTicket();
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
	
	/**
	 * 获取临时二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneId		场景值ID，临时二维码时为32位非0整型
	 * @return	ApiResult 二维码信息
	 */
	public QRCodeResult getTempQrcode(int expireSeconds, int sceneId) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("expire_seconds", expireSeconds);
        params.put("action_name", "QR_SCENE");

        Map<String, Object> actionInfo = new HashMap<String, Object>();
        Map<String, Object> scene = new HashMap<String, Object>();
        scene.put("scene_id", sceneId);

        actionInfo.put("scene", scene);
        params.put("action_info", actionInfo);
        return qrcodeRequset(gson.toJson(params));
	}
	
	/**
	 * 获取临时二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneStr		场景值ID（字符串形式的ID），字符串类型
	 * @return
	 */
	public QRCodeResult getTempQrcode(int expireSeconds, String sceneStr) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("expire_seconds", expireSeconds);
        params.put("action_name", "QR_STR_SCENE");

        Map<String, Object> actionInfo = new HashMap<String, Object>();
        Map<String, Object> scene = new HashMap<String, Object>();
        scene.put("scene_str", sceneStr);

        actionInfo.put("scene", scene);
        params.put("action_info", actionInfo);
        return qrcodeRequset(gson.toJson(params));
	}
	
	/**
	 * 获取永久二维码
	 * @param sceneId	场景值ID，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return	ApiResult 二维码信息
	 */
	public QRCodeResult getEternalQrcode(int sceneId) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("action_name", "QR_LIMIT_SCENE");

        Map<String, Object> actionInfo = new HashMap<String, Object>();
        Map<String, Object> scene = new HashMap<String, Object>();
        scene.put("scene_id", sceneId);

        actionInfo.put("scene", scene);
        params.put("action_info", actionInfo);
        return qrcodeRequset(gson.toJson(params));
	}
	
	/**
	 * 获取永久二维码
	 * @param sceneStr	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
	 * @return	ApiResult 二维码信息
	 */
	public QRCodeResult getEternalQrcode(String sceneStr) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("action_name", "QR_LIMIT_STR_SCENE");

        Map<String, Object> actionInfo = new HashMap<String, Object>();
        Map<String, Object> scene = new HashMap<String, Object>();
        scene.put("scene_str", sceneStr);

        actionInfo.put("scene", scene);
        params.put("action_info", actionInfo);
        return qrcodeRequset(gson.toJson(params));
	}
	
	/**
	 * 获取二维码请求
	 * @param post	请求数据
	 * @return	ApiResult 二维码信息
	 */
	public QRCodeResult qrcodeRequset(String post) {
		String url = C.QRCODE_WITH_PARAM.replace("ACCESS_TOKEN", getAccessToken());
		String jsonStr = CommonUtil.httpsRequest(url, "POST", post);
		JSONObject json = new JSONObject(jsonStr);
		if (json.has("errcode")) {
			if (json.get("errcode") instanceof Integer) {
				if (json.getInt("errcode") != 0) {
					try {
						throw new Exception(jsonStr);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (json.get("errcode") instanceof String) {
				if (!json.getString("errcode").equals("0")) {
					try {
						throw new Exception(jsonStr);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		QRCodeResult result = gson.fromJson(jsonStr, QRCodeResult.class);
		
		return result;
	}
	
	/**
	 * 生成并下载带临时的参数二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneId		场景值ID，临时二维码时为32位非0整型
	 * @param filePath		文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String getAndDownTempQrcode(int expireSeconds, int sceneId, String filePath) {
		QRCodeResult qrcode = getTempQrcode(expireSeconds, sceneId);
		String url = C.GET_QRCODE_PIC_BY_TICKET.replace("TICKET", qrcode.getTicket());
		return DownFile.down(url, filePath);
	}
	
	/**
	 * 生成并下载带临时的参数二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneStr		场景值ID（字符串形式的ID），字符串类型
	 * @param filePath		文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String getAndDownTempQrcode(int expireSeconds, String sceneStr, String filePath) {
		QRCodeResult qrcode = getTempQrcode(expireSeconds, sceneStr);
		String url = C.GET_QRCODE_PIC_BY_TICKET.replace("TICKET", qrcode.getTicket());
		return DownFile.down(url, filePath);
	}
	
	/**
	 * 生成并下载带永久的参数二维码
	 * @param sceneId	场景值ID，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @param filePath	文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String getAndDownEternalQrcode(int sceneId, String filePath) {
		QRCodeResult qrcode = getEternalQrcode(sceneId);
		String url = C.GET_QRCODE_PIC_BY_TICKET.replace("TICKET", qrcode.getTicket());
		return DownFile.down(url, filePath);
	}
	
	/**
	 * 生成并下载带永久的参数二维码
	 * @param sceneStr	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
	 * @param filePath	文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String getAndDownEternalQrcode(String sceneStr, String filePath) {
		QRCodeResult qrcode = getEternalQrcode(sceneStr);
		String url = C.GET_QRCODE_PIC_BY_TICKET.replace("TICKET", qrcode.getTicket());
		return DownFile.down(url, filePath);
	}

	/**
	 * 通过ticket下载带参数二维码
	 * @param ticket	获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
	 * @param filePath	文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return	文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String downQrcodeByTicket(String ticket, String filePath) {
		String url = C.GET_QRCODE_PIC_BY_TICKET.replace("TICKET", ticket);
		return DownFile.down(url, filePath);
	}
	
	/**
	 * 获取微信短链接
	 * @param longUrl 需要生成短链接的长链接
	 * @return	ShortUrl 短链接信息
	 */
	public ShortUrl getShortUrl(String longUrl) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "long2short");
        params.put("long_url", longUrl);
		String url = C.LONG_2_SHORT_URL.replace("ACCESS_TOKEN", getAccessToken());
		String jsonStr = CommonUtil.httpsRequest(url, "POST", gson.toJson(params));
		ShortUrl sUrl = gson.fromJson(jsonStr, ShortUrl.class);
		if (sUrl.getErrcode() != 0) {
			try {
				throw new Exception(jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sUrl;
	}
	
	// FIXME 下面是私有方法
	private String getAccessToken() {
		return this.token.getAccess_token();
	}
	
	private String getTicket() {
		return this.ticket.getTicket();
	}
}
