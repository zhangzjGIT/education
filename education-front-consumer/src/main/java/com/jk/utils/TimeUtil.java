package com.jk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
  public  static String format(Date date,String format) {
		
		  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		 return  simpleDateFormat.format(date);
  }
  
  public static String format(Date date) {
	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	  return  simpleDateFormat.format(date);
  }
	
}
