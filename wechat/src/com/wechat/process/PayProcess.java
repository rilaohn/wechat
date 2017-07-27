package com.wechat.process;

import com.wechat.pay.pojo.SignType;
import com.wechat.pay.pojo.UnifyOrder;
import com.wechat.utils.C;
import com.wechat.utils.CodeUtil;
import com.wechat.utils.CommonUtil;
import com.wechat.utils.WeChatPayUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class PayProcess {

	/**
	 * 获取微信扫码支付模式一url
	 * @param appid			微信分配的公众账号ID
	 * @param mch_id		微信支付分配的商户号
	 * @param product_id	商户定义的商品id 或者订单号
	 * @param payKey		商户自己设置的API安全密钥
	 * @return	微信扫码支付模式一中二维码的url
	 */
	public String getWXScanPay1Url(String appid, String mch_id, String product_id, String payKey) {
		Map<String, String> packageParams = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		try {
			String timeStamp = Long.toString(WeChatPayUtil.getCurrentTimestamp());
			String nonceStr = WeChatPayUtil.generateUUID();
			packageParams.put("appid", appid);
			packageParams.put("mch_id", mch_id);
			packageParams.put("product_id", product_id);
			packageParams.put("time_stamp", timeStamp);
			packageParams.put("nonce_str", nonceStr);
			String sign = WeChatPayUtil.generateSignature(packageParams, payKey);
			sb.append("weixin://wxpay/bizpayurl?sign=");
			sb.append(sign);
			sb.append("&appid=");
			sb.append(appid);
			sb.append("&mch_id=");
			sb.append(mch_id);
			sb.append("&product_id=");
			sb.append(product_id);
			sb.append("&time_stamp=");
			sb.append(timeStamp);
			sb.append("&nonce_str=");
			sb.append(nonceStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public boolean getWXScanPay1UrlQRCodeImage(String appid, String mch_id, String product_id, String payKey, String filePath) {
		String url = getWXScanPay1Url(appid, mch_id, product_id, payKey);
		 boolean ok = CodeUtil.getQRCode(url, "jpg", filePath, 256, 256, 0);
		 return ok;
	}

	/**
	 * 统一下单请求
	 * @param unifyOrder	com.wechat.pay.pojo.UnifyOrder对象
	 * @param key	商户自己设置的API安全密钥
	 * @return 统一下单微信返回的带有prepay_id的Map对象
	 */
	public Map<String, String> reqUnifiedOrder(UnifyOrder unifyOrder, String key) {
		String str;
		Map<String, String> map = null;
		try {
			String postData = getUnifyOrderXMLStr(unifyOrder, key);
			str = CommonUtil.httpsRequest(C.WXPAY_UNIFY_ORDER_URL, "POST", postData);
			map = WeChatPayUtil.xmlToMap(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 统一下单请求
	 * @param paramsMap		含有统一下单需要信息的Map对象
	 * @param key	商户自己设置的API安全密钥
	 * @return 统一下单微信返回的带有prepay_id的Map对象
	 */
	public Map<String, String> reqUnifiedOrder(Map<String, String> paramsMap, String key) {
		Map<String, String> map = null;
		try {
			String postData = getUnifyOrderXMLStr(paramsMap, key);
			String str = CommonUtil.httpsRequest(C.WXPAY_UNIFY_ORDER_URL, "POST", postData);
			map = WeChatPayUtil.xmlToMap(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 把HttpServletRequest对象内容返还的xml的转换为Map对象
	 * @param request	HttpServletRequest请求
	 * @return 把请求返还的xml转换为Map对象
	 */
	public Map<String, String> getXMLRequestMap(HttpServletRequest request) {
		Map<String, String> map = null;
		try {
			map = WeChatPayUtil.xmlToMap(getRequestStr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取请求HttpServletRequest的内容
	 * @param request	HttpServletRequest请求
	 * @return	HttpServletRequest内容字符串
	 */
	public String getRequestStr(HttpServletRequest request) {
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			is = request.getInputStream();
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 获取统一下单的XML字符串
	 * @param unifyOrder	com.wechat.pay.pojo.UnifyOrder对象
	 * @param key	商户自己设置的API安全密钥
	 * @return 统一下单的XML字符串
	 * @throws Exception 必要内容缺失异常
	 */
	public String getUnifyOrderXMLStr(UnifyOrder unifyOrder, String key) throws Exception {
		Map<String, String> prePayMap = new HashMap<String, String>();
		if (unifyOrder == null)
			return null;

		if (unifyOrder.getAppid() == null || unifyOrder.getAppid().trim().length() == 0)
			throw new Exception("unified order appid is null");
		else
			prePayMap.put("appid", unifyOrder.getAppid());

		if (unifyOrder.getMch_id() == null || unifyOrder.getMch_id().trim().length() == 0)
			throw new Exception("unified order mch_id is null");
		else
			prePayMap.put("mch_id", unifyOrder.getMch_id());

		if (unifyOrder.getNonce_str() == null || unifyOrder.getNonce_str().trim().length() == 0)
			throw new Exception("unified order nonce_str is null");
		else
			prePayMap.put("nonce_str", unifyOrder.getNonce_str());

		if (unifyOrder.getBody() == null || unifyOrder.getBody().trim().length() == 0)
			throw new Exception("unified order body is null");
		else
			prePayMap.put("body", unifyOrder.getBody());

		if (unifyOrder.getOut_trade_no() == null || unifyOrder.getOut_trade_no().trim().length() == 0)
			throw new Exception("unified order out_trade_no is null");
		else
			prePayMap.put("out_trade_no", unifyOrder.getOut_trade_no());

		if (unifyOrder.getTotal_fee() == null)
			throw new Exception("unified order total_fee is null");
		else
			prePayMap.put("total_fee", unifyOrder.getTotal_fee().toString());

		if (unifyOrder.getSpbill_create_ip() == null || unifyOrder.getSpbill_create_ip().trim().length() == 0)
			throw new Exception("unified order spbill_create_ip is null");
		else
			prePayMap.put("spbill_create_ip", unifyOrder.getSpbill_create_ip());

		if (unifyOrder.getNotify_url() == null || unifyOrder.getNotify_url().trim().length() == 0)
			throw new Exception("unified order notify_url is null");
		else
			prePayMap.put("notify_url", unifyOrder.getNotify_url());

		if (unifyOrder.getTrade_type() == null || unifyOrder.getTrade_type().trim().length() == 0)
			throw new Exception("unified order trade_type is null");
		else
			prePayMap.put("trade_type", unifyOrder.getTrade_type());

		if (unifyOrder.getDevice_info() != null && unifyOrder.getDevice_info().trim().length() > 0)
			prePayMap.put("device_info", unifyOrder.getDevice_info());

		if (unifyOrder.getSign_type() != null && unifyOrder.getSign_type().trim().length() > 0)
			prePayMap.put("sign_type", unifyOrder.getSign_type());

		if (unifyOrder.getDetail() != null && unifyOrder.getDetail().trim().length() > 0)
			prePayMap.put("detail", unifyOrder.getDetail());

		if (unifyOrder.getAttach() != null && unifyOrder.getAttach().trim().length() > 0)
			prePayMap.put("attach", unifyOrder.getAttach());

		if (unifyOrder.getFee_type() != null && unifyOrder.getFee_type().trim().length() > 0)
			prePayMap.put("fee_type", unifyOrder.getFee_type());

		if (unifyOrder.getTime_start() != null && unifyOrder.getTime_start().trim().length() > 0)
			prePayMap.put("time_start", unifyOrder.getTime_start());

		if (unifyOrder.getTime_expire() != null && unifyOrder.getTime_expire().trim().length() > 0)
			prePayMap.put("time_expire", unifyOrder.getTime_expire());

		if (unifyOrder.getGoods_tag() != null && unifyOrder.getGoods_tag().trim().length() > 0)
			prePayMap.put("goods_tag", unifyOrder.getGoods_tag());

		if (unifyOrder.getProduct_id() != null && unifyOrder.getProduct_id().trim().length() > 0)
			prePayMap.put("product_id", unifyOrder.getProduct_id());

		if (unifyOrder.getLimit_pay() != null && unifyOrder.getLimit_pay().trim().length() > 0)
			prePayMap.put("limit_pay", unifyOrder.getLimit_pay());

		if (unifyOrder.getOpenid() != null && unifyOrder.getOpenid().trim().length() > 0)
			prePayMap.put("openid", unifyOrder.getOpenid());

		if (unifyOrder.getScene_info() != null && unifyOrder.getScene_info().trim().length() > 0)
			prePayMap.put("scene_info", unifyOrder.getScene_info());

		String str = null;
		String sign;
		try {
			if (prePayMap.containsKey("sign_type") && prePayMap.get("sign_type").trim().equals(SignType.HMACSHA256.toString())){
				sign = WeChatPayUtil.generateSignature(prePayMap, key, SignType.HMACSHA256);
			} else
				sign = WeChatPayUtil.generateSignature(prePayMap, key);
			prePayMap.put("sign", sign);
			str = map2XML(prePayMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获取统一下单的XML字符串
	 * @param paramsMap 含有统一下单所需信息的Map对象
	 * @param key	商户自己设置的API安全密钥
	 * @return 统一下单的XML字符串
	 * @throws Exception 必要内容缺失异常
	 */
	public String getUnifyOrderXMLStr(Map<String, String> paramsMap, String key) throws Exception {
		Map<String, String> prePayMap = new HashMap<String, String>();
		if (paramsMap.containsKey("appid") && paramsMap.get("appid").trim().length() > 0)
			prePayMap.put("appid", paramsMap.get("appid"));
		else
			throw new Exception("unified order appid is null!");

		if (paramsMap.containsKey("mch_id") && paramsMap.get("mch_id").trim().length() > 0)
			prePayMap.put("mch_id", paramsMap.get("mch_id"));
		else
			throw new Exception("unified order mch_id is null!");

		if (paramsMap.containsKey("device_info") && paramsMap.get("device_info").trim().length() > 0)
			prePayMap.put("device_info", paramsMap.get("device_info"));

		if (paramsMap.containsKey("nonce_str") && paramsMap.get("nonce_str").trim().length() > 0)
			prePayMap.put("nonce_str", paramsMap.get("nonce_str"));
		else
			throw new Exception("unified order nonce_str is null!");

		if (paramsMap.containsKey("sign_type") && paramsMap.get("sign_type").trim().length() > 0)
			prePayMap.put("sign_type", paramsMap.get("sign_type"));

		if (paramsMap.containsKey("body") && paramsMap.get("body").trim().length() > 0)
			prePayMap.put("body", paramsMap.get("body"));
		else
			throw new Exception("unified order body is null!");

		if (paramsMap.containsKey("detail") && paramsMap.get("detail").trim().length() > 0)
			prePayMap.put("detail", paramsMap.get("detail"));

		if (paramsMap.containsKey("attach") && paramsMap.get("attach").trim().length() > 0)
			prePayMap.put("attach", paramsMap.get("attach"));

		if (paramsMap.containsKey("out_trade_no") && paramsMap.get("out_trade_no").trim().length() > 0)
			prePayMap.put("out_trade_no", paramsMap.get("out_trade_no"));
		else
			throw new Exception("unified order out_trade_no is null!");

		if (paramsMap.containsKey("fee_type") && paramsMap.get("fee_type").trim().length() > 0)
			prePayMap.put("fee_type", paramsMap.get("fee_type"));

		if (paramsMap.containsKey("total_fee") && paramsMap.get("total_fee").trim().length() > 0)
			prePayMap.put("total_fee", paramsMap.get("total_fee"));
		else
			throw new Exception("unified order total_fee is null!");

		if (paramsMap.containsKey("spbill_create_ip") && paramsMap.get("spbill_create_ip").trim().length() > 0)
			prePayMap.put("spbill_create_ip", paramsMap.get("spbill_create_ip"));
		else
			throw new Exception("unified order spbill_create_ip is null!");

		if (paramsMap.containsKey("time_start") && paramsMap.get("time_start").trim().length() > 0)
			prePayMap.put("time_start", paramsMap.get("time_start"));

		if (paramsMap.containsKey("time_expire") && paramsMap.get("time_expire").trim().length() > 0)
			prePayMap.put("time_expire", paramsMap.get("time_expire"));

		if (paramsMap.containsKey("goods_tag") && paramsMap.get("goods_tag").trim().length() > 0)
			prePayMap.put("goods_tag", paramsMap.get("goods_tag"));

		if (paramsMap.containsKey("notify_url") && paramsMap.get("notify_url").trim().length() > 0)
			prePayMap.put("notify_url", paramsMap.get("notify_url"));
		else
			throw new Exception("unified order notify_url is null!");

		if (paramsMap.containsKey("trade_type") && paramsMap.get("trade_type").trim().length() > 0)
			prePayMap.put("trade_type", paramsMap.get("trade_type"));
		else
			throw new Exception("unified order trade_type is null!");

		if (paramsMap.containsKey("product_id") && paramsMap.get("product_id").trim().length() > 0)
			prePayMap.put("product_id", paramsMap.get("product_id"));

		if (paramsMap.containsKey("limit_pay") && paramsMap.get("limit_pay").trim().length() > 0)
			prePayMap.put("limit_pay", paramsMap.get("limit_pay"));

		if (paramsMap.containsKey("openid") && paramsMap.get("openid").trim().length() > 0)
			prePayMap.put("openid", paramsMap.get("openid"));

		if (paramsMap.containsKey("scene_info") && paramsMap.get("scene_info").trim().length() > 0)
			prePayMap.put("scene_info", paramsMap.get("scene_info"));


		String str = null;
		try {
			String sign;
			if (paramsMap.containsKey("sign_type") && paramsMap.get("sign_type").equals(SignType.HMACSHA256.toString())){
				sign = WeChatPayUtil.generateSignature(prePayMap, key, SignType.HMACSHA256);
			} else
				sign = WeChatPayUtil.generateSignature(prePayMap, key);
			prePayMap.put("sign", sign);
			str = map2XML(prePayMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * Map对象转XML字符串（带CDATA）
	 * @param map	Map对象
	 * @return XML字符串
	 */
	public String map2XML(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
//			sb.append("<" + key + "><![CDATA[" + value + "]]></" + key + ">");
			sb.append("<").append(key).append("><![CDATA[").append(value).append("]]></").append(key).append(">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
