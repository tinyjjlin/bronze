package com.tiny.publicAccount.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.tiny.publicAccount.wechatsupport.AccessToken;

import com.tiny.publicAccount.wechatsupport.WeChatUtil;

@Service
public class WeChatAccessTokenService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	public void writeToCache() {

		AccessToken accessToken = WeChatUtil.getAccessToken();
		String accessTokenValue = accessToken.getToken();
		stringRedisTemplate.opsForValue().set("access_token", accessTokenValue);

		String value = stringRedisTemplate.opsForValue().get("access_token");
		log.info("redis access-token value--->" + value);
	}

}
