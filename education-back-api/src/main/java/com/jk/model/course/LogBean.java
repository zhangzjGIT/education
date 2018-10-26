/** 
 * <pre>项目名称:demo 
 * 文件名称:LogBean.java 
 * 包名:com.jk.model 
 * 创建日期:2018年7月12日下午7:20:54 
 * Copyright (c) 2018, yuxy123@gmail.com All Rights Reserved.</pre> 
 */  
package com.jk.model.course;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/** 
 * <pre>项目名称：demo    
 * 类名称：LogBean    
 * 类描述：    
 * 创建人：曹丹亚 
 * 创建时间：2018年7月12日 下午7:20:54    
 * 修改人：曹丹亚
 * 修改时间：2018年7月12日 下午7:20:54    
 * 修改备注：       
 * @version </pre>    
 */

@Document(collection="education_log")
public class LogBean implements Serializable{
	

	private static final long serialVersionUID = -6233983131744880286L;

	private String id;
	
	private String ip;
	
	private String className;
	
	private String methodName;
	
	private String params;
	
	private String userId;

	@DateTimeFormat
	private Date createTime;

	
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
}
