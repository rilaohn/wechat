package com.wechat.pojo.user;

import java.util.List;

/**
 * 类名：WebUserInfo <br>
 * 描述：通过网页获取的用户信息类 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class WebUserInfo {

	// 用户标识
		private String openid;
		// 用户昵称
		private String nickname;
		// 性别（1是男性，2是女性，0是未知）
		private int sex;
		// 省份
		private String province;
		// 城市
		private String city;
		// 国家
		private String country;
		// 用户头像链接
		private String headimgurl;
		// 用户特权信息
		private List<String> privilege;
		// 联合ID
		private String unionid;
		
		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public int getSex() {
			return sex;
		}
		public void setSex(int sex) {
			this.sex = sex;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getHeadimgurl() {
			return headimgurl;
		}
		public void setHeadimgurl(String headimgurl) {
			this.headimgurl = headimgurl;
		}
		public List<String> getPrivilege() {
			return privilege;
		}
		public void setPrivilege(List<String> privilege) {
			this.privilege = privilege;
		}
		public String getUnionid() {
			return unionid;
		}
		public void setUnionid(String unionid) {
			this.unionid = unionid;
		}
}
