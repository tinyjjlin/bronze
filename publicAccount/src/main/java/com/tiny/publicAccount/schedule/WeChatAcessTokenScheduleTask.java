package com.tiny.publicAccount.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tiny.publicAccount.service.WeChatAccessTokenService;

@Component
public class WeChatAcessTokenScheduleTask {

	@Autowired
	private WeChatAccessTokenService accessTokenService;

	@Scheduled(fixedDelay = 7180000)
	public void excuteSchedule() {
		accessTokenService.writeToCache();
	}

}
