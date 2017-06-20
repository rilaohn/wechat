package com.wechat.process;

import static com.wechat.utils.C.BATCH_GET_MATERIAL;
import static com.wechat.utils.C.CUSTOM_SERVICE_ADD;
import static com.wechat.utils.C.CUSTOM_SERVICE_DELETE;
import static com.wechat.utils.C.CUSTOM_SERVICE_GET_ALL;
import static com.wechat.utils.C.CUSTOM_SERVICE_SEND_MESSAGE;
import static com.wechat.utils.C.CUSTOM_SERVICE_SET_AVATAR;
import static com.wechat.utils.C.CUSTOM_SERVICE_UPDATE;
import static com.wechat.utils.C.DELETE_MASS_MESSAGE;
import static com.wechat.utils.C.DELETE_TEMPLATE;
import static com.wechat.utils.C.GET;
import static com.wechat.utils.C.GET_INDUSTRY;
import static com.wechat.utils.C.GET_TEMPLATE_ID;
import static com.wechat.utils.C.GET_TEMPLATE_LIST;
import static com.wechat.utils.C.POST;
import static com.wechat.utils.C.PREVIEW_MASS_MESSAGE;
import static com.wechat.utils.C.SEND_MESSAGE_BY_OPENID_LIST;
import static com.wechat.utils.C.SEND_MESSAGE_TO_ALL;
import static com.wechat.utils.C.SEND_TEMPLATE_MESSAGE;
import static com.wechat.utils.C.SET_INDUSTRY;
import static com.wechat.utils.C.UPLOAD_IMAGE_RETURN_URL;
import static com.wechat.utils.C.UPLOAD_VIDEO_S_M_T_A;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.wechat.pojo.msg.send.kf.Article;
import com.wechat.pojo.msg.send.kf.CSAccount;
import com.wechat.pojo.msg.send.kf.KFSendCard;
import com.wechat.pojo.msg.send.kf.KFSendCardBy;
import com.wechat.pojo.msg.send.kf.KFSendImage;
import com.wechat.pojo.msg.send.kf.KFSendImageBy;
import com.wechat.pojo.msg.send.kf.KFSendMPNews;
import com.wechat.pojo.msg.send.kf.KFSendMPNewsBy;
import com.wechat.pojo.msg.send.kf.KFSendMusic;
import com.wechat.pojo.msg.send.kf.KFSendMusicBy;
import com.wechat.pojo.msg.send.kf.KFSendNews;
import com.wechat.pojo.msg.send.kf.KFSendNewsBy;
import com.wechat.pojo.msg.send.kf.KFSendText;
import com.wechat.pojo.msg.send.kf.KFSendTextBy;
import com.wechat.pojo.msg.send.kf.KFSendVideo;
import com.wechat.pojo.msg.send.kf.KFSendVideoBy;
import com.wechat.pojo.msg.send.kf.KFSendVoice;
import com.wechat.pojo.msg.send.kf.KFSendVoiceBy;
import com.wechat.pojo.msg.send.kf.Music;
import com.wechat.pojo.msg.send.kf.News;
import com.wechat.pojo.msg.send.kf.Text;
import com.wechat.pojo.msg.send.kf.Video;
import com.wechat.pojo.msg.send.kf.WXCard;
import com.wechat.pojo.msg.send.mass.DeleteMass;
import com.wechat.pojo.msg.send.mass.Filter;
import com.wechat.pojo.msg.send.mass.MassCard;
import com.wechat.pojo.msg.send.mass.MassCard2List;
import com.wechat.pojo.msg.send.mass.MassImage;
import com.wechat.pojo.msg.send.mass.MassImage2List;
import com.wechat.pojo.msg.send.mass.MassMPNews;
import com.wechat.pojo.msg.send.mass.MassMPNews2List;
import com.wechat.pojo.msg.send.mass.MassText;
import com.wechat.pojo.msg.send.mass.MassText2List;
import com.wechat.pojo.msg.send.mass.MassVideo;
import com.wechat.pojo.msg.send.mass.MassVideo2List;
import com.wechat.pojo.msg.send.mass.MassVoice;
import com.wechat.pojo.msg.send.mass.MassVoice2List;
import com.wechat.pojo.msg.send.mass.Media;
import com.wechat.pojo.msg.send.mass.PreviewCard;
import com.wechat.pojo.msg.send.mass.PreviewImage;
import com.wechat.pojo.msg.send.mass.PreviewMPNews;
import com.wechat.pojo.msg.send.mass.PreviewText;
import com.wechat.pojo.msg.send.mass.PreviewVideo;
import com.wechat.pojo.msg.send.mass.PreviewVoice;
import com.wechat.pojo.msg.send.mass.UploadVideo;
import com.wechat.pojo.msg.send.template.DeleteTemplate;
import com.wechat.pojo.msg.send.template.GetTemplateId;
import com.wechat.pojo.msg.send.template.SendTemplate;
import com.wechat.pojo.msg.send.template.SetIndustry;
import com.wechat.pojo.msg.send.template.TemplateData;
import com.wechat.pojo.msg.send.template.TemplateList;
import com.wechat.pojo.user.SetKF;
import com.wechat.utils.CommonUtil;
import com.wechat.utils.MD5;

