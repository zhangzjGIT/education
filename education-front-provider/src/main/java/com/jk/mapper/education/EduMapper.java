package com.jk.mapper.education;

import com.jk.model.education.ClassBean;
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
    List<ClassBean> queryVideoList(@Param("classBean") ClassBean classBean);

    List<ClassBean> searchList(@Param("search")String search);

    List<ClassBean> searchmany(@Param("search")String search);
}
