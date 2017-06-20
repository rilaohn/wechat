package com.wechat.corp.pojo.res.manager;

/**
 * 类名：SetAgent.java <br>
 * 描述：设置企业号应用 <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月14日 上午10:38:43 <br>
 * 发布版本：V1.0 <br>
 */
public class SetAgent {
	private String agentid;
	private String report_location_flag;
	private String logo_mediaid;
	private String name;
	private String description;
	private String redirect_domain;
	private int isreportuser;
	private int isreportenter;
	private String home_url;
	private String chat_extension_url;

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getReport_location_flag() {
		return report_location_flag;
	}

	public void setReport_location_flag(String report_location_flag) {
		this.report_location_flag = report_location_flag;
	}

	public String getLogo_mediaid() {
		return logo_mediaid;
	}

	public void setLogo_mediaid(String logo_mediaid) {
		this.logo_mediaid = logo_mediaid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRedirect_domain() {
		return redirect_domain;
	}

	public void setRedirect_domain(String redirect_domain) {
		this.redirect_domain = redirect_domain;
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

	public String getHome_url() {
		return home_url;
	}

	public void setHome_url(String home_url) {
		this.home_url = home_url;
	}

	public String getChat_extension_url() {
		return chat_extension_url;
	}

	public void setChat_extension_url(String chat_extension_url) {
		this.chat_extension_url = chat_extension_url;
	}
}
