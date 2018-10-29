package com.jk.service.course;

import com.jk.model.ResultPage;
import com.jk.model.course.LogBean;
import com.jk.model.user.UserBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface CourseServiceApi {

    //查询
    @RequestMapping(value = "user/queryCoursePage",method = RequestMethod.POST)
    Map<String, Object> queryCoursePage(@RequestParam(value = "page") int page, @RequestParam(value = "limit") int limi);

    //修改密码回显
    @RequestMapping(value= "user/toEditPass",method = RequestMethod.POST)
    UserBean queryPwdById(@RequestBody UserBean user);

    //修改密码
    @RequestMapping(value= "user/editPass",method = RequestMethod.POST)
    void editPass(@RequestBody UserBean userBean);

    //Heichars报表
    @RequestMapping(value = "user/queryTypeHigh",method = RequestMethod.POST)
    List<Map<String, Object>> queryTypeHigh();




}
