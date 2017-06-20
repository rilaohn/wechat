package com.wechat.corp.pojo.msg.send;

import com.wechat.pojo.msg.send.mass.Media;

/**
 * 类名：CorpImage.java <br>
 * 描述：企业图片消息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午2:42:38 <br>
 * 发布版本：V1.0 <br>
 */
public class CorpImage extends CorpMsg {
	private Media image;

	public Media getImage() {
		return image;
	}

	public void setImage(Media image) {
		this.image = image;
	}
}

