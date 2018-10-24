package com.jk.controller.user;

import com.jk.model.ResultPage;
import com.jk.model.user.UserBean;
import com.jk.service.user.UserServiceApi;
import com.jk.utils.Md5Util;
import com.jk.utils.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserServiceApi userServiceApi;

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login/login";
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

    @RequestMapping("toLayout")
    public String toLayout(){
        return "login/main";
    }

    @RequestMapping("toUserPage")
    public String toUserPage(){
        return "user/userList";
    }

    @RequestMapping("queryUserList")
    @ResponseBody
    public ResultPage queryUserList(UserBean userBean){
        ResultPage resultPage =userServiceApi.queryUserList(userBean);
        return resultPage;
    }

    @RequestMapping("toAddUser")
    public String toAddUser(){
        return "user/addUser";
    }

    @RequestMapping("addUser")
    @ResponseBody
    public String addUser(UserBean userBean){
        userServiceApi.addUser(userBean);
        return "{}";
    }

    @RequestMapping("toEditUser")
    public String toEditUser(Model model, Integer userId){
        UserBean editUser = userServiceApi.queryUserById(userId);
        model.addAttribute("editUser", editUser);
        return "user/editUser";
    }

    @RequestMapping("editUser")
    @ResponseBody
    public String editUser(UserBean userBean){
        userServiceApi.editUser(userBean);
        return "{}";
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public String deleteUser(Integer userId){
        userServiceApi.deleteUser(userId);
        return "{}";
    }



}
