package com.jk.mapper.deils;

import com.jk.model.education.ClassBean;
import com.jk.model.education.CourseBean;
import com.jk.model.education.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface DeilsMapper {

    CourseBean querydeils(CourseBean courseBean);

    void updateCollect(String courseId);

    User queryuser(String userId);

}
