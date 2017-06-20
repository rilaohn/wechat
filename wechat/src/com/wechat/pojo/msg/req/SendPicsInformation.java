package com.wechat.pojo.msg.req;

/**
 * 类名：SendPicsInformation <br>
 * 描述：图片组信息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class SendPicsInformation {
	private int Count;
	private PictureList PicList;
	
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public PictureList getPicList() {
		return PicList;
	}
	public void setPicList(PictureList picList) {
		PicList = picList;
	}
}
