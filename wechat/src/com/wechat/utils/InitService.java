package com.wechat.utils;

import com.wechat.pojo.token.AccessToken;
import com.wechat.pojo.token.TokenAndTicket;
import com.wechat.thread.TokenTicketRunnable;

public class InitService {
	private AccessToken accessToken;
	public InitService(String appId, String secret, boolean isCorp) {
		accessToken = TokenAndTicket.get().getTokenMap().get(appId);
		if (accessToken == null || accessToken.getAccess_token() == null){
			new Thread(new TokenTicketRunnable(appId, secret, isCorp)).start();
		}
	}
}