public class SendMessage {
	private Gson gson;
	private String accessToken;
	private String kf_send_msg_url;
	private String mass_msg_url;
	private String mass_msg_list_url;
	private String outputStr;
	private String del_mass_msg_url;
	private String prev_mass_msg_url;
	private String set_industry_url;
	private String get_industry_url;
	private String get_template_id_url;
	private String get_template_list_url;
	private String del_template_url;
	private String send_temp_msg_url;
	private String upload_video_to_all;
	private String upload_image_return_url;
	private String batch_get_material;

	public SendMessage(String accessToken) {
		gson = new Gson();
//		System.out.println("*-+-* accessToken" + accessToken);
		kf_send_msg_url = CUSTOM_SERVICE_SEND_MESSAGE.replace("ACCESS_TOKEN", accessToken);
		mass_msg_url = SEND_MESSAGE_TO_ALL.replace("ACCESS_TOKEN", accessToken);
		mass_msg_list_url = SEND_MESSAGE_BY_OPENID_LIST.replace("ACCESS_TOKEN", accessToken);
		del_mass_msg_url = DELETE_MASS_MESSAGE.replace("ACCESS_TOKEN", accessToken);
		prev_mass_msg_url = PREVIEW_MASS_MESSAGE.replace("ACCESS_TOKEN", accessToken);
		set_industry_url = SET_INDUSTRY.replace("ACCESS_TOKEN", accessToken);
		get_industry_url = GET_INDUSTRY.replace("ACCESS_TOKEN", accessToken);
		get_template_id_url = GET_TEMPLATE_ID.replace("ACCESS_TOKEN", accessToken);
		get_template_list_url = GET_TEMPLATE_LIST.replace("ACCESS_TOKEN", accessToken);
		del_template_url = DELETE_TEMPLATE.replace("ACCESS_TOKEN", accessToken);
		send_temp_msg_url = SEND_TEMPLATE_MESSAGE.replace("ACCESS_TOKEN", accessToken);
		upload_video_to_all = UPLOAD_VIDEO_S_M_T_A.replace("ACCESS_TOKEN", accessToken);
		upload_image_return_url = UPLOAD_IMAGE_RETURN_URL.replace("ACCESS_TOKEN", accessToken);
		batch_get_material = BATCH_GET_MATERIAL.replace("ACCESS_TOKEN", accessToken);
	}

	/**
	 * 增加，修改，删除客服
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @param nickname		客服昵称，最长6个汉字或12个英文字符
	 * @param password		客服账号登录密码。
	 * @param type			0:增加； 1：修改； 2：删除
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean operateKF(String kf_account, String nickname, String password, int type) {
		SetKF kf = new SetKF();
		kf.setKf_account(kf_account);
		kf.setNickname(nickname);
		kf.setPassword(MD5.encrypt(password));
		outputStr = gson.toJson(kf);
		String requestUrl = null;
		switch (type) {
		case 0:
			requestUrl = CUSTOM_SERVICE_ADD.replace("ACCESS_TOKEN", accessToken);
			break;
		case 1:
			requestUrl = CUSTOM_SERVICE_UPDATE.replace("ACCESS_TOKEN", accessToken);
			break;
		case 2:
			requestUrl = CUSTOM_SERVICE_DELETE.replace("ACCESS_TOKEN", accessToken);

			break;
		default:
			break;
		}
		String backJson = CommonUtil.httpsRequest(requestUrl, POST, outputStr);
		JSONObject json = new JSONObject(backJson);
		if (json.getInt("errcode") == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 设置客服头像
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @param imagePath		图片路劲
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean setKFAvatar(String kf_account, String imagePath) {
		String url = CUSTOM_SERVICE_SET_AVATAR.replace("ACCESS_TOKEN", accessToken)
				.replace("KFACCOUNT", kf_account);
		String jsonStr = null;
		try {
			jsonStr = CommonUtil.upload(url, imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(jsonStr);
		if (json.getInt("errcode") == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取所有客服
	 * @return		客服列表
	 */
	public String getAllKF() {
		String requestUrl = CUSTOM_SERVICE_GET_ALL.replace("ACCESS_TOKEN", accessToken);
		String jsonStr = CommonUtil.httpsRequest(requestUrl, GET, null);

		return jsonStr;
	}

