package com.jk.service.education;

import com.jk.model.education.MessageBean;
import com.jk.model.education.MessageBean;
import com.jk.model.education.TypeBean;
import com.jk.model.education.User;

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

    @RequestMapping(value = "/updateMessage",method = RequestMethod.PUT)
    void updateMessage(@RequestBody MessageBean messageBean);

    @RequestMapping(value = "/addCourse",method = RequestMethod.POST)
    void addCourse(@RequestBody MessageBean messageBean);

    @RequestMapping(value = "/queryMess",method = RequestMethod.POST)
    MessageBean queryMess(@RequestParam(value="couTitleId")String couTitleId);

    @RequestMapping(value = "/queryHotList",method = RequestMethod.POST)
    List<MessageBean> queryHotList();

    @RequestMapping(value = "/getBuyInfo",method = RequestMethod.POST)
    MessageBean getBuyInfo(@RequestParam(value="couId")String couId);
}
