package com.tiny.publicAccount.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.tiny.publicAccount.wechatMsg.TextResponseMsg;
import com.tiny.publicAccount.wechatMsg.WeChatMsg;
import com.tiny.publicAccount.wechatMsg.WeChatMsgType;

@Component
public class TextMsgHandler implements MsgHandler {

	@Override
	public String dealMsg(WeChatMsg requestmsg) {
		//1根据请求的内容进行文本回复。
		//回复的内容来自数据库，则需要查询数据库，获取回复消息。
		TextResponseMsg textRespMsg = new TextResponseMsg();
		textRespMsg.setFromUserName(requestmsg.getToUserName());
		textRespMsg.setToUserName(requestmsg.getFromUserName());
		textRespMsg.setCreateTime(new Date().toString());
		textRespMsg.setMsgTpye(WeChatMsgType.TEXT);
		textRespMsg.setContent("nihao");
		
		return textRespMsg.convertToXml();
	}

}
