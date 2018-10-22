package com.jk.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.jk.model.ResultPage;
import com.jk.model.user.NavBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.UserBean;
import com.jk.service.user.UserServiceApi;
import com.jk.utils.Md5Util;
import com.jk.utils.RandomValidateCodeUtil;
import com.jk.utils.SessionUserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserServiceApi userServiceApi;

    @RequestMapping("toIndex2")
    private String toIndex2(){
        return "login/index";
    }

    //跳转到shiro登录页面
    @RequestMapping("/toLogin2")
    public String toLogin2(){
        return "login/login2";
    }

    //shiro登录验证
  /*@RequestMapping("/userlogin")
  public String loginFail(HttpServletRequest request) {
   // 获取到当前认证器所抛出的异常类的类名
   String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
   if (UnknownAccountException.class.getName().equals(exceptionClassName)
     || AuthenticationException.class.getName().equals(exceptionClassName)) {
    request.setAttribute("flag", "账号不存在");
   }
   if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
    request.setAttribute("flag", "密码错误");
   }
   return "login/login2";
  }*/

    @RequestMapping(value ="/userlogin")
    @ResponseBody
    public HashMap<String, Object> userLogin(UserBean user,HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<String, Object>();
        if(user.getUserName()==""){
            result.put("message", "请填写用户名");
            return result;
        }
        if(user.getPassword()==""){
            result.put("message", "请填写密码");
            return result;
        }
        String account=user.getUserName();
        String password=user.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(account,password,false);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            //此步将 调用realm的认证方法
        }catch(IncorrectCredentialsException e){
            //这最好把 所有的 异常类型都背会
            result.put("message", "密码错误");
            return result;
        } catch (AuthenticationException e) {
            result.put("message", "账户不存在");
            return result;
        }
        result.put("message", "SUCCESS");

        return result;
    }

  @RequestMapping(value = "/logout")
  public String logout(HttpServletRequest request) {
   HttpSession session = request.getSession();
   session.removeAttribute(session.getId());
   return "login/login2";
  }

    @RequestMapping("/toMain")
    public String toMain(){
        return "login/main";
    }

    @RequestMapping("toLogin")
    public String toIndex(){
        return "login/login";
    }

    //保存角色赋权限
    @RequestMapping("addRoleNav")
    @ResponseBody
    public Boolean addRoleNav(Integer roleId,String ids){
        try {
            userServiceApi.addRoleNav(roleId,ids);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //根据角色ID查询相应的权限
    @RequestMapping("queryRoleNavTree")
    @ResponseBody
    public List<NavBean> queryRoleNavTree(Integer roleId){
        return userServiceApi.queryRoleNavTree(roleId);
    }

    //根据Id修改角色表中的数据
    @RequestMapping("updateRole")
    @ResponseBody
    public String updateRole(RoleBean roleBean){
     userServiceApi.updateRole(roleBean);
     return "{}";
    }

    //根据Id查询一条信息回显到修改页面
    @RequestMapping("toEditRole")
    public String toEditRole(Integer roleId, ModelMap mm){
     RoleBean role = userServiceApi.getRoleInfoById(roleId);
     mm.put("ro",role);
     return "user/editRole";
    }

    //根据Id删除角色信息
    @RequestMapping("deleteRole")
    @ResponseBody
    public String deleteRoleById(Integer roleId){
     userServiceApi.deleteRole(roleId);
     return "{}";
    }

    //跳转到新增角色页面
    @RequestMapping("toAddRole")
    public String toAddRole(){
     return "user/saveRole";
    }

    //新增角色
    @RequestMapping("addRole")
    @ResponseBody
    public Boolean addRole(RoleBean roleBean){
     try {
      userServiceApi.addRole(roleBean);
     } catch (Exception e) {
      e.printStackTrace();
      return false;
     }
     return true;
    }

    //根据用户Id查询左边菜单树
    @RequestMapping("queryNavTreeByUserId")
    @ResponseBody
    public List<NavBean> queryNavTreeByUserId(){
        Session  session=  SecurityUtils.getSubject().getSession();
        UserBean userBean = (UserBean) session.getAttribute(session.getId());
        Integer userId = userBean.getUserId();
        List<NavBean> navs = userServiceApi.queryNavTreeByUserId(userId);
        return navs;
    }


    @RequestMapping("getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            /*logger.error("获取验证码失败>>>>   ", e);*/
        }
    }

    //普通的登陆注册
    @RequestMapping("userLogin")
    @ResponseBody
    public HashMap<String, Object> userLogin(UserBean userBean, String imgCode, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String sessionCode = session.getAttribute("RANDOMVALIDATECODEKEY").toString();
        if (!sessionCode.equals(imgCode)) {
            result.put("code", 1);
            result.put("msg", "验证码错误");
            return result;
        }
        String loginNumber = userBean.getLoginNumber();
        UserBean userLoginInfo = userServiceApi.queryUserLoginInfo(userBean);
        if (userLoginInfo == null) {
            result.put("code", 2);
            result.put("msg", "账号不存在");
            return result;
        }
        String password = userBean.getPassword();
        String md516 = Md5Util.getMd516(password);
        if (!userLoginInfo.getPassword().equals(md516)) {
            result.put("code", 3);
            result.put("msg", "密码错误");
            return result;
        }
        session.setAttribute(session.getId(), userLoginInfo);
        result.put("code", 0);
        result.put("msg", "登录成功");
        return result;
    }

    //跳转到首页面(easyui前台展示)
    @RequestMapping("toLayout")
    public String toLayout(ModelMap mm){
     Session session = SecurityUtils.getSubject().getSession();
     UserBean users = (UserBean) session.getAttribute(session.getId());
     String userName = users.getUserName();
     mm.put("name",userName);
     return "login/main";
    }

    //查询用户信息
    @RequestMapping("toUserPage")
    public String toUserPage(){
        return "user/userList";
    }

    //跳转到角色信息页面
    @RequestMapping("toRolePage")
    public String toRolePage(){
        return "user/roleList";
    }

    //查询角色表中的所有信息
    @RequestMapping("getRolePage")
    @ResponseBody
    public ResultPage getRolePage(RoleBean roleBean){
        ResultPage roles = userServiceApi.getRolePage(roleBean);
        return roles;
    }

    //跳转到新增用户页面
    @RequestMapping("toAddUser")
    public String toAddUser(){
        return "user/addUser";
    }

    //新增一条信息到用户列表
    @RequestMapping("addUser")
    @ResponseBody
    public Boolean addUser(UserBean userBean){
     try {
      userServiceApi.addUser(userBean);
     }catch (Exception e){
       return false;
     }
       return true;
    }

    //跳转到修改页面
    @RequestMapping("toEditUser")
    public String toEditUser(Model model, Integer userId){
        UserBean editUser = userServiceApi.queryUserById(userId);
        model.addAttribute("editUser", editUser);
        return "user/editUser";
    }

    //修改用户信息
    @RequestMapping("editUser")
    @ResponseBody
    public Boolean editUser(UserBean userBean){
      try {
       userServiceApi.editUser(userBean);
      }catch (Exception e){
       e.printStackTrace();
       return false;
      }
        return true;
    }

    //删除用户信息
    @RequestMapping("deleteUser")
    @ResponseBody
    public String deleteUser(Integer userId){
        userServiceApi.deleteUser(userId);
        return "{}";
    }


 //分页查询用户表的所有信息
 @RequestMapping("queryUserListAndLimit")
 @ResponseBody
 public String queryUserListAndLimit (@RequestParam(value="page",defaultValue="1",required=true)int page, @RequestParam(value="limit",defaultValue="10",required=true)int limit){

  Map<String,Object> map= userServiceApi.queryUserListAndLimit(page,limit);
  List<UserBean> list = (List<UserBean>) map.get("rows");
  int count= (int) map.get("total");

  //list转成json
//		 JSONArray array =new JSONArray();
  JSONObject obj=new JSONObject();
  //前台通过key值获得对应的value值
  obj.put("code", 0);
  obj.put("msg", "");
  obj.put("count",count);
  obj.put("data",list);
  return obj.toString();

 }


}
