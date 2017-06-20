package com.wechat.pojo.token;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class WechatIP {
	private List<String> list;
	private List<String> corpList;
	private List<String> sublist;
	private String ipList;
	private String corpIPList;
	
	private static WechatIP address;
	private WechatIP() {
		list = new ArrayList<String>();
		corpList = new ArrayList<String>();
		sublist = new ArrayList<String>();
		ipList = "";
		corpIPList = "";
//		Gson gson = new Gson();
//		ipList = "{\"ip_list\":[\"101.226.62.77\",\"101.226.62.78\",\"101.226.62.79\",\"101.226.62.80\",\"101.226.62.81\",\"101.226.62.82\",\"101.226.62.83\",\"101.226.62.84\",\"101.226.62.85\",\"101.226.62.86\",\"101.226.103.59\",\"101.226.103.60\",\"101.226.103.61\",\"101.226.103.62\",\"101.226.103.63\",\"101.226.103.69\",\"101.226.103.70\",\"101.226.103.71\",\"101.226.103.72\",\"101.226.103.73\",\"140.207.54.73\",\"140.207.54.74\",\"140.207.54.75\",\"140.207.54.76\",\"140.207.54.77\",\"140.207.54.78\",\"140.207.54.79\",\"140.207.54.80\",\"182.254.11.203\",\"182.254.11.202\",\"182.254.11.201\",\"182.254.11.200\",\"182.254.11.199\",\"182.254.11.198\",\"59.37.97.100\",\"59.37.97.101\",\"59.37.97.102\",\"59.37.97.103\",\"59.37.97.104\",\"59.37.97.105\",\"59.37.97.106\",\"59.37.97.107\",\"59.37.97.108\",\"59.37.97.109\",\"59.37.97.110\",\"59.37.97.111\",\"59.37.97.112\",\"59.37.97.113\",\"59.37.97.114\",\"59.37.97.115\",\"59.37.97.116\",\"59.37.97.117\",\"59.37.97.118\",\"112.90.78.158\",\"112.90.78.159\",\"112.90.78.160\",\"112.90.78.161\",\"112.90.78.162\",\"112.90.78.163\",\"112.90.78.164\",\"112.90.78.165\",\"112.90.78.166\",\"112.90.78.167\",\"140.207.54.19\",\"140.207.54.76\",\"140.207.54.77\",\"140.207.54.78\",\"140.207.54.79\",\"140.207.54.80\",\"180.163.15.149\",\"180.163.15.151\",\"180.163.15.152\",\"180.163.15.153\",\"180.163.15.154\",\"180.163.15.155\",\"180.163.15.156\",\"180.163.15.157\",\"180.163.15.158\",\"180.163.15.159\",\"180.163.15.160\",\"180.163.15.161\",\"180.163.15.162\",\"180.163.15.163\",\"180.163.15.164\",\"180.163.15.165\",\"180.163.15.166\",\"180.163.15.167\",\"180.163.15.168\",\"180.163.15.169\",\"180.163.15.170\",\"101.226.103.0/25\",\"101.226.233.128/25\",\"58.247.206.128/25\",\"182.254.86.128/25\",\"103.7.30.21\",\"103.7.30.64/26\",\"58.251.80.32/27\",\"183.3.234.32/27\",\"121.51.130.64/27\"]}";
//		corpIPList = "{\"ip_list\":[\"101.226.103.*\",\"101.226.125.*\",\"101.226.62.*\",\"103.7.30.*\",\"112.5.138.*\",\"112.90.75.*\",\"112.90.78.*\",\"117.135.171.*\",\"120.198.199.*\",\"14.17.43.*\",\"140.207.54.*\",\"183.61.32.*\",\"183.61.51.*\",\"203.205.167.*\"]}";
//		GetAddressList ip = gson.fromJson(ipList, GetAddressList.class);
//		GetAddressList corpIp = gson.fromJson(corpIPList, GetAddressList.class);
//		list = ip.getIp_list();
//		corpList = corpIp.getIp_list();
	}
	
	public static WechatIP get() {
		if (address == null)
			address = new WechatIP();
		return address;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<String> getCorpList() {
		return corpList;
	}

	public void setCorpList(List<String> corpList) {
		this.corpList = corpList;
	}

	public String getIpList() {
		return ipList;
	}

	public void setIpList(String ipList) {
		this.ipList = ipList;
	}

	public String getCorpIPList() {
		return corpIPList;
	}

	public void setCorpIPList(String corpIPList) {
		this.corpIPList = corpIPList;
	}

	public List<String> getSublist() {
		return sublist;
	}

	public void setSublist(List<String> sublist) {
		this.sublist = sublist;
	}
}
