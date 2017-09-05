package com.wechat.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wechat.pojo.msg.resp.Article;
import com.wechat.pojo.msg.resp.ImageMsg;
import com.wechat.pojo.msg.resp.MusicMsg;
import com.wechat.pojo.msg.resp.NewsMsg;
import com.wechat.pojo.msg.resp.TextMsg;
import com.wechat.pojo.msg.resp.VideoMsg;
import com.wechat.pojo.msg.resp.VoiceMsg;
import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.Token;
import com.wechat.pojo.token.WechatData;

/**
 * 类名: MessageUtil <br>
 * 描述: 消息处理工具类<br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.0 <br>
 */
public class MessageUtil {

	// 请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	// 请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	// 请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	// 请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	// 请求消息类型：小视频
	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
	// 请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	// 请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	// 请求消息类型：事件推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：scancode_push(扫码推事件的事件推送)
	public static final String EVENT_TYPE_SCAN = "SCAN";
	// 事件类型：scancode_push(扫码推事件的事件推送)
	public static final String EVENT_TYPE_SCAN_PUSH = "scancode_push";
	// 事件类型：scancode_waitmsg(扫码推事件且弹出“消息接收中”提示框的事件推送)
	public static final String EVENT_TYPE_SCAN_WAIT = "scancode_waitmsg";
	// 事件类型：LOCATION(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(自定义菜单)
	public static final String EVENT_TYPE_CLICK = "CLICK";
	// 事件类型：VIEW(点击菜单跳转链接时的事件推送)
	public static final String EVENT_TYPE_VIEW = "VIEW";
	// 事件类型：pic_sysphoto(弹出系统拍照发图的事件推送)
	public static final String EVENT_TYPE_PIC_SYSPHOTO = "pic_sysphoto";
	// 事件类型：pic_photo_or_album(弹出拍照或者相册发图的事件推送)
	public static final String EVENT_TYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
	// 事件类型：pic_weixin(弹出微信相册发图器的事件推送)
	public static final String EVENT_TYPE_PIC_WEIXIN = "pic_weixin";
	// 事件类型：location_select(弹出地理位置选择器的事件推送)
	public static final String EVENT_TYPE_LOCATION_SELECT = "location_select";
	// 事件类型：MASSSENDJOBFINISH(群发消息完成)
	public static final String EVENT_TYPE_MASS_SEND_JOB_FINISH = "MASSSENDJOBFINISH";
	// 事件类型：TEMPLATESENDJOBFINISH(模板消息完成返回)
	public static final String EVENT_TYPE_TEMPLATE_SEND_JOB_FINISH = "TEMPLATESENDJOBFINISH";

	// 响应消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// 响应消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// 响应消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// 响应消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// 响应消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// 响应消息类型：图文
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	private Token token;
	private InitData initData;

	public MessageUtil(WechatData data) {
		this.token = data.getToken();
		this.initData = data.getInitData();
	}

	/**
	 * 解析微信发来的请求（XML）
	 *
	 * @param request HttpServletRequest
	 * @return Map&lt;String, String&gt;对象
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> parseXml(HttpServletRequest request, String stream) {

		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = null;
		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader br = null;
		String encryptType = request.getParameter("encrypt_type");
		try {
			if ((encryptType == null || encryptType.equals("raw")) && (!initData.isCorp())) {
				if (stream == null)
					inputStream = request.getInputStream();
				else
					inputStream = new ByteArrayInputStream(stream.getBytes());
				encryptType = null;
			} else {
				if (stream == null)
					isr = new InputStreamReader(request.getInputStream());
				else
					isr = new InputStreamReader(new ByteArrayInputStream(stream.getBytes()));
				br = new BufferedReader(isr);
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				// 密文
				String encryptMsg = sb.toString();
				// 微信加密签名
				String msgSignature = request.getParameter("msg_signature");
				// 时间戳
				String timeStamp = request.getParameter("timestamp");
				// 随机数
				String nonce = request.getParameter("nonce");
				WechatAESUtil wechatAESUtil = new WechatAESUtil(token, initData.getId());
				String msg = wechatAESUtil.decryptMsg(msgSignature, timeStamp, nonce, encryptMsg);
				if (msg.length() == 0) {
					throw new Exception("密文为空");
				} else {
					inputStream = new ByteArrayInputStream(msg.getBytes("UTF-8"));
				}
			}
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			map = getAllElements(elementList, map);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (!(encryptType == null)) {
					br.close();
					isr.close();
					br = null;
					isr = null;
				}

				inputStream.close();
				inputStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

//		System.out.println("---MessageUtil.map: " + map.toString());

		return map;

	}

	private Map<String, String> getAllElements(List<Element> ele, Map<String, String> map) {
		// 遍历所有子节点
		int i = 1;
		for (Element element : ele) {
			if (!map.containsKey(element.getName())) {
				map.put(element.getName(), element.getText());
			} else {
				map.put(element.getName() + i, element.getText());
				i++;
			}

			if (element.elements().size() > 0) {
				map = getAllElements(element.elements(), map);
			}
		}
		return map;
	}

	/**
	 * 扩展xstream使其支持CDATA
	 */
	private XStream xStream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {

				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 文本消息对象转换成xml
	 *
	 * @param text 文本消息对象
	 * @return xml格式字符串
	 */
	public String messageToXml(TextMsg text) {
		xStream.alias("xml", text.getClass());
		return xStream.toXML(text);
	}

	/**
	 * 图片消息对象转换成xml
	 *
	 * @param text 图片消息对象
	 * @return xml格式字符串
	 */
	public String messageToXml(ImageMsg text) {
		xStream.alias("xml", text.getClass());
		return xStream.toXML(text);
	}

	/**
	 * 语音消息对象转换成xml
	 *
	 * @param text 语音消息对象
	 * @return xml格式字符串
	 */
	public String messageToXml(VoiceMsg text) {
		xStream.alias("xml", text.getClass());
		return xStream.toXML(text);
	}

	/**
	 * 视频消息对象转换成xml
	 *
	 * @param text 视频消息对象
	 * @return xml格式字符串
	 */
	public String messageToXml(VideoMsg text) {
		xStream.alias("xml", text.getClass());
		return xStream.toXML(text);
	}

	/**
	 * 音乐消息对象转换成xml
	 *
	 * @param text 音乐消息对象
	 * @return xml格式字符串
	 */
	public String messageToXml(MusicMsg text) {
		xStream.alias("xml", text.getClass());
		return xStream.toXML(text);
	}

	/**
	 * 图文消息对象转换成xml
	 *
	 * @param text 图文消息对象
	 * @return xml格式字符串
	 */
	public String messageToXml(NewsMsg text) {
		xStream.alias("xml", text.getClass());
		xStream.alias("item", new Article().getClass());
		return xStream.toXML(text);
	}

}
