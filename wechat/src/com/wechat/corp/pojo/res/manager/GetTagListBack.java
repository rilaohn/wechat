package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：GetTagListBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午6:21:47 <br>
 * 发布版本：V <br>
 */
public class GetTagListBack {

	private int errcode;
	private String errmsg;
	private List<Tag> taglist;
	
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public List<Tag> getTaglist() {
		return taglist;
	}
	public void setTaglist(List<Tag> taglist) {
		this.taglist = taglist;
	}
}

