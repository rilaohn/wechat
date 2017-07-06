package com.wechat.utils;

import static com.wechat.utils.C.ACCESS_TOKEN_URL;
import static com.wechat.utils.C.CORP_ACCESSTOKEN;
import static com.wechat.utils.C.CORP_JSAPI_TICKET;
import static com.wechat.utils.C.JSAPI_TICKET_URL;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wechat.pojo.token.AccessToken;
import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.JSApiTicket;
import com.wechat.pojo.token.QRCodeResult;
import com.wechat.pojo.token.ShortUrl;
import com.wechat.pojo.token.Token;
import com.wechat.pojo.token.WechatData;
import com.wechat.process.CorpProcess;
import com.wechat.process.MsgProcess;
import com.wechat.process.SendMessage;
import com.wechat.process.WebProcess;

public class TestUtil {
	private InitData initData;
	private Token token;
	private WechatData wechatData;
	private AccessToken accessToken;
	private JSApiTicket jsApiTicket;
	private WebProcess webProcess;
	private SendMessage send;
	private CorpProcess corp;
	private MsgProcess process;
	public TestUtil(String appId, String secret, boolean isCorp) {
		this.initData = new InitData();
		this.initData.setId(appId);
		this.initData.setSecret(secret);
		this.initData.setCorp(isCorp);
		if (getTokenAndTicket(initData)) {
			webProcess = new WebProcess(initData, this.accessToken, this.jsApiTicket);
		}
	}
	public TestUtil(String appId, String secret, boolean isCorp, Token token) {
		this(appId, secret, isCorp);
		this.token = token;
		this.wechatData.setInitData(initData);
		this.wechatData.setToken(token);
	}
	
	public TestUtil(String appId, String secret, boolean isCorp, String token, String encodingAESKey, boolean encrypt) {
		this(appId, secret, isCorp);
		this.token = new Token();
		this.token.setToken(token);
		this.token.setEncodingAESKey(encodingAESKey);
		this.token.setEncrypt(encrypt);
		this.wechatData = new WechatData();
		this.wechatData.setInitData(initData);
		this.wechatData.setToken(this.token);
	}
	
	public AccessToken getAccessToken() {
		return this.accessToken;
	}
	
	public JSApiTicket getJSApiTicket() {
		return this.jsApiTicket;
	}
	
	private boolean getTokenAndTicket(InitData data) {
		String urlStr = "";
		boolean success;
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
				success = false;
			}
		}
		this.accessToken = accessToken;
		String url = null;
		if (data.isCorp()) {
			url = CORP_JSAPI_TICKET;
		} else {
			url = JSAPI_TICKET_URL;
		}
		try {
			JSApiTicket jsApiTicket = AdvancedUtil.getJSApiTicket(url, accessToken.getAccess_token());
			this.jsApiTicket = jsApiTicket;
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	/**
	 * 获取临时二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneId		场景值ID，临时二维码时为32位非0整型
	 * @return	ApiResult 二维码信息
	 */
	public QRCodeResult getTempQrcode(int expireSeconds, int sceneId) {
        return webProcess.getTempQrcode(expireSeconds, sceneId);
	}
	
	/**
	 * 获取临时二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneStr		场景值ID（字符串形式的ID），字符串类型
	 * @return
	 */
	public QRCodeResult getTempQrcode(int expireSeconds, String sceneStr) {
        return webProcess.getTempQrcode(expireSeconds, sceneStr);
	}
	
	/**
	 * 获取永久二维码
	 * @param sceneId	场景值ID，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return	ApiResult 二维码信息
	 */
	public QRCodeResult getEternalQrcode(int sceneId) {
        return webProcess.getEternalQrcode(sceneId);
	}
	
	/**
	 * 获取永久二维码
	 * @param sceneStr	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
	 * @return	ApiResult 二维码信息
	 */
	public QRCodeResult getEternalQrcode(String sceneStr) {
        return getEternalQrcode(sceneStr);
	}
	
	/**
	 * 生成并下载带临时的参数二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneId		场景值ID，临时二维码时为32位非0整型
	 * @param filePath		文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String getAndDownTempQrcode(int expireSeconds, int sceneId, String filePath) {
		return webProcess.getAndDownTempQrcode(expireSeconds, sceneId, filePath);
	}
	
	/**
	 * 生成并下载带临时的参数二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneStr		场景值ID（字符串形式的ID），字符串类型
	 * @param filePath		文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String getAndDownTempQrcode(int expireSeconds, String sceneStr, String filePath) {
		return webProcess.getAndDownTempQrcode(expireSeconds, sceneStr, filePath);
	}
	
	/**
	 * 生成并下载带永久的参数二维码
	 * @param sceneId	场景值ID，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @param filePath	文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String getAndDownEternalQrcode(int sceneId, String filePath) {
		return webProcess.getAndDownEternalQrcode(sceneId, filePath);
	}
	
	/**
	 * 生成并下载带永久的参数二维码
	 * @param sceneStr	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
	 * @param filePath	文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String getAndDownEternalQrcode(String sceneStr, String filePath) {
		return webProcess.getAndDownEternalQrcode(sceneStr, filePath);
	}

	/**
	 * 通过ticket下载带参数二维码
	 * @param ticket	获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
	 * @param filePath	文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return	文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统会自动根据文件本来的根式自动补上
	 */
	public String downQrcodeByTicket(String ticket, String filePath) {
		return webProcess.downQrcodeByTicket(ticket, filePath);
	}
	
	/**
	 * 获取微信短链接
	 * @param longUrl 需要生成短链接的长链接
	 * @return	ShortUrl 短链接信息
	 */
	public ShortUrl getShortUrl(String longUrl) {
		return webProcess.getShortUrl(longUrl);
	}

	private String getToken() {
		return this.accessToken.getAccess_token();
	}
	
	private String getTicket() {
		return this.jsApiTicket.getTicket();
	}
}
