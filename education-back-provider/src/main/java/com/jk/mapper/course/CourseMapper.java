package com.jk.mapper.course;

import com.jk.model.course.CourseBean;
import com.jk.model.course.MessageBean;
import com.jk.model.user.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("courseDao")
public interface CourseMapper {
    int queryCourseTotal();

    List<MessageBean> queryCourse(@Param("start") int start, @Param("end") int end);

    UserBean queryPwdById(UserBean user);

    void editPass(UserBean userBean);
}
