package com.tiny.publicAccount.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tiny.publicAccount.util.StrUtil;

@Service
public class WeChatVisitValidatorService {
	Logger log = LoggerFactory.getLogger(getClass());

	public boolean visitCheck(String signature, String token, String timest, String randomNum) {
		if (StrUtil.isNULL(signature) || StrUtil.isNULL(timest) || StrUtil.isNULL(randomNum)) {
			return false;
		}
		String[] strArr = { token, timest, randomNum };
		String encryp = sha1(sortByDict(strArr));
		log.info("signature---->" + signature);
		log.info("encryp-------->" + encryp);
		
		return (encryp.equals(signature)) ? true : false;
	}

	// sort by dict
	private String sortByDict(String[] strs) {
		Arrays.sort(strs);
		StringBuilder sBuilder = new StringBuilder();
		for (String str : strs) {
			sBuilder.append(str);
		}
		return sBuilder.toString();
	}

	// str sha1 加密
	private String sha1(String str) {
		StringBuffer hexStr = new StringBuffer();

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			byte digetArr[] = digest.digest();

			for (byte b : digetArr) {
				String shaHex = Integer.toHexString(b & 0xFF);
				if (shaHex.length() < 2) {
					hexStr.append(0);
				}
				hexStr.append(shaHex);
			}

			return hexStr.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexStr.toString();
	}

}
