package com.wechat.menu;

import static com.wechat.utils.C.GET;
import static com.wechat.utils.C.MENU_CREATE_CONDITIONAL_URL;
import static com.wechat.utils.C.MENU_CREATE_URL;
import static com.wechat.utils.C.MENU_DELETE_URL;
import static com.wechat.utils.C.MENU_GET_INFO_URL;
import static com.wechat.utils.C.MENU_GET_URL;
import static com.wechat.utils.C.POST;
import static com.wechat.utils.C.WEB_CODE_URL;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wechat.pojo.menu.Menu;
import com.wechat.pojo.menu.MenuBack;
import com.wechat.pojo.menu.View;
import com.wechat.pojo.token.AccessToken;
import com.wechat.utils.CommonUtil;

public class MenuUtil {

	private AccessToken accessToken;

	public MenuUtil(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 创建菜单
	 * @param menu			菜单实例
	 * @param conditional	是否是个性化菜单
	 * @return				成功TRUE，失败FALSE
	 */
	public boolean createMenu(Menu menu, boolean conditional) {

		String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken.getAccess_token());
		if (conditional) {
			url = MENU_CREATE_CONDITIONAL_URL.replace("ACCESS_TOKEN",
					accessToken.getAccess_token());
		} else {
			url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken.getAccess_token());
		}

		// 将菜单对象转换成json字符串
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String jsonMenu = gson.toJson(menu).toString();
		jsonMenu = jsonMenu.replace("\"sub_button\":\"[]\"", "\"sub_button\":[]");

		// 调用接口创建菜单
		String jsonStr = CommonUtil.httpsRequest(url, POST, jsonMenu);
		Gson json = new Gson();
		MenuBack menuBack = json.fromJson(jsonStr, MenuBack.class);
		if (null != menuBack) {
			if (0 != menuBack.getErrcode()) {
				System.err.println("创建菜单失败 errcode:{" + menuBack.getErrcode() + "} errmsg:{"
						+ menuBack.getErrmsg() + "}");
				return true;
			}
			return false;
		}

		return true;
	}

	/**
	 * 删除菜单
	 * @return		成功TRUE，失败FALSE
	 */
	public boolean deleteMenu() {
		String url = MENU_DELETE_URL.replace("ACCESS_TOKEN", accessToken.getAccess_token());
		String jsonStr = CommonUtil.httpsRequest(url, GET, null);
		JSONObject json = new JSONObject(jsonStr);
		if (json.getInt("errcode") == 0)
			return true;
		return false;
	}

	/**
	 * 查询菜单（获取菜单）
	 * @return		菜单配置字符串
	 */
	public String getMenu() {
		String url = MENU_GET_URL.replace("ACCESS_TOKEN", accessToken.getAccess_token());
		return CommonUtil.httpsRequest(url, GET, null);
	}

	/**
	 * 获取自定义菜单配置接口
	 * @return 自定义菜单配置字符串
	 */
	public String getMenuInfo() {
		String url = MENU_GET_INFO_URL.replace("ACCESS_TOKEN", accessToken.getAccess_token());
		return CommonUtil.httpsRequest(url, GET, null);
	}

	/**
	 * 获取支持微信JS的菜单View控件
	 * @param name			菜单view名称
	 * @param id			公众号的Appid 唯一标识
	 * @param REDIRECT_URI	授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param SCOPE			应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @param STATE			重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @return				菜单view对象
	 */
	public View getWebView(String name, String id, String REDIRECT_URI, String SCOPE, String STATE) {
		String url = WEB_CODE_URL;
		if (STATE == null) {
			STATE = "STATE";
		}
		url = url.replace("APPID", id).replace("REDIRECT_URI", REDIRECT_URI)
				.replace("SCOPE", SCOPE).replace("STATE", STATE);
		return getWebView(name, url);
	}
	
	/**
	 * 获取菜单view控件
	 * @param name	菜单view名称
	 * @param url	跳转的url
	 * @return		菜单view对象
	 */
	public View getWebView(String name, String url) {
		View view = new View();
		view.setName(name);
		view.setUrl(url);
		return view;
	}
}
