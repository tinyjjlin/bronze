package com.tiny.publicAccount.util;

import java.util.HashMap;
import java.util.Map;

public class StrUtil {

	public static boolean isNULL(String str) {
		if (str == null || str.length() <= 0) {
			return true;
		}
		return false;
	}
	
	public static void traverHashMap(HashMap<String, String> map) {
		 for(Map.Entry<String, String> entry: map.entrySet())
	        {
	         System.out.println("Key: "+ entry.getKey()+ " Value: "+entry.getValue());
	        }
	}

}
