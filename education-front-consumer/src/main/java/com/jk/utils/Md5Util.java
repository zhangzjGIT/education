package com.jk.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * Copyright 漏 2017 閲戠鏁欒偛. All rights reserved. <br>
 * 绫�: Md5Util <br>
 * 鎻忚堪: MD5鍔犲瘑 <br>
 * 浣滆��: Teacher song<br>
 * 鏃堕棿: 2017骞�6鏈�27鏃� 涓嬪崍2:27:06
 */
public class Md5Util {
	
	/**
	 * 
	 * 鏂规硶: getMd532 <br>
	 * 鎻忚堪: 32浣峬d5鍔犲瘑 <br>
	 * 浣滆��: Teacher song<br>
	 * 鏃堕棿: 2017骞�6鏈�27鏃� 涓嬪崍2:27:38
	 * @param plainText
	 * @return
	 */
	public static String getMd532(String plainText) {  
	    try {  
	        MessageDigest md = MessageDigest.getInstance("MD5");  
	        md.update(plainText.getBytes());  
	        byte b[] = md.digest();  
	        int i;
	        StringBuffer buf = new StringBuffer("");  
	        for (int offset = 0; offset < b.length; offset++) {  
	            i = b[offset];  
	            if (i < 0)  
	                i += 256;  
	            if (i < 16)  
	                buf.append("0");  
	            buf.append(Integer.toHexString(i));  
	        }  
	        //32浣嶅姞瀵�  
	        return buf.toString();  
	        // 16浣嶇殑鍔犲瘑  
	        //return buf.toString().substring(8, 24);  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	        return null;  
	    }  
	}  
	/**
	 * 
	 * 鏂规硶: getMd516 <br>
	 * 鎻忚堪: 16浣峂D5鍔犲瘑 <br>
	 * 浣滆��: Teacher song<br>
	 * 鏃堕棿: 2017骞�6鏈�27鏃� 涓嬪崍2:27:52
	 * @param plainText
	 * @return
	 */
	public static String getMd516(String plainText) {  
	    try {  
	        MessageDigest md = MessageDigest.getInstance("MD5");  
	        md.update(plainText.getBytes());  
	        byte b[] = md.digest();  
	        int i;  
	        StringBuffer buf = new StringBuffer("");  
	        for (int offset = 0; offset < b.length; offset++) {  
	            i = b[offset];  
	            if (i < 0)  
	                i += 256;  
	            if (i < 16)  
	                buf.append("0");  
	            buf.append(Integer.toHexString(i));  
	        }  
	        //32浣嶅姞瀵�  
	       // return buf.toString();  
	        // 16浣嶇殑鍔犲瘑  
	        return buf.toString().substring(8, 24);  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	        return null;  
	    }  
	}
}
