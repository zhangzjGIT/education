package com.jk.mapper.education;

import com.jk.model.education.*;
import com.jk.model.education.MessageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: yangjianguang
 * @Date: 2018/10/17 10:39
 * @Description:
 */
@Mapper
@Component("eduMapper")
public interface EduMapper {
    List<MessageBean> queryVideoList();

    List<MessageBean> searchList(@Param("search")String search);

    List<MessageBean> searchmany(@Param("search")String search);

    List<TypeBean> queryCLassTypeList();

    List<MessageBean> queryClassByTypeId();

    List<MessageBean> priceType(@Param("search")String search);

    User login(@Param("phone")String phone);

    void adduser(@Param("user")User user);

    MessageBean querydeils(MessageBean messageBean);

    void updateCollect(String courseId);

    User queryuser(String userId);

    void addCourse(MessageBean messageBean);

    List<MessageBean> queryHotList();

    MessageBean getBuyInfo(String couId);

    Teacher queryTeacherById(String teaId);

    Integer queryMessageCountById(String teaId);

    void addTeacher(Teacher teacher);

    List<MessageBean> getMesByUserId(String id);

    void addClassUser(@Param("couId")String couId,@Param("id")String id);
}
