package com.tiny.publicAccount.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tiny.publicAccount.aop.LoggerLocation;
import com.tiny.publicAccount.service.TextMsgHandler;
import com.tiny.publicAccount.service.WeChatMessageManagerService;
import com.tiny.publicAccount.service.WeChatVisitValidatorService;
import com.tiny.publicAccount.util.MessageUtil;
import com.tiny.publicAccount.util.StrUtil;
import com.tiny.publicAccount.wechatMsg.TextRequestMsg;
import com.tiny.publicAccount.wechatMsg.TextResponseMsg;
import com.tiny.publicAccount.wechatconfig.Certificate;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired

	private WeChatVisitValidatorService wechatService;

	/**
	 * wechat send parameter: 1.signature 2.timestamp 3.nonce 4.echostr and extra
	 * token(sent by ourself)
	 * 
	 * 
	 * @return
	 */

	@RequestMapping(value = "/visit", method = RequestMethod.GET)
	@LoggerLocation(desc = "访问验证：")
	public String visit(@RequestParam("signature") String signature, @RequestParam("timestamp") String timest,
			@RequestParam("nonce") String randomNum, @RequestParam("echostr") String randomStr) {

		Boolean success = wechatService.visitCheck(signature, Certificate.TOKEN, timest, randomNum);

		return (success) ? randomStr : "";
	}

	@RequestMapping(value = "/visit", method = RequestMethod.POST)
	@LoggerLocation(desc = "消息处理--------->：")
	public void handleMsg(HttpServletRequest req, HttpServletResponse resp) {
		HashMap<String, String> map = MessageUtil.parseXmlDom4j(req);
		if ("text".equals(map.get("MsgType"))) {
			String responseXml = new TextMsgHandler().dealMsg(MessageUtil.convertToTextRequestMsg(map));
			log.info("{response text msg}=====>"+responseXml);
			MessageUtil.outputStreamWrite(resp, responseXml);
		}

	}

	@RequestMapping(value = "/handler1", method = RequestMethod.GET)
	@LoggerLocation(desc = "handler1---->:")
	public void doHandler1(HttpServletRequest req, HttpServletResponse resp) {
		travwerReq(req);
	}

	@RequestMapping(value = "/handler2", method = RequestMethod.GET)
	@LoggerLocation(desc = "handler2------>:")
	public void doHandler2(HttpServletRequest req, HttpServletResponse resp) {
		travwerReq(req);
	}

	@RequestMapping(value = "/handler3", method = RequestMethod.GET)
	@LoggerLocation(desc = "handler3------>:")
	public void doHandler3(HttpServletRequest req, HttpServletResponse resp) {
		travwerReq(req);
	}

	@RequestMapping(value = "/handler4", method = RequestMethod.GET)
	@LoggerLocation(desc = "handler4------>:")
	public void doHandler4(HttpServletRequest req, HttpServletResponse resp) {
		travwerReq(req);

	}

	private void travwerReq(HttpServletRequest req) {
		Map<String, String[]> map = req.getParameterMap();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			for (String str : value) {
				System.out.println("parameter:" + key);
				System.out.println("value:" + str);
			}

		}
	}

}
