package com.jk.service.education;

import com.jk.model.education.*;
import com.jk.model.education.MessageBean;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: yangjianguang
 * @Date: 2018/10/17 10:06
 * @Description:
 */
public interface EduServiceApi {
    @RequestMapping(value = "/queryVideoList",method = RequestMethod.POST)
    List<MessageBean> queryVideoList();

    @RequestMapping(value = "/searchList",method = RequestMethod.POST)
    List<MessageBean> searchList(@RequestParam(value = "search")String search);

    @RequestMapping(value = "/searchmany",method = RequestMethod.POST)
    List<MessageBean> searchmany(@RequestParam(value = "search")String search);

    @RequestMapping(value="/queryCLassTypeList",method = RequestMethod.GET)
    List<TypeBean> queryCLassTypeList();

    @RequestMapping(value = "/queryClassByTypeId",method = RequestMethod.GET)
    List<MessageBean> queryClassByTypeId();

    @RequestMapping(value = "/priceType",method = RequestMethod.POST)
    List<MessageBean> priceType(@RequestParam(value = "search")String search);

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    User login(@RequestParam(value = "phone")String phone);

    @RequestMapping(value = "/adduser",method = RequestMethod.POST)
    void adduser(@RequestBody User user);

    @RequestMapping(value = "/querydeils",method = RequestMethod.POST)
    MessageBean querydeils(@RequestBody MessageBean messageBean);

    @RequestMapping(value = "/collect",method = RequestMethod.PUT)
    void updateCollect(@RequestParam(value="courseId") String courseId);

    @RequestMapping(value = "/queryuser",method = RequestMethod.GET)
    User queryuser(@RequestParam(value="userId")String userId);

    @RequestMapping(value = "/addCourse",method = RequestMethod.POST)
    void addCourse(@RequestBody MessageBean messageBean);

    @RequestMapping(value = "/queryHotList",method = RequestMethod.POST)
    List<MessageBean> queryHotList();

    @RequestMapping(value = "/getBuyInfo",method = RequestMethod.POST)
    MessageBean getBuyInfo(@RequestParam(value="couId")String couId);

    @RequestMapping(value = "/queryTeacherById",method = RequestMethod.POST)
    Teacher queryTeacherById(@RequestParam(value="teaId") String teaId);

    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    void addTeacher(@RequestBody Teacher teacher);

    @RequestMapping(value = "/getMesByUserId",method = RequestMethod.GET)
    List<MessageBean> getMesByUserId(@RequestParam(value="id") String id);

    @RequestMapping(value = "/addClassUser",method = RequestMethod.POST)
    void addClassUser(@RequestParam(value="couId") String couId,@RequestParam(value="id") String id);
}
