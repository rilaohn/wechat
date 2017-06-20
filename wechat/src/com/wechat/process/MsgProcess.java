package com.wechat.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wechat.pojo.msg.event.BaseEvent;
import com.wechat.pojo.msg.event.LocationEvent;
import com.wechat.pojo.msg.event.MassEventStatusBack;
import com.wechat.pojo.msg.event.QRCodeEvent;
import com.wechat.pojo.msg.event.SubscribeEvent;
import com.wechat.pojo.msg.event.TemplateStatusBack;
import com.wechat.pojo.msg.req.BaseMessage;
import com.wechat.pojo.msg.req.ClickEvent;
import com.wechat.pojo.msg.req.EventMessage;
import com.wechat.pojo.msg.req.ImageMessage;
import com.wechat.pojo.msg.req.LinkMessage;
import com.wechat.pojo.msg.req.LocationInfo;
import com.wechat.pojo.msg.req.LocationMessage;
import com.wechat.pojo.msg.req.LocationSelectEvent;
import com.wechat.pojo.msg.req.OriginMessage;
import com.wechat.pojo.msg.req.PictureEvent;
import com.wechat.pojo.msg.req.PictureList;
import com.wechat.pojo.msg.req.ScanCodeInformarion;
import com.wechat.pojo.msg.req.ScanPushOrWaitmsg;
import com.wechat.pojo.msg.req.SendPicsInformation;
import com.wechat.pojo.msg.req.TextMessage;
import com.wechat.pojo.msg.req.VideoMessage;
import com.wechat.pojo.msg.req.ViewEvent;
import com.wechat.pojo.msg.req.VoiceMessage;
import com.wechat.pojo.msg.resp.Article;
import com.wechat.pojo.msg.resp.BaseMsg;
import com.wechat.pojo.msg.resp.Image;
import com.wechat.pojo.msg.resp.ImageMsg;
import com.wechat.pojo.msg.resp.Music;
import com.wechat.pojo.msg.resp.MusicMsg;
import com.wechat.pojo.msg.resp.NewsMsg;
import com.wechat.pojo.msg.resp.TextMsg;
import com.wechat.pojo.msg.resp.Video;
import com.wechat.pojo.msg.resp.VideoMsg;
import com.wechat.pojo.msg.resp.Voice;
import com.wechat.pojo.msg.resp.VoiceMsg;
import com.wechat.pojo.token.WechatData;
import com.wechat.utils.MessageUtil;
import com.wechat.utils.WechatAESUtil;

/**
 * 类名: MsgProcess <br>
 * 描述: 消息处理服务类 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.0 <br>
 */
public class MsgProcess {

	private MessageUtil messageUtil;
	private String respContent;
	private Map<String, String> requestMap;
	private String reqMessageType;
	private String respXml;
	private WechatData data;
	private WechatAESUtil aesUtil;

	public MsgProcess(WechatData data) {
		this.data = data;
		messageUtil = new MessageUtil(this.data);
		if (data.getToken().isEncrypt()) {
			aesUtil = new WechatAESUtil(data.getToken(), data.getInitData().getId());
		}
		reqMessageType = null;
	}

