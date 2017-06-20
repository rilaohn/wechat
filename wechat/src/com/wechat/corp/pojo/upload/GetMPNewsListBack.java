package com.wechat.corp.pojo.upload;

import java.util.List;

/**
 * 类名：GetMPNewsListBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 下午6:22:01 <br>
 * 发布版本：V <br>
 */
public class GetMPNewsListBack {
	private int errcode;
	private String errmsg;
	private String type;
	private List<MPNewsBack> itemlist;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<MPNewsBack> getItemlist() {
		return itemlist;
	}
	public void setItemlist(List<MPNewsBack> itemlist) {
		this.itemlist = itemlist;
	}
}

