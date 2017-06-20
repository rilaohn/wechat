package com.wechat.corp.pojo.res.manager;

/**
 * 类名：BatchSyncBack.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 上午9:28:38 <br>
 * 发布版本：V <br>
 */
public class BatchSyncBack {
	private int errcode;
	private String errmsg;
	private String jobid;
	
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
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
}

