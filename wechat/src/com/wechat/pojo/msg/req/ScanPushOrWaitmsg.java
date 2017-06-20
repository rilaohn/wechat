package com.wechat.pojo.msg.req;

/**
* 类名: ScanPushOrWaitmsg <br>
* 描述: 扫码推事件的事件推送 <br>
* 开发人员： 廖日辰 <br>
* 创建时间： Mar 06, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class ScanPushOrWaitmsg extends EventMessage {

	
	private ScanCodeInformarion ScanCodeInfo;

	public ScanCodeInformarion getScanCodeInfo() {
		return ScanCodeInfo;
	}

	public void setScanCodeInfo(ScanCodeInformarion scanCodeInfo) {
		ScanCodeInfo = scanCodeInfo;
	}
}
