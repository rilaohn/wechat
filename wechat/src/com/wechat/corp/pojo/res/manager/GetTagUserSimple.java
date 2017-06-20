package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：GetTagUserSimpleBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午5:34:18 <br>
 * 发布版本：V <br>
 */
public class GetTagUserSimple {
	private List<TagUserInfoSimple> userlist;
	private List<Integer> partylist;
	
	public List<TagUserInfoSimple> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<TagUserInfoSimple> userlist) {
		this.userlist = userlist;
	}
	public List<Integer> getPartylist() {
		return partylist;
	}
	public void setPartylist(List<Integer> partylist) {
		this.partylist = partylist;
	}
}

