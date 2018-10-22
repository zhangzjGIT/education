package com.jk.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jk.model.user.UserBean;

/**
 * 获取session用户信息工具类
 * @author songxj
 *
 */
public class SessionUserUtil {

	/**
	 * 获取session用户Bean
	 * @param request
	 * @return
	 */
	public static UserBean getUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserBean attribute = (UserBean)session.getAttribute(session.getId());
		return attribute;
	}
	
	/**
	 * 获取session中的用户id
	 * @param request
	 * @return
	 */
	public static Integer getUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserBean attribute = (UserBean)session.getAttribute(session.getId());
		if(attribute != null ) {
			return attribute.getUserId();
		}
		return null;
	}


}
