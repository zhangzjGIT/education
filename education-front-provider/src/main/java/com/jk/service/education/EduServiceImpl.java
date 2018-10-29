package com.jk.service.education;

import com.jk.mapper.education.EduMapper;


import com.jk.model.education.ClassBean;
import com.jk.model.education.MessageBean;
import com.jk.model.education.MessageBean;
import com.jk.model.education.TypeBean;
import com.jk.model.education.User;

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
    public List<ClassBean> queryVideoList(@RequestBody ClassBean classBean) {
        return eduMapper.queryVideoList(classBean);
    }

    @Override
    @RequestMapping(value = "/searchList")
    public List<ClassBean> searchList(@RequestParam(value = "search")String search) {
        return eduMapper.searchList(search);
    }

    @Override
    @RequestMapping(value = "/searchmany")
    public List<ClassBean> searchmany(String search) {

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
    public List<ClassBean> priceType(String search) {

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
    public List<MessageBean> queryHotList() {
        return  eduMapper.queryHotList();
    }

   /* @Override
    public GridFSDBFile getImgById(String id) {
        DBObject query = new BasicDBObject("_id",id);
        GridFS gridFS = new GridFS(mongoTemplate.getDb());
        GridFSDBFile findOne = gridFS.findOne(query);
        return findOne;
    }*/
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
    @RequestMapping(value="updateMessage")
    public void updateMessage(@RequestBody MessageBean messageBean) {
        eduMapper.updateMessage(messageBean);
    }

    @Override
    @RequestMapping(value="addCourse")
    public void addCourse(@RequestBody MessageBean messageBean) {
        eduMapper.addCourse(messageBean);
    }

    @Override
    @RequestMapping(value="queryMess")
    public MessageBean queryMess(@RequestParam(value="couTitleId") String couTitleId) {
        return eduMapper.queryMess(couTitleId);
    }


}
