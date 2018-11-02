package com.tiny.publicAccount.service;

import com.tiny.publicAccount.wechatMsg.WeChatMsg;
import com.tiny.publicAccount.wechatMsg.WeChatMsgType;

public class WeChatMessageManagerService {

	private MsgHandler msgHandler;
	
	public String dealMsg(WeChatMsg requestMsg) {
		String msgType =requestMsg.getMsgTpye();
	
		//TODO
	    //1如需要可将用户请求文本消息保存在服务端（数据库）mogodb,考虑采用异步线程。
		//2线程池，负责用户发来信息的保存。
		
		//TODO
		//回复用户的请求，根据用户请求的内容进行回复
		switch (msgType) {
		case WeChatMsgType.TEXT:
			
			break;
		case WeChatMsgType.IMAGE:
			
			break;
		case WeChatMsgType.LINK:
			
			break;
		case WeChatMsgType.VOICE:
			
			break;
		case WeChatMsgType.VIDEO:
			
			break;
		case WeChatMsgType.LOCATION:
			
			break;

		default:
			break;
		}
	
		
		return msgHandler.dealMsg(requestMsg);
		
	}

}
