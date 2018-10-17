package com.jk.service.user;

import com.jk.mapper.user.UserMapper;

import com.jk.model.ResultPage;
import com.jk.model.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
