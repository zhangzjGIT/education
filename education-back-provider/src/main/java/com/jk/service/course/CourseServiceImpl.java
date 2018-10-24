package com.jk.service.course;

import com.jk.mapper.course.CourseMapper;
import com.jk.mapper.user.UserMapper;
import com.jk.model.advertising.Advertising;
import com.jk.model.course.CourseBean;
import com.jk.model.course.MessageBean;
import com.jk.model.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseServiceImpl implements CourseServiceApi {

    @Autowired
    private CourseMapper courseDao;

    @Override
        public Map<String, Object> queryCoursePage(@RequestParam(value = "page")int page, @RequestParam(value = "limit") int limit) {
            int total=courseDao.queryCourseTotal();
            //2.起始位置：（当前页 - 1） *  每页条数
            int start=(page-1)*limit;
            //3.结束位置 = 起始位置 +  每页条数
            int end = start+limit;
            //4.获取对象集合
            List<MessageBean> dlist=courseDao.queryCourse(start,end);
            //5.装进Map中，总条数，展示数据
            Map<String,Object>map=new HashMap<>();//实例化
            map.put("total",total);
            map.put("rows",dlist);
            map.put("page",page);
            map.put("limit",limit);
            return map;
    }

    @Override
    @RequestMapping(value = "course/toEditPass")
    public UserBean queryPwdById(@RequestBody UserBean user) {
        return courseDao.queryPwdById(user);
    }

    @Override
    @RequestMapping(value = "course/editPass")
    public void editPass(@RequestBody UserBean userBean) {
        courseDao.editPass(userBean);
    }
}
