package com.tiny.publicAccount.wechatMsg;

import com.tiny.publicAccount.util.MessageUtil;

public class TextResponseMsg extends ResponseMsg {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String convertToXml() {

//		return "<xml> <ToUserName>< ![CDATA["+getToUserName()+"] ]></ToUserName> "
//				+ "<FromUserName>< ![CDATA["+getFromUserName()+"] ]></FromUserName>"
//				+ " <CreateTime>"+getCreateTime()+"</CreateTime>"
//				+ " <MsgType>< ![CDATA["+getMsgTpye()+"] ]></MsgType>"
//				+ " <Content>< ![CDATA["+getContent()+"] ]></Content> </xml>";

		/**
		 * 文本消息XML数据格式
		 */
		return String.format(
				"<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" + "<FromUserName><![CDATA[%s]]></FromUserName>"
						+ "<CreateTime>%s</CreateTime>" + "<MsgType><![CDATA[%s]]></MsgType>"
						+ "<Content><![CDATA[%s]]></Content>" + "</xml>",
				getToUserName(), getFromUserName(), MessageUtil.getUtcTime(), getMsgTpye(), getContent());
	}

}
