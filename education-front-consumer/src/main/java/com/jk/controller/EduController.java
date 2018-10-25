package com.jk.controller;


import com.jk.ConstantsConf;
import com.jk.model.education.ClassBean;
import com.jk.model.education.MessageBean;
import com.jk.model.education.TypeBean;
import com.jk.model.education.User;
import com.jk.service.EduService;
import com.jk.utils.HttpClientUtil;
import com.jk.utils.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.jk.utils.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @Auther: yangjianguang
 * @Date: 2018/10/16 21:16
 * @Description:
 */
@Controller
@RequestMapping("user")
public class EduController {
    @Autowired
    private EduService eduService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("toregister")
    public String toregister(){
        return "register";
    }

    /**
     * 跳转到视频播放列表
     */
   @RequestMapping("tovideolist")
    public String tovideolist(){
        return "videolist";
    }

   /* @RequestMapping("getImg")
    public void getImg(String id, HttpServletRequest request, HttpServletResponse response) {

        GridFSDBFile file = eduService.getImgById(id);

        try {
            if (file != null) {
                OutputStream sos;
                sos = response.getOutputStream();
                response.setContentType("application/octet-stream");
                file.writeTo(sos);
                sos.flush();
                sos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 跳转到视频列表
     * @param md
     * @return
     */
    @RequestMapping("toList")
    public String toList(ClassBean classBean, ModelMap md){
       List<ClassBean> list =  eduService.queryVideoList(classBean);
       md.put("list",list);
       return "list";
    }

    @RequestMapping("searchList")
    public String searchList(String search, ModelMap md){
        List<ClassBean> list =  eduService.searchList(search);
        md.put("list",list);
        return "list";
    }
    @RequestMapping("priceType")
    public String priceType(String search, ModelMap md){
        List<ClassBean> list =  eduService.priceType(search);
        md.put("list",list);
        return "list";
    }
    @RequestMapping("searchmany")
    public String searchmany(String search, ModelMap md){
        List<ClassBean> list =  eduService.searchmany(search);
        md.put("list",list);
        return "list";
    }
    @RequestMapping("toinfo")
    public String toinfo(){

        return "info";
    }
    @RequestMapping("tomain")
    public String tomain(ModelMap md) {
        List<TypeBean> typelist = eduService.queryCLassTypeList();
        List<MessageBean> mesList = eduService.queryClassByTypeId();
        md.put("mesList", mesList);
        md.put("typelist", typelist);
        return "index";
    }

   /* @RequestMapping("queryClassByTypeId")
    public String queryClassByTypeId(Integer typeId,ModelMap md){
        List<ClassBean> classList = eduService.queryClassByTypeId(typeId);
        if (classList != null) {

        }
        return "index";
    }*/
    }

    @RequestMapping("sendSMS")
    @ResponseBody
    public Boolean sendSMS(String phone) {
        try {
            if(StringUtils.isNotEmpty(phone)) {
                //缓存验证码key 为了保证唯一 特定字符加上手机号
                String cacheCodeKey = ConstantsConf.Login_Code + phone;
                //缓存1分钟锁key
                String cacheFlagKey =  ConstantsConf.Login_Code_Flag + phone;
                //判断证号距离上一次获取是否距离1分钟
                String lock = redisTemplate.opsForValue().get(cacheFlagKey);
                if(StringUtils.isEmpty(lock)) {
                    int random = (int) ((Math.random()*9+1)*100000);
                    HashMap<String,Object> params = new HashMap<String, Object>();
                    params.put("accountSid", ConstantsConf.ACCOUNTSID);
                    params.put("templateid", ConstantsConf.TEMPLATEID);
                    params.put("param", ""+random+"");
                    params.put("to", phone);
                    String  timestamp = TimeUtil.format(new Date());
                    params.put("timestamp", timestamp);
                    String sign = Md5Util.getMd532(ConstantsConf.ACCOUNTSID+ConstantsConf.AUTH_TOKEN+timestamp);
                    params.put("sig", sign);
                    HttpClientUtil.post(ConstantsConf.SMS_URL,params);
                    redisTemplate.opsForValue().set(cacheCodeKey, ""+random+"", ConstantsConf.Login_Code_Time_OUT, TimeUnit.MINUTES);
                    redisTemplate.opsForValue().set(cacheFlagKey, "lock", 1, TimeUnit.MINUTES);
                }
                else {
                    System.out.println("1分钟内不能重复获取验证码");
                    return false;
                }
            }
            else {
                System.out.println("手机号不能为空");
                return false;
            }

        } catch (Exception e) {
            System.out.println("异常"+e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 登陆
     */
    @ResponseBody
    @RequestMapping("login")
    public Boolean login(String phone, String password, HttpServletRequest request){
        try {
            User user = eduService.login(phone);
            if(user==null){
                return false;
            }
            if(!password.equals(user.getPassword())){
                return false;
            }
            request.getSession().setAttribute("userId", user.getId());
            return true;
        } catch (Exception e) {
            return false;
        }




    }
    @RequestMapping("adduser")
    @ResponseBody
    public Map<String, Object> addusername(User user, HttpServletRequest request, String numPhone){
        HashMap<String,Object> result = new HashMap<String, Object>();
     /* User userone = eduService.queryUserOne(user.getPhoneNumber());
        if(userone==null){*/
            String password = user.getPassword();
            if(password==null || password=="") {
                result.put("code", 1);
                result.put("msg","密码不能为空");
                return result;
            }
            if(numPhone==null || numPhone=="") {
                result.put("code", 2);
                result.put("msg","验证码不能为空");
                return result;
            }
            String cacheCodeKey = ConstantsConf.Login_Code + user.getPhoneNumber();
            String randomcode = redisTemplate.opsForValue().get(cacheCodeKey);
            if(!numPhone.equals(randomcode)){
                result.put("code", 3);
                result.put("msg","验证码错误");
                return result;
            }
            eduService.adduser(user);
            result.put("code", 0);
            result.put("msg","注册成功");
            return result;
      }/*else{
           result.put("code", 4);
           result.put("msg","注册失败");
           return result;
      }

   }*/

}
