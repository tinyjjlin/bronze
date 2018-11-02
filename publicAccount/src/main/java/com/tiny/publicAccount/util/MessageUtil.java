package com.tiny.publicAccount.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiny.publicAccount.wechatMsg.TextRequestMsg;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

	public static void domsgEvent(HashMap<String, String> xmlMap) {
		if (xmlMap.get("MsgType") != null) {
			if ("event".equals(xmlMap.get("MsgType"))) {
				if ("subscribe".equals(xmlMap.get("Event"))) {
					System.out.println("subscribe------->");

				}
			}

		}
	}

	public static Map<String, String> parseXmlSAX(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return map
	 * @throws Exception
	 */
	public static TextRequestMsg convertToTextRequestMsg(HashMap<String, String>map) {
		TextRequestMsg textRequestMsg = new TextRequestMsg();
		textRequestMsg.setFromUserName(map.get("FromUserName"));
		textRequestMsg.setToUserName(map.get("ToUserName"));
		textRequestMsg.setContent(map.get("Content"));
		textRequestMsg.setContent(map.get("CreateTime"));
		textRequestMsg.setMsgId(map.get("MsgType"));
		return textRequestMsg;
		
	}
	public static HashMap<String, String> parseXmlDom4j(HttpServletRequest request) {
		// 将解析结果存储在HashMap中
		HashMap<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream = null;
		Document document;
		try {
			inputStream = request.getInputStream();
			System.out.println("获取输入流");
			// 读取输入流
			SAXReader reader = new SAXReader();
			document = reader.read(inputStream);
			
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList) {
				System.out.println(e.getName() + "---->" + e.getText());
				map.put(e.getName(), e.getText());
			}

			// 释放资源
			inputStream.close();
			inputStream = null;
			
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return map;
	}

	/**
	 * 根据消息类型 构造返回消息
	 */
	public static String buildXml(Map<String, String> map) {
		String result;
		String msgType = map.get("MsgType").toString();
		System.out.println("MsgType:" + msgType);
		if (msgType.toUpperCase().equals("TEXT")) {
			result = buildTextMessage(map, "Cherry的小小窝, 请问客官想要点啥?");
		} else {
			String fromUserName = map.get("FromUserName");
			// 开发者微信号
			String toUserName = map.get("ToUserName");
			result = String.format(
					"<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" + "<FromUserName><![CDATA[%s]]></FromUserName>"
							+ "<CreateTime>%s</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
							+ "<Content><![CDATA[%s]]></Content>" + "</xml>",
					fromUserName, toUserName, getUtcTime(), "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文");
		}

		return result;
	}

	/**
	 * 构造文本消息
	 *
	 * @param map
	 * @param content
	 * @return
	 */
	private static String buildTextMessage(Map<String, String> map, String content) {
		// 发送方帐号
		String fromUserName = map.get("FromUserName");
		// 开发者微信号
		String toUserName = map.get("ToUserName");
		/**
		 * 文本消息XML数据格式
		 */
		return String.format(
				"<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" + "<FromUserName><![CDATA[%s]]></FromUserName>"
						+ "<CreateTime>%s</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
						+ "<Content><![CDATA[%s]]></Content>" + "</xml>",
				fromUserName, toUserName, getUtcTime(), content);
	}

	public static String getUtcTime() {
		Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
		String nowTime = df.format(dt);
		long dd = (long) 0;
		try {
			dd = df.parse(nowTime).getTime();
		} catch (Exception e) {

		}
		return String.valueOf(dd);
	}

	public static void outputStreamWrite(HttpServletResponse resp, String text) {

		try {
			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.write(text.getBytes("utf-8"));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
