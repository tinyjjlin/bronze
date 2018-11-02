package com.tiny.publicAccount.service;

import com.tiny.publicAccount.wechatMsg.WeChatMsg;

public interface MsgHandler {
	
	public String  dealMsg(WeChatMsg requestmsg);

}
