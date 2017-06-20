package com.wechat.pojo.msg.send.kf;

import com.wechat.pojo.msg.send.mass.Media;

public class KFSendImage extends KFSend {

	private Media image;

	public Media getImage() {
		return image;
	}

	public void setImage(Media image) {
		this.image = image;
	}
}
