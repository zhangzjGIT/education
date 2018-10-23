package com.jk.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.jk.model.ResultPage;
import com.jk.model.user.MessageBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.Teacher;
import com.jk.model.user.UserBean;
import com.jk.service.user.UserServiceApi;
import com.jk.utils.Md5Util;
import com.jk.utils.OSSClientUtil;
import com.jk.utils.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 类描述：  教师审批
     * 创建人：LDW
     * 创建时间：2018/10/18 11:51
     * 修改人：LDW
     * 修改时间：2018/10/18 11:51
     * 修改备注：
     * @version ：1.0
     */
    @RequestMapping("toTeacher")
    public String toTeacher(){
        return "teacher/showTeacher";
    }

    @RequestMapping("queryTeacher")
    @ResponseBody
    public String queryTeacher (@RequestParam(value="page",defaultValue="1",required=true)int page,@RequestParam(value="limit",defaultValue="10",required=true)int limit){

        Map<String,Object> map= userServiceApi.queryTeacher(page,limit);
        List<Teacher> list = (List<Teacher>) map.get("rows");
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
    @RequestMapping("updStatus")
    @ResponseBody
    public String updStatus(Teacher teacher){
        userServiceApi.updStatus(teacher);
        return "{}";
    }
    /**
     * 类描述：  登录人信息
     * 创建人：LDW
     * 创建时间：2018/10/19 11:11
     * 修改人：LDW
     * 修改时间：2018/10/19 11:11
     * 修改备注：
     * @version ：1.0
     */
    @RequestMapping("toMyInfo")
    public String toMyInfo(Model model,Integer userId){
        UserBean editUser = userServiceApi.queryUserById(userId);
        RoleBean role=userServiceApi.queryRoleById(userId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = df.format(editUser.getBirthday());
        model.addAttribute("editUser", editUser);
        model.addAttribute("bir", formatDate);
        model.addAttribute("role", role);
        return "teacher/loginInfo";
    }


    @RequestMapping(value = "updUser",method = RequestMethod.POST)
    @ResponseBody
    public String updUser(UserBean userBean){
        userServiceApi.updUser(userBean);
        return "{}";
    }
    //oss图片上传
    @RequestMapping(value = "userImgUpload",method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, Object> headImgUpload(MultipartFile file) throws IOException {

        if (file == null || file.getSize() <= 0) {
            throw new IOException("file不能为空");
        }
        //获取文件的大小,单位/KB
        long size = file.getSize();
        OSSClientUtil ossClient = new OSSClientUtil();
        String name = ossClient.uploadImg2Oss(file);
        String imgUrl = ossClient.getImgUrl(name);
        HashMap<String, Object> map = new HashMap<>();
        //文件存储的路径
        map.put("name", imgUrl);

        return map;
    }
   /**
    * 类描述：课程审核
    * 创建人：LDW
    * 创建时间：2018/10/23 10:24
    * 修改人：LDW
    * 修改时间：2018/10/23 10:24
    * 修改备注：
    * @version ：1.0
    */
    @RequestMapping("toCourse")
    public String toCourse(){
        return "teacher/showClass";
    }

    @RequestMapping("queryCourse")
    @ResponseBody
    public String queryCourse (@RequestParam(value="page",defaultValue="1",required=true)int page,@RequestParam(value="limit",defaultValue="10",required=true)int limit){

        Map<String,Object> map= userServiceApi.queryCourse(page,limit);
        List<MessageBean> list = (List<MessageBean>) map.get("rows");
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

    @RequestMapping(value = "updClassStatus",method = RequestMethod.POST)
    @ResponseBody
    public String updClassStatus(MessageBean messageBean){
        userServiceApi.updClassStatus(messageBean);
        return "{}";
    }
}
