package com.wechat.pojo.msg.event;

public class MassEventStatusBack extends BaseEvent {

	private long MsgID;
	private String Status;
	private long TotalCount;
	private long FilterCount;
	private long SentCount;
	private long ErrorCount;
	
	public long getMsgID() {
		return MsgID;
	}
	public void setMsgID(long msgID) {
		MsgID = msgID;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public long getTotalCount() {
		return TotalCount;
	}
	public void setTotalCount(long totalCount) {
		TotalCount = totalCount;
	}
	public long getFilterCount() {
		return FilterCount;
	}
	public void setFilterCount(long filterCount) {
		FilterCount = filterCount;
	}
	public long getSentCount() {
		return SentCount;
	}
	public void setSentCount(long sentCount) {
		SentCount = sentCount;
	}
	public long getErrorCount() {
		return ErrorCount;
	}
	public void setErrorCount(long errorCount) {
		ErrorCount = errorCount;
	}
}
