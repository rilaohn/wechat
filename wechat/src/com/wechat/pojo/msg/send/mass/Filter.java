package com.wechat.pojo.msg.send.mass;

public class Filter {
	private boolean is_to_all;
	private long group_id;

	public boolean isIs_to_all() {
		return is_to_all;
	}
	public void setIs_to_all(boolean is_to_all) {
		this.is_to_all = is_to_all;
	}
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
}
