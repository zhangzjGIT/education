package com.jk.service.education;

import com.jk.model.education.ClassBean;
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

    @RequestMapping(value = "/searchList",method = RequestMethod.POST)
    List<ClassBean> searchList(@RequestParam(value = "search")String search);

    @RequestMapping(value = "/searchmany",method = RequestMethod.POST)
    List<ClassBean> searchmany(@RequestParam(value = "search")String search);
}
