package com.jk.controller.course;

import com.alibaba.fastjson.JSONObject;
import com.jk.model.advertising.Advertising;
import com.jk.model.course.CourseBean;
import com.jk.model.course.MessageBean;
import com.jk.model.user.UserBean;
import com.jk.service.course.CourseServiceApi;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class CourseController {

    @Autowired
    private CourseServiceApi courseServiceApi;


    /**
     * 跳转到查询页面
     */
    @RequestMapping("toCoursePage")
    public String toAdverPage() {
        return "course/courseList";
    }

    /**
     * 查询列表
     */
    @RequestMapping("queryCoursePage")
    @ResponseBody
    public String queryCoursePage(@RequestParam(value = "page", defaultValue = "1", required = true) int page, @RequestParam(value = "limit", defaultValue = "10", required = true) int limit) {
        Map<String, Object> map = courseServiceApi.queryCoursePage(page, limit);

        List<MessageBean> list = (List<MessageBean>) map.get("rows");
        int count = (int) map.get("total");

        //list转成json
//		 JSONArray array =new JSONArray();
        JSONObject obj = new JSONObject();
        //前台通过key值获得对应的value值
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", count);
        obj.put("data", list);
        return obj.toString();
    }


    //密码回显
    @RequestMapping("toEditPass")
    public String toEditPass(Model model){
        Session session = SecurityUtils.getSubject().getSession();
        UserBean user = (UserBean) session.getAttribute(session.getId());
        UserBean userInfo = courseServiceApi.queryPwdById(user);
        model.addAttribute("user",userInfo);
        return "course/editPass";
    }


    //密码修改
    @RequestMapping("editPass")
    @ResponseBody
    public Boolean editPass(UserBean userBean) {
        try {
            courseServiceApi.editPass(userBean);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
