package com.tiny.publicAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tiny.publicAccount.wechatconfig.Certificate;
import com.tiny.publicAccount.wechatconfig.WebUrl;
import com.tiny.publicAccount.wechatmenu.ComplexButton;
import com.tiny.publicAccount.wechatmenu.DefaultButton;
import com.tiny.publicAccount.wechatmenu.Menu;
import com.tiny.publicAccount.wechatmenu.Button;
import com.tiny.publicAccount.wechatmenu.ViewButton;
import com.tiny.publicAccount.wechatsupport.AccessToken;
import com.tiny.publicAccount.wechatsupport.UrlOrUriSupport;
import com.tiny.publicAccount.wechatsupport.WeChatUtil;

/**
 * 
 * @author jjlin
 *
 */
public class WeChatMenuTools {

	private static Logger log = LoggerFactory.getLogger(WeChatMenuTools.class);

	public static void main(String[] args) {

		AccessToken accessToken = WeChatUtil.getAccessToken();

		if (null != accessToken) {

			deleteMenu(accessToken.getToken());
			int result = createMenu(getMenu(), accessToken.getToken());
			if (0 == result) {
				log.info("menu create successful!");
			} else {
				log.info("menu create failed!" + result);
			}

			queryMenu(accessToken.getToken());

		}
	}

	/**
	 * 获得微信菜单信息
	 */
	public static JSONObject queryMenu(String accessToken) {
		JSONObject menuJsonObject = null;

		String uri = UrlOrUriSupport.getMenuQueryUri(accessToken);
		menuJsonObject = WeChatUtil.httpsRequest(uri, "GET", null);
		log.info(menuJsonObject.toString());
		return menuJsonObject;

	}

	/**
	 * 创建微信菜单
	 * 
	 * @param menu
	 * @param accessToken
	 * @return
	 */
	public static int createMenu(Menu menu, String accessToken) {

		int result = 0;

		String url = UrlOrUriSupport.getMenuCreateUri(accessToken);

		log.info("create menu url--->" + url);

		String jsonMenu = JSON.toJSONString(menu);
		log.info("menu json format data--->" + jsonMenu);
		JSONObject jsonObject = WeChatUtil.httpsRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
				log.error("create menu failed!");
			}
		}

		return result;
	}

	/**
	 * 删除微信自定义的菜单
	 * 
	 * @param accessToken
	 */
	public static void deleteMenu(String accessToken) {
		WeChatUtil.httpsRequest(UrlOrUriSupport.getMenuDeleteUri(accessToken), "GET", null);
	}

	private static Menu getMenu() {
		Menu menu = new Menu();
		ViewButton btn_1 = new ViewButton();
		btn_1.setName("新闻社区");
		btn_1.setType("view");
		btn_1.setUrl(UrlOrUriSupport.getAuthorizeUri(Certificate.APP_ID, WebUrl.REDIRCT_COMMUNITY_URL,
				Certificate.SCOPE_SNSAPI[1]));

		DefaultButton btn_21 = new DefaultButton();
		btn_21.setName("头条消息");
		btn_21.setKey("21");
		btn_21.setType("click");

		DefaultButton btn_22 = new DefaultButton();
		btn_22.setName("国际");
		btn_22.setKey("22");
		btn_22.setType("click");

		DefaultButton btn_23 = new DefaultButton();
		btn_23.setName("热点");
		btn_23.setKey("23");
		btn_23.setType("click");

		ComplexButton btn_2 = new ComplexButton();

		btn_2.setName("分享");
		btn_2.setSub_button(new DefaultButton[] { btn_21, btn_22, btn_23 });

		ViewButton btn_31 = new ViewButton();
		btn_31.setName("我是科学家");
		btn_31.setType("view");
		btn_31.setUrl(UrlOrUriSupport.getAuthorizeUri(Certificate.APP_ID, WebUrl.REDIRCT_MASTER_URL,
				Certificate.SCOPE_SNSAPI_USERINFO));

		ViewButton btn_32 = new ViewButton();
		btn_32.setName("工程师");
		btn_32.setType("view");
		btn_32.setUrl(UrlOrUriSupport.getAuthorizeUri(Certificate.APP_ID, WebUrl.REDIRCT_SELLER_URL,
				Certificate.SCOPE_SNSAPI_USERINFO));

		ViewButton btn_33 = new ViewButton();
		btn_33.setName("我是卖家");
		btn_33.setType("view");
		btn_33.setUrl(UrlOrUriSupport.getAuthorizeUri(Certificate.APP_ID, WebUrl.REDIRCT_BUYER_URL,
				Certificate.SCOPE_SNSAPI_USERINFO));
		ComplexButton btn_3 = new ComplexButton();

		btn_3.setName("用户服务");
		btn_3.setSub_button(new ViewButton[] { btn_31, btn_32, btn_33 });

		menu.setButton(new Button[] { btn_1, btn_2, btn_3 });

		return menu;
	}
}
