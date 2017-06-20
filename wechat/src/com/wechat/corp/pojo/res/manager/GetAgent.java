package com.wechat.corp.pojo.res.manager;

/**
 * 类名：GetAgent.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 上午10:17:52 <br>
 * 发布版本：V <br>
 */
public class GetAgent {
	private String errcode;
	private String errmsg;
	private String agentid;
	private String name;
	private String square_logo_url;
	private String round_logo_url;
	private String description;
	private AllowUserinfos allow_userinfos;
	private AllowPatrys allow_partys;
	private AllowTags allow_tags;
	private int close;
	private String redirect_domain;
	private int report_location_flag;
	private int isreportuser;
	private int isreportenter;
	private String chat_extension_url;
	private int type;
	
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSquare_logo_url() {
		return square_logo_url;
	}
	public void setSquare_logo_url(String square_logo_url) {
		this.square_logo_url = square_logo_url;
	}
	public String getRound_logo_url() {
		return round_logo_url;
	}
	public void setRound_logo_url(String round_logo_url) {
		this.round_logo_url = round_logo_url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AllowUserinfos getAllow_userinfos() {
		return allow_userinfos;
	}
	public void setAllow_userinfos(AllowUserinfos allow_userinfos) {
		this.allow_userinfos = allow_userinfos;
	}
	public AllowPatrys getAllow_partys() {
		return allow_partys;
	}
	public void setAllow_partys(AllowPatrys allow_partys) {
		this.allow_partys = allow_partys;
	}
	public AllowTags getAllow_tags() {
		return allow_tags;
	}
	public void setAllow_tags(AllowTags allow_tags) {
		this.allow_tags = allow_tags;
	}
	public int getClose() {
		return close;
	}
	public void setClose(int close) {
		this.close = close;
	}
	public String getRedirect_domain() {
		return redirect_domain;
	}
	public void setRedirect_domain(String redirect_domain) {
		this.redirect_domain = redirect_domain;
	}
	public int getReport_location_flag() {
		return report_location_flag;
	}
	public void setReport_location_flag(int report_location_flag) {
		this.report_location_flag = report_location_flag;
	}
	public int getIsreportuser() {
		return isreportuser;
	}
	public void setIsreportuser(int isreportuser) {
		this.isreportuser = isreportuser;
	}
	public int getIsreportenter() {
		return isreportenter;
	}
	public void setIsreportenter(int isreportenter) {
		this.isreportenter = isreportenter;
	}
	public String getChat_extension_url() {
		return chat_extension_url;
	}
	public void setChat_extension_url(String chat_extension_url) {
		this.chat_extension_url = chat_extension_url;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}

