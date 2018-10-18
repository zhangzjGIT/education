package com.jk.service.education;

import com.jk.mapper.education.EduMapper;


import com.jk.model.education.ClassBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @Auther: yangjianguang
 * @Date: 2018/10/17 10:34
 * @Description:
 */
@RestController
public class EduServiceImpl implements EduServiceApi {

    @Autowired
    private EduMapper eduMapper;

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


}
