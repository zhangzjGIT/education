package com.jk.controller.deils;

import com.jk.service.deils.DeilsService;
import com.jk.service.education.EduServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("deils")
public class DeilsController {

    @Autowired
    private DeilsService deilsService;

    @RequestMapping("deilsList")
    public String deilsList(){
        return "deils/deils";
    }
}
