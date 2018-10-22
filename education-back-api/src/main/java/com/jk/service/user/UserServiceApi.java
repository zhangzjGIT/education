package com.jk.service.user;

import com.jk.model.ResultPage;
import com.jk.model.user.RoleBean;
import com.jk.model.user.Teacher;
import com.jk.model.user.UserBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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

    @RequestMapping(value = "/queryTeacher",method = RequestMethod.POST)
    Map<String, Object> queryTeacher(@RequestParam(value = "page")int page, @RequestParam(value = "limit")int limit);

    @RequestMapping(value = "/updStatus",method = RequestMethod.POST)
    void updStatus(@RequestBody Teacher teacher);

    @RequestMapping(value = "/queryRoleById",method = RequestMethod.POST)
    RoleBean queryRoleById(@RequestParam(value = "userId")Integer userId);

    @RequestMapping(value = "/updUser",method = RequestMethod.POST)
    void updUser(@RequestBody UserBean userBean);
}
