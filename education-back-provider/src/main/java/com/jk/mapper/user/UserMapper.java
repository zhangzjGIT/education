package com.jk.mapper.user;

import com.jk.model.user.RoleBean;
import com.jk.model.user.Teacher;
import com.jk.model.user.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


import java.util.List;

@Mapper
@Component("userDao")
public interface UserMapper {

    UserBean queryUserLoginInfo(String loginNumber);

    Integer queryUserCount(@Param("userBean") UserBean userBean);

    List<UserBean> queryUserList(@Param("userBean") UserBean userBean);

    void addUser(UserBean userBean);

    UserBean queryUserById(Integer userId);

    void editUser(UserBean userBean);

    void deleteUser(Integer userId);

    int queryTeacherTotal();

    List<Teacher> queryTeacher(@Param("st")int start, @Param("end")int end);

    void updStatusUp(Teacher teacher);

    void updStatusDown(Teacher teacher);

    RoleBean queryRoleById(Integer userId);

    void updUser(UserBean userBean);
}
