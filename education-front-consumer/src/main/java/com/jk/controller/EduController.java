package com.jk.controller;


import com.jk.ConstantsConf;
import com.jk.OSSClientUtil;
import com.jk.model.education.ClassBean;
import com.jk.model.education.MessageBean;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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

    @RequestMapping("agLogin")
    public String agLogin(HttpServletRequest request){
        request.getSession().removeAttribute("userInfo");
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
    public String toList(ClassBean classBean, ModelMap md,HttpServletRequest request){
       List<ClassBean> list =  eduService.queryVideoList(classBean);
       List<MessageBean> cou = eduService.queryHotList();
      User user = (User) request.getSession().getAttribute("userInfo");
      if(user!=null){
          md.put("codes",1);
          md.put("user",user);
          md.put("list",list);
          md.put("cou",cou);
      }else{
          User uu = new User();
          uu.setImg("../images/logo.jpg");
          uu.setUserName("无");
          md.put("codes",2);
          md.put("user",uu);
          md.put("list",list);
          md.put("cou",cou);
      }

        return "list";
    }

    @RequestMapping("searchList")
    public String searchList(String search, ModelMap md,HttpServletRequest request){
        List<ClassBean> list =  eduService.searchList(search);
        List<MessageBean> cou = eduService.queryHotList();
        User user = (User) request.getSession().getAttribute("userInfo");
        md.put("user",user);
        md.put("list",list);
        md.put("cou",cou);
        return "list";
    }
    @RequestMapping("priceType")
    public String priceType(String search, ModelMap md,HttpServletRequest request){
        List<ClassBean> list =  eduService.priceType(search);
        List<MessageBean> cou = eduService.queryHotList();
        User user = (User) request.getSession().getAttribute("userInfo");
        md.put("user",user);
        md.put("list",list);
        md.put("cou",cou);
        return "list";
    }
    @RequestMapping("searchmany")
    public String searchmany(String search, ModelMap md,HttpServletRequest request){
        List<ClassBean> list =  eduService.searchmany(search);
        List<MessageBean> cou = eduService.queryHotList();
        User user = (User) request.getSession().getAttribute("userInfo");
        md.put("user",user);
        md.put("list",list);
        md.put("cou",cou);
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
            request.getSession().setAttribute("userInfo", user);
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
      }


    @RequestMapping("querydeils")
    public String querydeils(MessageBean messageBean, ModelMap modelMap,HttpServletRequest request){
        MessageBean cl = eduService.querydeils(messageBean);
        modelMap.put("cl",cl);
        /*HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("UserId");
        User us=eduService.queryuser(userId);
        modelMap.put("us",us);*/
        return "deils/deils";
    }

    @RequestMapping("quitId")
    public String quitId(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        return "";
    }

    @RequestMapping("collect")
    @ResponseBody
    public Boolean updateCollect(String courseId){
        try {
            eduService.updateCollect(courseId);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping("enroll")
    public String enroll(ModelMap modelMap,HttpServletRequest request){
        /*HttpSession session = request.getSession();
        String couTitleId = (String) session.getAttribute(session.getId());
        MessageBean messageBean=eduService.queryMess(couTitleId);
        modelMap.put("me",messageBean);*/
        return "deils/enrollThor";
    }

    @RequestMapping("upImg")
    @ResponseBody
    public HashMap<String, Object> headImgUpload(MultipartFile file) throws IOException {

        if (file == null || file.getSize() <= 0) {
            throw new IOException("file不能为空");
        }
        file.getSize();
        OSSClientUtil ossClient=new OSSClientUtil();
        String name = ossClient.uploadImg2Oss(file);
        String imgUrl = ossClient.getImgUrl(name);
        HashMap<String, Object> map=new HashMap<>();
        //文件存储的路径
        map.put("aaa", imgUrl);
        return map ;
    }

    @RequestMapping("toPersonal")
    public String toPersonal(ModelMap mm){
        /*HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("UserId");
        User us=eduService.queryuser(userId);
        modelMap.put("user",us);*/
        return "deils/personal";
    }

    @RequestMapping("updateMessage")
    @ResponseBody
    public Boolean updateMessage(MessageBean messageBean,HttpServletRequest request){
        try {
            eduService.updateMessage(messageBean);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping("addCourse")
    @ResponseBody
    public Boolean addCourse(MessageBean messageBean,HttpServletRequest request){
        try {
            eduService.addCourse(messageBean);
            HttpSession session = request.getSession();
            session.setAttribute(session.getId(),messageBean.getCouTitle());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
