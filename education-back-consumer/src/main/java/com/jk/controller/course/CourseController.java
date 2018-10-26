package com.jk.controller.course;

import com.alibaba.fastjson.JSONObject;
import com.jk.model.ResultPage;
import com.jk.model.course.LogBean;
import com.jk.model.course.MessageBean;
import com.jk.model.user.UserBean;
import com.jk.service.course.CourseServiceApi;
import com.jk.utils.SessionUserUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class CourseController {

    @Autowired
    private CourseServiceApi courseServiceApi;

    @Autowired
    private MongoTemplate mongoTemplate;


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
        JSONObject obj = new JSONObject();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", count);
        obj.put("data", list);
        return obj.toString();
    }


    //密码回显
    @RequestMapping("toEditPass")
    public String toEditPass(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        UserBean user = (UserBean) session.getAttribute(session.getId());
        UserBean userInfo = courseServiceApi.queryPwdById(user);
        model.addAttribute("user", userInfo);
        return "course/editPass";
    }


    //密码修改
    @RequestMapping("editPass")
    @ResponseBody
    public Boolean editPass(UserBean userBean, HttpServletRequest request) {
        try {
            /*HashMap<String, Object> result = new HashMap<>();
            HttpSession session = request.getSession();
            UserBean userInfo = new UserBean();*/
            courseServiceApi.editPass(userBean);
            /*if (!userInfo.getPassword().equals(userBean.getPassword())) {
                    result.put("msg", "密码错误！");
            }*/
        } catch (Exception e) {

            return false;
        }
        return true;


    }

    //Heichars报表
    @RequestMapping("queryTypeHigh")
    @ResponseBody
    public List<Map<String, Object>> queryTypeHigh() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> listType = courseServiceApi.queryTypeHigh();
        for (Map<String, Object> map : listType) {
            Map<String, Object> maps = new HashMap<>();
            Integer object = (Integer) map.get("课程分类");
            if (object == 1) {
                maps.put("name", "IT▪互联网");
            }
            if (object == 2) {
                maps.put("name", "设计▪创作");
            }
            if (object == 3) {
                maps.put("name", "语言▪留学");
            }
            if (object == 4) {
                maps.put("name", "职业▪考证");
            }
            if (object == 5) {
                maps.put("name", "升学▪考研");
            }
            if (object == 6) {
                maps.put("name", "兴趣▪生活");
            }
            maps.put("y", map.get("所有分类"));
            maps.put("sliced", false);
            maps.put("selected", false);
            list.add(maps);
        }
        return list;
    }


    //跳转到日志列表页面
    @RequestMapping("toLogPage")
    public String toLogPage() {
        return "course/logPage";
    }

    //日志监控
    @RequestMapping("getUserLog")
    @ResponseBody
    public String getUserlog(HttpServletRequest request, LogBean logbean,@RequestParam(value = "page", defaultValue = "1", required = true) int page, @RequestParam(value = "limit", defaultValue = "10", required = true) int limit) {
        ResultPage resultPage = new ResultPage();
        Query query = new Query();

        int count = (int) mongoTemplate.count(query,LogBean.class);
        resultPage.setTotal(count);
        query.skip((page-1)*limit);
        query.limit(limit);
        List<LogBean> find =  mongoTemplate.find(query,LogBean.class);
        resultPage.setRows(find);
        JSONObject obj = new JSONObject();
        //前台通过key值获得对应的value值
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", count);
        obj.put("data",find);
        return obj.toString();
    }


}
