package com.tiny.publicAccount.wechatsupport;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/**
 * 
 * @author jjlin
 *
 */
public class UrlOrUriSupport {

	private static String USER_INTERNAL_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	private static String AUTHORIZE_URI = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=tiny#wechat_redirect";

	private static String MENU_CREATE_URI = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static String MENU_QUERY_URI = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static String MENU_DELETE_URI = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	private static String ACCESS_TOKEN_RUL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static String WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private static String REFRESH_WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	private static String CHECK_WEB_ACCESS_TOKKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";

	private static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	public static String getUserInternalUrl(String accessToken, String openId) {

		return USER_INTERNAL_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
	}

	/**
	 * get the code
	 * 
	 * @param appId
	 * @param redirectUri
	 * @param scope
	 * @return
	 */
	public static String getAuthorizeUri(String appId, String redirectUri, String scope) {
		return AUTHORIZE_URI.replace("APPID", appId).replace("REDIRECT_URI", redirectUri).replace("SCOPE", scope);
	}

	public static String getMenuDeleteUri(String accessToken) {
		return MENU_DELETE_URI.replace("ACCESS_TOKEN", accessToken);
	}

	/**
	 * query menu uri
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getMenuQueryUri(String accessToken) {
		return MENU_QUERY_URI.replace("ACCESS_TOKEN", accessToken);
	}

	/**
	 * create menu uri
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getMenuCreateUri(String accessToken) {
		return MENU_CREATE_URI.replace("ACCESS_TOKEN", accessToken);
	}

	/**
	 * get access_token url
	 * 
	 * @param appid
	 * @param appSecret
	 * @return
	 */
	public static String getAccessTokenUrl(String appId, String appSecret) {
		return ACCESS_TOKEN_RUL.replace("APPID", appId).replace("APPSECRET", appSecret);
	}

	/**
	 * get web_access_token url
	 * 
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static String getWebAccessTokenUrl(String appId, String appSecret, String code) {
		return WEB_ACCESS_TOKEN_URL.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
	}

	/**
	 * 
	 * @param appId
	 * @param refresh_token
	 * @return
	 */
	public static String getRefreshWebAccessTokenUrl(String appId, String refresh_token) {
		return REFRESH_WEB_ACCESS_TOKEN_URL.replace("APPID", appId).replace("REFRESH_TOKEN", refresh_token);
	}

	public static String getCheckWebAccessTokenUrl(String openId, String access_token) {

		return CHECK_WEB_ACCESS_TOKKEN_URL.replace("OPENID", openId).replace("ACCESS_TOKEN", access_token);
	}

	/**
	 * get the user info url
	 * 
	 * @param webAccessToken
	 * @param openId
	 * @return
	 */
	public static String getUserInfoUrl(String webAccessToken, String openId) {
		return USER_INFO_URL.replace("ACCESS_TOKEN", webAccessToken).replace("OPENID", openId);
	}

}
