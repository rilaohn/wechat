package com.wechat.pojo.msg.req;

/**
* 类名: ScanCodeInformation <br>
* 描述: 扫描信息 <br>
* 开发人员： 廖日辰 <br>
* 创建时间： Mar 06, 2017 <br>
* 发布版本：V1.0  <br>
 */
public class ScanCodeInformarion {

	// 扫描类型，一般是qrcode
	private String ScanType;
	// 扫描结果，即二维码对应的字符串信息
	private String ScanResult;
	
	public String getScanType() {
		return ScanType;
	}
	public void setScanType(String scanType) {
		ScanType = scanType;
	}
	public String getScanResult() {
		return ScanResult;
	}
	public void setScanResult(String scanResult) {
		ScanResult = scanResult;
	}
}
