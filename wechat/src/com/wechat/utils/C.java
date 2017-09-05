package com.wechat.utils;

/**
 * 类名：C <br>
 * 描述：常用字符级 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：1.00 <br>
 */
public class C {

	// GET请求
	public static final String GET = "GET";

	// POST请求
	public static final String POST = "POST";

	/**
	 * 获取access token
	 * <br>需要：
	 * <ol><li>APPID	&emsp;	公众号唯一ID</li>
	 * <li>APPSECRET &emsp; 	公众号密钥</li>
	 * </ol>
	 */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取用户授权CODE
	 * <br>需要：
	 * <ol><li>APPID &emsp;  公众号唯一ID</li>
	 * <li>REDIRECT_URI &emsp; 需要跳转的网站</li>
	 * <li>SCOPE &emsp; snsapi_base只获取用户openID；  snsapi_userinfo获取用户详细信息</li>
	 * <li>STATE &emsp; 【可不填】重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节)</li>
	 * </ol>
	 */
	public static final String WEB_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	/**
	 * 网页access token
	 * <br>需要：
	 * <ol><li>APPID &emsp;  公众号唯一ID</li>
	 * <li>SECRET &emsp;  公众号密钥</li>
	 * <li>CODE &emsp;  用户授权code</li>
	 * </ol>
	 */
	public static final String WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	/**
	 * 刷新access_token（如果需要）
	 * <br>需要：
	 * <ol><li>APPID &emsp; 公众号唯一ID</li>
	 * <li>REFRESH_TOKEN &emsp; WebAccessToken的refresh_token参数</li>
	 * </ol>
	 */
	public static final String REFRESH_WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	
	/**
	 * 检验网页授权凭证（access_token）是否有效
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 网页授权凭证</li>
	 * <li>OPENID &emsp; 用户的唯一标识</li>
	 * </ol><br>
	 */
	public static final String CHECK_WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
	
	/**
	 * 微信js 的基础ticket
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; ACCESS_TOKEN 非 webACCESS_TOKEN</li>
	 * </ol><br>
	 */
	public static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * api_ticket 是用于调用微信卡券JS API的临时票据
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String API_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";

	/**
	 * 拉取用户信息(非web)
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 网页授权接口调用凭证</li>
	 * <li>OPENID &emsp; 用户的唯一标识</li>
	 * <li>lang &emsp; 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语</li>
	 * </ol>
	 */
	public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 网页授权接口调用凭证</li>
	 * <li>OPENID &emsp; 用户的唯一标识</li>
	 * <li>lang &emsp; 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语</li>
	 * </ol>
	 */
	public static final String WEB_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 创建菜单
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建个性化菜单
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String MENU_CREATE_CONDITIONAL_URL = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除菜单
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 查询菜单
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取公众号菜单配置信息
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String MENU_GET_INFO_URL = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";
	
	// 开锁页面动态url
	/**
	 * 
	 * <br>需要：
	 * <ol><li> &emsp; </li>
	 * </ol>
	 */
	public static final String UNLOCK_WEB_URL = "http://rilaohnwechat.s1.natapp.cc/Unlocking/web.do?code=CODE&state=STATE";

	/**
	 * 添加客服帐号
	 * <br>需要：【POST】
	 * <ol><li> ACCESS_TOKEN &emsp; 调用接口凭证 </li>
	 * </ol><br>
	 */
	public static final String CUSTOM_SERVICE_ADD = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";

	/**
	 * 修改客服帐号
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CUSTOM_SERVICE_UPDATE = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除客服帐号
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证 </li>
	 * </ol><br>
	 */
	public static final String CUSTOM_SERVICE_DELETE = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN";

	/**
	 * 设置客服帐号的头像
	 * <br>需要：【POST/FORM】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * <li>KFACCOUNT &emsp; 完整客服账号，格式为：账号前缀@公众号微信号</li>
	 * </ol>
	 * <br>使用curl命令，用FORM表单方式上传一个多媒体文件，curl命令的具体用法请自行了解<br>
	 * 开发者可调用本接口来上传图片作为客服人员的头像，<br>
	 * 头像图片文件必须是jpg格式，<br>
	 * 推荐使用640*640大小的图片以达到最佳效果。
	 */
	public static final String CUSTOM_SERVICE_SET_AVATAR = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";

	/**
	 * 获取所有客服账号
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证 </li>
	 * </ol><br>
	 */
	public static final String CUSTOM_SERVICE_GET_ALL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
	
	/**
	 * 客服接口-发消息
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证 </li>
	 * </ol><br>
	 */
	public static final String CUSTOM_SERVICE_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	/**
	 * 上传图文消息内的图片获取URL
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol>
	 * 请注意:<br>
	 * 本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制
	 * <br>图片仅支持jpg/png格式，大小必须在1MB以下。
	 */
	public static final String UPLOAD_IMAGE_RETURN_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	
	/**
	 * 上传图文消息素材
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	
	/**
	 * 根据分组进行群发
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String SEND_MESSAGE_TO_ALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	
	/**
	 * 分组群发 视频上传
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String UPLOAD_VIDEO_S_M_T_A = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
	
	/**
	 * 根据OpenID列表群发
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String SEND_MESSAGE_BY_OPENID_LIST = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	
	/**
	 * OpenID群发 视频上传
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String UPLOAD_VIDEO_S_M_B_O_L = "https://api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除群发
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String DELETE_MASS_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 预览接口
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String PREVIEW_MASS_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	
	/**
	 * 查询群发消息发送状态
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String GET_MASS_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 设置所属行业
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String SET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取设置的行业信息
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String GET_INDUSTRY = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
	
	/**
	 * 获得模板ID
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String GET_TEMPLATE_ID = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取模板列表
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String GET_TEMPLATE_LIST = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除模板
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String DELETE_TEMPLATE = "https://api,weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN";
	
	/**
	 * 发送模板消息
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	
	/**
	 * 获取自动回复规则
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String GET_CURRENT_AUTO_REPLY_INFO = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取素材列表
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String BATCH_GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	
	/**
	 * 企业号根据code获取user ticket
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * <li>CODE &emsp; 通过成员授权获取到的code，每次成员授权带上的code将不一样，code只能使用一次，10分钟未被使用自动过期</li>
	 * </ol><br>
	 */
	public static final String CORP_WEB_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	
	/**
	 * 企业号根据uset ticket获取成员信息
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_WEB_USER_INFO_BY_TICKET = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=ACCESS_TOKEN";
	
	/**
	 * 企业号根据code获取成员信息
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 调用接口凭证</li>
	 * <li>CODE &emsp; 通过成员授权获取到的code，每次成员授权带上的code将不一样，code只能使用一次，10分钟未被使用自动过期</li>
	 * </ol><br>
	 */
	public static final String CORP_ACCESSTOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=id&corpsecret=secrect";
	
	/**
	 * 企业号userid转换成openid接口
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_USERID_2_OPENID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=ACCESS_TOKEN";
	
	/**
	 * 企业号userid转换成openid接口
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_OPENID_2_USERID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token=ACCESS_TOKEN";
	
	/**
	 * 企业号成员登录授权
	 * <br>需要：
	 * <ol><li>CORPID &emsp; 服务商的CorpID或者企业的CorpID</li>
	 * <li>REDIRECTURL &emsp; 授权登录之后目的跳转网址，所在域名需要与登录授权域名一致</li>
	 * <li>STATE &emsp; 用于企业或服务商自行校验session，防止跨域攻击</li>
	 * <li>USERTYPE &emsp; redirect_uri支持登录的类型，有member(成员登录)、admin(管理员登录)、all(成员或管理员皆可登录)，默认值为admin</li>
	 * </ol><br>
	 */
	public static final String CORP_LOGIN_PAGE = "https://qy.weixin.qq.com/cgi-bin/loginpage?corp_id=CORPID&redirect_uri=REDIRECTURL&state=STATE&usertype=USERTYPE";
	
	/**
	 * 获取企业号登录用户信息
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_LOGIN_INFO = "https://qyapi.weixin.qq.com/cgi-bin/service/get_login_info?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取企业号登录用户信息
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_LOGIN_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_login_url?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取企业号应用的URL
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>AGENTID &emsp; 企业应用的id</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_AGENTID = "https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=ACCESS_TOKEN&agentid=AGENTID";
	
	/**
	 * 设置企业号应用的URL
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_SET_AGENTID = "https://qyapi.weixin.qq.com/cgi-bin/agent/set?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取应用概况列表
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_AGENTID_LIST = "https://qyapi.weixin.qq.com/cgi-bin/agent/list?access_token=ACCESS_TOKEN";
	
	/**
	 * 二次验证
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>USERID &emsp; 成员UserID</li>
	 * </ol><br>
	 */
	public static final String CORP_TWO_VAL = "https://qyapi.weixin.qq.com/cgi-bin/user/authsucc?access_token=ACCESS_TOKEN&userid=USERID";
	
	/**
	 * 创建部门
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_CREATE_DEPARTMENT = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
	
	/**
	 * 更新部门
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_UPDATE_DEPARTMENT = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除部门
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>ID &emsp; 部门id。（注：不能删除根部门；不能删除含有子部门、成员的部门）</li>
	 * </ol><br>
	 */
	public static final String CORP_DELETE_DEPARTMENT = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";
	
	/**
	 * 删除部门
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>ID &emsp; 部门id。（注：不能删除根部门；不能删除含有子部门、成员的部门）</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_DEPARTMENT_LIST = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";
	

	/**
	 * 创建成员
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_CREATE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
	
	/**
	 * 更新成员
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_UPDATE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除成员
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>USERID &emsp; 成员UserID。对应管理端的帐号</li>
	 * </ol><br>
	 */
	public static final String CORP_DELETE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";
	
	/**
	 * 批量删除成员
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_BATCH_DELETE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取成员
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>USERID &emsp; 成员UserID。对应管理端的帐号</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
	
	/**
	 * 获取部门成员(简单的成员信息)
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>DEPARTMENT_ID &emsp; 获取的部门id</li>
	 * <li>FETCH_CHILD &emsp; 1/0：是否递归获取子部门下面的成员</li>
	 * <li>STATUS &emsp; 0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_DEPARTMENT_USER_SIMPLE = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
	
	/**
	 * 获取部门成员(详情)
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>DEPARTMENT_ID &emsp; 获取的部门id</li>
	 * <li>FETCH_CHILD &emsp; 1/0：是否递归获取子部门下面的成员</li>
	 * <li>STATUS &emsp; 0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认为4</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_DEPARTMENT_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
	
	/**
	 * 创建标签
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_CREATE_TAG = "https://qyapi.weixin.qq.com/cgi-bin/tag/create?access_token=ACCESS_TOKEN";
	
	/**
	 * 更新标签名字
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_UPDATE_TAG = "https://qyapi.weixin.qq.com/cgi-bin/tag/update?access_token=ACCESS_TOKEN";	
	

	/**
	 * 删除标签
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>TAGID &emsp; 标签ID</li>
	 * </ol><br>
	 */
	public static final String CORP_DELETE_TAG = "https://qyapi.weixin.qq.com/cgi-bin/tag/delete?access_token=ACCESS_TOKEN&tagid=TAGID";
	
	/**
	 * 获取标签成员
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>TAGID &emsp; 标签ID</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_TAG_USER = "https://qyapi.weixin.qq.com/cgi-bin/tag/get?access_token=ACCESS_TOKEN&tagid=TAGID";
	
	/**
	 * 增加标签成员
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_ADD_TAG_USER = "https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除标签成员
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_DELETE_TAG_USER = "https://qyapi.weixin.qq.com/cgi-bin/tag/deltagusers?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取标签列表
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_TAG_LIST = "https://qyapi.weixin.qq.com/cgi-bin/tag/list?access_token=ACCESS_TOKEN";
	
	/**
	 * 通讯录更新-增量更新成员
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_BATCH_SYNC_USER = "https://qyapi.weixin.qq.com/cgi-bin/batch/syncuser?access_token=ACCESS_TOKEN";
	
	/**
	 * 通讯录更新-全量覆盖部门
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_BATCH_REPLACE_USER = "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceuser?access_token=ACCESS_TOKEN";
	
	/**
	 * 通讯录更新-全量覆盖成员
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_BATCH_REPLACE_PARTY = "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceparty?access_token=ACCESS_TOKEN";
	
	/**
	 * 通讯录更新-获取异步任务结果
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>JOBID &emsp; 异步任务id，最大长度为64字节</li>
	 * </ol><br>
	 */
	public static final String CORP_BATCH_GET_RESULT = "https://qyapi.weixin.qq.com/cgi-bin/batch/getresult?access_token=ACCESS_TOKEN&jobid=JOBID";
	
	/**
	 * 上传临时素材文件
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>TYPE &emsp; 媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)</li>
	 * </ol><br>
	 */
	public static final String CORP_UPLOAD_TEMP_MEADIA = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 获取临时素材文件
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>MEDIA_ID &emsp; 媒体文件id。最大长度为256字节</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_TEMP_MEADIA = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 上传永久图文素材
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_UPLOAD_MPNEWS = "https://qyapi.weixin.qq.com/cgi-bin/material/add_mpnews?access_token=ACCESS_TOKEN";

	/**
	 * 上传永久素材
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>TYPE &emsp; 媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)</li>
	 * </ol><br>
	 */
	public static final String CORP_UPLOAD_MATERIAL = "https://qyapi.weixin.qq.com/cgi-bin/material/add_material?type=TYPE&access_token=ACCESS_TOKEN";

	/**
	 * 获取永久素材
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>MEDIA_ID &emsp; 媒体文件id。最大长度为256字节</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_MATERIAL = "https://qyapi.weixin.qq.com/cgi-bin/material/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 删除永久素材
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * <li>MEDIA_ID &emsp; 媒体文件id。最大长度为256字节</li>
	 * </ol><br>
	 */
	public static final String CORP_DELETE_MATERIAL = "https://qyapi.weixin.qq.com/cgi-bin/material/del?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 修改永久图文素材
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_UPDATE_MPNEWS = "https://qyapi.weixin.qq.com/cgi-bin/material/update_mpnews?access_token=ACCESS_TOKEN";

	/**
	 * 获取素材总数
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_MATERIAL_COUNT = "https://qyapi.weixin.qq.com/cgi-bin/material/get_count?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取素材列表
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_GET_MATERIAL_LIST = "https://qyapi.weixin.qq.com/cgi-bin/material/batchget?access_token=ACCESS_TOKEN";

	/**
	 * 上传图文消息内的图片
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_UPLOAD_MPNEWS_IMAGE = "https://qyapi.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	
	/**
	 * 企业号jsapi_ticket
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_JSAPI_TICKET = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取公众号的ip地址
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 公众号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String GET_WECHAT_ADDRESS_LIST = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
	
	/**
	 * 获取企业号的ip地址
	 * <br>需要：【GET】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String GET_CORP_WECHAT_ADDRESS_LIST = "https://qyapi.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";

	/**
	 * 企业号发送消息地址
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_SEND_MSG_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
	
	/**
	 * 公众号生成带参数二维码
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 公众号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String QRCODE_WITH_PARAM = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	
	/**
	 * 公众号生成带参数二维码
	 * <br>需要：【POST】
	 * <ol><li>TICKET &emsp; 生成的二维码ticket</li>
	 * </ol><br>
	 */
	public static final String GET_QRCODE_PIC_BY_TICKET = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	
	/**
	 * 微信短链接
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 公众号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String LONG_2_SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";

	/**
	 * 统一下单请求接口
	 * <br>需要：【POST】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String WXPAY_UNIFY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";













	/**
	 * 
	 * <br>需要：【】
	 * <ol><li>ACCESS_TOKEN &emsp; 企业号的调用接口凭证</li>
	 * </ol><br>
	 */
	public static final String CORP_ = "";
	
	
	
	
	
	
	/**
	 * 
	 * <br>需要：【】
	 * <ol><li> &emsp; </li>
	 * </ol><br>
	 */
}
