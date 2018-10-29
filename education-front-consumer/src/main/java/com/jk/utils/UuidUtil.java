/** 
 * <pre>项目名称:aaa 
 * 文件名称:StringUtil.java 
 * 包名:com.jk.utils 
 * 创建日期:2018年8月4日上午10:23:25 
 * Copyright (c) 2018, lxm_man@163.com All Rights Reserved.</pre> 
 */  
package com.jk.utils;

import java.util.UUID;

/** 
 * <pre>项目名称：aaa    
 * 类名称：StringUtil    
 * 类描述：    
 * 创建人：张紫坚
 * 创建时间：2018年8月4日 上午10:23:25    
 * 修改人：   
 * 修改时间：2018年8月4日 上午10:23:25    
 * 修改备注：       
 * @version </pre>    
 */
public class UuidUtil {

	public static String getUUId () {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
