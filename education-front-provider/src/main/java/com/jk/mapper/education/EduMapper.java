package com.jk.mapper.education;

import com.jk.model.education.MessageBean;
import com.jk.model.education.MessageBean;
import com.jk.model.education.TypeBean;
import com.jk.model.education.User;
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

    User queryUserOne(@Param("phoneNumber")String phoneNumber);

    MessageBean querydeils(MessageBean messageBean);

    void updateCollect(String courseId);

    User queryuser(String userId);

    void updateMessage(MessageBean messageBean);

    void addCourse(MessageBean messageBean);

    MessageBean queryMess(String couTitleId);

    List<MessageBean> queryHotList();
}
