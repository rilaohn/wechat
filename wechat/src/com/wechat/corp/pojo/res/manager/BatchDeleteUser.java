package com.wechat.corp.pojo.res.manager;

import java.util.List;

/**
 * 类名：BatchDeleteUser.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 下午3:46:55 <br>
 * 发布版本：V <br>
 */
public class BatchDeleteUser {
	private List<String> useridlist;

	public List<String> getUseridlist() {
		return useridlist;
	}

	public void setUseridlist(List<String> useridlist) {
		this.useridlist = useridlist;
	}
}

