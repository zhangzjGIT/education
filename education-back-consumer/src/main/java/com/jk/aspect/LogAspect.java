
package com.jk.aspect;


import java.lang.reflect.Method;
import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jk.model.course.LogBean;
import com.jk.model.user.UserBean;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;





@Aspect
@Component
public class LogAspect {
	
	@Autowired
	private MongoTemplate mongoTemplate;


	@Pointcut("execution(* com.jk.service..*.*(..))")
	public void logPointCut(){};
	
	
	@AfterReturning(value="logPointCut()",argNames = "rtv", returning = "rtv")
	public void saveLog(JoinPoint joinPoint, Object rtv) throws Exception {
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		Object[] os = joinPoint.getArgs();
		 //获取类名  
        String className = joinPoint.getTarget().getClass().getSimpleName();  
        //获取方法名  
        String methodName = joinPoint.getSignature().getName();  
        String param = className + "." + methodName + ":";  
        for (int i = 0; i < os.length; i++) {  
            param += "参数[" + i + "]:" + os[i].toString();  
        }  
        
        LogBean logBean = new LogBean();
        logBean.setClassName(className);
        logBean.setMethodName(methodName);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        if(sra != null) {
        	 HttpServletRequest request = sra.getRequest();
             logBean.setIp(getIp(request));
             HttpSession session = request.getSession();
             UserBean attribute = (UserBean) session.getAttribute("userLoginInfo");
            if (attribute != null) {				
            	logBean.setUserId(attribute.getUserId().toString());
			}
        }
        logBean.setCreateTime(new Date());
        
        mongoTemplate.save(logBean);
	}
	
	//获取客户端ip  
    public static String getIp(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip  
            int index = ip.indexOf(",");  
            if(index != -1){  
                return ip.substring(0,index);  
            }else{  
                return ip;  
            }  
        }
        ip = request.getHeader("X-Real-IP");  
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){  
            return ip;  
        }  
        return request.getRemoteAddr();  
    }  
	
	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
	 */
	public String adminOptionContent(Object[] args, String mName) throws Exception {


		if (args == null) {
			return null;
		}


		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {


			// 获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			rs.append("[参数" + index + "，类型：" + className + "，值：");
			// 获取对象的所有方法
			Method[] methods = info.getClass().getDeclaredMethods();
			// 遍历方法，判断get方法
			for (Method method : methods) {
				String methodName = method.getName();
				// 判断是不是get方法
				if (methodName.indexOf("get") == -1) {// 不是get方法
					continue;// 不处理
				}
				Object rsValue = null;
				try {
					// 调用get方法，获取返回值
					rsValue = method.invoke(info);
					if (rsValue == null) {// 没有返回值
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				// 将值加入内容中
				rs.append("(" + methodName + " : " + rsValue + ")");
			}
			rs.append("]");
			index++;
		}
		return rs.toString();
	}






	
	
	
}