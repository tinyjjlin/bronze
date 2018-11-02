package com.tiny.publicAccount.wechatsupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.tiny.publicAccount.wechatconfig.Certificate;

public class WeChatUtil {
	private static Logger log = LoggerFactory.getLogger(WeChatUtil.class);

	public static JSON getUserInternal(String accessToken, String openId) {
		String requestUrl = UrlOrUriSupport.getUserInternalUrl(accessToken, openId);

		return httpsRequest(requestUrl, "GET", null);
	}

	 /**
	 * 1 第一步：用户同意授权，获取code

       2 第二步：通过code换取网页授权access_token

       3 第三步：刷新access_token（如果需要）

       4 第四步：拉取用户信息(需scope为 snsapi_userinfo)

       5检验授权凭证（access_token）是否有效
      */
	/**
	 * 根据用户的openid 获取用户信息。
	 * 
	 * @param webAccessToken
	 * @param openId
	 * @return
	 */
	public static JSONObject getUserInfo(String webAccessToken, String openId) {

		String requestUrl = UrlOrUriSupport.getUserInfoUrl(webAccessToken, openId);

		JSONObject userJsonObject = httpsRequest(requestUrl, "GET", null);

		return userJsonObject;
	}

	/**
	 * 检查获取的web access_token是否有效
	 */
	public JSONObject checkWebAccessToken(String openId, String webAccessToken) {
		String requestUrl = UrlOrUriSupport.getCheckWebAccessTokenUrl(openId, webAccessToken);
		return httpsRequest(requestUrl, "GET", null);
	}

	/**
	 * 根据请求获得的code，获取用户的openid
	 * 
	 * @param code
	 * @return openId
	 */
	public static JSONObject getWebAccessToken(String code) {
		return getWebAccessToken(Certificate.APP_ID, Certificate.APP_SECRET, code);

	}

	public static JSONObject getWebAccessToken(String appId, String appSecret, String code) {
		String requestUrl = UrlOrUriSupport.getWebAccessTokenUrl(appId, appSecret, code);
		return httpsRequest(requestUrl, "GET", null);
	}

	/**
	 * 当 web access_token 失效时，可以通过刷新的方式再次获取web access_token 值和openId
	 * 
	 * @param refresh_token
	 * @return
	 */
	public static JSONObject getWebAccessToken2(String refresh_token) {
		return getWebAccessToken2(Certificate.APP_ID, refresh_token);
	}

	public static JSONObject getWebAccessToken2(String appId, String refresh_token) {
		String requestUrl = UrlOrUriSupport.getRefreshWebAccessTokenUrl(appId, refresh_token);
		return httpsRequest(requestUrl, "GET", null);

	}

	public static JSONObject httpGetAccessToken(String url) {
		JSONObject jsObj = null;
		log.info("url----------->" + url);
		InputStream is = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();

			is = http.getInputStream();
			int size = is.available();
			byte[] msgBytes = new byte[size];
			is.read(msgBytes);
			String msg = new String(msgBytes, "UTF-8");

			log.info("get acess token---->" + msg);
			jsObj = JSON.parseObject(msg);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				is.close();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jsObj;
	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl    请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr     提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new DefaultX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

			jsonObject = JSON.parseObject(buffer.toString());

			log.info("data---------->" + jsonObject.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 获取微信用户信息
	 * 
	 * @param code
	 * @return
	 */
	public static JSONObject getUserInfo(String code) {
		JSONObject webAccessTokenJsonObject = WeChatUtil.getWebAccessToken(Certificate.APP_ID, Certificate.APP_SECRET,
				code);

		JSONObject userJsonObject = null;
		if (webAccessTokenJsonObject.containsKey("access_token") && webAccessTokenJsonObject.containsKey("openid")) {
			String access_token = webAccessTokenJsonObject.getString("access_token");
			String openid = webAccessTokenJsonObject.getString("openid");

			userJsonObject = WeChatUtil.getUserInfo(access_token, openid);

			System.out.println("get the user info---->" + userJsonObject.toJSONString());
		}

		return userJsonObject;
	}

	/**
	 * 将微信JSon数据转为Object
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static WeChatUser convertToObject(JSONObject jsonObject) {
		WeChatUser user = new WeChatUser();
		try {
			user.setOpenid(jsonObject.getString("openid"));
			user.setNickname(jsonObject.getString("nickname"));
			user.setSex(jsonObject.getIntValue("sex"));
			user.setCountry(jsonObject.getString("country"));
			user.setProvince(jsonObject.getString("province"));
			user.setLanguage(jsonObject.getString("language"));
			user.setHeadimgurl(jsonObject.getString("headimgurl"));
			return user;
		} catch (Exception e) {
			return null;
		}

	}

	public static AccessToken getAccessToken() {
		return getAccessToken(Certificate.APP_ID, Certificate.APP_SECRET);
	}

	/**
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static AccessToken getAccessToken(String appId, String appSecret) {
		AccessToken accessToken = null;

		String requestUrl = UrlOrUriSupport.getAccessTokenUrl(appId, appSecret);
		System.out.println("local -->request url->" + requestUrl);

		JSONObject jsonObject = httpGetAccessToken(requestUrl);
		if (null != jsonObject) {

			accessToken = new AccessToken();
			accessToken.setToken(jsonObject.getString("access_token"));
			accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			log.info("accessToken-->{access_token:" + accessToken.getToken() + ";expires_id--->"
					+ accessToken.getExpiresIn());

		}
		return accessToken;
	}

	public static void main(String[] args) {
		// WeChatUtil.getAccessToken(Certificate.APP_ID, Certificate.APP_SECRET);
	}

}