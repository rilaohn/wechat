package com.wechat.corp.pojo.res.manager;

/**
 * 类名：BatchSyncUserPost.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 上午9:25:58 <br>
 * 发布版本：V <br>
 */
public class BatchSyncUserPost {
	private String media_id;
	private BatchSyncCallback callback;
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public BatchSyncCallback getCallback() {
		return callback;
	}
	public void setCallback(BatchSyncCallback callback) {
		this.callback = callback;
	}
}

