package com.jk.service.education;

import com.jk.model.education.ClassBean;
import com.jk.model.education.MessageBean;
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
    List<ClassBean> queryVideoList(@RequestBody ClassBean classBean);

    @RequestMapping(value = "/deils/querydeils",method = RequestMethod.POST)
    MessageBean querydeils(@RequestBody MessageBean messageBean);

    @RequestMapping(value = "/deils/collect",method = RequestMethod.PUT)
    void updateCollect(@RequestParam(value="courseId") String courseId);

    @RequestMapping(value = "/deils/queryuser",method = RequestMethod.GET)
    User queryuser(@RequestParam(value="userId")String userId);

    @RequestMapping(value = "/deils/updateMessage",method = RequestMethod.PUT)
    void updateMessage(@RequestBody MessageBean messageBean);

    @RequestMapping(value = "/deils/addCourse",method = RequestMethod.POST)
    void addCourse(@RequestBody MessageBean messageBean);

    @RequestMapping(value = "/deils/queryMess",method = RequestMethod.POST)
    MessageBean queryMess(@RequestParam(value="couTitleId")String couTitleId);
}
