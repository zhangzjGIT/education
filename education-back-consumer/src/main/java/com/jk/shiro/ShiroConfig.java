/** 
 * <pre>项目名称:springboot-shiro 
 * 文件名称:ShiroConfig.java 
 * 包名:com.jk.shiro 
 * 创建日期:2018年10月7日下午6:45:52 
 * Copyright (c) 2018, lxm_man@163.com All Rights Reserved.</pre> 
 */  
package com.jk.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/** 
 * <pre>项目名称：springboot-shiro    
 * 类名称：ShiroConfig    
 * 类描述：    
 * 创建人：张紫坚
 * 创建时间：2018年10月7日 下午6:45:52    
 * 修改人：   
 * 修改时间：2018年10月7日 下午6:45:52    
 * 修改备注：       
 * @version </pre>    
 */

/**
 * shiro的配置文件类 相当与之前的spring.xml配置文件
 */
@Configuration//声明当前类是一个spring配置文件类
public class ShiroConfig {

	//@Bean 相当于 <bean name="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor">
    // </bean>
    //LifecycleBeanPostProcessor将Initializable和Destroyable的实现类统一在其内部自动分别调用了
    // Initializable.init()和Destroyable.destroy()方法，从而达到管理shiro bean生命周期的目的
    @Bean(name = "lifecycleBeanPostProcessor")//shiro和spring bean之间的结合类
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    //shiro 安全管理器 shiro的主入口
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        // 注入缓存管理器;
        //securityManager.setCacheManager(ehCacheManager());
        //在安全管理器中 注入realm.数据源
        securityManager.setRealm(myShiroRealm());
        //securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }
    
    //shiro的主要核心过滤器
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        System.out.println("ShiroConfiguration.shirFilter()");
        //创建一个shiro过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //LoginUrl 设置登录请求路径 访问此路径 会进入shiro的认证器方法 根据页面传递的用户名密码进行认证
        //当认证失败之后还会调用此路径 调用Controller中的login方法 提示用户 用户名错误还是密码错误
        shiroFilterFactoryBean.setLoginUrl("/user/userlogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/user/toMain");
        // 未授权界面;
       // shiroFilterFactoryBean.setUnauthorizedUrl("../warning");
        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        
        //anon  放过权限拦截过滤器 不需要权限也能访问的资源
        filterChainDefinitionMap.put("/EasyUI/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico","anon");
        filterChainDefinitionMap.put("/user/toLogin2", "anon");
        //除上面放过拦截的路径之外 其余路径都会被拦截
        // authc权限拦截过滤器 会走认证和授权 查看当前认证有无通过 是否有权限访问
        //filterChainDefinitionMap.put("/toIndex", "authc");
        filterChainDefinitionMap.put("/**", "authc");
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //shiro过滤器链 按照过滤器链顺序执行过滤内容
        //logout 退出登录 /logout当前项目中的退出路径  logout是shiro的退出登录过滤器
        //当页面访问退出登录路径时 会进入shiro退出登录拦截器 自动退出登录
        filterChainDefinitionMap.put("/logout", "logout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    //注入数据源 （认证、授权类）
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")//@DependsOn保证lifecycleBeanPostProcessor在它之前执行
    public MyRealm myShiroRealm(){
        MyRealm myShiroRealm = new MyRealm();
        return myShiroRealm;
    }

    //为当前shiro配置aop切面 使shiro与springAOP结合
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    //设置当前aop动态代理为cglib代理
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        //ProxyTargetClass 代理目标(实例) 类 把当前代理目标设置为类代理 默认为interface接口代理
        daap.setProxyTargetClass(true);
        return daap;
    }

    //thymlef和shiro整合时需要注入此类来完成权限验证
    /*@Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }*/
}
