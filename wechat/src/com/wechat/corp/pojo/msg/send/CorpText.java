package com.wechat.corp.pojo.msg.send;

import com.wechat.pojo.msg.send.kf.Text;

/**
 * 类名：CorpText.java <br>
 * 描述：企业文本消息 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月10日 下午2:39:59 <br>
 * 发布版本：V1.0 <br>
 */
public class CorpText extends CorpMsg {
	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
}

