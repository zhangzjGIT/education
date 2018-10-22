package com.jk.service.user;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.jk.mapper.user.UserMapper;

import com.jk.model.ResultPage;
import com.jk.model.user.RoleBean;
import com.jk.model.user.Teacher;
import com.jk.model.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserServiceImpl implements UserServiceApi{

    @Autowired
    private UserMapper userDao;

    @Override
    @RequestMapping(value = "queryUserLoginInfo")
    public UserBean queryUserLoginInfo(@RequestBody UserBean userBean) {
        return userDao.queryUserLoginInfo(userBean.getLoginNumber());
    }

    @Override
    @RequestMapping(value = "queryUserList")
    public ResultPage queryUserList(@RequestBody UserBean userBean) {
        ResultPage resultPage = new ResultPage();
        userBean.calculate();
        Integer count = userDao.queryUserCount(userBean);
        List<UserBean> userList = userDao.queryUserList(userBean);
        resultPage.setTotal(count);
        resultPage.setRows(userList);
        return resultPage;
    }

    @Override
    @RequestMapping(value = "addUser")
    public void addUser(@RequestBody UserBean userBean) {
        userDao.addUser(userBean);
    }

    @Override
    @RequestMapping(value = "queryUserById")
    public UserBean queryUserById(@RequestParam(value = "userId") Integer userId) {
        return userDao.queryUserById(userId);
    }

    @Override
    @RequestMapping(value = "editUser")
    public void editUser(@RequestBody UserBean userBean) {
        userDao.editUser(userBean);
    }

    @Override
    public void deleteUser(Integer userId) {
        userDao.deleteUser(userId);
    }

    @Override
    public Map<String, Object> queryTeacher(@RequestParam(value = "page") int page,@RequestParam(value = "limit") int  limit) {

            int total=userDao.queryTeacherTotal();
            //2.起始位置：（当前页 - 1） *  每页条数
            int start=(page-1)*limit;
            //3.结束位置 = 起始位置 +  每页条数
            int end = start+limit;
            //4.获取对象集合
            List<Teacher>dlist=userDao.queryTeacher(start,end);
            //5.装进Map中，总条数，展示数据
            Map<String,Object>map=new HashMap<>();//实例化
            map.put("total",total);
            map.put("rows",dlist);
            map.put("page",page);
            map.put("limit",limit);
            return map;
        }

    @Override
    @RequestMapping(value = "updStatus")
    public void updStatus(@RequestBody Teacher teacher) {
        int status=teacher.getStatus();

        if(status==1){
            userDao.updStatusUp(teacher);
        }else {
            userDao.updStatusDown(teacher);
        }
    }

    @Override
    @RequestMapping(value = "queryRoleById")
    public RoleBean queryRoleById(@RequestParam(value = "userId")Integer userId) {
        return userDao.queryRoleById(userId);
    }

    @Override
    @RequestMapping(value = "updUser")
    public void updUser(@RequestBody UserBean userBean) {
        userDao.updUser(userBean);
    }


}