	/**
	 * 客服发送文本消息
	 * @param touser	目标用户的openID
	 * @param content	消息内容
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendText(String touser, String content) {
		KFSendText sendText = new KFSendText();
		sendText.setTouser(touser);
		sendText.setMsgtype("text");
		sendText.setText(geText(content));

		outputStr = gson.toJson(sendText);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 客服发送图片消息
	 * @param touser		目标用户的openID
	 * @param media_id		发送的图片的媒体ID
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendImage(String touser, String media_id) {
		KFSendImage sendImage = new KFSendImage();
		sendImage.setTouser(touser);
		sendImage.setMsgtype("image");
		sendImage.setImage(getMedia(media_id));
		outputStr = gson.toJson(sendImage);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 客服发送语音消息
	 * @param touser		目标用户的openID
	 * @param media_id		发送的语言的媒体ID
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendVoice(String touser, String media_id) {
		KFSendVoice send = new KFSendVoice();
		send.setTouser(touser);
		send.setMsgtype("voice");
		send.setVoice(getMedia(media_id));
		outputStr = gson.toJson(send);
		return sendMessage(kf_send_msg_url, POST, outputStr);
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
	public boolean kfSendVideo(String touser, String media_id, String thumb_media_id,
			String title, String description) {
		Video param = new Video();
		param.setMedia_id(media_id);
		param.setThumb_media_id(thumb_media_id);
		param.setTitle(title);
		param.setDescription(description);

		return kfSendVideo(touser, param);
	}

	/**
	 * 客服发送视频消息
	 * @param touser	目标用户的openID
	 * @param video		客服视频实体
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendVideo(String touser, Video video) {
		KFSendVideo send = new KFSendVideo();
		send.setTouser(touser);
		send.setMsgtype("video");
		send.setVideo(video);
		outputStr = gson.toJson(send);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 客服发送音乐消息
	 * @param touser	目标用户的openID
	 * @param music		客服Music实体
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendMusic(String touser, Music music) {
		KFSendMusic sendMusic = new KFSendMusic();
		sendMusic.setTouser(touser);
		sendMusic.setMsgtype("music");
		sendMusic.setMusic(music);
		outputStr = gson.toJson(sendMusic);
		return sendMessage(kf_send_msg_url, POST, outputStr);
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
	public boolean kfSendMusic(String touser, String title, String description,
			String musicurl, String hqmusicurl, String thumb_media_id) {
		Music music = new Music();
		music.setTitle(title);
		music.setDescription(description);
		music.setMusicurl(musicurl);
		music.setHqmusicurl(hqmusicurl);
		music.setThumb_media_id(thumb_media_id);
		return kfSendMusic(touser, music);
	}

	/**
	 * 客服发送新闻消息
	 * @param touser	目标用户的openID
	 * @param news		客服新闻实体
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendNews(String touser, News news) {
		KFSendNews sendNews = new KFSendNews();
		sendNews.setTouser(touser);
		sendNews.setMsgtype("news");
		sendNews.setNews(news);
		outputStr = gson.toJson(sendNews);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 客服发送新闻消息
	 * @param touser		目标用户的openID
	 * @param articles		Article列表
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendNews(String touser, List<Article> articles) {
		News news = new News();
		news.setArticles(articles);
		return kfSendNews(touser, news);
	}

	/**
	 * 客服发送站内图文消息
	 * @param touser		目标用户的openID
	 * @param media_id		图文消息ID
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendMPNews(String touser, String media_id) {
		KFSendMPNews sendMPNews = new KFSendMPNews();
		sendMPNews.setTouser(touser);
		sendMPNews.setMsgtype("mpnews");
		sendMPNews.setMpnews(getMedia(media_id));
		outputStr = gson.toJson(sendMPNews);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 客服发送卡卷消息
	 * @param touser	目标用户的openID
	 * @param wxcard	客服WXCard实体
	 * @return 			成回TRUE，失败FALSE
	 */
	public boolean kfSendCard(String touser, WXCard wxcard) {
		KFSendCard sendCard = new KFSendCard();
		sendCard.setTouser(touser);
		sendCard.setMsgtype("wxcard");
		sendCard.setWxcard(wxcard);
		outputStr = gson.toJson(sendCard);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 指定客服发送文本消息
	 * @param touser		目标用户的openID
	 * @param content		消息内容
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendTextBy(String touser, String content, String kf_account) {
		CSAccount customservice = new CSAccount();
		customservice.setKf_account(kf_account);
		KFSendTextBy sendText = new KFSendTextBy();
		sendText.setTouser(touser);
		sendText.setMsgtype("text");
		sendText.setText(geText(content));
		sendText.setCustomservice(customservice);
		outputStr = gson.toJson(sendText);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 指定客服发送图片消息
	 * @param touser		目标用户的openID
	 * @param media_id		发送的图片的媒体ID
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendImageBy(String touser, String media_id, String kf_account) {
		CSAccount customservice = new CSAccount();
		customservice.setKf_account(kf_account);
		KFSendImageBy sendImage = new KFSendImageBy();
		sendImage.setTouser(touser);
		sendImage.setMsgtype("image");
		sendImage.setImage(getMedia(media_id));
		sendImage.setCustomservice(customservice);
		outputStr = gson.toJson(sendImage);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 指定客服发送语音消息
	 * @param touser		目标用户的openID
	 * @param media_id		发送的语言的媒体ID
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendVoiceBy(String touser, String media_id, String kf_account) {
		CSAccount customservice = new CSAccount();
		customservice.setKf_account(kf_account);
		KFSendVoiceBy send = new KFSendVoiceBy();
		send.setTouser(touser);
		send.setMsgtype("voice");
		send.setVoice(getMedia(media_id));
		send.setCustomservice(customservice);
		outputStr = gson.toJson(send);
		return sendMessage(kf_send_msg_url, POST, outputStr);
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
	public boolean kfSendVideoBy(String touser, String media_id, String thumb_media_id,
			String title, String description, String kf_account) {
		Video param = new Video();
		param.setMedia_id(media_id);
		param.setThumb_media_id(thumb_media_id);
		param.setTitle(title);
		param.setDescription(description);

		return kfSendVideoBy(touser, param, kf_account);
	}

	/**
	 * 指定客服发送视频消息
	 * @param touser		目标用户的openID
	 * @param video			客服视频实体
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendVideoBy(String touser, Video video, String kf_account) {
		CSAccount customservice = new CSAccount();
		customservice.setKf_account(kf_account);
		KFSendVideoBy send = new KFSendVideoBy();
		send.setTouser(touser);
		send.setMsgtype("video");
		send.setVideo(video);
		send.setCustomservice(customservice);
		outputStr = gson.toJson(send);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 指定客服发送音乐消息
	 * @param touser		目标用户的openID
	 * @param music			客服Music实体
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 				成回TRUE，失败FALSE
	 */
	public boolean kfSendMusicBy(String touser, Music music, String kf_account) {
		CSAccount customservice = new CSAccount();
		customservice.setKf_account(kf_account);
		KFSendMusicBy sendMusic = new KFSendMusicBy();
		sendMusic.setTouser(touser);
		sendMusic.setMsgtype("music");
		sendMusic.setMusic(music);
		sendMusic.setCustomservice(customservice);
		outputStr = gson.toJson(sendMusic);
		return sendMessage(kf_send_msg_url, POST, outputStr);
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
	public boolean kfSendMusicBy(String touser, String title, String description,
			String musicurl, String hqmusicurl, String thumb_media_id, String kf_account) {
		Music music = new Music();
		music.setTitle(title);
		music.setDescription(description);
		music.setMusicurl(musicurl);
		music.setHqmusicurl(hqmusicurl);
		music.setThumb_media_id(thumb_media_id);
		return kfSendMusicBy(touser, music, kf_account);
	}

	/**
	 * 指定客服发送新闻消息
	 * @param touser			目标用户的openID
	 * @param news				客服新闻实体
	 * @param kf_account		完整客服账号，格式为：账号前缀@公众号微信号
	 * @return 					成回TRUE，失败FALSE
	 */
	public boolean kfSendNewsBy(String touser, News news, String kf_account) {
		CSAccount customservice = new CSAccount();
		customservice.setKf_account(kf_account);
		KFSendNewsBy sendNews = new KFSendNewsBy();
		sendNews.setTouser(touser);
		sendNews.setMsgtype("news");
		sendNews.setNews(news);
		sendNews.setCustomservice(customservice);
		outputStr = gson.toJson(sendNews);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 指定客服发送新闻消息
	 * @param touser		目标用户的openID
	 * @param articles		Article列表
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean kfSendNewsBy(String touser, List<Article> articles, String kf_account) {
		News news = new News();
		news.setArticles(articles);
		return kfSendNewsBy(touser, news, kf_account);
	}

	/**
	 * 指定客服发送站内图文消息
	 * @param touser			目标用户的openID
	 * @param media_id			图文消息ID
	 * @param kf_account		完整客服账号，格式为：账号前缀@公众号微信号
	 * @return					成回TRUE，失败FALSE
	 */
	public boolean kfSendMPNewsBy(String touser, String media_id, String kf_account) {
		CSAccount customservice = new CSAccount();
		customservice.setKf_account(kf_account);
		KFSendMPNewsBy sendMPNews = new KFSendMPNewsBy();
		sendMPNews.setTouser(touser);
		sendMPNews.setMsgtype("mpnews");
		sendMPNews.setMpnews(getMedia(media_id));
		sendMPNews.setCustomservice(customservice);
		outputStr = gson.toJson(sendMPNews);
		return sendMessage(kf_send_msg_url, POST, outputStr);
	}

	/**
	 * 指定客服发送卡卷消息
	 * @param touser		目标用户的openID
	 * @param wxcard		客服WXCard实体
	 * @param kf_account	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean kfSendCardBy(String touser, WXCard wxcard, String kf_account) {
		CSAccount customservice = new CSAccount();
		customservice.setKf_account(kf_account);
		KFSendCardBy sendCard = new KFSendCardBy();
		sendCard.setTouser(touser);
		sendCard.setMsgtype("wxcard");
		sendCard.setWxcard(wxcard);
		sendCard.setCustomservice(customservice);
		outputStr = gson.toJson(sendCard);
		return sendMessage(kf_send_msg_url, POST, outputStr);
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
		MassMPNews massMPNews = new MassMPNews();
		massMPNews.setFilter(getFilter(is_to_all, group_id));
		massMPNews.setMpnews(getMedia(media_id));
		massMPNews.setMsgtype("mpnews");
		outputStr = gson.toJson(massMPNews);
		return sendMessage(mass_msg_url, POST, outputStr);
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
		MassText massText = new MassText();
		massText.setFilter(getFilter(is_to_all, group_id));
		massText.setMsgtype("text");
		massText.setText(geText(content));
		outputStr = gson.toJson(massText);
		return sendMessage(mass_msg_url, POST, outputStr);
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
		MassVoice massVoice = new MassVoice();
		massVoice.setFilter(getFilter(is_to_all, group_id));
		massVoice.setMsgtype("voice");
		massVoice.setVoice(getMedia(media_id));
		outputStr = gson.toJson(massVoice);
		return sendMessage(mass_msg_url, POST, outputStr);
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
		MassImage massImage = new MassImage();
		massImage.setFilter(getFilter(is_to_all, group_id));
		massImage.setMsgtype("image");
		massImage.setImage(getMedia(media_id));
		outputStr = gson.toJson(massImage);
		return sendMessage(mass_msg_url, POST, outputStr);
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
		MassVideo massVideo = new MassVideo();
		massVideo.setFilter(getFilter(is_to_all, group_id));
		massVideo.setMsgtype("mpvideo");
		massVideo.setMpvideo(getMedia(media_id));
		outputStr = gson.toJson(massVideo);
		return sendMessage(mass_msg_url, POST, outputStr);
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
	public boolean massVideo2All(boolean is_to_all, int group_id, String videoPath,
			String title, String description) {
		String jsonStr = CommonUtil.uploadVideo(upload_video_to_all, videoPath, title, description);
		JSONObject json = new JSONObject(jsonStr);
		String media = json.getString("media_id");
		if (media == null || media.length() < 1) {
			return false;
		}
		return massVideo2All(is_to_all, group_id, media);
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
		MassCard massCard = new MassCard();
		massCard.setFilter(getFilter(is_to_all, group_id));
		massCard.setMsgtype("wxcard");
		massCard.setWxcard(getMassWXCard(card_id));
		outputStr = gson.toJson(massCard);
		return sendMessage(mass_msg_url, POST, outputStr);
	}

	/**
	 * 通过用户列表群发*图文*消息
	 * @param touser		用户列表
	 * @param media_id		图文消息id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massMPNews2List(List<String> touser, String media_id) {
		MassMPNews2List list = new MassMPNews2List();
		list.setTouser(touser);
		list.setMsgtype("mpnews");
		list.setMpnews(getMedia(media_id));
		outputStr = gson.toJson(list);
		return sendMessage(mass_msg_list_url, POST, outputStr);
	}

	/**
	 * 通过用户列表群发*文本*消息
	 * @param touser		用户列表
	 * @param content		文本内容	
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massText2List(List<String> touser, String content) {
		MassText2List list = new MassText2List();
		list.setText(geText(content));
		list.setMsgtype("text");
		list.setTouser(touser);
		outputStr = gson.toJson(list);
		return sendMessage(mass_msg_list_url, POST, outputStr);
	}

	/**
	 * 通过用户列表群发*语音*消息
	 * @param touser		用户列表
	 * @param media_id		语音id	
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massVoice2List(List<String> touser, String media_id) {
		MassVoice2List list = new MassVoice2List();
		list.setTouser(touser);
		list.setMsgtype("voice");
		list.setVoice(getMedia(media_id));
		outputStr = gson.toJson(list);
		return sendMessage(mass_msg_list_url, POST, outputStr);
	}

	/**
	 * 通过用户列表群发*图片*消息
	 * @param touser		用户列表
	 * @param media_id		图片id	
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massImage2List(List<String> touser, String media_id) {
		MassImage2List list = new MassImage2List();
		list.setTouser(touser);
		list.setMsgtype("image");
		list.setImage(getMedia(media_id));
		outputStr = gson.toJson(list);
		return sendMessage(mass_msg_list_url, POST, outputStr);
	}

	/**
	 * 通过用户列表群发*视频*消息
	 * @param touser		用户列表
	 * @param videoPath		视频文件位置
	 * @param title			视频的标题
	 * @param description	视频的描述
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massVideo2List(List<String> touser, String videoPath, String title,
			String description) {
		MassVideo2List list = new MassVideo2List();
		list.setTouser(touser);
		String jsonStr = CommonUtil.uploadVideo(upload_video_to_all, videoPath, title, description);
		JSONObject json = new JSONObject(jsonStr);
		String media_id = json.getString("media_id");
		if (media_id == null || media_id.length() < 1) {
			list.setVideo(getVideo(media_id, title, description));
		} else {
			try {
				throw new Exception("上传文件失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		list.setVideo(getVideo(media_id, title, description));
		list.setMsgtype("video");
		outputStr = gson.toJson(list);
		return sendMessage(mass_msg_list_url, POST, outputStr);
	}

	/**
	 * 通过用户列表群发*卡卷*消息
	 * @param touser		用户列表
	 * @param card_id		卡卷的id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean massWXCard2List(List<String> touser, String card_id) {
		MassCard2List list = new MassCard2List();
		list.setTouser(touser);
		list.setMsgtype("wxcard");
		list.setWxcard(getMassWXCard(card_id));
		outputStr = gson.toJson(list);
		return sendMessage(mass_msg_list_url, POST, outputStr);
	}

	/**
	 * 删除一条群发消息
	 * @param msg_id	群发消息的内容
	 * @return			成回TRUE，失败FALSE
	 */
	public boolean deleteMassMsg(String msg_id) {
		DeleteMass mass = new DeleteMass();
		mass.setMsg_id(msg_id);
		outputStr = gson.toJson(mass);
		return sendMessage(del_mass_msg_url, POST, outputStr);
	}

	/**
	 * 预览群发的*图文*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param media_id		用于群发的*图文*消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassMPNews(String touser, String towxname, String media_id) {
		PreviewMPNews preview = new PreviewMPNews();
		preview.setTouser(touser);
		preview.setTowxname(towxname);
		preview.setMpnews(getMedia(media_id));
		preview.setMsgtype("mpnews");
		outputStr = gson.toJson(preview);
		return sendMessage(prev_mass_msg_url, POST, outputStr);
	}

	/**
	 * 预览群发的*文本*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param content		文本消息内容
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassText(String touser, String towxname, String content) {
		PreviewText pre = new PreviewText();
		pre.setTouser(touser);
		pre.setTowxname(towxname);
		pre.setText(geText(content));
		pre.setMsgtype("text");
		outputStr = gson.toJson(pre);
		return sendMessage(prev_mass_msg_url, POST, outputStr);
	}

	/**
	 * 预览群发的*语音*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param media_id		用于群发的*语音*消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassVoice(String touser, String towxname, String media_id) {
		PreviewVoice pre = new PreviewVoice();
		pre.setTouser(touser);
		pre.setTowxname(towxname);
		pre.setVoice(getMedia(media_id));
		pre.setMsgtype("voice");
		outputStr = gson.toJson(pre);
		return sendMessage(prev_mass_msg_url, POST, outputStr);
	}

	/**
	 * 预览群发的*图片*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param media_id		用于群发的*图片*消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassImage(String touser, String towxname, String media_id) {
		PreviewImage pre = new PreviewImage();
		pre.setTouser(touser);
		pre.setTowxname(towxname);
		pre.setImage(getMedia(media_id));
		pre.setMsgtype("image");
		outputStr = gson.toJson(pre);
		return sendMessage(prev_mass_msg_url, POST, outputStr);
	}

	/**
	 * 预览群发的*视频*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param media_id		用于群发的*视频*消息的media_id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassVideo(String touser, String towxname, String media_id) {
		PreviewVideo pre = new PreviewVideo();
		pre.setTouser(touser);
		pre.setTowxname(towxname);
		pre.setMpvideo(getMedia(media_id));
		pre.setMsgtype("mpvideo");
		outputStr = gson.toJson(pre);
		return sendMessage(prev_mass_msg_url, POST, outputStr);
	}

	/**
	 * 预览群发的*卡卷*消息
	 * @param touser		接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
	 * @param towxname		查看预览用户的微信号
	 * @param wxcard		kf.WXCard实体
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean previewMassCard(String touser, String towxname, WXCard wxcard) {
		PreviewCard pre = new PreviewCard();
		pre.setTouser(touser);
		pre.setTowxname(towxname);
		pre.setWxcard(wxcard);
		pre.setMsgtype("wxcard");
		outputStr = gson.toJson(pre);
		return sendMessage(prev_mass_msg_url, POST, outputStr);
	}

	/**
	 * 设置行业信息
	 * @param industry_id1		公众号模板消息所属行业编号
	 * @param industry_id2		公众号模板消息所属行业编号
	 * @return					成回TRUE，失败FALSE
	 */
	public boolean setIndustry(int industry_id1, int industry_id2) {
		SetIndustry industry = new SetIndustry();
		industry.setIndustry_id1(industry_id1);
		industry.setIndustry_id2(industry_id2);
		outputStr = gson.toJson(industry);
		return sendMessage(set_industry_url, POST, outputStr);
	}

	/**
	 * 获取行业信息
	 * @return		行业信息的字符串
	 */
	public String getIndustry() {
		return CommonUtil.httpsRequest(get_industry_url, GET, null);
	}

	/**
	 * 获取模板id
	 * @param template_id_short		模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return						成回TRUE，失败FALSE
	 */
	public String getTemplateId(String template_id_short) {
		GetTemplateId templateId = new GetTemplateId();
		templateId.setTemplate_id_short(template_id_short);
		outputStr = gson.toJson(templateId);
		String backJson = CommonUtil.httpsRequest(get_template_id_url, POST, outputStr);
		JSONObject json = new JSONObject(backJson);
		return json.getString("template_id");
	}

	/**
	 * 获取模板列表
	 * @return	成回TRUE，失败FALSE
	 */
	public TemplateList getTemplateList() {
		String backJson = CommonUtil.httpsRequest(get_template_list_url, GET, null);
		TemplateList list = gson.fromJson(backJson, TemplateList.class);
		return list;
	}

	/**
	 * 删除模板
	 * @param template_id	模板的id
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean deleteTemplate(String template_id) {
		DeleteTemplate template = new DeleteTemplate();
		template.setTemplate_id(template_id);
		outputStr = gson.toJson(template);
		return sendMessage(del_template_url, POST, outputStr);
	}

	/**
	 * 发送模板消息
	 * @param touser		发送对象的id
	 * @param template_id	模板id
	 * @param url			模板点击后转跳的url
	 * @param data			模板数据，key value形式
	 * @return				成回TRUE，失败FALSE
	 */
	public boolean sendTemplateMsg(String touser, String template_id, String url,
			TemplateData data) {
		SendTemplate template = new SendTemplate();
		template.setTouser(touser);
		template.setTemplate_id(template_id);
		template.setUrl(url);
		template.setData(data);
		outputStr = gson.toJson(template);
		return sendMessage(send_temp_msg_url, POST, outputStr);
	}
	
	/**
	 * 上传图片并换行图片的url
	 * @param path		图片本地位置
	 * @return			图片的url
	 */
	public String uploadImage(String path) {
		String jsonStr = null;
		try {
			jsonStr = CommonUtil.upload(upload_image_return_url, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(jsonStr);
		return json.getString("url");
	}
	
	/**
	 * 上传视频，并返回视频的Media_id
	 * @param reqUrl		请求地址
	 * @param path			视频的本地位置
	 * @param title			视频的标题
	 * @param description	视频的描述
	 * @return				视频的Media_id
	 */
	public String uploadVideo(String reqUrl, String path, String title,
			String description) {
		String jsonStr = null;
		jsonStr = CommonUtil.uploadVideo(reqUrl, path, title, description);
		JSONObject json = new JSONObject(jsonStr);
		return json.getString("media_id");
	}
	
	/**
	 * 获取素材列表
	 * @param type		素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset	从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count		返回素材的数量，取值在1到20之间
	 * @return			素材列表字符串
	 */
	public String getMaterialList(String type, int offset, int count) {
		String post = "{\"type\":\"" + type + "\",\"offset\":" + offset + ",\"count\":" + count + "}";
		String backStr = CommonUtil.httpsRequest(batch_get_material, POST, post);
		return backStr;
	}

	/**
	 * 获取群发的Filter对象
	 * @param is_to_all		用于设定是否向全部用户发送，值为true或false，<br>
	 * &emsp;&emsp;&emsp;&emsp;选择true该消息群发给所有用户，选择false可根据group_id发送给指定群组的用户
	 * @param group_id		群发到的分组的group_id，参加用户管理中用户分组接口，<Br>
	 * &emsp; &emsp;&emsp;&emsp; 若is_to_all值为true，可不填写group_id	
	 * @return				Filter对象.
	 */
	private Filter getFilter(boolean is_to_all, int group_id) {
		Filter filter = new Filter();
		filter.setIs_to_all(is_to_all);
		filter.setGroup_id(group_id);
		return filter;
	}

	/**
	 * 获取Media对象
	 * @param media_id		用于群发的消息的media_id
	 * @return				Media对象
	 */
	private Media getMedia(String media_id) {
		Media media = new Media();
		media.setMedia_id(media_id);
		return media;
	}

	/**
	 * 主动发送消息
	 * @param url		消息发送的地址
	 * @param method	发送消息的方法POST,GET
	 * @param output	发送消息的内容
	 * @return			成回TRUE，失败FALSE
	 */
	private boolean sendMessage(String url, String method, String output) {
		String backJson = CommonUtil.httpsRequest(url, method, output);
		JSONObject json = new JSONObject(backJson);
		if (json.getInt("errcode") == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取Text对象
	 * @param content	文本消息内容
	 * @return			Text对象
	 */
	private Text geText(String content) {
		Text text = new Text();
		text.setContent(content);
		return text;
	}

	/**
	 * 获取群发卡卷实体
	 * @param card_id	卡卷的card_id
	 * @return			mass.WXCard实体
	 */
	private com.wechat.pojo.msg.send.mass.WXCard getMassWXCard(String card_id) {
		com.wechat.pojo.msg.send.mass.WXCard card = new com.wechat.pojo.msg.send.mass.WXCard();
		card.setCard_id(card_id);
		return card;
	}

	/**
	 * 获取通过list群发视频的视频实体
	 * @param media_id			用于群发的消息的media_id
	 * @param title				视频标题
	 * @param description		视频描述
	 * @return					UploadVideo实体
	 */
	private UploadVideo getVideo(String media_id, String title, String description) {
		UploadVideo video = new UploadVideo();
		video.setMedia_id(media_id);
		video.setTitle(title);
		video.setDescription(description);
		return video;
	}
}
