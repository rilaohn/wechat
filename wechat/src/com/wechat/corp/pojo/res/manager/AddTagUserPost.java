package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：AddTagUserPost.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午6:03:24 <br>
 * 发布版本：V <br>
 */
public class AddTagUserPost {
	private int tagid;
	private List<String> userlist;
	private List<Integer> partylist;
	public int getTagid() {
		return tagid;
	}
	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
	public List<String> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<String> userlist) {
		this.userlist = userlist;
	}
	public List<Integer> getPartylist() {
		return partylist;
	}
	public void setPartylist(List<Integer> partylist) {
		this.partylist = partylist;
	}
}

