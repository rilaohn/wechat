package com.wechat.utils;

import java.util.UUID;

import com.wechat.mp.aes.AesException;
import com.wechat.mp.aes.WXBizMsgCrypt;
import com.wechat.pojo.token.Token;

public class WechatAESUtil {

	private String TOKEN;
	private String ENCODINGAESKEY;
	private String APPID;
	private WXBizMsgCrypt crypt;

	/**
	 * 初始化
	 * @param token		com.wechat.pojo.token.Token对象
	 * @param appid		应用唯一id
	 */
	public WechatAESUtil(Token token, String appid) {
		this.APPID = appid;
		this.TOKEN = token.getToken();
		this.ENCODINGAESKEY = token.getEncodingAESKey();
		init();
	}

	/**
	 * 解密消息
	 * 
	 * @param msgSignature	加密签名串
	 * @param timeStamp		时间戳
	 * @param nonce			随机串
	 * @param encryptMsg	密文
	 * @return 				返回解密文本
	 */
	public String decryptMsg(String msgSignature, String timeStamp, String nonce,
			String encryptMsg) {
		String result = "";
		try {
			result = crypt.DecryptMsg(msgSignature, timeStamp, nonce, encryptMsg);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 加密消息
	 * @param replyMsg		需要加密的字符串
	 * @return				加密后的字符串
	 */
	public String ecryptMsg(String replyMsg) {
		String result = "";
		try {
			result = crypt.EncryptMsg(replyMsg,
					Long.toString(System.currentTimeMillis() / 1000),
					UUID.randomUUID().toString());
		} catch (AesException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 验证URL
	 * @param msgSignature	签名串，对应URL参数的msg_signature
	 * @param timeStamp		时间戳，对应URL参数的timestamp
	 * @param nonce			随机串，对应URL参数的nonce
	 * @param echoStr		随机串，对应URL参数的echostr
	 * @return				解密之后的echostr
	 */
	public String verifyURL(String msgSignature, String timeStamp, String nonce,
			String echoStr) {
		String result = "";
		try {
			result = crypt.VerifyURL(msgSignature, timeStamp, nonce, echoStr);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void init() {
		if (crypt == null) {
			try {
				crypt = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			} catch (AesException e) {
				e.printStackTrace();
			}
		}
	}
}
