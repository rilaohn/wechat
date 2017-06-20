package com.wechat.corp.pojo.msg.send;

import com.wechat.pojo.msg.send.mass.Media;

/**
 * 类名：CorpFile.java <br>
 * 描述：企业文件消息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午2:47:02 <br>
 * 发布版本：V1.0 <br>
 */
public class CorpFile extends CorpMsg {
	private Media file;

	public Media getFile() {
		return file;
	}

	public void setFile(Media file) {
		this.file = file;
	}
}

