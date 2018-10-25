package com.jk.utils;

public class StringUtil {
	
	/**
	 *  判断字符串是否为空，为空返回true，不为空返回false
	 * @param str 判断的字符串
	 * @return
	 */
	private static boolean isEmpty(String str){
		
		if(str == null || "".equals(str) || str.length() == 0){
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * 判断字符串不为空，不为空true ，为空则返回false
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		
		if(!isEmpty(str)){
			return true;
		} else {
			return false;
		}
		
	}

}
