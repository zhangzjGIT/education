package com.jk.mapper.user;

import com.jk.model.user.NavBean;
import com.jk.model.user.NavRoleBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Component("userDao")
public interface UserMapper {

    UserBean queryUserLoginInfo(String loginNumber);

    /*Integer queryUserCount(@Param("userBean") UserBean userBean);

    List<UserBean> queryUserList(@Param("userBean") UserBean userBean);*/

    void addUser(UserBean userBean);

    UserBean queryUserById(@Param("userId") Integer userId);

    void editUser(UserBean userBean);

    void deleteUser(Integer userId);

    //根据用户Id查询所拥有的权限树
    List<NavBean> queryNavTreeByUserId(Integer userId);

    //查询角色表的总条数
    int getRolePageCount(@Param("roleBean")RoleBean roleBean);

    //查询角色表的所有信息
    List<RoleBean> getRolePage(@Param("roleBean")RoleBean roleBean);

    //新增角色表信息
    void addRole(RoleBean roleBean);

    //根据Id删除角色信息
    void deleteRole(Integer roleId);

    //根据Id查询一条信息回显
    RoleBean getRoleInfoById(Integer roleId);

    //根据Id修改角色表中的信息
    void updateRole(RoleBean roleBean);

    //根据角色Id查询角色树
    List<NavRoleBean> queryNavIdListByRoleId(Integer roleId);

    List<NavBean> queryRoleNavTree(Integer id);

    //根据角色Id删除原有的权限
    void deleteRoleNav(Integer roleId);

    //给角色赋新的权限
    void addRoleNav(ArrayList<NavRoleBean> arrayList);

    UserBean queryUserByLoginNumer(String userName);

    //layui的前台
    int queryUserTotal();

    List<UserBean> queryUser(@Param("start")int start, @Param("end")int end);
}
