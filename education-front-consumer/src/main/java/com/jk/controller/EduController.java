package com.jk.controller;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.jk.ConstantsConf;
import com.jk.OSSClientUtil;
import com.jk.config.AlipayConfig;
import com.jk.model.education.MessageBean;
import com.jk.model.education.Teacher;
import com.jk.model.education.TypeBean;
import com.jk.model.education.User;
import com.jk.service.EduService;
import com.jk.utils.HttpClientUtil;
import com.jk.utils.Md5Util;
import com.jk.utils.UuidUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.jk.utils.TimeUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public String toLogin() {
        return "login";
    }

    @RequestMapping("agLogin")
    public String agLogin(HttpServletRequest request) {
        request.getSession().removeAttribute("userInfo");
        return "login";
    }

    @RequestMapping("toregister")
    public String toregister() {
        return "register";
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
     *
     * @param md
     * @return
     */
    @RequestMapping("toList")
    public String toList(ModelMap md, HttpServletRequest request) {
        List<MessageBean> list = eduService.queryVideoList();
        List<MessageBean> cou = eduService.queryHotList();
        User user = (User) request.getSession().getAttribute("userInfo");
        if (user != null) {
            md.put("codes", 1);
            md.put("user", user);
            md.put("list", list);
            md.put("cou", cou);
        } else {
            User uu = new User();
            uu.setImg("../images/logo.jpg");
            uu.setUserName("无");
            md.put("codes", 2);
            md.put("user", uu);
            md.put("list", list);
            md.put("cou", cou);
        }

        return "list";
    }

    @RequestMapping("searchList")
    public String searchList(String search, ModelMap md, HttpServletRequest request) {
        List<MessageBean> list = eduService.searchList(search);
        List<MessageBean> cou = eduService.queryHotList();
        User user = (User) request.getSession().getAttribute("userInfo");
        if (user != null) {
            md.put("codes", 1);
            md.put("user", user);
        } else {
            User uu = new User();
            uu.setImg("../images/logo.jpg");
            uu.setUserName("无");
            md.put("codes", 2);
            md.put("user", uu);
        }

        md.put("list", list);
        md.put("cou", cou);
        return "list";
    }

    @RequestMapping("priceType")
    public String priceType(String search, ModelMap md, HttpServletRequest request) {
        List<MessageBean> list = eduService.priceType(search);
        List<MessageBean> cou = eduService.queryHotList();
        User user = (User) request.getSession().getAttribute("userInfo");
        if (user != null) {
            md.put("codes", 1);
            md.put("user", user);
        } else {
            User uu = new User();
            uu.setImg("../images/logo.jpg");
            uu.setUserName("无");
            md.put("codes", 2);
            md.put("user", uu);
        }

        md.put("list", list);
        md.put("cou", cou);
        return "list";
    }

    @RequestMapping("searchmany")
    public String searchmany(String search, ModelMap md, HttpServletRequest request) {
        List<MessageBean> list = eduService.searchmany(search);
        List<MessageBean> cou = eduService.queryHotList();
        User user = (User) request.getSession().getAttribute("userInfo");
        if (user != null) {
            md.put("codes", 1);
            md.put("user", user);
        } else {
            User uu = new User();
            uu.setImg("../images/logo.jpg");
            uu.setUserName("无");
            md.put("codes", 2);
            md.put("user", uu);
        }

        md.put("list", list);
        md.put("cou", cou);
        return "list";
    }

    @RequestMapping("tomain")
    public String tomain(ModelMap md, HttpServletRequest request) {
        List<TypeBean> typelist = eduService.queryCLassTypeList();
        List<MessageBean> mesList = eduService.queryClassByTypeId();
        md.put("mesList", mesList);
        md.put("typelist", typelist);
        User user = (User) request.getSession().getAttribute("userInfo");
        String teaId = user.getId();
        Teacher teacher = eduService.queryTeacherById(teaId);
        md.put("tea", teacher);
        if (user != null) {
            md.put("codes", 1);
            md.put("user", user);
        } else {
            User uu = new User();
            uu.setImg("../images/logo.jpg");
            uu.setUserName("无");
            md.put("codes", 2);
            md.put("user", uu);
        }
        return "index";
    }

    @RequestMapping("sendSMS")
    @ResponseBody
    public Boolean sendSMS(String phone) {
        try {
            if (StringUtils.isNotEmpty(phone)) {
                //缓存验证码key 为了保证唯一 特定字符加上手机号
                String cacheCodeKey = ConstantsConf.Login_Code + phone;
                //缓存1分钟锁key
                String cacheFlagKey = ConstantsConf.Login_Code_Flag + phone;
                //判断证号距离上一次获取是否距离1分钟
                String lock = redisTemplate.opsForValue().get(cacheFlagKey);
                if (StringUtils.isEmpty(lock)) {
                    int random = (int) ((Math.random() * 9 + 1) * 100000);
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("accountSid", ConstantsConf.ACCOUNTSID);
                    params.put("templateid", ConstantsConf.TEMPLATEID);
                    params.put("param", "" + random + "");
                    params.put("to", phone);
                    String timestamp = TimeUtil.format(new Date());
                    params.put("timestamp", timestamp);
                    String sign = Md5Util.getMd532(ConstantsConf.ACCOUNTSID + ConstantsConf.AUTH_TOKEN + timestamp);
                    params.put("sig", sign);
                    HttpClientUtil.post(ConstantsConf.SMS_URL, params);
                    redisTemplate.opsForValue().set(cacheCodeKey, "" + random + "", ConstantsConf.Login_Code_Time_OUT, TimeUnit.MINUTES);
                    redisTemplate.opsForValue().set(cacheFlagKey, "lock", 1, TimeUnit.MINUTES);
                } else {
                    System.out.println("1分钟内不能重复获取验证码");
                    return false;
                }
            } else {
                System.out.println("手机号不能为空");
                return false;
            }

        } catch (Exception e) {
            System.out.println("异常" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 登陆
     */
    @ResponseBody
    @RequestMapping("login")
    public Boolean login(String phone, String password, HttpServletRequest request) {
        try {
            User user = eduService.login(phone);
            if (user == null) {
                return false;
            }
            if (!password.equals(user.getPassword())) {
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
    public Map<String, Object> addusername(User user, HttpServletRequest request, String numPhone) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        String password = user.getPassword();
        if (password == null || password == "") {
            result.put("code", 1);
            result.put("msg", "密码不能为空");
            return result;
        }
        if (numPhone == null || numPhone == "") {
            result.put("code", 2);
            result.put("msg", "验证码不能为空");
            return result;
        }
        String cacheCodeKey = ConstantsConf.Login_Code + user.getPhoneNumber();
        String randomcode = redisTemplate.opsForValue().get(cacheCodeKey);
        if (!numPhone.equals(randomcode)) {
            result.put("code", 3);
            result.put("msg", "验证码错误");
            return result;
        }
        eduService.adduser(user);
        result.put("code", 0);
        result.put("msg", "注册成功");
        return result;
    }


    @RequestMapping("querydeils")
    public String querydeils(MessageBean messageBean, ModelMap modelMap, HttpServletRequest request) {
        MessageBean cl = eduService.querydeils(messageBean);
        modelMap.put("cl", cl);
        String teaId = cl.getTeaId();
        Teacher teacher = eduService.queryTeacherById(teaId);
        modelMap.put("teacher", teacher);
        User user = (User) request.getSession().getAttribute("userInfo");
        User us = eduService.queryuser(user.getId());
        if (user != null) {
            modelMap.put("codes", 1);
            modelMap.put("user", us);
        } else {
            User uu = new User();
            uu.setImg("../images/logo.jpg");
            uu.setUserName("无");
            modelMap.put("codes", 2);
            modelMap.put("user", uu);
        }
        return "deils/deils";
    }

    @RequestMapping("quitId")
    public String quitId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        return "";
    }

    @RequestMapping("collect")
    @ResponseBody
    public Boolean updateCollect(String courseId) {
        try {
            eduService.updateCollect(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping("upImg")
    @ResponseBody
    public HashMap<String, Object> headImgUpload(MultipartFile file) throws IOException {

        if (file == null || file.getSize() <= 0) {
            throw new IOException("file不能为空");
        }
        file.getSize();
        OSSClientUtil ossClient = new OSSClientUtil();
        String name = ossClient.uploadImg2Oss(file);
        String imgUrl = ossClient.getImgUrl(name);
        HashMap<String, Object> map = new HashMap<>();
        //文件存储的路径
        map.put("aaa", imgUrl);
        return map;
    }

    //跳转到发布课程页面
    @RequestMapping("toPersonal")
    public String toPersonal(ModelMap modelMap,HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        String userId = user.getId();
        User us=eduService.queryuser(userId);
        modelMap.put("user",us);
        return "deils/personal";
    }

    //发布课程
    @RequestMapping("addCourse")
    @ResponseBody
    public Boolean addCourse(MessageBean messageBean, HttpServletRequest request) {
        try {
            messageBean.setCouId(UuidUtil.getUUId());
            eduService.addCourse(messageBean);
            HttpSession session = request.getSession();
            session.setAttribute(session.getId(), messageBean.getCouId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @Title: AlipayController.java
     * @Package com.sihai.controller
     * @Description: 前往支付宝第三方网关进行支付
     * Copyright: Copyright (c) 2017
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
     * @author sihai
     * @date 2017年8月23日 下午8:50:43
     * @version V1.0
     */
    @RequestMapping(value = "/goAlipay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String goAlipay(String couId, HttpServletRequest request, HttpServletRequest response) throws Exception {
        //购买前新增数据到课程用户关联表
        User user = (User) request.getSession().getAttribute("userInfo");
        eduService.addClassUser(couId,user.getId());

        MessageBean buyInfo = eduService.getBuyInfo(couId);

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = couId;
        //付款金额，必填
        String price = buyInfo.getCouPrice().toString();
        String total_amount = price;
        //订单名称，必填
        String subject = buyInfo.getInfoName();
        //商品描述，可空
        String body = buyInfo.getCouState();

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

    //跳转到播放视频页面
    @RequestMapping("showVideo")
    public String showVideo(String couInfo,String couId,String infoName, ModelMap modelMap,HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        eduService.addClassUser(couId,user.getId());
        modelMap.put("couInfo", couInfo);
        modelMap.put("infoName", infoName);
        return "deils/shipin";
    }

    //讲师跳转到直播页面
    @RequestMapping("toZhibo")
    public String toZhibo() {
        return "deils/zhibo";
    }
    //学生跳转到参与直播页面
    @RequestMapping("toJoinZhibo")
    public String toJoinZhibo() {
        return "joinZhibo";
    }

    //跳转到教师信息注册页面
    @RequestMapping("toRegiTeacher")
    private String toRegiTeacher(HttpServletRequest request,ModelMap mm){
        User user = (User) request.getSession().getAttribute("userInfo");
        mm.put("users",user);
        return "deils/regiTeacher";
    }

    //教师信息注册
    @RequestMapping("addTeacher")
    @ResponseBody
    public Boolean addTeacher(Teacher teacher,String userId){
        try {
            teacher.setTeacherId(userId);
            eduService.addTeacher(teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping("toClasses")
    public String toClasses(HttpServletRequest request, ModelMap md){
        User user = (User) request.getSession().getAttribute("userInfo");
        if (user != null) {
            List<MessageBean> mes = eduService.getMesByUserId(user.getId());
            md.put("codes", 1);
            md.put("user", user);
            md.put("mes",mes);
            return "classes";

        } else {
            User uu = new User();
            md.put("codes", 2);
            md.put("user", uu);
            return "index";
        }
    }

    //取消课程报名
    @RequestMapping("deleteUserMes")
    @ResponseBody
    public Boolean deleteUserMes(Integer userMesId){
        try {
            eduService.deleteUserMes(userMesId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}