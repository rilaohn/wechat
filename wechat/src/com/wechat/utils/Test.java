package com.wechat.utils;

import java.util.*;

import com.wechat.pay.pojo.SignType;
import com.wechat.pojo.token.InitData;
import com.wechat.pojo.token.TokenAndTicket;
import com.wechat.thread.TokenTicketThread;

/**
 * 类名：Test.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月12日 下午2:35:01 <br>
 * 发布版本：V <br>
 */
public class Test {
	private static String appId = "wxe5e79628254bb7f7";
	private static String secret = "7e6f8fb31f2af6af97b5f3bff9bd8a95";
	private static String token = "EmergencyUnlockingPlatformToken";
	static Map<String, Data> datas;
	static class Aaa {
		long a = 5200;
	}

	public static void main(String[] args) {
//		boolean data1 = false;
//		boolean data2 = false;
//		if (data2 == data1){
//			System.err.println("true");
//		} else {
//			System.err.println("false");
//		}
//		TestThreadInner inner = new TestThreadInner();
//		inner.printStr();
//		inner = null;
//		long a = 123456789;
//		System.out.println(a / 1000);
//		Aaa aa = new Aaa();
//		String lll = "456";
//		if (aa == null || aa.a - 5000 > 180)
//			lll = "46589";
//		System.out.println(lll);
//		String a = null;
//		if (a == null){
//		}
//		Map<String, String> map = new HashMap<>();
//		map.put("key", " ");
//		map.put("body", "5555");
//		map.put("sign_type", " HMACSHA256 ");
//		System.out.println(map.get("body"));
//		map.put("body", "6666");
//		System.out.println(map.get("body"));
//		map.put("body", "6666");
//		System.out.println(map.get("body"));
//		if (map.containsKey("sign_type") && map.get("sign_type").trim().equals(SignType.HMACSHA256.toString())){
//			System.out.println("gogogogogo");
//		} else
//			System.out.println("hahahahaha");
//		int i = 0;
//		if (false && ++i > 0)
//			System.out.println("true: " + i);
//		else
//			System.out.println("false: " + i);
//		map.put("detail", "{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1," +
//				" \"price\":528800, \"goods_category\":\"123456\", \"body\":\"苹果手机\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", " +
//				"\"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"苹果手机\" }");
//		String str = null;
//		try {
//			str = WXPayUtil.mapToXml(map);
//			System.out.println(str);
//			String str2 = "<xml>\n" +
//					"   <appid>wx2421b1c4370ec43b</appid>\n" +
//					"   <attach>支付测试</attach>\n" +
//					"   <body>JSAPI支付测试</body>\n" +
//					"   <mch_id>10000100</mch_id>\n" +
//					"   <detail><![CDATA[{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", " +
//					"\"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"苹果手机\" }, { \"goods_id\":\"iphone6s_32G\", " +
//					"\"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", " +
//					"\"body\":\"苹果手机\" } ] }]]></detail>\n" +
//					"   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>\n" +
//					"   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>\n" +
//					"   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>\n" +
//					"   <out_trade_no>1415659990</out_trade_no>\n" +
//					"   <spbill_create_ip>14.23.150.211</spbill_create_ip>\n" +
//					"   <total_fee>1</total_fee>\n" +
//					"   <trade_type>JSAPI</trade_type>\n" +
//					"   <sign>0CB01533B8C1EF103065174F50BCA001</sign>\n" +
//					"</xml>";
//			Map<String, String> map1 = WXPayUtil.xmlToMap(str2);
//			System.out.println();
//			System.out.println(map1.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Result result = CodeUtil.decode("C:\\Users\\admin\\Desktop\\qrcodetest\\ha.jpg");
//		System.out.println(result.toString());


//		// 沙盒
//		String url = "https://openauth.alipaydev.com/oauth2/publicAppAuthorize.htm?app_id=2016081600256167&scope=auth_base&redirect_uri=http://lrc.natappvip.cc/social-service/h5/alicityunlock.html";
//		CodeUtil.getQRCode(url, "jpg", "C:\\Users\\admin\\Desktop\\qrcodetest\\alicityunlock.jpg", 512, 512, 0);
//		// 应急开锁 城市服务
//		String url = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2017082408355245&scope=auth_base&redirect_uri=http://lrc.natappvip.cc/social-service/h5/alicityunlock.html";
//		CodeUtil.getQRCode(url, "jpg", "C:\\Users\\admin\\Desktop\\qrcodetest\\alicityunlockNotSandBox.jpg", 512, 512, 0);
		// 急开锁 生活号
		String url = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2017081708236463&scope=auth_base&redirect_uri=http://lrc.natappvip.cc/social-service/h5/alicityunlock.html";
		CodeUtil.getQRCode(url, "jpg", "C:\\Users\\admin\\Desktop\\qrcodetest\\alicityunlockLife.jpg", 512, 512, 0);


//		System.out.println(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
//		TestUtil util = new TestUtil(appId, secret, false, token, "", false);
//		String rUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe5e79628254bb7f7&redirect_uri=http://lrc.natappvip.cc/social-service/h5/unlock.html&response_type=code&scope=snsapi_userinfo&state=unlock#wechat_redirect";
//		
//		ShortUrl url = null;
//		try {
//			String uurl = new String(rUrl.getBytes(), "UTF-8");
//			url = util.getShortUrl(uurl);
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		System.out.println(url.getShort_url());
//		util.getAndDownEternalQrcode(url.getShort_url(), "C:\\Users\\admin\\Desktop\\qrcodetest\\redirectUnlock");

	}
}
