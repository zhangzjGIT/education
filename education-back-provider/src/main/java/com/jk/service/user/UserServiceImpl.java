package com.jk.service.user;

import com.jk.mapper.user.UserMapper;
import com.jk.model.ResultPage;
import com.jk.model.user.NavBean;
import com.jk.model.user.NavRoleBean;
import com.jk.model.user.RoleBean;
import com.jk.model.user.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class UserServiceImpl implements UserServiceApi{

    @Autowired
    private UserMapper userDao;

    @Override
    @RequestMapping(value = "queryUserLoginInfo")
    public UserBean queryUserLoginInfo(@RequestBody UserBean userBean) {
        return userDao.queryUserLoginInfo(userBean.getLoginNumber());
    }

    /*@Override
    @RequestMapping(value = "queryUserList")
    public ResultPage queryUserList(@RequestBody UserBean userBean) {
        ResultPage resultPage = new ResultPage();
        userBean.calculate();
        Integer count = userDao.queryUserCount(userBean);
        List<UserBean> userList = userDao.queryUserList(userBean);
        resultPage.setTotal(count);
        resultPage.setRows(userList);
        return resultPage;
    }*/

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
     @RequestMapping(value = "queryNavTreeByUserId")
     public List<NavBean> queryNavTreeByUserId(@RequestParam(value = "userId") Integer userId) {
      List<NavBean> navs = userDao.queryNavTreeByUserId(userId);
      return navs;
     }

    @Override
    @RequestMapping(value = "getRolePage")
    public ResultPage getRolePage(@RequestBody RoleBean roleBean) {
        ResultPage resultPage = new ResultPage();
        roleBean.calculate();
        int count = userDao.getRolePageCount(roleBean);
        resultPage.setTotal(count);
        List<RoleBean> roleList =userDao.getRolePage(roleBean);
        resultPage.setRows(roleList);
        return resultPage;
    }

     //新增角色信息
     @Override
     @RequestMapping(value = "addRole")
     public void addRole(@RequestBody RoleBean roleBean) {
         Date date = new Date();
         roleBean.setCreateTime(date);
      userDao.addRole(roleBean);
     }

    //根据Id删除角色信息
    @Override
    @RequestMapping(value = "deleteRole")
    public void deleteRole(Integer roleId) {
     userDao.deleteRole(roleId);
    }

    //根据Id查询一条信息回显
    @Override
    @RequestMapping(value = "getRoleInfoById")
    public RoleBean getRoleInfoById(@RequestParam(value = "roleId") Integer roleId) {
     return userDao.getRoleInfoById(roleId);
    }

    @Override
    @RequestMapping(value = "updateRole")
    public void updateRole(@RequestBody RoleBean roleBean) {
     userDao.updateRole(roleBean);
    }

    //根据角色ID查询相应的权限
    @Override
    @RequestMapping(value = "queryRoleNavTree")
    public List<NavBean> queryRoleNavTree(@RequestParam(value = "roleId") Integer roleId) {
        List<NavRoleBean> navRoles = userDao.queryNavIdListByRoleId(roleId);
        Integer id = 0;
        List<NavBean> navs = navNode(roleId,id,navRoles);
        return navs;
    }

    private List<NavBean> navNode(Integer roleId,Integer id,List<NavRoleBean> navRoles){
        List<NavBean> navBeans = userDao.queryRoleNavTree(id);
        for(NavBean navBean:navBeans){
            for (NavRoleBean navRoleBean:navRoles){
                if(navBean.getId().equals(navRoleBean.getNavId())){
                    navBean.setChecked(true);
                    break;
                }else {
                    navBean.setChecked(false);
                }
            }
            List<NavBean> navNode = navNode(roleId,navBean.getId(),navRoles);
            navBean.setChildren(navNode);
        }
        return navBeans;
    }

    @Override
    public void addRoleNav(Integer roleId, String ids) {
        //删除原有的权限
        userDao.deleteRoleNav(roleId);
        //添加新的权限
        ArrayList<NavRoleBean> arrayList = new ArrayList<NavRoleBean>();
        String[] navArr = ids.split(",");
        for(int i = 0 ;i < navArr.length;i++){
            NavRoleBean navRoleBean = new NavRoleBean();
            navRoleBean.setNavId(Integer.parseInt(navArr[i]));
            navRoleBean.setRoleId(roleId);
            arrayList.add(navRoleBean);
        }
        userDao.addRoleNav(arrayList);
    }

    @Override
    @RequestMapping(value = "queryUserByLoginNumer")
    public UserBean queryUserByLoginNumer(@RequestParam(value="userName") String userName) {
        return userDao.queryUserByLoginNumer(userName);
    }


    @Override
    @RequestMapping(value = "queryUserListAndLimit")
    public Map<String, Object> queryUserListAndLimit(@RequestParam(value = "page")int page, @RequestParam(value = "limit") int limit) {
        int total=userDao.queryUserTotal();
        //2.起始位置：（当前页 - 1） *  每页条数
        int start=(page-1)*limit;
        //3.结束位置 = 起始位置 +  每页条数
        int end = start+limit;
        //4.获取对象集合
        List<UserBean>dlist=userDao.queryUser(start,end);
        //5.装进Map中，总条数，展示数据
        Map<String,Object>map=new HashMap<>();//实例化
        map.put("total",total);
        map.put("rows",dlist);
        map.put("page",page);
        map.put("limit",limit);
        return map;
    }


}
