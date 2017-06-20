package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：GetBatchResult.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 上午10:29:44 <br>
 * 发布版本：V <br>
 */
public class GetBatchResult {
	private int errcode;
	private String errmsg;
	private int status;
	private String type;
	private int total;
	private int percentage;
	private int remaintime;
	List<BatchSyncResult> result;
	
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public int getRemaintime() {
		return remaintime;
	}
	public void setRemaintime(int remaintime) {
		this.remaintime = remaintime;
	}
	public List<BatchSyncResult> getResult() {
		return result;
	}
	public void setResult(List<BatchSyncResult> result) {
		this.result = result;
	}
}

