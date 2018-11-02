package com.tiny.publicAccount.util;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.SysexMessage;

import org.apache.commons.logging.Log;

public class TextParseUtil {
	
	public static String  parsestr(String  str ) {
		String accessToken ="";
		String reg = "(\\w+\\-*)+";
		Pattern pattern = Pattern.compile(reg);
		Matcher mc = pattern.matcher(str);

		ArrayList<String> list = new ArrayList<>();

		while (mc.find()) {
			list.add(mc.group());
		//	System.out.println("value-->"+mc.group());
		}
		//System.out.println(list.size());
		if(list.size()==4) {
		    accessToken =list.get(1);
		   // System.out.println("value-->"+accessToken);
		}
		return accessToken;
		
	}
	// str style: {"key":"value","key2":"value"}
	// TODO 字符串处理，json 正则表达式
	public static HashMap<String, String> parseStrToMap(String str) {
		HashMap<String, String> map = new HashMap<>();
		String reg = "(\\w \\W*)*";
		Pattern pattern = Pattern.compile(reg);
		Matcher mc = pattern.matcher(str);

		ArrayList<String> list = new ArrayList<>();

		while (mc.find()) {
			list.add(mc.group());
		}
		if(list.size()==4) {
			map.put(list.get(0), list.get(1));
			map.put(list.get(2),list.get(3));
		}
		list=null;

		return map;

	}
	
	private static void testTraverMap(HashMap<String, String>map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}
	}

	public static void test() {
		String reg = "\\w+";
		String str = "{\"access_token\":\"15_0vnkw8xo\",\"expires_in\":\"700\"} ";
		System.out.println("str-->" + str);
		Pattern pattern = Pattern.compile(reg);
		Matcher mc = pattern.matcher(str);
		ArrayList<String> list = new ArrayList<>();

		while (mc.find()) {
			list.add(mc.group());
		}

	}
	
	public static void main(String[] args) {

		String str="{\"access_token\":\"15_V_Er2j1SpyaIyxMvIRpZXS_LoiKn1zCuPKHllrx4AEm73tcyMUFs_lUbLo7G-VlPLEvnY4qqTgiVj2-Cy7bKAtfFI9rO69goL6_KrEM4NjrAQKA1O_fXC6INdKaw0qIlW3eFWwB0KWkcjDn7KUIeADAYIW\",\"expires_in\":7200}";
		//HashMap<String, String> map = ParseSupport.parseStrToMap(str);
		parsestr(str);
		

	}

}