	public void setRequest(HttpServletRequest request) {
		try {
			requestMap = messageUtil.parseXml(request);
//			System.out.println("+++++++" + requestMap);
			getUserMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MsgProcess(WechatData data, HttpServletRequest request) {
		this(data);
		try {
			requestMap = messageUtil.parseXml(request);
			getUserMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 回复文本消息
	 * @param content	文本消息内容
	 * @return respXml xml格式文本消息字符串
	 */
	public String replyText(String content) {

		// xml格式的消息数据
		respXml = null;

		// 默认返回的文本消息内容
		if (content == null) {
			respContent = "很高兴为您服务！";
		} else {
			respContent = content;
		}

		TextMsg msg = (TextMsg) backBaseMsg(new TextMsg(), MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		msg.setContent(respContent);
		// 将文本消息对象转换成xml
		respXml = messageUtil.messageToXml(msg);

		return replay(respXml);
	}

	/**
	 * 回复图片消息
	 * @param image		图片消息内容 Image对象
	 * @return respXml xml格式图片消息字符串
	 */
	public String replyImage(Image image) {

		// xml格式的消息数据
		respXml = null;

		ImageMsg msg = (ImageMsg) backBaseMsg(new ImageMsg(),
				MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
		msg.setImage(image);
		// 将文本消息对象转换成xml
		respXml = messageUtil.messageToXml(msg);

		return replay(respXml);
	}

	/**
	 * 回复语音消息
	 * @param voice		语音消息内容 Voice对象
	 * @return respXml xml格式语音消息字符串
	 */
	public String replyVoice(Voice voice) {

		// xml格式的消息数据
		respXml = null;

		VoiceMsg msg = (VoiceMsg) backBaseMsg(new VoiceMsg(),
				MessageUtil.RESP_MESSAGE_TYPE_VOICE);
		msg.setVoice(voice);
		// 将文本消息对象转换成xml
		respXml = messageUtil.messageToXml(msg);

		return replay(respXml);
	}

	/**
	 * 回复视频消息
	 * @param video		视频消息内容 Video对象
	 * @return respXml xml格式视频消息字符串
	 */
	public String replyVideo(Video video) {

		// xml格式的消息数据
		respXml = null;

		VideoMsg msg = (VideoMsg) backBaseMsg(new VideoMsg(),
				MessageUtil.RESP_MESSAGE_TYPE_VIDEO);
		msg.setVideo(video);
		// 将文本消息对象转换成xml
		respXml = messageUtil.messageToXml(msg);

		return replay(respXml);
	}

	/**
	 * 回复音乐消息
	 * @param music		音乐消息内容 Music对象
	 * @return respXml xml格式音乐消息字符串
	 */
	public String replyMusic(Music music) {

		// xml格式的消息数据
		respXml = null;

		MusicMsg msg = (MusicMsg) backBaseMsg(new MusicMsg(),
				MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
		msg.setMusic(music);
		// 将文本消息对象转换成xml
		respXml = messageUtil.messageToXml(msg);

		return replay(respXml);
	}

	/**
	 * 回复图文消息
	 * @param articles	图文消息内容 Article对象集合
	 * @return respXml xml格式图文消息字符串
	 */
	public String replyNews(List<Article> articles) {

		// xml格式的消息数据
		respXml = null;

		NewsMsg msg = (NewsMsg) backBaseMsg(new NewsMsg(), MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		msg.setArticleCount(articles.size());
		msg.setArticles(articles);
		// 将文本消息对象转换成xml
		respXml = messageUtil.messageToXml(msg);

		return replay(respXml);
	}

	/**
	 * 获取用户发送的消息
	 * 
	 * @return OriginMessage 各种消息向上转型后的实体 具体类容需要向下转型 可以通过
	 *         getRequestMessageType() 方法获取具体的类型
	 */
	public OriginMessage getUserMessage() {
		if (requestMap == null) {
			return null;
		}
		
//		System.out.println("--**--" + requestMap);
		// 消息类型
		String msgType = requestMap.get("MsgType");
		if (msgType == null || msgType.length() < 1) {
			try {
				throw new Exception("MsgType为空，消息中没有MsgType字段");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		reqMessageType = null;

		// 事件推送
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = requestMap.get("Event");
			// 关注
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				SubscribeEvent event = (SubscribeEvent) backBaseEvent(new SubscribeEvent());
				reqMessageType = event.toString().split("@")[0];
				return event;
			}
			// 取消关注
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				SubscribeEvent event = (SubscribeEvent) backBaseEvent(new SubscribeEvent());
				reqMessageType = event.toString().split("@")[0];
				return event;
			}
			// 点击事件
			else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
				ClickEvent event = (ClickEvent) backEvent(new ClickEvent());
				reqMessageType = event.toString().split("@")[0];
				return event;
			}

			// 点击菜单跳转链接时的事件推送
			else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
				// 在这里写__用户点击__后要处理的逻辑
				ViewEvent event = (ViewEvent) backEvent(new ViewEvent());
				event.setMenuID(requestMap.get("MenuID"));
				reqMessageType = event.toString().split("@")[0];
				return event;
			}

			// 扫描带参数二维码
			else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
				QRCodeEvent event = (QRCodeEvent) backBaseEvent(new QRCodeEvent());
				event.setEventKey(requestMap.get("EventKey"));
				event.setTicket(requestMap.get("Ticket"));
				reqMessageType = event.toString().split("@")[0];
				return event;
			}

			// 扫描带参数二维码推送事件
			else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN_PUSH)) {
				ScanPushOrWaitmsg event = (ScanPushOrWaitmsg) backEvent(
						new ScanPushOrWaitmsg());
				ScanCodeInformarion scanCodeInfo = new ScanCodeInformarion();
				scanCodeInfo.setScanType(requestMap.get("ScanType"));
				scanCodeInfo.setScanResult(requestMap.get("ScanResult"));

				event.setScanCodeInfo(scanCodeInfo);
				reqMessageType = event.toString().split("@")[0];
				return event;
			}

			// 扫描带参数二维码 带提示
			else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN_WAIT)) {
				ScanPushOrWaitmsg event = (ScanPushOrWaitmsg) backEvent(
						new ScanPushOrWaitmsg());
				ScanCodeInformarion scanCodeInfo = new ScanCodeInformarion();
				scanCodeInfo.setScanType(requestMap.get("ScanType"));
				scanCodeInfo.setScanResult(requestMap.get("ScanResult"));

				event.setScanCodeInfo(scanCodeInfo);
				reqMessageType = event.toString().split("@")[0];
				return event;
			}

			// 弹出系统拍照发图的事件推送
			else if (eventType.equals(MessageUtil.EVENT_TYPE_PIC_SYSPHOTO)) {
				PictureEvent event = backPictureEvent();
				reqMessageType = event.toString().split("@")[0];
				return event;

			}

			// 弹出拍照或者相册发图的事件推送
			else if (eventType.equals(MessageUtil.EVENT_TYPE_PIC_PHOTO_OR_ALBUM)) {
				PictureEvent event = backPictureEvent();
				reqMessageType = event.toString().split("@")[0];
				return event;

			}

			// 弹出微信相册发图器的事件推送
			else if (eventType.equals(MessageUtil.EVENT_TYPE_PIC_WEIXIN)) {
				PictureEvent event = backPictureEvent();
				reqMessageType = event.toString().split("@")[0];
				return event;

			}

			// 上报地理位置
			else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
				LocationEvent event = (LocationEvent) backBaseEvent(new LocationEvent());

				event.setLatitude(requestMap.get("Latitude"));
				event.setLongitude(requestMap.get("Longitude"));
				event.setPrecision(requestMap.get("Precision"));
				reqMessageType = event.toString().split("@")[0];
				return event;
			}
			// 地理位置选择事件
			else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION_SELECT)) {
				LocationSelectEvent event = (LocationSelectEvent) backEvent(
						new LocationSelectEvent());
				LocationInfo sendLocationInfo = new LocationInfo();

				sendLocationInfo.setLocation_X(requestMap.get("Location_X"));
				sendLocationInfo.setLocation_Y(requestMap.get("Location_Y"));
				sendLocationInfo.setScale(requestMap.get("Scale"));
				sendLocationInfo.setLabel(requestMap.get("Label"));
				sendLocationInfo.setPoiname(requestMap.get("Poiname"));

				event.setSendLocationInfo(sendLocationInfo);
				reqMessageType = event.toString().split("@")[0];
				return event;
			}

			// 事件推送群发结果
			else if (eventType.equals(MessageUtil.EVENT_TYPE_MASS_SEND_JOB_FINISH)) {
				MassEventStatusBack event = (MassEventStatusBack) backBaseEvent(
						new MassEventStatusBack());
				event.setMsgID(Long.parseLong(requestMap.get("MsgID")));
				event.setStatus(requestMap.get("Status"));
				event.setTotalCount(Long.parseLong(requestMap.get("TotalCount")));
				event.setFilterCount(Long.parseLong(requestMap.get("FilterCount")));
				event.setSentCount(Long.parseLong(requestMap.get("SentCount")));
				event.setErrorCount(Long.parseLong(requestMap.get("ErrorCount")));

				return event;
			}

			// 模板消息返回结果
			else if (eventType.equals(MessageUtil.EVENT_TYPE_TEMPLATE_SEND_JOB_FINISH)) {
				TemplateStatusBack event = (TemplateStatusBack) backBaseEvent(
						new TemplateStatusBack());
				event.setMsgID(Long.parseLong(requestMap.get("MsgID")));
				event.setStatus(requestMap.get("Status"));

				return event;
			}
		} else {

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				TextMessage message = (TextMessage) backMessage(new TextMessage());

				message.setContent(requestMap.get("Content"));
				reqMessageType = message.toString().split("@")[0];
				return message;
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				ImageMessage message = (ImageMessage) backMessage(new ImageMessage());

				message.setMediaId(requestMap.get("MediaId"));
				message.setPicUrl(requestMap.get("PicUrl"));
				reqMessageType = message.toString().split("@")[0];
				return message;
			}
			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				VoiceMessage message = (VoiceMessage) backMessage(new VoiceMessage());

				message.setMediaId(requestMap.get("MediaId"));
				message.setRecognition(requestMap.get("Recognition"));
				message.setFormat(requestMap.get("Format"));
				reqMessageType = message.toString().split("@")[0];
				return message;
			}
			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				VideoMessage message = (VideoMessage) backMessage(new VideoMessage());

				message.setMediaId(requestMap.get("MediaId"));
				message.setThumbMediaId(requestMap.get("ThumbMediaId"));
				reqMessageType = message.toString().split("@")[0];
				return message;
			}
			// 短视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
				VideoMessage message = (VideoMessage) backMessage(new VideoMessage());

				message.setMediaId(requestMap.get("MediaId"));
				message.setThumbMediaId(requestMap.get("ThumbMediaId"));
				reqMessageType = message.toString().split("@")[0];
				return message;
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				LocationMessage message = (LocationMessage) backMessage(new LocationMessage());

				message.setLocation_X(requestMap.get("Location_X"));
				message.setLocation_Y(requestMap.get("Location_Y"));
				message.setScale(requestMap.get("Scale"));
				message.setLabel(requestMap.get("Label"));
				reqMessageType = message.toString().split("@")[0];
				return message;
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				LinkMessage message = (LinkMessage) backMessage(new LinkMessage());

				message.setTitle(requestMap.get("Title"));
				message.setDescription(requestMap.get("Description"));
				message.setUrl(requestMap.get("Url"));
				reqMessageType = message.toString().split("@")[0];
				return message;
			}
		}
		return null;
	}

	public BaseMessage backMessage(BaseMessage message) {
		if (requestMap.containsKey("AgentID")) {
			message.setAgentID(Integer.parseInt(requestMap.get("AgentID")));
		}
		message.setFromUserName(requestMap.get("FromUserName"));
		message.setToUserName(requestMap.get("ToUserName"));
		message.setMsgType(requestMap.get("MsgType"));
		message.setCreateTime(Long.parseLong(requestMap.get("CreateTime")));
		message.setMsgId(Long.parseLong(requestMap.get("MsgId")));

		return message;
	}

	private BaseMsg backBaseMsg(BaseMsg msg, String msgType) {
		msg.setFromUserName(requestMap.get("ToUserName"));
		msg.setToUserName(requestMap.get("FromUserName"));
		msg.setMsgType(msgType);
		msg.setCreateTime(new Date().getTime());
		return msg;
	}

	private BaseEvent backBaseEvent(BaseEvent baseEvent) {
		if (requestMap.containsKey("AgentID")) {
			baseEvent.setAgentID(Integer.parseInt(requestMap.get("AgentID")));
		}
		baseEvent.setFromUserName(requestMap.get("FromUserName"));
		baseEvent.setToUserName(requestMap.get("ToUserName"));
		baseEvent.setMsgType(requestMap.get("MsgType"));
		baseEvent.setCreateTime(Long.parseLong(requestMap.get("CreateTime")));
		baseEvent.setEvent(requestMap.get("Event"));
		return baseEvent;
	}

	private EventMessage backEvent(EventMessage event) {
		if (requestMap.containsKey("AgentID")) {
			event.setAgentID(Integer.parseInt(requestMap.get("AgentID")));
		}
		event.setFromUserName(requestMap.get("FromUserName"));
		event.setToUserName(requestMap.get("ToUserName"));
		event.setMsgType(requestMap.get("MsgType"));
		event.setCreateTime(Long.parseLong(requestMap.get("CreateTime")));
		event.setEvent(requestMap.get("Event"));
		event.setEventKey(requestMap.get("EventKey"));
		return event;
	}

	private PictureEvent backPictureEvent() {
		PictureEvent event = (PictureEvent) backEvent(new PictureEvent());
		SendPicsInformation sendPicsInfo = new SendPicsInformation();
		PictureList picList = new PictureList();
		List<String> picMd5Sum = new ArrayList<String>();
		String picmd50 = requestMap.get("PicMd5Sum");
		picMd5Sum.add(picmd50);
		for (int i = 1; i < 9; i++) {
			String name = "PicMd5Sum" + i;
			if (requestMap.containsKey(name)) {
				String picmd5 = requestMap.get(name);
				picMd5Sum.add(picmd5);
			} else {
				break;
			}
		}
		picList.setPicMd5Sum(picMd5Sum);
		sendPicsInfo.setPicList(picList);
		sendPicsInfo.setCount(Integer.parseInt(requestMap.get("Count")));
		event.setSendPicsInfo(sendPicsInfo);
		return event;
	}

	/**
	 * 获取用户消息的具体类型
	 * @return reqMessageType 用户消息的具体类型
	 */
	public String getRequestMessageType() {
		return reqMessageType;
	}


	public Map<String, String> getMessageMap() {
		return requestMap;
	}
	
	private String replay(String msg) {
		if (data.getToken().isEncrypt()) 
			return aesUtil.ecryptMsg(msg);
		return msg;
	}
}
