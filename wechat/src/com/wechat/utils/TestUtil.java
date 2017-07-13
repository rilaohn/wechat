package com.wechat.utils;

import static com.wechat.utils.C.ACCESS_TOKEN_URL;
import static com.wechat.utils.C.CORP_ACCESSTOKEN;
import static com.wechat.utils.C.CORP_JSAPI_TICKET;
import static com.wechat.utils.C.JSAPI_TICKET_URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wechat.corp.pojo.auth.login.LoginInfo;
import com.wechat.corp.pojo.res.manager.AgentInfo;
import com.wechat.corp.pojo.res.manager.Department;
import com.wechat.corp.pojo.res.manager.DepartmentUser;
import com.wechat.corp.pojo.res.manager.GetAgent;
import com.wechat.corp.pojo.res.manager.GetBatchResult;
import com.wechat.corp.pojo.res.manager.GetTagUserSimple;
import com.wechat.corp.pojo.res.manager.GetUser;
import com.wechat.corp.pojo.res.manager.NameValue;
import com.wechat.corp.pojo.res.manager.Tag;
import com.wechat.corp.pojo.res.manager.TagUserInfoSimple;
import com.wechat.corp.pojo.res.manager.UserInfo;
import com.wechat.corp.pojo.upload.MPNewsBack;
import com.wechat.corp.pojo.upload.MaterialItem;
import com.wechat.menu.MenuUtil;
import com.wechat.pojo.menu.Menu;
import com.wechat.pojo.menu.View;
import com.wechat.pojo.msg.resp.Article;
import com.wechat.pojo.msg.resp.Image;
import com.wechat.pojo.msg.resp.Music;
import com.wechat.pojo.msg.resp.Video;
import com.wechat.pojo.msg.resp.Voice;
import com.wechat.pojo.msg.send.kf.News;
import com.wechat.pojo.msg.send.kf.WXCard;
import com.wechat.pojo.msg.send.template.TemplateData;
import com.wechat.pojo.msg.send.template.TemplateList;
import com.wechat.pojo.token.AccessToken;
import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.JSApiTicket;
import com.wechat.pojo.token.QRCodeResult;
import com.wechat.pojo.token.ShortUrl;
import com.wechat.pojo.token.Token;
import com.wechat.pojo.token.TokenAndTicket;
import com.wechat.pojo.token.WebAccessToken;
import com.wechat.pojo.token.WechatData;
import com.wechat.pojo.user.CorpWebUserInfo;
import com.wechat.pojo.user.CorpWebUserTicket;
import com.wechat.pojo.user.Signature;
import com.wechat.pojo.user.WebUserInfo;
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
	private CheckUtil util;
	private HttpServletRequest request;
	private MenuUtil menuUtil;
	public TestUtil(String appId, String secret, boolean isCorp) {
		this.initData = new InitData();
		this.initData.setId(appId);
		this.initData.setSecret(secret);
		this.initData.setCorp(isCorp);
		if (getTokenAndTicket(initData)) {
			webProcess = new WebProcess(initData, this.accessToken, this.jsApiTicket);
		}
		util = new CheckUtil();
		menuUtil = new MenuUtil(this.accessToken);
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
	
	public TestUtil(String appId, String secret, boolean isCorp, String token, String encodingAESKey, boolean encrypt, HttpServletRequest request) {
		this(appId, secret, isCorp, token, encodingAESKey, encrypt);
		this.request = request;
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
	 * 检查请求是否来自微信
	 * @param request		HttpServletRequest请求
	 * @return				echostr
	 */
	public String checkSignature(HttpServletRequest request) {
		checkToken();
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		if (!util.checkSignature(token.getToken(), signature, timestamp, nonce)) {
			return "error";
		}
		return echostr;
	}

	/**
	 * 检查请求是否来自微信
	 * <ol>
	 * <li>仅初始化时有HttpServletRequest才能使用</li></ol>
	 * @return		echostr
	 */
	public String checkSignature() {
		return checkSignature(request);
	}

	/**
	 * 检查请求是否来自微信
	 * @param sign		微信签名对象
	 * @return			来自微信返回随机字符串echostr否则返回字符error
	 */
	public String checkSignature(Signature sign) {
		checkToken();
		if (util.checkSignature(token.getToken(), sign.getSignature(), sign.getTimestamp(), sign.getNonce())) {
			return sign.getEchostr();
		} else {
			return "error";
		}
	}

	/**
	 * 检查请求是否来自微信
	 * 
	 * @param signature		微信加密签名
	 * @param timestamp		时间戳
	 * @param nonce			随机数
	 * @return fromWechat	校验正确返回true，失败返回false
	 */
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		checkToken();
		boolean fromWechat = false;
		if (util.checkSignature(token.getToken(), signature, timestamp, nonce)) {
			fromWechat = true;
		}
		return fromWechat;
	}

	/**
	 * 检查请求是否来自微信
	 * 
	 * @param signature		微信加密签名
	 * @param timestamp		时间戳
	 * @param nonce			随机数
	 * @param echostr		随机字符串
	 * @return 				校验正确返回签名对象中的随机字符串否则返回字符error
	 */
	public String checkSignature(String signature, String timestamp, String nonce, String echostr) {
		checkToken();
		if (util.checkSignature(token.getToken(), signature, timestamp, nonce)) {
			return echostr;
		} else {
			return "error";
		}
	}

	/**
	 * 获取公众号唯一凭证AccessToken
	 * 初始化了 AppId和AppSecret的可以调用这个函数 没有初始化的通过带参数的调用
	 * @return accessToken		AccessToken对象
	 */

	/**
	 * 通过code换取网页授权access_token
	 * @param code					微信服务器发来的code参数
	 * @return webAccessToken		WebAccessToken对象
	 */
	public WebAccessToken getWebAccessToken(String code) {
		WebAccessToken webAccessToken = null;
		try {
			webAccessToken = AdvancedUtil.getWebAccessToken(initData.getId(), initData.getSecret(), code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webAccessToken;
	}

	/**
	 * 创建菜单
	 * @param menu				Menu对象
	 * @param isPersonality		是个性化菜单TRUE，否侧FALSE
	 * @return					成功TRUE，失败FALSE
	 */
	public boolean createMenu(Menu menu, boolean isPersonality) {
		Gson gson = new Gson();
		String oldMenu = getMenu();
		String newMenu = gson.toJson(menu);
		boolean result = false;
		if (!oldMenu.equals(newMenu)) {
			result = menuUtil.createMenu(menu, false);
		}
		return result;
	}

	/**
	 * 删除菜单
	 * @return		成功TRUE，失败FALSE
	 */
	public boolean deleteMenu() {
		return menuUtil.deleteMenu();
	}

	/**
	 * 获取菜单列表数据
	 * @return		json字符串数据
	 */
	public String getMenu() {
		return menuUtil.getMenu();
	}

	/**
	 * 获取自定义菜单配置接口
	 * @return 自定义菜单配置字符串
	 */
	public String getMenuInfo() {
		return menuUtil.getMenuInfo();
	}

	/**
	 * 获取支持微信JS的菜单View控件
	 * @param name			菜单view名称
	 * @param REDIRECT_URI	授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param SCOPE			应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @param STATE			重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @return				菜单view对象
	 */
	public View getWebView(String name, String REDIRECT_URI, String SCOPE, String STATE) {
		return menuUtil.getWebView(name, initData.getId(), REDIRECT_URI, SCOPE, STATE);
	}

	/**
	 * 获取菜单view控件
	 * @param name	菜单view名称
	 * @param url	跳转的url
	 * @return		菜单view对象
	 */
	public View getWebView(String name, String url) {
		return menuUtil.getWebView(name, url);
	}

	/**
	 * 回复用户文本消息
	 * @param request		HttpServletRequest请求
	 * @param content		返回给用户的文本内容
	 * @return respXml		xml格式文本消息字符串
	 */
	public String replyText(HttpServletRequest request, String content) {
		String respXml = "";
		if (this.request != request) {
			process.setRequest(request);
		}
		respXml = process.replyText(content);
		return respXml;
	}

	/**
	 * 回复用户文本消息
	 * 初始化带有HttpServletRequest可以此方法
	 * @param content		要回复的文本内容
	 * @return				xml格式文本消息字符串
	 */
	public String replyText(String content) {
		return request == null ? null : replyText(request, content);
	}

	/**
	 * 回复用户图片消息
	 * @param request	HttpServletRequest请求
	 * @param image		Image类型实体
	 * @return			xml格式图片消息字符串
	 */
	public String replyImage(HttpServletRequest request, Image image) {
		String respXml = "";
		if (this.request != request) {
			process.setRequest(request);
		}
		respXml = process.replyImage(image);
		return respXml;
	}

	/**
	 * 回复用户图片消息
	 * 初始化带有HttpServletRequest可以此方法
	 * @param image		Image类型实体
	 * @return			xml格式图片消息字符串
	 */
	public String replyImage(Image image) {
		return request == null ? null : replyImage(request, image);
	}

	/**
	 * 返回语音消息
	 * @param request	HttpServletRequest请求
	 * @param voice		Voice实体
	 * @return			xml格式语音消息字符串
	 */
	public String replyVoice(HttpServletRequest request, Voice voice) {
		String respXml = "";
		if (this.request != request) {
			process.setRequest(request);
		}
		respXml = process.replyVoice(voice);
		return respXml;
	}

	/**
	 * 返回语音消息
	 * 初始化带有HttpServletRequest可以此方法
	 * @param voice		Voice实体
	 * @return			xml格式语音消息字符串
	 */
	public String replyVoice(Voice voice) {
		return request == null ? null : replyVoice(request, voice);
	}

	/**
	 * 返回视频消息
	 * @param request	HttpServletRequest请求
	 * @param video		Video实体
	 * @return			xml格式视频消息字符串
	 */
	public String replyVideo(HttpServletRequest request, Video video) {
		String respXml = "";
		if (this.request != request) {
			process.setRequest(request);
		}
		respXml = process.replyVideo(video);
		return respXml;
	}

	/**
	 * 返回视频消息
	 * 初始化带有HttpServletRequest可以此方法
	 * @param video		Video实体
	 * @return			xml格式视频消息字符串
	 */
	public String replyVideo(Video video) {
		return request == null ? null : replyVideo(request, video);
	}

	/**
	 * 返回音乐消息
	 * @param request		HttpServletRequest请求
	 * @param music			Music实体
	 * @return				xml格式视音乐息字符串
	 */
	public String replyMusic(HttpServletRequest request, Music music) {
		String respXml = "";
		if (this.request != request) {
			process.setRequest(request);
		}
		respXml = process.replyMusic(music);
		return respXml;
	}

	/**
	 * 返回音乐消息
	 * 初始化带有HttpServletRequest可以此方法
	 * @param music		Music实体
	 * @return			xml格式视音乐息字符串
	 */
	public String replyMusic(Music music) {
		return request == null ? null : replyMusic(request, music);
	}

	/**
	 * 返回新闻消息
	 * @param request		HttpServletRequest请求
	 * @param articles		Article集合
	 * @return				xml格式视新闻息字符串
	 */
	public String replyNews(HttpServletRequest request, List<Article> articles) {
		String respXml = "";
		if (this.request != request) {
			process.setRequest(request);
		}
		respXml = process.replyNews(articles);
		return respXml;
	}

	/**
	 * 返回新闻消息
	 * 初始化带有HttpServletRequest可以此方法
	 * @param articles	Article集合
	 * @return			xml格式视新闻息字符串
	 */
	public String replyNews(List<Article> articles) {
		return request == null ? null : replyNews(request, articles);
	}

	private void checkToken() {
		if (token.getToken() == null || token.getToken().length() == 0) {
			try {
				throw new Exception("请初始化Token");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取用户发送的消息类型
	 * @return				返回用户发送的消息类型
	 */
	public String getUserMessageType() {
		return process.getRequestMessageType();
	}

	/**
	 * 获取用户消息
	 * @return		返回用户消息的map
	 */
	public Map<String, String> getUserMessageMap() {
		return process.getMessageMap();
	}

	/**
	 * 增加客服
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @param nickname		客服昵称，最长6个汉字或12个英文字符
	 * @param password		客服账号登录密码
	 */
	public void addKF(String kf_account, String nickname, String password) {
		send.operateKF(kf_account, nickname, password, 0);
	}

	/**
	 * 修改客服帐号
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @param nickname		客服昵称，最长6个汉字或12个英文字符
	 * @param password		客服账号登录密码
	 */
	public void updateKF(String kf_account, String nickname, String password) {
		send.operateKF(kf_account, nickname, password, 1);
	}

	/**
	 * 删除客服帐号
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @param nickname		客服昵称，最长6个汉字或12个英文字符
	 * @param password		客服账号登录密码
	 */
	public void deleteKF(String kf_account, String nickname, String password) {
		send.operateKF(kf_account, nickname, password, 2);
	}

	/**
	 * 设置客服头像
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @param imagePath		图片路劲
	 */
	public void setKFAvatar(String kf_account, String imagePath) {
		send.setKFAvatar(kf_account, imagePath);
	}

	/**
	 * 获取所有客服
	 * @return		客服列表
	 */
	public String getAllKF() {
		return send.getAllKF();
	}

	/**
	 * 客服发送文本消息
	 * @param touser	目标用户的openID
	 * @param content	消息内容
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendText(String touser, String content) {
		return send.kfSendText(touser, content);
	}

	/**
	 * 客服发送图片消息
	 * @param touser		目标用户的openID
	 * @param media_id		发送的图片的媒体ID
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendImage(String touser, String media_id) {
		return send.kfSendImage(touser, media_id);
	}

	/**
	 * 客服发送语音消息
	 * @param touser		目标用户的openID
	 * @param media_id		发送的语言的媒体ID
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendVoice(String touser, String media_id) {
		return send.kfSendVoice(touser, media_id);
	}

	/**
	 * 客服发送视频消息
	 * @param touser			目标用户的openID
	 * @param media_id			发送的视频的媒体ID
	 * @param thumb_media_id	缩略图的媒体ID
	 * @param title				视频消息的标题
	 * @param description		视频消息的描述
	 * @return 					成回TRUE，失败FALSE
	 */
	public boolean kfSendVideo(String touser, String media_id, String thumb_media_id, String title,
			String description) {
		return send.kfSendVideo(touser, media_id, thumb_media_id, title, description);
	}

	/**
	 * 客服发送视频消息
	 * @param touser	目标用户的openID
	 * @param video		客服视频实体
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendVideo(String touser, com.wechat.pojo.msg.send.kf.Video video) {
		return send.kfSendVideo(touser, video);
	}

	/**
	 * 客服发送音乐消息
	 * @param touser	目标用户的openID
	 * @param music		客服Music实体
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendMusic(String touser, com.wechat.pojo.msg.send.kf.Music music) {
		return send.kfSendMusic(touser, music);
	}

	/**
	 * 客服发送音乐消息
	 * @param touser			目标用户的openID
	 * @param title				音乐的名称
	 * @param description		音乐的描述
	 * @param musicurl			音乐的url
	 * @param hqmusicurl		高品质音乐链接，wifi环境优先使用该链接播放音乐
	 * @param thumb_media_id	缩略图的媒体ID
	 * @return 					成回TRUE，失败FALSE
	 */
	public boolean kfSendMusic(String touser, String title, String description, String musicurl, String hqmusicurl,
			String thumb_media_id) {
		return send.kfSendMusic(touser, title, description, musicurl, hqmusicurl, thumb_media_id);
	}

	/**
	 * 客服发送新闻消息
	 * @param touser	目标用户的openID
	 * @param news		客服新闻实体
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendNews(String touser, News news) {
		return send.kfSendNews(touser, news);
	}

	/**
	 * 客服发送新闻消息
	 * @param touser		目标用户的openID
	 * @param articles		Article列表
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendNews(String touser, List<com.wechat.pojo.msg.send.kf.Article> articles) {
		return send.kfSendNews(touser, articles);
	}

	/**
	 * 客服发送站内图文消息
	 * @param touser		目标用户的openID
	 * @param media_id		图文消息ID
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendMPNews(String touser, String media_id) {
		return send.kfSendMPNews(touser, media_id);
	}

	/**
	 * 客服发送卡卷消息
	 * @param touser	目标用户的openID
	 * @param wxcard	客服WXCard实体
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendMPNews(String touser, WXCard wxcard) {
		return send.kfSendCard(touser, wxcard);
	}

	/**
	 * 指定客服发送文本消息
	 * @param touser		目标用户的openID
	 * @param content		消息内容
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendTextBy(String touser, String content, String kf_account) {
		return send.kfSendTextBy(touser, content, kf_account);
	}

	/**
	 * 指定客服发送图片消息
	 * @param touser		目标用户的openID
	 * @param media_id		发送的图片的媒体ID
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendImageBy(String touser, String media_id, String kf_account) {
		return send.kfSendImageBy(touser, media_id, kf_account);
	}

	/**
	 * 指定客服发送语音消息
	 * @param touser		目标用户的openID
	 * @param media_id		发送的语言的媒体ID
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendVoiceBy(String touser, String media_id, String kf_account) {
		return send.kfSendVoiceBy(touser, media_id, kf_account);
	}

	/**
	 * 指定客服发送视频消息
	 * @param touser			目标用户的openID
	 * @param media_id			发送的视频的媒体ID
	 * @param thumb_media_id	缩略图的媒体ID
	 * @param title				视频消息的标题
	 * @param description		视频消息的描述
	 * @param kf_account		完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 					成回TRUE，失败FALSE
	 */
	public boolean kfSendVideoBy(String touser, String media_id, String thumb_media_id, String title,
			String description, String kf_account) {
		return send.kfSendVideoBy(touser, media_id, thumb_media_id, title, description, kf_account);
	}

	/**
	 * 指定客服发送视频消息
	 * @param touser		目标用户的openID
	 * @param video			客服视频实体
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendVideoBy(String touser, com.wechat.pojo.msg.send.kf.Video video, String kf_account) {
		return send.kfSendVideoBy(touser, video, kf_account);
	}

	/**
	 * 指定客服发送音乐消息
	 * @param touser		目标用户的openID
	 * @param music			客服Music实体
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendMusicBy(String touser, com.wechat.pojo.msg.send.kf.Music music, String kf_account) {
		return send.kfSendMusicBy(touser, music, kf_account);
	}

	/**
	 * 指定客服发送音乐消息
	 * @param touser			目标用户的openID
	 * @param title				音乐的名称
	 * @param description		音乐的描述
	 * @param musicurl			音乐的url
	 * @param hqmusicurl		高品质音乐链接，wifi环境优先使用该链接播放音乐
	 * @param thumb_media_id	缩略图的媒体ID
	 * @param kf_account		完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 					成回TRUE，失败FALSE
	 */
	public boolean kfSendMusicBy(String touser, String title, String description, String musicurl, String hqmusicurl,
			String thumb_media_id, String kf_account) {
		return send.kfSendMusicBy(touser, title, description, musicurl, hqmusicurl, thumb_media_id, kf_account);
	}

	/**
	 * 指定客服发送新闻消息
	 * @param touser			目标用户的openID
	 * @param news				客服新闻实体
	 * @param kf_account		完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 					成回TRUE，失败FALSE
	 */
	public boolean kfSendNewsBy(String touser, News news, String kf_account) {
		return send.kfSendNewsBy(touser, news, kf_account);
	}

	/**
	 * 指定客服发送新闻消息
	 * @param touser		目标用户的openID
	 * @param articles		Article列表
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean kfSendNewsBy(String touser, List<com.wechat.pojo.msg.send.kf.Article> articles, String kf_account) {
		return send.kfSendNewsBy(touser, articles, kf_account);
	}

	/**
	 * 指定客服发送站内图文消息
	 * @param touser			目标用户的openID
	 * @param media_id			图文消息ID
	 * @param kf_account		完整客服账号，格式为：账号前缀@公众号微信号
	 * @return					成回TRUE，失败FALSE
	 */
	public boolean kfSendMPNewsBy(String touser, String media_id, String kf_account) {
		return send.kfSendMPNewsBy(touser, media_id, kf_account);
	}

	/**
	 * 指定客服发送卡卷消息
	 * @param touser		目标用户的openID
	 * @param wxcard		客服WXCard实体
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean kfSendMPNews(String touser, WXCard wxcard, String kf_account) {
		return send.kfSendCardBy(touser, wxcard, kf_account);
	}

	/**
	 * 群发站内图文消息
	 * @param is_to_all		用于设定是否向全部用户发送，值为true或false，<br>
	 * &emsp;&emsp;&emsp;&emsp;选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id		群发到的分组的group_id，参加用户管理中用户分组接口，<Br>
	 * &emsp; &emsp;&emsp;&emsp; 若is_to_all值为true，可不填写group_id
	 * @param media_id		用于群发的站内图文消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massMPNews2All(boolean is_to_all, int group_id, String media_id) {
		return send.massMPNews2All(is_to_all, group_id, media_id);
	}

	/**
	 * 群发文本消息
	 * @param is_to_all		用于设定是否向全部用户发送，值为true或false，<br>
	 * &emsp;&emsp;&emsp;&emsp;选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id		群发到的分组的group_id，参加用户管理中用户分组接口，<Br>
	 * &emsp; &emsp;&emsp;&emsp; 若is_to_all值为true，可不填写group_id
	 * @param content		用于群发的消息的内容
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massText2All(boolean is_to_all, int group_id, String content) {
		return send.massText2All(is_to_all, group_id, content);
	}

	/**
	 * 群发语音消息
	 * @param is_to_all		用于设定是否向全部用户发送，值为true或false，<br>
	 * &emsp;&emsp;&emsp;&emsp;选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id		群发到的分组的group_id，参加用户管理中用户分组接口，<Br>
	 * &emsp; &emsp;&emsp;&emsp; 若is_to_all值为true，可不填写group_id
	 * @param media_id		用于群发的语音消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massVoice2All(boolean is_to_all, int group_id, String media_id) {
		return send.massVoice2All(is_to_all, group_id, media_id);
	}

	/**
	 * 群发图片消息
	 * @param is_to_all		用于设定是否向全部用户发送，值为true或false，<br>
	 * &emsp;&emsp;&emsp;&emsp;选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id		群发到的分组的group_id，参加用户管理中用户分组接口，<Br>
	 * &emsp; &emsp;&emsp;&emsp; 若is_to_all值为true，可不填写group_id
	 * @param media_id		用于群发的图片消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massImage2All(boolean is_to_all, int group_id, String media_id) {
		return send.massImage2All(is_to_all, group_id, media_id);
	}

	/**
	 * 群发视频消息
	 * @param is_to_all		用于设定是否向全部用户发送，值为true或false，<br>
	 * &emsp;&emsp;&emsp;&emsp;选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id		群发到的分组的group_id，参加用户管理中用户分组接口，<Br>
	 * &emsp; &emsp;&emsp;&emsp; 若is_to_all值为true，可不填写group_id
	 * @param media_id		用于群发的图片消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massVideo2All(boolean is_to_all, int group_id, String media_id) {
		return send.massVideo2All(is_to_all, group_id, media_id);
	}

	/**
	 * 群发视频消息
	 * @param is_to_all		用于设定是否向全部用户发送，值为true或false，<br>
	 * &emsp;&emsp;&emsp;&emsp;选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id		群发到的分组的group_id，参加用户管理中用户分组接口，<Br>
	 * &emsp; &emsp;&emsp;&emsp; 若is_to_all值为true，可不填写group_id
	 * @param videoPath		视频文件位置
	 * @param title			视频标题
	 * @param description	视频描述
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massVideo2All(boolean is_to_all, int group_id, String videoPath, String title, String description) {
		return send.massVideo2All(is_to_all, group_id, videoPath, title, description);
	}

	/**
	 * 群发微信卡卷消息
	 * @param is_to_all		用于设定是否向全部用户发送，值为true或false，<br>
	 * &emsp;&emsp;&emsp;&emsp;选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id		群发到的分组的group_id，参加用户管理中用户分组接口，<Br>
	 * &emsp; &emsp;&emsp;&emsp; 若is_to_all值为true，可不填写group_id
	 * @param card_id		用于群发的卡卷消息的card_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massCard2All(boolean is_to_all, int group_id, String card_id) {
		return send.massCard2All(is_to_all, group_id, card_id);
	}

	/**
	 * 通过用户列表群发*图文*消息
	 * @param touser		用户列表
	 * @param media_id		图文消息id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massMPNews2List(List<String> touser, String media_id) {
		return send.massMPNews2List(touser, media_id);
	}

	/**
	 * 通过用户列表群发*文本*消息
	 * @param touser		用户列表
	 * @param content		文本内容	
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massText2List(List<String> touser, String content) {
		return send.massText2List(touser, content);
	}

	/**
	 * 通过用户列表群发*语音*消息
	 * @param touser		用户列表
	 * @param media_id		语音id	
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massVoice2List(List<String> touser, String media_id) {
		return send.massVoice2List(touser, media_id);
	}

	/**
	 * 通过用户列表群发*图片*消息
	 * @param touser		用户列表
	 * @param media_id		图片id	
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massImage2List(List<String> touser, String media_id) {
		return send.massImage2List(touser, media_id);
	}

	/**
	 * 通过用户列表群发*视频*消息
	 * @param touser		用户列表
	 * @param videoPath		视频文件位置
	 * @param title			视频的标题
	 * @param description	视频的描述
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massVideo2List(List<String> touser, String videoPath, String title, String description) {
		return send.massVideo2List(touser, videoPath, title, description);
	}

	/**
	 * 通过用户列表群发*卡卷*消息
	 * @param touser		用户列表
	 * @param card_id		卡卷的id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massWXCard2List(List<String> touser, String card_id) {
		return send.massWXCard2List(touser, card_id);
	}

	/**
	 * 删除一条群发消息
	 * @param msg_id	群发消息的内容
	 * @return			成回TRUE，失败FALSE
	 */
	public boolean deleteMassMsg(String msg_id) {
		return send.deleteMassMsg(msg_id);
	}

	/**
	 * 预览群发的*图文*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param media_id		用于群发的*图文*消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassMPNews(String touser, String towxname, String media_id) {
		return send.previewMassMPNews(touser, towxname, media_id);
	}

	/**
	 * 预览群发的*文本*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param content		文本消息内容
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassText(String touser, String towxname, String content) {
		return send.previewMassText(touser, towxname, content);
	}

	/**
	 * 预览群发的*语音*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param media_id		用于群发的*语音*消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassVoice(String touser, String towxname, String media_id) {
		return send.previewMassVoice(touser, towxname, media_id);
	}

	/**
	 * 预览群发的*图片*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param media_id		用于群发的*图片*消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassImage(String touser, String towxname, String media_id) {
		return send.previewMassImage(touser, towxname, media_id);
	}

	/**
	 * 预览群发的*视频*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param media_id		用于群发的*视频*消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassVideo(String touser, String towxname, String media_id) {
		return send.previewMassVideo(touser, towxname, media_id);
	}

	/**
	 * 预览群发的*卡卷*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param wxcard		kf.WXCard实体
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassCard(String touser, String towxname, WXCard wxcard) {
		return send.previewMassCard(touser, towxname, wxcard);
	}

	/**
	 * 设置行业信息
	 * @param industry_id1		公众号模板消息所属行业编号
	 * @param industry_id2		公众号模板消息所属行业编号
	 * @return					成回TRUE，失败FALSE
	 */
	public boolean setIndustry(int industry_id1, int industry_id2) {
		return send.setIndustry(industry_id1, industry_id2);
	}

	/**
	 * 获取行业信息
	 * @return		行业信息的字符串
	 */
	public String getIndustry() {
		return send.getIndustry();
	}

	/**
	 * 获取模板id
	 * @param template_id_short		模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return						成回TRUE，失败FALSE
	 */
	public String getTemplateId(String template_id_short) {
		return send.getTemplateId(template_id_short);
	}

	/**
	 * 获取模板列表
	 * @return	成回TRUE，失败FALSE
	 */
	public TemplateList getTemplateList() {
		return send.getTemplateList();
	}

	/**
	 * 删除模板
	 * @param template_id	模板的id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean deleteTemplate(String template_id) {
		return send.deleteTemplate(template_id);
	}

	/**
	 * 发送模板消息
	 * @param touser		发送对象的id
	 * @param template_id	模板id
	 * @param url			模板点击后转跳的url
	 * @param data			模板数据，key value形式
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean sendTemplateMsg(String touser, String template_id, String url, TemplateData data) {
		return send.sendTemplateMsg(touser, template_id, url, data);
	}

	/**
	 * 上传图片并换行图片的url
	 * @param path		图片本地位置
	 * @return			图片的url
	 */
	public String uploadImage(String path) {
		return send.uploadImage(path);
	}

	/**
	 * 上传视频，并返回视频的Media_id
	 * @param reqUrl		请求地址
	 * @param path			视频的本地位置
	 * @param title			视频的标题
	 * @param description	视频的描述
	 * @return				视频的Media_id
	 */
	public String uploadVideo(String reqUrl, String path, String title, String description) {
		return send.uploadVideo(reqUrl, path, title, description);
	}

	/**
	 * 获取素材列表
	 * @param type		素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset	从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count		返回素材的数量，取值在1到20之间
	 * @return			素材列表字符串
	 */
	public String getMaterialList(String type, int offset, int count) {
		return send.getMaterialList(type, offset, count);
	}

	

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
		process.setRequest(request);
	}

	// FIXME 以下是企业号的方法
	/*** ***** ***** ***** ***** ***** ***** ***** ***** ***/

	/**
	 * 检测企业请求是否来自微信
	 * @param request	HttpServletRequest请求
	 * @return			返回echostr明文
	 */
	public String checkMsgSignature(HttpServletRequest request) {
		return corp.checkSignature(request);
	}

	/**
	 * 企业号发送*文本*信息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param content		消息内容，最长不超过2048个字节，注意：主页型应用推送的文本消息在微信端最多只显示20个字（包含中英文）
	 * @param safe			表示是否是保密消息，0表示否，1表示是，默认0
	 * @return				失败false，成功true
	 */
	public boolean corpSendText(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String content, int safe) {
		return corp.sendText(touser, toparty, totag, agentid, content, safe);
	}

	/**
	 * 企业号发送*图片*消息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id		图片媒体文件id，可以调用上传临时素材或者永久素材接口获取,永久素材media_id必须由发消息的应用创建
	 * @param safe			表示是否是保密消息，0表示否，1表示是，默认0
	 * @return				失败false，成功true
	 */
	public boolean corpSendImage(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, int safe) {
		return corp.sendImage(touser, toparty, totag, agentid, media_id, safe);
	}

	/**
	 * 企业号发送*语音*消息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id		语音文件id，可以调用上传临时素材或者永久素材接口获取
	 * @param safe			表示是否是保密消息，0表示否，1表示是，默认0
	 * @return				失败false，成功true
	 */
	public boolean corpSendVoice(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, int safe) {
		return corp.sendVoice(touser, toparty, totag, agentid, media_id, safe);
	}

	/**
	 * 企业号发送*视频*消息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id		视频媒体文件id，可以调用上传临时素材或者永久素材接口获取
	 * @param title			视频消息的标题，不超过128个字节，超过会自动截断
	 * @param description	视频消息的描述，不超过512个字节，超过会自动截断
	 * @param safe			表示是否是保密消息，0表示否，1表示是，默认0
	 * @return				失败false，成功true
	 */
	public boolean corpSendVideo(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, String title, String description, int safe) {
		return corp.sendVideo(touser, toparty, totag, agentid, media_id, title, description, safe);
	}

	/**
	 * 企业号发送*视频*消息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param video			com.wechat.pojo.msg.send.kf.Video对象（不要给thumb_media_id赋值)
	 * @param safe			表示是否是保密消息，0表示否，1表示是，默认0
	 * @return				失败false，成功true
	 */
	public boolean corpSendVideo(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			com.wechat.pojo.msg.send.kf.Video video, int safe) {
		return corp.sendVideo(touser, toparty, totag, agentid, video, safe);
	}

	/**
	 * 企业号发送*文件*消息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id		媒体文件id，可以调用上传临时素材或者永久素材接口获取
	 * @param safe			表示是否是保密消息，0表示否，1表示是，默认0
	 * @return				失败false，成功true
	 */
	public boolean corpSendFile(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, int safe) {
		return corp.sendFile(touser, toparty, totag, agentid, media_id, safe);
	}

	/**
	 * 企业号发送*新闻（图文）*消息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param articles		com.wechat.pojo.msg.send.kf.Article集合
	 * @return				失败false，成功true
	 */
	public boolean corpSendNews(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			List<com.wechat.pojo.msg.send.kf.Article> articles) {
		return corp.sendNews(touser, toparty, totag, agentid, articles);
	}

	/**
	 * 企业号发送*微信图文*消息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id		素材资源标识ID，通过上传永久图文素材接口获得。注：必须是在该agent下创建的。
	 * @param safe			表示是否是保密消息，0表示否，1表示是，默认0
	 * @return				失败false，成功true
	 */
	public boolean corpSendMPNews(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, int safe) {
		return corp.sendMPNews(touser, toparty, totag, agentid, media_id, safe);
	}

	/**
	 * 企业号发送*微信图文*消息
	 * @param touser		成员ID列表，特殊情况：指定为@all，则向关注该企业应用的全部成员发送（最多支持1000个）
	 * @param toparty		部门ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param totag			标签ID列表，当touser为@all时忽略本参数（最多支持100个）
	 * @param agentid		企业应用的id，整型。可在应用的设置页面查看
	 * @param articles		com.wechat.corp.pojo.msg.send.Article对象
	 * @param safe			表示是否是保密消息，0表示否，1表示是，默认0
	 * @return				失败false，成功true
	 */
	public boolean corpSendMPNews(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			List<com.wechat.corp.pojo.msg.send.Article> articles, int safe) {
		return corp.sendMPNews(touser, toparty, totag, agentid, articles, safe);
	}

	/**
	 * 企业号userid转换成openid
	 * @param userid	用户的userid
	 * @param agentid	用户的本部id
	 * @return			用户的openid
	 */
	public String userid2Openid(String userid, int agentid) {
		return corp.userid2Openid(userid, agentid);
	}

	/**
	 * 企业号openid转换成userid
	 * @param openid	用户的openid
	 * @return			用户的userid
	 */
	public String openid2Userid(String openid) {
		return corp.openid2Userid(openid);
	}

	/**
	 * 获取企业号成员登录授权url
	 * @param corpId		服务商的CorpID或者企业的CorpID
	 * @param redirectId	授权登录之后目的跳转网址，所在域名需要与登录授权域名一致
	 * @param state			用于企业或服务商自行校验session，防止跨域攻击
	 * @param userType		redirect_uri支持登录的类型，有member(成员登录)、admin(管理员登录)、all(成员或管理员皆可登录)，默认值为admin
	 * @return				企业号成员登录授权url
	 */
	public String getCorpLoginPageUrl(String corpId, String redirectId, String state, String userType) {
		return corp.getCorpLoginPageUrl(corpId, redirectId, state, userType);
	}

	/**
	 * 获取获取企业号登录用户信息
	 * @param authCode	oauth2.0授权企业号管理员登录产生的code，只能使用一次，10分钟未被使用自动过期<br>
	 * 授权后回调URI，得到授权码和过期时间 授权流程完成后，会进入回调URI，并在URL参数中返回授权码(redirect_url?auth_code=xxx)
	 * @return			com.wechat.corp.pojo.login.LoginInfo对象
	 */
	public LoginInfo getCorpLoginUserInfo(String authCode) {
		return corp.getCorpLoginUserInfo(authCode);
	}

	/**
	 * 获取登录企业号官网的url
	 * @param loginTicket	通过get_login_info得到的login_ticket, 10小时有效
	 * @param target		登录跳转到企业号后台的目标页面，目前有：agent_setting、send_msg、contact
	 * @param agentid		授权方应用id
	 * @return				登录企业号官网的url
	 */
	public String getCorpLoginUrl(String loginTicket, String target, int agentid) {
		return corp.getCorpLoginUrl(loginTicket, target, agentid);
	}

	/**
	 * 获取企业号应用
	 * @param agentid	企业应用的id
	 * @return			com.wechat.corp.pojo.res.manager.GetAgent对象
	 */
	public GetAgent getAgent(String agentid) {
		return corp.getAgent(agentid);
	}

	/**
	 * 设置企业号应用
	 * @param agentid				企业应用的id
	 * @param reportLocationFlag	企业应用是否打开地理位置上报 0：不上报；1：进入会话上报；2：持续上报
	 * @param logoMediaid			企业应用头像的mediaid，通过多媒体接口上传图片获得mediaid，上传后会自动裁剪成方形和圆形两个头像
	 * @param name					企业应用名称
	 * @param description			企业应用详情
	 * @param redirectDomain		企业应用可信域名
	 * @param isreportuser			是否接收用户变更通知。0：不接收；1：接收
	 * @param isreportenter			是否上报用户进入应用事件。0：不接收；1：接收。
	 * @param home_url				是否上报用户进入应用事件。0：不接收；1：接收。
	 * @param chatExtensionUrl		关联会话url。设置该字段后，企业会话"+"号将出现该应用，点击应用可直接跳转到此url，支持jsapi向当前会话发送消息。
	 * @return						成功true，失败false
	 */
	public boolean setAgent(String agentid, String reportLocationFlag, String logoMediaid, String name,
			String description, String redirectDomain, int isreportuser, int isreportenter, String home_url,
			String chatExtensionUrl) {
		return corp.setAgent(agentid, reportLocationFlag, logoMediaid, name, description, redirectDomain, isreportuser,
				isreportenter, home_url, chatExtensionUrl);
	}

	/**
	 * 企业号获取应用概况列表
	 * @return	com.wechat.corp.pojo.res.manager.AgentInfo集合
	 */
	public List<AgentInfo> getAgentList() {
		return corp.getAgentList();
	}

	/**
	 * 企业成员的二次验证
	 * @param userid	成员UserID
	 * @return			成功true，失败false
	 */
	public boolean twovalidate(String userid) {
		return corp.twovalidate(userid);
	}

	/**
	 * 企业号创建部门
	 * @param name		部门名称。长度限制为32个字（汉字或英文字母），字符不能包括\:*?"&lt;&gt;｜
	 * @param parentid	父亲部门id。根部门id为1
	 * @param order		在父部门中的次序值。order值小的排序靠前。
	 * @param id		部门id，整型。指定时必须大于1，不指定时则填写为数字0
	 * @return			返回创建的部门id
	 */
	public int createDepartment(String name, int parentid, int order, int id) {
		return corp.createDepartment(name, parentid, order, id);
	}

	/**
	 * 企业号更新部门
	 * @param id		部门id
	 * @param name		更新的部门名称。长度限制为32个字（汉字或英文字母），字符不能包括\:*?"&lt;&gt;｜。修改部门名称时指定该参数
	 * @param parentid	父亲部门id。根部门id为1
	 * @param order		在父部门中的次序值。order值小的排序靠前。
	 * @return			成功true，失败false
	 */
	public boolean updateDepartment(int id, String name, int parentid, int order) {
		return corp.updateDepartment(id, name, parentid, order);
	}

	/**
	 * 企业号删除部门
	 * @param id	部门id。（注：不能删除根部门；不能删除含有子部门、成员的部门）
	 * @return		成功true，失败false
	 */
	public boolean deleteDepartment(int id) {
		return deleteDepartment(id);
	}

	/**
	 * 企业号获取部门列表
	 * @param id	部门id。获取指定部门及其下的子部门
	 * @return		List&lt;Department&gt;
	 */
	public List<Department> getDepartList(int id) {
		return corp.getDepartList(id);
	}

	/**
	 * 企业号创建成员
	 * @param userid			必须,成员UserID。对应管理端的帐号，企业内必须唯一。不区分大小写，长度为1~64个字节
	 * @param name				必须,成员名称。长度为1~64个字节
	 * @param department		必须,成员所属部门id列表,不超过20个
	 * @param position			职位信息。长度为0~64个字节
	 * @param mobile			手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
	 * @param gender			性别。1表示男性，2表示女性
	 * @param email				邮箱。长度为0~64个字节。企业内必须唯
	 * @param weixinid			微信号。企业内必须唯一。（注意：是微信号，不是微信的名字）
	 * @param avatar_mediaid	成员头像的mediaid，通过多媒体接口上传图片获得的mediaid
	 * @param attrs				扩展属性。扩展属性需要在WEB管理端创建后才生效，否则忽略未知属性的赋值
	 * @return					成功true，失败false
	 */
	public boolean creteCorpUser(String userid, String name, List<Integer> department, String position, String mobile,
			String gender, String email, String weixinid, String avatar_mediaid, List<NameValue> attrs) {
		return corp.creteUser(userid, name, department, position, mobile, gender, email, weixinid, avatar_mediaid,
				attrs);
	}

	/**
	 * 企业号更新成员
	 * @param userid			必须,成员UserID。对应管理端的帐号，企业内必须唯一。不区分大小写，长度为1~64个字节
	 * @param name				必须,成员名称。长度为1~64个字节
	 * @param department		必须,成员所属部门id列表,不超过20个
	 * @param position			职位信息。长度为0~64个字节
	 * @param mobile			手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
	 * @param gender			性别。1表示男性，2表示女性
	 * @param email				邮箱。长度为0~64个字节。企业内必须唯
	 * @param weixinid			微信号。企业内必须唯一。（注意：是微信号，不是微信的名字）
	 * @param avatar_mediaid	成员头像的mediaid，通过多媒体接口上传图片获得的mediaid
	 * @param attrs				扩展属性。扩展属性需要在WEB管理端创建后才生效，否则忽略未知属性的赋值
	 * @return					成功true，失败false
	 */
	public boolean updateCorpUser(String userid, String name, List<Integer> department, String position, String mobile,
			String gender, String email, String weixinid, String avatar_mediaid, List<NameValue> attrs) {
		return corp.updateUser(userid, name, department, position, mobile, gender, email, weixinid, avatar_mediaid,
				attrs);
	}

	/**
	 * 企业号删除成员
	 * @param userid	成员UserID。对应管理端的帐号
	 * @return			成功true，失败false
	 */
	public boolean deleteCorpUser(String userid) {
		return corp.deleteUser(userid);
	}

	/**
	 * 企业号批量删除成员
	 * @param useridlist	成员UserID列表。对应管理端的帐号。（最多支持200个）
	 * @return				成功true，失败false
	 */
	public boolean batchDeleteCorpUser(List<String> useridlist) {
		return corp.batchDeleteUser(useridlist);
	}

	/**
	 * 企业号获取成员
	 * @param userid	成员UserID。对应管理端的帐号
	 * @return			com.wechat.corp.pojo.res.manager.GetUser对象
	 */
	public GetUser getCorpUser(String userid) {
		return corp.getUser(userid);
	}

	/**
	 * 企业号获取部门成员(简单的成员信息)
	 * @param departmentId	获取的部门id
	 * @param fetchChild	1/0：是否递归获取子部门下面的成员
	 * @param status		0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	 * @return				List&lt;DepartmentUser&gt;对象
	 */
	public List<DepartmentUser> getDepartmentUserSimeple(String departmentId, int fetchChild, int status) {
		return corp.getDepartmentUserSimeple(departmentId, fetchChild, status);
	}

	/**
	 * 企业号获取部门成员(详情)
	 * @param departmentId	获取的部门id
	 * @param fetchChild	1/0：是否递归获取子部门下面的成员
	 * @param status		0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	 * @return				List&lt;com.wechat.corp.pojo.res.manager.UserInfo&gt;对象
	 */
	public List<UserInfo> getDepartmentUser(String departmentId, int fetchChild, int status) {
		return corp.getDepartmentUser(departmentId, fetchChild, status);
	}

	/**
	 * 企业号创建标签
	 * @param tagname	标签名称，长度限制为32个字（汉字或英文字母），标签名不可与其他标签重名。
	 * @param tagid		标签id，整型，指定此参数时新增的标签会生成对应的标签id，不指定时填-1,不指定时则以目前最大的id自增。
	 * @return			标签id，整型
	 */
	public int createTag(String tagname, int tagid) {
		return corp.createTag(tagname, tagid);
	}

	/**
	 * 企业号更新标签名字
	 * @param tagid		标签ID
	 * @param tagname	标签名称，长度限制为32个字（汉字或英文字母），标签不可与其他标签重名。
	 * @return			成功true，失败false
	 */
	public boolean updateTag(int tagid, String tagname) {
		return corp.updateTag(tagid, tagname);
	}

	/**
	 * 企业号删除标签
	 * @param tagid		标签ID
	 * @return			成功true，失败false
	 */
	public boolean deleteTag(int tagid) {
		return corp.deleteTag(tagid);
	}

	/**
	 * 企业号获取标签成员
	 * @param tagid		标签ID
	 * @return			List&lt;TagUserInfoSimple&gt;对象
	 */
	public List<TagUserInfoSimple> getTagUserSimple(int tagid) {
		return corp.getTagUserSimple(tagid);
	}

	/**
	 * 企业号获取标签成员
	 * @param tagid		标签ID
	 * @return			GetTagUserSimple对象
	 */
	public GetTagUserSimple getTagUserSimple(String tagid) {
		return corp.getTagUserSimple(tagid);
	}

	/**
	 * 企业号增加标签成员
	 * @param tagid			标签ID
	 * @param userlist		企业成员ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过1000
	 * @param partylist		企业部门ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过100
	 * @return				成功true，失败抛出异常
	 */
	public boolean addTagUser(int tagid, List<String> userlist, List<Integer> partylist) {
		return corp.addTagUser(tagid, userlist, partylist);
	}

	/**
	 * 企业号删除标签成员
	 * @param tagid			标签ID
	 * @param userlist		企业成员ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过1000
	 * @param partylist		企业部门ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过100
	 * @return				成功true，失败抛出异常
	 */
	public boolean deleteTagUser(int tagid, List<String> userlist, List<Integer> partylist) {
		return corp.deleteTagUser(tagid, userlist, partylist);
	}

	/**
	 * 企业号获取标签列表
	 * @return	List&lt;Tag	&gt;对象
	 */
	public List<Tag> getTags() {
		return corp.getTags();
	}

	/**
	 * 企业号通讯录批量更新-增量更新成员<br>
	 * 如果不需要返回给企业后台，url，token，encodingaeskey都填null
	 * @param media_id 			上传的csv文件的media_id
	 * @param url				企业应用接收企业号推送请求的访问协议和地址，支持http或https协议
	 * @param token				用于生成签名
	 * @param encodingaeskey	用于消息体的加密，是AES密钥的Base64编码
	 * @return					异步任务id【jobid】
	 */
	public String batchCorpSyncUser(String media_id, String url, String token, String encodingaeskey) {
		return corp.batchSyncUser(media_id, url, token, encodingaeskey);
	}

	/**
	 * 企业号通讯录批量更新-全量覆盖成员<br>
	 * 如果不需要返回给企业后台，url，token，encodingaeskey都填null
	 * @param media_id 			上传的csv文件的media_id
	 * @param url				企业应用接收企业号推送请求的访问协议和地址，支持http或https协议
	 * @param token				用于生成签名
	 * @param encodingaeskey	用于消息体的加密，是AES密钥的Base64编码
	 * @return					异步任务id【jobid】
	 */
	public String batchCorpReplaceUser(String media_id, String url, String token, String encodingaeskey) {
		return corp.batchReplaceUser(media_id, url, token, encodingaeskey);
	}

	/**
	 * 企业号通讯录批量更新-全量覆盖部门<br>
	 * 如果不需要返回给企业后台，url，token，encodingaeskey都填null
	 * @param media_id 			上传的csv文件的media_id
	 * @param url				企业应用接收企业号推送请求的访问协议和地址，支持http或https协议
	 * @param token				用于生成签名
	 * @param encodingaeskey	用于消息体的加密，是AES密钥的Base64编码
	 * @return					异步任务id【jobid】
	 */
	public String batchCorpReplaceParty(String media_id, String url, String token, String encodingaeskey) {
		return corp.batchReplaceParty(media_id, url, token, encodingaeskey);
	}

	/**
	 * 企业号获取异步任务结果
	 * @param jobid		异步任务id，最大长度为64字节
	 * @return			GetBatchResult对象
	 */
	public GetBatchResult getCorpBatchResult(String jobid) {
		return corp.getBatchResult(jobid);
	}

	/**
	 * 企业号上传临时素材文件
	 * @param path		文件位置
	 * @param type		媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)
	 * @return			媒体文件的media_id
	 */
	public String uploadCorpTempMedia(String path, String type) {
		return corp.uploadTempMedia(path, type);
	}

	/**
	 * 企业号获取临时素材文件
	 * @param mediaId	媒体文件id。最大长度为256字节
	 * @param filePath 文件保存地址（文件保存地址请填写到文件名称，但不要填写文件格式，文件格式将自动获取！）
	 * @return			成功true，失败false
	 */
	public String getCorpTempMedia(String mediaId, String filePath) {
		return corp.getTempMedia(mediaId, filePath);
	}

	/**
	 * 企业号上传永久图文素材
	 * @param articles	图文消息，一个图文消息支持1到10个图文
	 * @return			素材资源标识ID。最大长度为256字节
	 */
	public String uploadCorpMPNews(List<com.wechat.corp.pojo.msg.send.Article> articles) {
		return corp.uploadMPNews(articles);
	}

	/**
	 * 企业号上传永久素材
	 * @param path		素材文件位置
	 * @param type		媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)
	 * @return			素材资源标识ID。最大长度为256字节
	 */
	public String uploadCorpMaterial(String path, String type) {
		return corp.uploadMaterial(path, type);
	}

	/**
	 * 企业号获取永久素材
	 * @param mediaId	素材资源标识ID
	 * @return			成功true，失败false
	 */
	public boolean getCorpMaterial(String mediaId) {
		return corp.getMaterial(mediaId);
	}

	/**
	 * 企业号图文消息素材
	 * @param mediaId	素材资源标识ID
	 * @return			com.wechat.corp.pojo.msg.send.Article对象
	 */
	public List<com.wechat.corp.pojo.msg.send.Article> getCorpMPNews(String mediaId) {
		return corp.getMPNews(mediaId);
	}

	/**
	 * 企业号删除永久素材
	 * @param mediaId	素材资源标识ID
	 * @return			成功true，失败false
	 */
	public boolean deleteCorpMaterial(String mediaId) {
		return corp.deleteMaterial(mediaId);
	}

	/**
	 * 企业号修改永久图文素材
	 * @param mediaId		素材资源标识ID。最大长度为256字节
	 * @param articles		图文消息，一个图文消息支持1到10个图文
	 * @return				成功true，失败false
	 */
	public boolean updateCorpMPNews(String mediaId, List<com.wechat.corp.pojo.msg.send.Article> articles) {
		return corp.updateMPNews(mediaId, articles);
	}

	/**
	 * 企业号获取素材总数
	 * @return	一个map对象，其中total：应用素材总数目，image：图片素材总数目，voice：音频素材总数目，video：视频素材总数目，file：文件素材总数目，mpnews：图文素材总数目
	 */
	public Map<String, Integer> getCorpMaterialCount() {
		return corp.getMaterialCount();
	}

	/**
	 * 获取获取素材列表
	 * @param type		素材类型，可以为图片（image）、音频（voice）、视频（video）、文件（file）
	 * @param offset	从该类型素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count		从该类型素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @return			List&lt;MaterialItem&gt;对象，MaterialItem中有media_id，filename，update_time
	 */
	public List<MaterialItem> getCorpMaterialList(String type, int offset, int count) {
		return corp.getMaterialList(type, offset, count);
	}

	/**
	 * 企业号获取图文(mpnews)素材列表
	 * @param offset	从该类型素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count		返回素材的数量，取值在1到50之间
	 * @return			List&lt;MPNewsBack&gt;对象
	 */
	public List<MPNewsBack> getCorpMPNewsList(int offset, int count) {
		return corp.getMPNewsList(offset, count);
	}

	/**
	 * 企业号上传图文消息内的图片
	 * @param path	素材文件位置
	 * @return		素材文件url地址
	 */
	public String uploadCorpMPNewsImage(String path) {
		return corp.uploadMPNewsImage(path);
	}

	// FIXME 以下是web网页方面的方法
	/*** ***** ***** ***** ***** ***** ***** ***** ***** ***/

	/**
	 * 获取WebAccessToken
	 * @param request		HttpServletRequest请求
	 * @return				WebAccessToken对象
	 * @throws Exception	失败抛出异常
	 */
	public WebAccessToken getWebAccessToken(HttpServletRequest request) throws Exception {
		return webProcess.getWebAccessToken(request);
	}

	/**
	 * 获取web授权的用户信息
	 * @param accessToken	webAccessToken
	 * @param openId		用户的openID
	 * @return				WebUserInfo对象
	 * @throws Exception	失败抛出异常
	 */
	public WebUserInfo getWebUserInfo(String accessToken, String openId) throws Exception {
		return webProcess.getWebUserInfo(accessToken, openId);
	}
	
	/**
	 * 获取web授权的用户信息
	 * @param code	获取的微信code
	 * @return		WebUserInfo对象
	 */
	public WebUserInfo getWebUserInfo(String code) {
		return webProcess.getWebUserInfo(code);
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
	public void redirect2JSSdk(HttpServletRequest request, HttpServletResponse response, String redirectUrl,
			String targetUrl, String ticket) throws Exception {
		webProcess.redirect2JSSdk(request, response, redirectUrl, targetUrl, ticket);
	}

	/**
	 * 获取网页授权url
	 * @param redirectUri	重定向网页（建议填处理改业务的servlet地址）请使用urlencode对链接进行处理
	 * @param scope			应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @param state			重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @return				OAUTH2的url字符串
	 */
	public String getOauth2Url(String redirectUri, String scope, String state) {
		return webProcess.getOauth2Url(initData.getId(), redirectUri, scope, state);
	}

	/**
	 * 企业获取web用户信息
	 * @param accessToken	调用接口凭证
	 * @param code			通过成员授权获取到的code，每次成员授权带上的code将不一样，code只能使用一次，10分钟未被使用自动过期
	 * @return				CorpWebUserInfo对象
	 */
	public CorpWebUserTicket getCorpWebUserInfo(String accessToken, String code) {
		return webProcess.getCorpWebUserInfo(accessToken, code);
	}
	
	/**
	 * 获取使用微信JSSDK的初始化差数
	 * @param url	通过请求地址加上code和state的url	
	 * @return	map对象
	 */
	public Map<String, String> getJSSDKConfig(String url) {
		return webProcess.getJSSDKConfig(url);
	}
	
	/**
	 * 获取使用微信JSSDK的初始化差数
	 * @param url		通过请求地址加上code和state的url	
	 * @param code		用户code
	 * @return			Signature对象
	 */
	public Signature getJSSDKConfig(String url, String code) {
		return webProcess.getJSSDKConfig(url, code);
	}

	/**
	 * 获取企业号web页面snsapi_nase信息
	 * @param code
	 * @return CorpWebUserTicket对象
	 */
	public CorpWebUserTicket getCorpSnsapiBase(String code) {
		return webProcess.getCorpSnsapiBase(code);
	}
	
	/**
	 * 获取企业号web页面snsapi_info信息
	 * @param code
	 * @return CorpWebUserInfo对象
	 */
	public CorpWebUserInfo getCorpSnsapiInfo(String code) {
		return webProcess.getCorpSnsapiInfo(code);
	}
	
	/**
	 * 获取使用微信JSSDK的初始化参数
	 * @param url		通过请求地址加上code和state的url	
	 * @param code		用户code
	 * @return			Signature对象
	 */
	public Signature getJSSDKConfigOutOpenId(String url) {
		return webProcess.getJSSDKConfigOutOpenId(url);
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
        return webProcess.getEternalQrcode(sceneStr);
	}
	
	/**
	 * 生成并下载带临时的参数二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneId		场景值ID，临时二维码时为32位非0整型
	 * @param filePath		文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统根据文件本来的根式自动补上
	 */
	public String getAndDownTempQrcode(int expireSeconds, int sceneId, String filePath) {
		return webProcess.getAndDownTempQrcode(expireSeconds, sceneId, filePath);
	}
	
	/**
	 * 生成并下载带临时的参数二维码
	 * @param expireSeconds	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）
	 * @param sceneStr		场景值ID（字符串形式的ID），字符串类型
	 * @param filePath		文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统根据文件本来的根式自动补上
	 */
	public String getAndDownTempQrcode(int expireSeconds, String sceneStr, String filePath) {
		return webProcess.getAndDownTempQrcode(expireSeconds, sceneStr, filePath);
	}
	
	/**
	 * 生成并下载带永久的参数二维码
	 * @param sceneId	场景值ID，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @param filePath	文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统根据文件本来的根式自动补上
	 */
	public String getAndDownEternalQrcode(int sceneId, String filePath) {
		return webProcess.getAndDownEternalQrcode(sceneId, filePath);
	}
	
	/**
	 * 生成并下载带永久的参数二维码
	 * @param sceneStr	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
	 * @param filePath	文件目录地址，请填写到文件名称，文件类型系统会自动带上
	 * @return 文件地址，filePath为“c:\cishi”将返回“c:\cishi.[jpg/png/icon/gif/bmp/...]”系统根据文件本来的根式自动补上
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
