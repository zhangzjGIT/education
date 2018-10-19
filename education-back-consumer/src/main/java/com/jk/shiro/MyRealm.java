/** 
 * <pre>项目名称:springboot-shiro 
 * 文件名称:MyRealm.java 
 * 包名:com.jk.shiro 
 * 创建日期:2018年10月7日下午6:46:32 
 * Copyright (c) 2018, lxm_man@163.com All Rights Reserved.</pre> 
 */  
package com.jk.shiro;

import java.util.ArrayList;
import java.util.List;

import com.jk.model.user.UserBean;
import com.jk.service.user.UserServiceApi;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <pre>项目名称：springboot-shiro    
 * 类名称：MyRealm    
 * 类描述：    
 * 创建人：张紫坚
 * 创建时间：2018年10月7日 下午6:46:32    
 * 修改人：   
 * 修改时间：2018年10月7日 下午6:46:32    
 * 修改备注：       
 * @version </pre>    
 */
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private UserServiceApi userServiceApi;

	//授权器
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserBean userInfo = (UserBean) principals.getPrimaryPrincipal();
		System.out.println(userInfo.getUserName());
		/*List<String> list = new ArrayList<>();
		list.add("user:add");
		list.add("user:delete");*/
		// 创建一个简单的授权器
		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		/*List<RoleBeanZzj> roleList = loginService.queryUserRolePowerByUserId(userInfo.getUserId());
		List<String> roles = new ArrayList<String>();
		for (RoleBeanZzj roleBeanZzj : roleList) {
			roles.add(roleBeanZzj.getroleName());
			sai.addStringPermissions(roleBeanZzj.getPower());
		}
		sai.addRoles(roles);*/
		return sai;
	}
	
	//认证器 登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token 令牌  token.getPrincipal() 获取到前台页面输入的用户名
        String userName = token.getPrincipal().toString();
        UserBean user = userServiceApi.queryUserByLoginNumer(userName);
        if (null == user){
            throw new UnknownAccountException();
        }
								Session session=SecurityUtils.getSubject().getSession();
								session.setAttribute(session.getId(),user);
        //创建一个简单认证器   第一个参数为当前登录用户的主体 可以是用户名 也可以是用户对象 一般都是用户对象
        //第二个参数为数据库查询出来的密码 认证器会用前台传递的密码后查询出来的密码对比
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return simpleAuthenticationInfo;
    }

}
