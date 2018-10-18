package com.jk.service.user;

import com.jk.model.ResultPage;
import com.jk.model.user.NavBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.UserBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserServiceApi {

    @RequestMapping(value = "/queryUserLoginInfo",method = RequestMethod.POST)
    UserBean queryUserLoginInfo(@RequestBody UserBean userBean);

    @RequestMapping(value = "/queryUserList",method = RequestMethod.POST)
    ResultPage queryUserList(@RequestBody UserBean userBean);

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    void addUser(@RequestBody UserBean userBean);

    @RequestMapping(value = "/queryUserById",method = RequestMethod.GET)
    UserBean queryUserById(@RequestParam(value = "userId") Integer userId);

    @RequestMapping(value = "/editUser",method = RequestMethod.POST)
    void editUser(@RequestBody UserBean userBean);

    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    void deleteUser(@RequestParam(value = "userId") Integer userId);

    //根据用户Id查询左边菜单树,展示该用户所拥有的权限
    @RequestMapping(value = "/queryNavTreeByUserId",method = RequestMethod.POST)
    List<NavBean> queryNavTreeByUserId(@RequestParam(value = "userId")Integer userId);

    //获取角色表的所有信息
    @RequestMapping(value = "/getRolePage",method = RequestMethod.POST)
    ResultPage getRolePage(@RequestBody RoleBean roleBean);

    //新增角色信息
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    void addRole(@RequestBody RoleBean roleBean);

    //根据Id删除角色表中的一条信息
    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    void deleteRole(@RequestParam(value = "roleId") Integer roleId);

    //根据Id查询一条信息回显到修改页面
    @RequestMapping(value = "/getRoleInfoById",method = RequestMethod.POST)
    RoleBean getRoleInfoById(@RequestParam(value = "roleId") Integer roleId);

    //根据Id修改角色表中的数据
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    void updateRole(@RequestBody RoleBean roleBean);

    //根据角色ID查询相应的权限
    @RequestMapping(value = "/queryRoleNavTree",method = RequestMethod.POST)
    List<NavBean> queryRoleNavTree(@RequestParam(value = "roleId")  Integer roleId);

    //保存角色赋权限
    @RequestMapping(value = "/addRoleNav",method = RequestMethod.POST)
    void addRoleNav(@RequestParam(value = "roleId") Integer roleId,@RequestParam(value = "ids") String ids);

    @RequestMapping(value = "/queryUserByLoginNumer",method = RequestMethod.POST)
    UserBean queryUserByLoginNumer(@RequestParam(value="userName") String userName);

}
