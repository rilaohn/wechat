package com.wechat.corp.pojo.upload;

/**
 * 类名：GetMaterialListPost.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月17日 下午5:58:43 <br>
 * 发布版本：V <br>
 */
public class GetMaterialListPost {
	private String type;
	private int offset;
	private int count;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}

