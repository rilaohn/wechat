package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：AddTagUserBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午5:53:47 <br>
 * 发布版本：V <br>
 */
public class AddTagUserBack {

	private int errcode;
	private String errmsg;
	private String invalidlist;
	private List<Integer> invalidparty;
	
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
	public String getInvalidlist() {
		return invalidlist;
	}
	public void setInvalidlist(String invalidlist) {
		this.invalidlist = invalidlist;
	}
	public List<Integer> getInvalidparty() {
		return invalidparty;
	}
	public void setInvalidparty(List<Integer> invalidparty) {
		this.invalidparty = invalidparty;
	}
}

