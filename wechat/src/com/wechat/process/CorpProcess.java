package com.wechat.process;

import static com.wechat.utils.C.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wechat.corp.pojo.auth.login.AuthCode;
import com.wechat.corp.pojo.auth.login.GetLoginUrlBack;
import com.wechat.corp.pojo.auth.login.GetLoginUrlPost;
import com.wechat.corp.pojo.auth.login.LoginInfo;
import com.wechat.corp.pojo.convert.Openid2UseridBack;
import com.wechat.corp.pojo.convert.Openid2UseridPost;
import com.wechat.corp.pojo.convert.Userid2OpenidBack;
import com.wechat.corp.pojo.convert.Userid2OpenidPost;
import com.wechat.corp.pojo.msg.send.CorpFile;
import com.wechat.corp.pojo.msg.send.CorpImage;
import com.wechat.corp.pojo.msg.send.CorpMPNews;
import com.wechat.corp.pojo.msg.send.CorpMPNewsById;
import com.wechat.corp.pojo.msg.send.CorpMsg;
import com.wechat.corp.pojo.msg.send.CorpNews;
import com.wechat.corp.pojo.msg.send.CorpSendMsgBack;
import com.wechat.corp.pojo.msg.send.CorpText;
import com.wechat.corp.pojo.msg.send.CorpVideo;
import com.wechat.corp.pojo.msg.send.CorpVoice;
import com.wechat.corp.pojo.msg.send.MPNews;
import com.wechat.corp.pojo.res.manager.AddTagUserBack;
import com.wechat.corp.pojo.res.manager.AddTagUserPost;
import com.wechat.corp.pojo.res.manager.AgentInfo;
import com.wechat.corp.pojo.res.manager.AgentList;
import com.wechat.corp.pojo.res.manager.BatchDeleteUser;
import com.wechat.corp.pojo.res.manager.BatchSyncBack;
import com.wechat.corp.pojo.res.manager.BatchSyncCallback;
import com.wechat.corp.pojo.res.manager.BatchSyncUserPost;
import com.wechat.corp.pojo.res.manager.CreateDepartmentBack;
import com.wechat.corp.pojo.res.manager.CreateTagBack;
import com.wechat.corp.pojo.res.manager.CreateUser;
import com.wechat.corp.pojo.res.manager.Department;
import com.wechat.corp.pojo.res.manager.DepartmentList;
import com.wechat.corp.pojo.res.manager.DepartmentUser;
import com.wechat.corp.pojo.res.manager.ExtAttr;
import com.wechat.corp.pojo.res.manager.GetAgent;
import com.wechat.corp.pojo.res.manager.GetBatchResult;
import com.wechat.corp.pojo.res.manager.GetDepartmentUser;
import com.wechat.corp.pojo.res.manager.GetDepartmentUserAll;
import com.wechat.corp.pojo.res.manager.GetTagListBack;
import com.wechat.corp.pojo.res.manager.GetTagUserSimple;
import com.wechat.corp.pojo.res.manager.GetTagUserSimpleBack;
import com.wechat.corp.pojo.res.manager.GetUser;
import com.wechat.corp.pojo.res.manager.NameValue;
import com.wechat.corp.pojo.res.manager.SetAgent;
import com.wechat.corp.pojo.res.manager.Tag;
import com.wechat.corp.pojo.res.manager.TagUserInfoSimple;
import com.wechat.corp.pojo.res.manager.UserInfo;
import com.wechat.corp.pojo.upload.GetMaterialListBack;
import com.wechat.corp.pojo.upload.GetMaterialListPost;
import com.wechat.corp.pojo.upload.MPNewsBack;
import com.wechat.corp.pojo.upload.GetMPNewsBack;
import com.wechat.corp.pojo.upload.GetMPNewsListBack;
import com.wechat.corp.pojo.upload.MPNewsPost;
import com.wechat.corp.pojo.upload.MaterialCount;
import com.wechat.corp.pojo.upload.MaterialItem;
import com.wechat.corp.pojo.upload.UpdateMPNewsPost;
import com.wechat.corp.pojo.upload.UploadTempMediaBack;
import com.wechat.pojo.menu.MenuBack;
import com.wechat.pojo.msg.send.kf.Article;
import com.wechat.pojo.msg.send.kf.News;
import com.wechat.pojo.msg.send.kf.Text;
import com.wechat.pojo.msg.send.kf.Video;
import com.wechat.pojo.msg.send.mass.Media;
import com.wechat.pojo.token.AccessToken;
import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.JSApiTicket;
import com.wechat.pojo.token.Token;
import com.wechat.pojo.token.WechatData;
import com.wechat.utils.CommonUtil;
import com.wechat.utils.DownFile;
import com.wechat.utils.WechatAESUtil;

/**
 * 类名：CorpProcess.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月6日 下午4:45:33 <br>
 * 发布版本：V <br>
 */
public class CorpProcess {

	private WechatAESUtil aesUtil;
	private Gson gson;
	private Token token;
	private InitData initData;
	private AccessToken accessToken;
	private JSApiTicket jsApiTicket;

