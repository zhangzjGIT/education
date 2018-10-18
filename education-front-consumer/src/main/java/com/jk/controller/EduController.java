package com.jk.controller;

import com.jk.model.education.ClassBean;
import com.jk.service.EduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Auther: yangjianguang
 * @Date: 2018/10/16 21:16
 * @Description:
 */
@Controller
@RequestMapping("user")
public class EduController {
    @Autowired
    private EduService eduService;

    /**
     * 跳转到视频列表
     * @param md
     * @return
     */
    @RequestMapping("toList")
    public String toList(ClassBean classBean, ModelMap md){
       List<ClassBean> list =  eduService.queryVideoList(classBean);
       md.put("list",list);
        return "list";
    }

    @RequestMapping("searchList")
    public String searchList(String search, ModelMap md){
        List<ClassBean> list =  eduService.searchList(search);
        md.put("list",list);
        return "list";
    }
    @RequestMapping("toinfo")
    public String toinfo(){

        return "info";
    }
    @RequestMapping("tomain")
    public String tomain(){
        return "index";
    }


}
