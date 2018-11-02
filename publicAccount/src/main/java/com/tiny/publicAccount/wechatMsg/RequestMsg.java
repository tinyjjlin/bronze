package com.tiny.publicAccount.wechatMsg;

public class RequestMsg extends WeChatMsg {
	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	private String MsgId;

}
