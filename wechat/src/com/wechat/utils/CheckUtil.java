package com.wechat.utils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.wechat.pojo.token.WechatIP;

/**
 * 类名: CheckUtil <br>
 * 描述: 检查工具类 <br>
 * 开发人员： 廖日辰 <br>
 * 创建时间： Mar 14, 2017 <br>
 * 发布版本：V1.0 <br>
 */
public class CheckUtil {

	/**
	 * 检查微信签名是否正取
	 * @param TOKEN		Token
	 * @param signature	微信返回的签名
	 * @param timestamp	微信返回的时间戳
	 * @param nonce		微信返回的随机数
	 * @return boolean 处理后对比，如果是微信返回的则是真，否则为假
	 * 
	 */
	public boolean checkSignature(String TOKEN, String signature, String timestamp, String nonce) {
		if (timestamp == null || nonce == null || timestamp.length() < 1 || nonce.length() < 1) {
			return false;
		}
		String[] strs = { TOKEN, timestamp, nonce };
		Arrays.sort(strs); // 字典序排序
		String sortStr = strs[0] + strs[1] + strs[2];

		// SHA1加密
		String SHA1Str = getSHA1(sortStr);
//		System.out.println("--- : my signature : " + SHA1Str);

		return SHA1Str != null ? SHA1Str.equals(signature) : false;
	}

	/**
	 * 对一个字符串进行SHA1加密
	 * @param str		一个按字典序牌好的字符串
	 * @return String 返回一个本地算出来的签名
	 * 
	 */
	public String getSHA1(String str) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes("UTF-8"));
			byte[] result = md.digest();
			for (byte b : result) {
				int i = b & 0xff;
				if (i < 0xf) {
					sb.append(0);
				}
				sb.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 检查IP来自微信
	 * @param ip	ip地址字符串
	 * @return		成功true，失败false
	 */
	public static boolean isWechatIP(String ip) {
		if (WechatIP.get().getList().contains(ip))
			return true;
		String[] ss = ip.split("\\.");
		String str = ss[0] + "." + ss[1] + "." + ss[2];
		if (WechatIP.get().getCorpList().contains(ip))
			return true;
		if (WechatIP.get().getCorpList().contains(str + ".*"))
			return true;
		for (String string : WechatIP.get().getSublist()) {
			if (isSubnet(string, ip)) 
				return true;
		}
		return false;
	}

	/**
	 * 检查IP是否来自微信
	 * @param request	HttpServletRequest请求
	 * @return			成功true，失败false
	 */
	public static boolean isWechatIP(HttpServletRequest request) {
		String ip = request.getHeader("x-real-ip");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
				InetAddress inetAddress = null;
				try {
					inetAddress = InetAddress.getLocalHost();
					ip = inetAddress.getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		if (ip != null) {
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
			}
			if (ips.length > 1)
				ip = ips[0];
		}
		return isWechatIP(ip);
	}

	/**
	 * 判断目标ip是否属于某个ip子网
	 * @param subIp		带有子网掩码的ip
	 * @param ip		目标ip
	 * @return			成功true，失败false
	 */
	private static boolean isSubnet(String subIp, String ip) {
		String[] subs = subIp.split("/");
		int[] subnetMask = getSubnetMask(Integer.parseInt(subs[1]));
		int[] ipArray = getIpv4Array(subs[0]);
		String subnet = getSubnet(subnetMask,ipArray);
		int[] ipArray2 = getIpv4Array(ip);
		String subnet2 = getSubnet(subnetMask, ipArray2);
		if (subnet.equals(subnet2))
			return true;
		return false;
	}

	/**
	 * 获取子网掩码数组
	 * @param num	子网掩码长（暨带子网掩码的ip/后面的数字）
	 * @return		长度为4的int数组
	 */
	private static int[] getSubnetMask(int num) {
		int[] masks = new int[4];
		int index = num / 8;
		int remainder = num % 8;
		String remainderToBinary = "";
		for (int i = 0; i < index; i++)
			masks[i] = 255;
		for (int i = 0; i < 8; i++) {
			int j = 0;
			if (i < remainder)
				j = 1;
			remainderToBinary = remainderToBinary + j;
		}
		int sublast = Integer.valueOf(remainderToBinary, 2);
		masks[index] = sublast;
		if (index < masks.length - 1) {
			for (int i = index + 1; i < masks.length; i++)
				masks[i] = 0;
		}
		return masks;
	}
	
	/**
	 * 分解ip地址
	 * @param ip	ip地址
	 * @return		长度为4的int数组
	 */
	private static int[] getIpv4Array(String ip) {
		int[] ipArray = new int[4];
		String[] ips = ip.split("\\.");
		for (int i = 0; i < ips.length; i++) 
			ipArray[i] = Integer.parseInt(ips[i]);
		return ipArray;
	}
	
	/**
	 * 通过子网掩码数组和，ip地址数组获取路由地址
	 * @param masks		子网掩码数组
	 * @param ipArray	ip数组
	 * @return			路由地址
	 */
	private static String getSubnet(int[] masks, int[] ipArray) {
		String subnet = "";
		if (masks.length != ipArray.length) {
			try {
				throw new Exception("子网掩码长度和ip地址长度不一样");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < ipArray.length; i++) {
			int and = masks[i] & ipArray[i];
			subnet = subnet + and;
			if (i < ipArray.length -1) 
				subnet = subnet + ".";
		}
		return subnet;
	}
}
