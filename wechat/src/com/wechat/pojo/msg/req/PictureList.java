package com.wechat.pojo.msg.req;

import java.util.List;

/**
 * 类名：PictureList <br>
 * 描述：图片列表md5 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：Mar 14, 2017 <br>
 * 发布版本：V1.00 <br>
 */
public class PictureList {

	private List<String> PicMd5Sum;

	public List<String> getPicMd5Sum() {
		return PicMd5Sum;
	}

	public void setPicMd5Sum(List<String> picMd5Sum) {
		PicMd5Sum = picMd5Sum;
	}
}