	public CorpProcess(WechatData data, AccessToken accessToken, JSApiTicket jsApiTicket) {
		token = data.getToken();
		initData = data.getInitData();
		if (data.getToken().isEncrypt()) 
			aesUtil = new WechatAESUtil(this.token, initData.getId());
		gson = new GsonBuilder().disableHtmlEscaping().create();
		this.accessToken = accessToken;
		this.jsApiTicket = jsApiTicket;
	}

	/**
	 * 检测企业请求是否来自微信
	 * @param request	HttpServletRequest请求
	 * @return			返回echostr明文
	 */
	public String checkSignature(HttpServletRequest request) {
		// 微信加密签名
		String signature = request.getParameter("msg_signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		return aesUtil.verifyURL(signature, timestamp, nonce, echostr);
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
	public boolean sendText(List<String> touser, List<String> toparty, List<String> totag, int agentid, String content,
			int safe) {
		CorpText corpText = (CorpText) getCorpMsg(new CorpText(), touser, toparty, totag, "text", agentid, safe);
		corpText.setText(getText(content));
		String text = gson.toJson(corpText);
//		System.out.println("企业号发送文本消息的消息体：" + text);
		return sendMsg(text);
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
	public boolean sendImage(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, int safe) {
		CorpImage image = (CorpImage) getCorpMsg(new CorpImage(), touser, toparty, totag, "image", agentid, safe);
		image.setImage(getMedia(media_id));
		return sendMsg(gson.toJson(image));
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
	public boolean sendVoice(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, int safe) {
		CorpVoice voice = (CorpVoice) getCorpMsg(new CorpVoice(), touser, toparty, totag, "voice", agentid, safe);
		voice.setVoice(getMedia(media_id));
		return sendMsg(gson.toJson(voice));
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
	public boolean sendVideo(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, String title, String description, int safe) {
		return sendVideo(touser, toparty, totag, agentid, getVideo(media_id, title, description), safe);
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
	public boolean sendVideo(List<String> touser, List<String> toparty, List<String> totag, int agentid, Video video,
			int safe) {
		CorpVideo corpVideo = (CorpVideo) getCorpMsg(new CorpVideo(), touser, toparty, totag, "video", agentid, safe);
		corpVideo.setVideo(video);
		return sendMsg(gson.toJson(corpVideo));
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
	public boolean sendFile(List<String> touser, List<String> toparty, List<String> totag, int agentid, String media_id,
			int safe) {
		CorpFile file = (CorpFile) getCorpMsg(new CorpFile(), touser, toparty, totag, "file", agentid, safe);
		file.setFile(getMedia(media_id));
		return sendMsg(gson.toJson(file));
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
	public boolean sendNews(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			List<Article> articles) {
		CorpNews news = (CorpNews) getCorpMsg(new CorpNews(), touser, toparty, totag, "news", agentid, -1);
		news.setNews(getNews(articles));
		return sendMsg(gson.toJson(news));
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
	public boolean sendMPNews(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			String media_id, int safe) {
		CorpMPNewsById news = (CorpMPNewsById) getCorpMsg(new CorpMPNewsById(), touser, toparty, totag, "mpnews", agentid, safe);
		news.setMpnews(getMedia(media_id));
		return sendMsg(gson.toJson(news));
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
	public boolean sendMPNews(List<String> touser, List<String> toparty, List<String> totag, int agentid,
			List<com.wechat.corp.pojo.msg.send.Article> articles, int safe) {
		CorpMPNews news = (CorpMPNews) getCorpMsg(new CorpMPNews(), touser, toparty, totag, "mpnews", agentid, safe);
		news.setMpnews(getMPNews(articles));
		return sendMsg(gson.toJson(news));
	}

	/**
	 * 企业号userid转换成openid
	 * @param userid	用户的userid
	 * @param agentid	用户的本部id
	 * @return			用户的openid
	 */
	public String userid2Openid(String userid, int agentid) {
		String url = CORP_USERID_2_OPENID.replace("ACCESS_TOKEN", getToken());
		String jsonStr = request(url, POST, gson.toJson(getUserid2OpenidPost(userid, agentid)));
		Userid2OpenidBack back = gson.fromJson(jsonStr, Userid2OpenidBack.class);
		return back.getOpenid();
	}

	/**
	 * 企业号openid转换成userid
	 * @param openid	用户的openid
	 * @return			用户的userid
	 */
	public String openid2Userid(String openid) {
		String url = CORP_OPENID_2_USERID.replace("ACCESS_TOKEN", getToken());
		String jsonStr = request(url, POST, gson.toJson(getOpenid2UseridPost(openid)));
		Openid2UseridBack back = gson.fromJson(jsonStr, Openid2UseridBack.class);
		return back.getUserid();
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
		String url = "  https://qy.weixin.qq.com/cgi-bin/loginpage?corp_id=" + corpId + "&redirect_uri=" + redirectId
				+ "&state=" + state + "&usertype=" + userType;
		return url;
	}

	/**
	 * 获取获取企业号登录用户信息
	 * @param authCode	oauth2.0授权企业号管理员登录产生的code，只能使用一次，10分钟未被使用自动过期<br>
	 * 授权后回调URI，得到授权码和过期时间 授权流程完成后，会进入回调URI，并在URL参数中返回授权码(redirect_url?auth_code=xxx)
	 * @return			com.wechat.corp.pojo.login.LoginInfo对象
	 */
	public LoginInfo getCorpLoginUserInfo(String authCode) {
		String requestUrl = CORP_GET_LOGIN_INFO.replace("ACCESS_TOKEN", getToken());
		String jsonStr = request(requestUrl, POST, gson.toJson(getAuthCode(authCode)));
		LoginInfo info = gson.fromJson(jsonStr, LoginInfo.class);
		return info;
	}

	/**
	 * 获取登录企业号官网的url
	 * @param loginTicket	通过get_login_info得到的login_ticket, 10小时有效
	 * @param target		登录跳转到企业号后台的目标页面，目前有：agent_setting、send_msg、contact
	 * @param agentid		授权方应用id
	 * @return				登录企业号官网的url
	 */
	public String getCorpLoginUrl(String loginTicket, String target, int agentid) {
		String url = CORP_GET_LOGIN_URL.replace("ACCESS_TOKEN", getToken());
		String jsonStr = request(url, POST, gson.toJson(getGetLoginUrlPost(loginTicket, target, agentid)));
		GetLoginUrlBack back = new GetLoginUrlBack();
		back = gson.fromJson(jsonStr, GetLoginUrlBack.class);
		return back.getLogin_url();
	}

	/**
	 * 获取企业号应用
	 * @param agentid	企业应用的id
	 * @return			com.wechat.corp.pojo.res.manager.GetAgent对象
	 */
	public GetAgent getAgent(String agentid) {
		String url = CORP_GET_AGENTID.replace("AGENTID", agentid).replace("ACCESS_TOKEN", getToken());
		String jsonStr = request(url, GET, null);
		GetAgent agent = gson.fromJson(jsonStr, GetAgent.class);
		return agent;
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
		String url = CORP_SET_AGENTID.replace("ACCESS_TOKEN", getToken());
		String jsonStr = request(url, POST, gson.toJson(getSetAgent(agentid, reportLocationFlag, logoMediaid, name,
				description, redirectDomain, isreportuser, isreportenter, home_url, chatExtensionUrl)));
		CharSequence errcode = "errcode\":\"0";
		if (jsonStr.contains(errcode))
			return true;
		return false;
	}

	/**
	 * 企业号获取应用概况列表
	 * @return	com.wechat.corp.pojo.res.manager.AgentInfo集合
	 */
	public List<AgentInfo> getAgentList() {
		String url = CORP_GET_AGENTID_LIST.replace("ACCESS_TOKEN", getToken());
		String jsonStr = request(url, GET, null);
		AgentList list = gson.fromJson(jsonStr, AgentList.class);
		return list.getAgentlist();
	}

	/**
	 * 企业成员的二次验证
	 * @param userid	成员UserID
	 * @return			成功true，失败false
	 */
	public boolean twovalidate(String userid) {
		String url = CORP_TWO_VAL.replace("USERID", userid).replace("ACCESS_TOKEN", getToken());
		String jsonStr = request(url, GET, null);
		CharSequence errcode = "errcode\":\"0";
		if (jsonStr.contains(errcode))
			return true;
		return false;
	}

	/**
	 * 企业号创建部门
	 * @param name		部门名称。长度限制为32个字（汉字或英文字母），字符不能包括\:*?"&lt;&gt;｜
	 * @param parentid	父亲部门id。根部门id为1
	 * @param order		在父部门中的次序值。order值小的排序靠前。 不指定时则填写为数字0
	 * @param id		部门id，整型。指定时必须大于1，不指定时则填写为数字0
	 * @return			返回创建的部门id
	 */
	public int createDepartment(String name, int parentid, int order, int id) {
		String url = CORP_CREATE_DEPARTMENT.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getDepartment(name, parentid, order, id)));
		CreateDepartmentBack back = gson.fromJson(json, CreateDepartmentBack.class);
		return back.getId();
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
		String url = CORP_UPDATE_DEPARTMENT.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getDepartment(name, parentid, order, parentid)));
		CreateDepartmentBack back = gson.fromJson(json, CreateDepartmentBack.class);
		if (back.getId() == 0)
			return false;
		return true;
	}

	/**
	 * 企业号删除部门
	 * @param id	部门id。（注：不能删除根部门；不能删除含有子部门、成员的部门）
	 * @return		成功true，失败false
	 */
	public boolean deleteDepartment(int id) {
		String url = CORP_DELETE_DEPARTMENT.replace("=ID", "=" + id).replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		MenuBack back = gson.fromJson(json, MenuBack.class);
		if (back.getErrcode() != 0)
			return false;
		return true;
	}

	/**
	 * 企业号获取部门列表
	 * @param id	部门id。获取指定部门及其下的子部门
	 * @return		List&lt;Department&gt;
	 */
	public List<Department> getDepartList(int id) {
		String url = CORP_GET_DEPARTMENT_LIST.replace("=ID", "=" + id).replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		DepartmentList list = gson.fromJson(json, DepartmentList.class);
		if (list.getErrcode() != 0) {
			try {
				throw new Exception("获取部门列表失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list.getDepartment();
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
	public boolean creteUser(String userid, String name, List<Integer> department, String position, String mobile,
			String gender, String email, String weixinid, String avatar_mediaid, List<NameValue> attrs) {
		String url = CORP_CREATE_USER.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getCreateUser(userid, name, department, position, mobile, gender,
				email, weixinid, avatar_mediaid, attrs)));
		MenuBack back = gson.fromJson(json, MenuBack.class);
		if (back.getErrcode() != 0)
			return false;
		return true;
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
	public boolean updateUser(String userid, String name, List<Integer> department, String position, String mobile,
			String gender, String email, String weixinid, String avatar_mediaid, List<NameValue> attrs) {
		String url = CORP_UPDATE_USER.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getCreateUser(userid, name, department, position, mobile, gender,
				email, weixinid, avatar_mediaid, attrs)));
		MenuBack back = gson.fromJson(json, MenuBack.class);
		if (back.getErrcode() != 0)
			return false;
		return true;
	}

	/**
	 * 企业号删除成员
	 * @param userid	成员UserID。对应管理端的帐号
	 * @return			成功true，失败false
	 */
	public boolean deleteUser(String userid) {
		String url = CORP_DELETE_USER.replace("USERID", userid).replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		MenuBack back = gson.fromJson(json, MenuBack.class);
		if (back.getErrcode() != 0)
			return false;
		return true;
	}

	/**
	 * 企业号批量删除成员
	 * @param useridlist	成员UserID列表。对应管理端的帐号。（最多支持200个）
	 * @return				成功true，失败false
	 */
	public boolean batchDeleteUser(List<String> useridlist) {
		String url = CORP_BATCH_DELETE_USER.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getBatchDeleteUser(useridlist)));
		MenuBack back = gson.fromJson(json, MenuBack.class);
		if (back.getErrcode() != 0)
			return false;
		return true;
	}

	/**
	 * 企业号获取成员
	 * @param userid	成员UserID。对应管理端的帐号
	 * @return			com.wechat.corp.pojo.res.manager.GetUser对象
	 */
	public GetUser getUser(String userid) {
		String url = CORP_GET_USER.replace("USERID", userid).replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		GetUser user = gson.fromJson(json, GetUser.class);
		return user;
	}

	/**
	 * 企业号获取部门成员(简单的成员信息)
	 * @param departmentId	获取的部门id
	 * @param fetchChild	1/0：是否递归获取子部门下面的成员
	 * @param status		0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	 * @return				List&lt;DepartmentUser&gt;对象
	 */
	public List<DepartmentUser> getDepartmentUserSimeple(String departmentId, int fetchChild, int status) {
		String url = CORP_GET_DEPARTMENT_USER_SIMPLE.replace("STATUS", "" + status)
				.replace("FETCH_CHILD", "" + fetchChild).replace("DEPARTMENT_ID", departmentId)
				.replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		GetDepartmentUser user = gson.fromJson(json, GetDepartmentUser.class);
		if (user.getErrcode() != 0)
			throwException("获取部门成员失败");
		return user.getUserlist();
	}

	/**
	 * 企业号获取部门成员(详情)
	 * @param departmentId	获取的部门id
	 * @param fetchChild	1/0：是否递归获取子部门下面的成员
	 * @param status		0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4
	 * @return				List&lt;com.wechat.corp.pojo.res.manager.UserInfo&gt;对象
	 */
	public List<UserInfo> getDepartmentUser(String departmentId, int fetchChild, int status) {
		String url = CORP_GET_DEPARTMENT_USER.replace("STATUS", "" + status).replace("FETCH_CHILD", "" + fetchChild)
				.replace("DEPARTMENT_ID", departmentId)
				.replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		GetDepartmentUserAll user = gson.fromJson(json, GetDepartmentUserAll.class);
		if (user.getErrcode() != 0)
			throwException("获取部门成员失败(详情)");
		return user.getUserlist();
	}

	/**
	 * 企业号创建标签
	 * @param tagname	标签名称，长度限制为32个字（汉字或英文字母），标签名不可与其他标签重名。
	 * @param tagid		标签id，整型，指定此参数时新增的标签会生成对应的标签id，不指定时填-1,不指定时则以目前最大的id自增。
	 * @return			标签id，整型
	 */
	public int createTag(String tagname, int tagid) {
		String url = CORP_CREATE_TAG.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getCreateTagPost(tagname, tagid)));
		CreateTagBack back = gson.fromJson(json, CreateTagBack.class);
		if (back.getErrcode() != 0)
			throwException("创建标签失败");
		return back.getTagid();
	}

	/**
	 * 企业号更新标签名字
	 * @param tagid		标签ID
	 * @param tagname	标签名称，长度限制为32个字（汉字或英文字母），标签不可与其他标签重名。
	 * @return			成功true，失败false
	 */
	public boolean updateTag(int tagid, String tagname) {
		String url = CORP_UPDATE_TAG.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getCreateTagPost(tagname, tagid)));
		MenuBack back = gson.fromJson(json, MenuBack.class);
		if (back.getErrcode() != 0)
			return false;
		return true;
	}

	/**
	 * 企业号删除标签
	 * @param tagid		标签ID
	 * @return			成功true，失败false
	 */
	public boolean deleteTag(int tagid) {
		String url = CORP_DELETE_TAG.replace("TAGID", tagid + "").replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		MenuBack back = gson.fromJson(json, MenuBack.class);
		if (back.getErrcode() != 0)
			return false;
		return true;
	}

	/**
	 * 企业号获取标签成员
	 * @param tagid		标签ID
	 * @return			List&lt;TagUserInfoSimple&gt;对象
	 */
	public List<TagUserInfoSimple> getTagUserSimple(int tagid) {
		String url = CORP_GET_TAG_USER.replace("ACCESS_TOKEN", getToken()).replace("TAGID", tagid + "");
		String json = request(url, GET, null);
		GetTagUserSimpleBack back = gson.fromJson(json, GetTagUserSimpleBack.class);
		if (back.getErrcode() != 0)
			throwException("获取标签成员失败");
		return back.getUserlist();
	}

	/**
	 * 企业号获取标签成员
	 * @param tagid		标签ID
	 * @return			GetTagUserSimple对象
	 */
	public GetTagUserSimple getTagUserSimple(String tagid) {
		String url = CORP_GET_TAG_USER.replace("ACCESS_TOKEN", getToken()).replace("TAGID", tagid);
		String json = request(url, GET, null);
		GetTagUserSimpleBack back = gson.fromJson(json, GetTagUserSimpleBack.class);
		if (back.getErrcode() != 0)
			throwException("获取标签成员失败");
		GetTagUserSimple user = new GetTagUserSimple();
		user.setUserlist(back.getUserlist());
		user.setPartylist(back.getPartylist());
		return user;
	}

	/**
	 * 企业号增加标签成员
	 * @param tagid			标签ID
	 * @param userlist		企业成员ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过1000
	 * @param partylist		企业部门ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过100
	 * @return				成功true，失败抛出异常
	 */
	public boolean addTagUser(int tagid, List<String> userlist, List<Integer> partylist) {
		String url = CORP_ADD_TAG_USER.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getAddTagUserPost(tagid, userlist, partylist)));
		AddTagUserBack back = gson.fromJson(json, AddTagUserBack.class);
		if (!back.getErrmsg().equals("ok"))
			throwException("增加标签成员失败：\n" + back.getErrcode() + "\n" + back.getErrmsg() + "\n" + back.getInvalidlist()
					+ "\n" + back.getInvalidparty());
		return true;
	}

	/**
	 * 企业号删除标签成员
	 * @param tagid			标签ID
	 * @param userlist		企业成员ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过1000
	 * @param partylist		企业部门ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过100
	 * @return				成功true，失败抛出异常
	 */
	public boolean deleteTagUser(int tagid, List<String> userlist, List<Integer> partylist) {
		String url = CORP_DELETE_TAG_USER.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getAddTagUserPost(tagid, userlist, partylist)));
		AddTagUserBack back = gson.fromJson(json, AddTagUserBack.class);
		if (!back.getErrmsg().equals("ok"))
			throwException("增加标签成员失败：\n" + back.getErrcode() + "\n" + back.getErrmsg() + "\n" + back.getInvalidlist()
					+ "\n" + back.getInvalidparty());
		return true;
	}

	/**
	 * 企业号获取标签列表
	 * @return	List&lt;Tag	&gt;对象
	 */
	public List<Tag> getTags() {
		String url = CORP_GET_TAG_LIST.replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		GetTagListBack back = gson.fromJson(json, GetTagListBack.class);
		if (back.getErrcode() != 0)
			throwException("获取标签列表失败");
		return back.getTaglist();
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
	public String batchSyncUser(String media_id, String url, String token, String encodingaeskey) {
		String urlPost = CORP_BATCH_SYNC_USER.replace("ACCESS_TOKEN", getToken());
		return batchSync(urlPost, media_id, urlPost, token, encodingaeskey);
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
	public String batchReplaceUser(String media_id, String url, String token, String encodingaeskey) {
		String urlPost = CORP_BATCH_REPLACE_USER.replace("ACCESS_TOKEN", getToken());
		return batchSync(urlPost, media_id, urlPost, token, encodingaeskey);
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
	public String batchReplaceParty(String media_id, String url, String token, String encodingaeskey) {
		String urlPost = CORP_BATCH_REPLACE_PARTY.replace("ACCESS_TOKEN", getToken());
		return batchSync(urlPost, media_id, urlPost, token, encodingaeskey);
	}

	/**
	 * 企业号获取异步任务结果
	 * @param jobid		异步任务id，最大长度为64字节
	 * @return			GetBatchResult对象
	 */
	public GetBatchResult getBatchResult(String jobid) {
		String url = CORP_BATCH_GET_RESULT.replace("ACCESS_TOKEN", getToken()).replace("JOBID", jobid);
		String json = request(url, GET, null);
		GetBatchResult result = gson.fromJson(json, GetBatchResult.class);
		return result;
	}

	/**
	 * 企业号上传临时素材文件
	 * @param path		文件位置
	 * @param type		媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)
	 * @return			媒体文件的media_id
	 */
	public String uploadTempMedia(String path, String type) {
		String url = CORP_UPLOAD_TEMP_MEADIA.replace("ACCESS_TOKEN", getToken()).replace("TYPE", type);
		String json = upload(url, path);
		UploadTempMediaBack back = gson.fromJson(json, UploadTempMediaBack.class);
		return back.getMedia_id();
	}

	/**
	 * 企业号获取临时素材文件
	 * @param mediaId	媒体文件id。最大长度为256字节
	 * @param filePath 文件保存地址（文件保存地址请填写到文件名称，但不要填写文件格式，文件格式将自动获取！）
	 * @return			成功true，失败false
	 */
	public String getTempMedia(String mediaId, String filePath) {
		String url = CORP_GET_TEMP_MEADIA.replace("ACCESS_TOKEN", getToken()).replace("MEDIA_ID", mediaId);
		String success = DownFile.down(url, filePath);
		return success;
	}

	/**
	 * 企业号上传永久图文素材
	 * @param articles	图文消息，一个图文消息支持1到10个图文
	 * @return			素材资源标识ID。最大长度为256字节
	 */
	public String uploadMPNews(List<com.wechat.corp.pojo.msg.send.Article> articles) {
		MPNewsPost post = getMPNewsPost(articles);
		String url = CORP_UPLOAD_MPNEWS.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(post));
		JSONObject object = new JSONObject(json);
		if (!object.getString("errcode").equals("0"))
			return null;
		return object.getString("media_id");
	}

	/**
	 * 企业号上传永久素材
	 * @param path		素材文件位置
	 * @param type		媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)
	 * @return			素材资源标识ID。最大长度为256字节
	 */
	public String uploadMaterial(String path, String type) {
		String url = CORP_UPLOAD_MATERIAL.replace("ACCESS_TOKEN", getToken()).replace("TYPE", type);
		String json = upload(url, path);
		JSONObject object = new JSONObject(json);
		if (object.getInt("errcode") != 0)
			throwException("上传" + type + "文件失败！");
		return object.getString("media_id");
	}

	/**
	 * 企业号获取永久素材
	 * @param mediaId	素材资源标识ID
	 * @return			成功true，失败false
	 */
	public boolean getMaterial(String mediaId) {
		String url = CORP_GET_MATERIAL.replace("ACCESS_TOKEN", getToken()).replace("MEDIA_ID", mediaId);
		String json = request(url, GET, null);
		JSONObject object = new JSONObject(json);
		if (object.has("errcode"))
			return false;
		return true;
	}

	/**
	 * 企业号图文消息素材
	 * @param mediaId	素材资源标识ID
	 * @return			com.wechat.corp.pojo.msg.send.Article对象
	 */
	public List<com.wechat.corp.pojo.msg.send.Article> getMPNews(String mediaId) {
		String url = CORP_GET_MATERIAL.replace("ACCESS_TOKEN", getToken()).replace("MEDIA_ID", mediaId);
		String json = request(url, GET, null);
		GetMPNewsBack back = gson.fromJson(json, GetMPNewsBack.class);
		JSONObject object = new JSONObject(json);
		if (object.has("errcode"))
			throwException("获取图文消息素材失败");
		return back.getMpnews().getArticles();
	}

	/**
	 * 企业号删除永久素材
	 * @param mediaId	素材资源标识ID
	 * @return			成功true，失败false
	 */
	public boolean deleteMaterial(String mediaId) {
		String url = CORP_DELETE_MATERIAL.replace("ACCESS_TOKEN", getToken()).replace("MEDIA_ID", mediaId);
		String json = request(url, GET, null);
		MenuBack back = gson.fromJson(json, MenuBack.class);
		if (back.getErrcode() != 0)
			return false;
		return true;
	}

	/**
	 * 企业号修改永久图文素材
	 * @param mediaId		素材资源标识ID。最大长度为256字节
	 * @param articles		图文消息，一个图文消息支持1到10个图文
	 * @return				成功true，失败false
	 */
	public boolean updateMPNews(String mediaId, List<com.wechat.corp.pojo.msg.send.Article> articles) {
		UpdateMPNewsPost post = getUpdateMPNewsPost(mediaId, articles);
		String url = CORP_UPDATE_MPNEWS.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(post));
		JSONObject object = new JSONObject(json);
		if (!object.getString("errmsg").equals("ok"))
			return false;
		return true;
	}

	/**
	 * 企业号获取素材总数
	 * @return	一个map对象，其中total：应用素材总数目，image：图片素材总数目，voice：音频素材总数目，video：视频素材总数目，file：文件素材总数目，mpnews：图文素材总数目
	 */
	public Map<String, Integer> getMaterialCount() {
		String url = CORP_GET_MATERIAL_COUNT.replace("ACCESS_TOKEN", getToken());
		String json = request(url, GET, null);
		return getMaterialCount(json);
	}

	/**
	 * 获取获取素材列表
	 * @param type		素材类型，可以为图片（image）、音频（voice）、视频（video）、文件（file）
	 * @param offset	从该类型素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count		从该类型素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @return			List&lt;MaterialItem&gt;对象，MaterialItem中有media_id，filename，update_time
	 */
	public List<MaterialItem> getMaterialList(String type, int offset, int count) {
		String url = CORP_GET_MATERIAL_LIST.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getMaterialListPost(type, offset, count)));
		GetMaterialListBack back = gson.fromJson(json, GetMaterialListBack.class);
		if (back.getErrcode() != 0)
			throwException("获取" + type + "素材列表失败");
		return back.getItemlist();
	}

	/**
	 * 企业号获取图文(mpnews)素材列表
	 * @param offset	从该类型素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count		返回素材的数量，取值在1到50之间
	 * @return			List&lt;MPNewsBack&gt;对象
	 */
	public List<MPNewsBack> getMPNewsList(int offset, int count) {
		String url = CORP_GET_MATERIAL_LIST.replace("ACCESS_TOKEN", getToken());
		String json = request(url, POST, gson.toJson(getMaterialListPost("mpnews", offset, count)));
		GetMPNewsListBack back = gson.fromJson(json, GetMPNewsListBack.class);
		if (back.getErrcode() != 0)
			throwException("获取mpnews素材列表失败");
		return back.getItemlist();
	}

	/**
	 * 企业号上传图文消息内的图片
	 * @param path	素材文件位置
	 * @return		素材文件url地址
	 */
	public String uploadMPNewsImage(String path) {
		String url = CORP_UPLOAD_MPNEWS_IMAGE.replace("ACCESS_TOKEN", getToken());
		String back = upload(url, path);
		JSONObject json = new JSONObject(back);
		return json.getString("url");
	}

	// FIXME 下面是私有方法
	/******** ******** ******** private ******** ******** ********/
	private CorpMsg getCorpMsg(CorpMsg corpMsg, List<String> user, List<String> party, List<String> tag, String msgtype,
			int agentid, int safe) {
		if (safe != -1)
			corpMsg.setSafe(safe);
		corpMsg.setAgentid(agentid);
		corpMsg.setMsgtype(msgtype);
		// if (user != null)
		corpMsg.setTouser(list2String(user));
		// if (party != null)
		corpMsg.setToparty(list2String(party));
		// if (tag != null)
		corpMsg.setTotag(list2String(tag));
		return corpMsg;
	}

	private String list2String(List<String> list) {
		String str = "";
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				str = str + list.get(i);
				if (i < list.size() - 1)
					str = str + "|";
			}
		}
		return str;
	}

	private Text getText(String content) {
		Text text = new Text();
		text.setContent(content);
		return text;
	}

	private Media getMedia(String media_id) {
		Media media = new Media();
		media.setMedia_id(media_id);
		return media;
	}

	private Video getVideo(String media_id, String title, String description) {
		Video video = new Video();
		video.setMedia_id(media_id);
		video.setTitle(title);
		video.setDescription(description);
		return video;
	}

	private News getNews(List<Article> articles) {
		News news = new News();
		news.setArticles(articles);
		return news;
	}

	private MPNews getMPNews(List<com.wechat.corp.pojo.msg.send.Article> articles) {
		MPNews news = new MPNews();
		news.setArticles(articles);
		return news;
	}

	private CreateUser getCreateUser(String userid, String name, List<Integer> department, String position,
			String mobile, String gender, String email, String weixinid, String avatar_mediaid, List<NameValue> attrs) {
		CreateUser user = new CreateUser();
		user.setUserid(userid);
		user.setName(name);
		user.setDepartment(department);
		user.setPosition(position);
		user.setMobile(mobile);
		user.setGender(gender);
		user.setEmail(email);
		user.setWeixinid(weixinid);
		user.setAvatar_mediaid(avatar_mediaid);
		user.setExtattr(getExtAttr(attrs));
		return user;
	}

	private ExtAttr getExtAttr(List<NameValue> attrs) {
		ExtAttr attr = new ExtAttr();
		attr.setAttrs(attrs);
		return attr;
	}

	private void throwException(String str) {
		try {
			throw new Exception(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Userid2OpenidPost getUserid2OpenidPost(String userid, int agentid) {
		Userid2OpenidPost post = new Userid2OpenidPost();
		post.setUserid(userid);
		post.setAgentid(agentid);
		return post;
	}

	private Openid2UseridPost getOpenid2UseridPost(String openid) {
		Openid2UseridPost post = new Openid2UseridPost();
		post.setOpenid(openid);
		return post;
	}

	private AuthCode getAuthCode(String authCode) {
		AuthCode code = new AuthCode();
		code.setAuth_code(authCode);
		return code;
	}

	private GetLoginUrlPost getGetLoginUrlPost(String loginTicket, String target, int agentid) {
		GetLoginUrlPost post = new GetLoginUrlPost();
		post.setLogin_ticket(loginTicket);
		post.setTarget(target);
		post.setAgentid(agentid);
		return post;
	}

	private SetAgent getSetAgent(String agentid, String reportLocationFlag, String logoMediaid, String name,
			String description, String redirectDomain, int isreportuser, int isreportenter, String home_url,
			String chatExtensionUrl) {
		SetAgent agent = new SetAgent();
		agent.setAgentid(agentid);
		agent.setReport_location_flag(reportLocationFlag);
		agent.setLogo_mediaid(logoMediaid);
		agent.setName(name);
		agent.setDescription(description);
		agent.setRedirect_domain(redirectDomain);
		agent.setIsreportuser(isreportuser);
		agent.setIsreportenter(isreportenter);
		agent.setHome_url(home_url);
		agent.setChat_extension_url(chatExtensionUrl);
		return agent;
	}

	private Department getDepartment(String name, int parentid, int order, int id) {
		Department department = new Department();
		department.setName(name);
		department.setParentid(parentid);
		if (order != 0) 
			department.setOrder(order);
		if (id != 0)
			department.setId(id);
		return department;
	}

	private BatchDeleteUser getBatchDeleteUser(List<String> useridlist) {
		BatchDeleteUser list = new BatchDeleteUser();
		list.setUseridlist(useridlist);
		return list;
	}

	private Tag getCreateTagPost(String tagname, int tagid) {
		Tag post = new Tag();
		post.setTagname(tagname);
		if (tagid != -1)
			post.setTagid(tagid);
		return post;
	}

	private String getToken() {
		return this.accessToken.getAccess_token();
	}

	private String request(String url, String method, String post) {
		return CommonUtil.httpsRequest(url, method, post);
	}

	private AddTagUserPost getAddTagUserPost(int tagid, List<String> userlist, List<Integer> partylist) {
		AddTagUserPost post = new AddTagUserPost();
		post.setTagid(tagid);
		post.setUserlist(userlist);
		post.setPartylist(partylist);
		return post;
	}

	private BatchSyncCallback getBatchSyncCallback(String url, String token, String encodingaeskey) {
		if (url == null || token == null || encodingaeskey == null) {
			return null;
		}
		BatchSyncCallback callback = new BatchSyncCallback();
		callback.setUrl(url);
		callback.setToken(token);
		callback.setEncodingaeskey(encodingaeskey);
		return callback;
	}

	private BatchSyncUserPost getBatchSyncUserPost(String media_id, BatchSyncCallback callback) {
		BatchSyncUserPost post = new BatchSyncUserPost();
		post.setMedia_id(media_id);
		if (callback == null)
			return post;
		post.setCallback(callback);
		return post;
	}

	private String batchSync(String urlPost, String media_id, String url, String token, String encodingaeskey) {
		BatchSyncCallback callback = getBatchSyncCallback(url, token, encodingaeskey);
		BatchSyncUserPost post = getBatchSyncUserPost(media_id, callback);
		String json = request(urlPost, POST, gson.toJson(post));
		BatchSyncBack back = gson.fromJson(json, BatchSyncBack.class);
		if (back.getErrcode() != 0)
			throwException("异步任务批量处理失败");
		return back.getJobid();
	}

	private String upload(String url, String path) {
		String jsonStr = null;
		try {
			jsonStr = CommonUtil.upload(url, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	private MPNewsPost getMPNewsPost(List<com.wechat.corp.pojo.msg.send.Article> articles) {
		MPNewsPost post = new MPNewsPost();
		post.setMpnews(getMPNews(articles));
		return post;
	}

	private UpdateMPNewsPost getUpdateMPNewsPost(String mediaId, List<com.wechat.corp.pojo.msg.send.Article> articles) {
		UpdateMPNewsPost post = new UpdateMPNewsPost();
		post.setMedia_id(mediaId);
		post.setMpnews(getMPNews(articles));
		return post;
	}

	private Map<String, Integer> getMaterialCount(String json) {
		MaterialCount count = gson.fromJson(json, MaterialCount.class);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("total", count.getTotal_count());
		map.put("image", count.getImage_count());
		map.put("voice", count.getVoice_count());
		map.put("video", count.getVideo_count());
		map.put("file", count.getFile_count());
		map.put("mpnews", count.getMpnews_count());
		return map;
	}

	private GetMaterialListPost getMaterialListPost(String type, int offset, int count) {
		GetMaterialListPost post = new GetMaterialListPost();
		post.setType(type);
		post.setOffset(offset);
		post.setCount(count);
		return post;
	}

	private String getSendUrl() {
		return CORP_SEND_MSG_URL.replace("ACCESS_TOKEN", getToken());
	}

	private boolean sendMsg(String content) {
		CorpSendMsgBack back = gson.fromJson(request(getSendUrl(), "POST", content), CorpSendMsgBack.class);
		boolean flag = true;
		if (back.getErrcode() != 0) {
			throwException("errcode：" + back.getErrcode() + "\nerrssg：" + back.getErrmsg() + "\ninvaliduser："
					+ back.getInvaliduser() + "\ninvalidparty：" + back.getInvalidparty() + "\ninvalidtag："
					+ back.getInvalidtag());
			flag = false;
		}
//		System.out.println("errcode：" + back.getErrcode() + "\nerrssg：" + back.getErrmsg() + "\ninvaliduser："
//				+ back.getInvaliduser() + "\ninvalidparty：" + back.getInvalidparty() + "\ninvalidtag："
//				+ back.getInvalidtag());
		return flag;
	}
}
