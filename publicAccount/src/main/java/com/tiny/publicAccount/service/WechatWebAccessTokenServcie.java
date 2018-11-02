package com.tiny.publicAccount.service;

import org.springframework.stereotype.Service;

import com.tiny.publicAccount.wechatsupport.WeChatUtil;

@Service
public class WechatWebAccessTokenServcie {

	public void getWebAccessToken(String code) {
		WeChatUtil.getWebAccessToken( code);
	}
}
