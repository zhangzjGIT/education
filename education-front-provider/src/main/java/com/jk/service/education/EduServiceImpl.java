package com.jk.service.education;

import com.jk.mapper.education.EduMapper;


import com.jk.model.education.*;
import com.jk.model.education.MessageBean;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.UUID;

/**
 * @Auther: yangjianguang
 * @Date: 2018/10/17 10:34
 * @Description:
 */
@RestController
public class EduServiceImpl implements EduServiceApi {

    @Autowired
    private EduMapper eduMapper;

   // @Autowired
   // private MongoTemplate mongoTemplate;

    @Override
    @RequestMapping(value = "/queryVideoList")
    public List<MessageBean> queryVideoList() {
        return eduMapper.queryVideoList();
    }

    @Override
    @RequestMapping(value = "/searchList")
    public List<MessageBean> searchList(@RequestParam(value = "search")String search) {
        return eduMapper.searchList(search);
    }

    @Override
    @RequestMapping(value = "/searchmany")
    public List<MessageBean> searchmany(String search) {

        return eduMapper.searchmany(search);
    }

    @Override
    public List<TypeBean> queryCLassTypeList() {

        return eduMapper.queryCLassTypeList();
    }

    @Override
    public List<MessageBean> queryClassByTypeId() {
        return eduMapper.queryClassByTypeId();
    }

    @Override
    @RequestMapping(value = "/priceType")
    public List<MessageBean> priceType(String search) {

            return eduMapper.priceType(search);
    }

    @Override
    @RequestMapping(value = "/login")
    public User login(@RequestParam(value = "phone")String phone) {
        return eduMapper.login(phone);
    }

    @Override
    public void adduser(@RequestBody User user) {
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        eduMapper.adduser(user);
    }

    @Override
    @RequestMapping(value="querydeils")
    public MessageBean querydeils( @RequestBody MessageBean messageBean) {
        return eduMapper.querydeils(messageBean);
    }

    @Override
    @RequestMapping(value="updateCollect")
    public void updateCollect( @RequestParam(value="courseId") String courseId) {
        eduMapper.updateCollect(courseId);
    }

    @Override
    @RequestMapping(value="queryuser")
    public User queryuser(@RequestParam(value="userId") String userId) {
        return eduMapper.queryuser(userId);
    }

    @Override
    @RequestMapping(value="addCourse")
    public void addCourse(@RequestBody MessageBean messageBean) {
        eduMapper.addCourse(messageBean);
    }

    @Override
    @RequestMapping(value="queryHotList")
    public List<MessageBean> queryHotList() {
        return eduMapper.queryHotList();
    }

    @Override
    @RequestMapping(value="getBuyInfo")
    public MessageBean getBuyInfo(@RequestParam(value="couId") String couId) {
        return eduMapper.getBuyInfo(couId);
    }

    @Override
    @RequestMapping(value="queryTeacherById")
    public Teacher queryTeacherById(@RequestParam(value="teaId") String teaId) {
        Teacher teacher = eduMapper.queryTeacherById(teaId);
        Integer messageCount = eduMapper.queryMessageCountById(teaId);
        teacher.setMessageCount(messageCount);
        return teacher;
    }

    @Override
    @RequestMapping(value="addTeacher")
    public void addTeacher(@RequestBody Teacher teacher) {
        eduMapper.addTeacher(teacher);
    }

    @Override
    @RequestMapping(value="getMesByUserId")
    public List<MessageBean> getMesByUserId(@RequestParam(value="id") String id) {
        return eduMapper.getMesByUserId(id);
    }

    @Override
    @RequestMapping(value="addClassUser")
    public void addClassUser(@RequestParam(value="couId") String couId,@RequestParam(value="id") String id) {
        eduMapper.addClassUser(couId,id);
    }

    @Override
    @RequestMapping(value="deleteUserMes")
    public void deleteUserMes(@RequestParam(value="userMesId") Integer userMesId) {
        eduMapper.deleteUserMes(userMesId);
    }


}
