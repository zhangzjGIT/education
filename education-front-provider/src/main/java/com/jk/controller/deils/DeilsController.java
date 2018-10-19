package com.jk.controller.deils;


import com.jk.model.education.CourseBean;
import com.jk.model.education.User;
import com.jk.service.education.EduServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("deils")
public class DeilsController {

    @Autowired
    private EduServiceApi eduServiceApi;

    @PostMapping("querydeils")
    @ResponseBody
    public CourseBean querydeils(@RequestBody CourseBean courseBean, HttpServletRequest request, ModelMap modelMap) {
        CourseBean cl = eduServiceApi.querydeils(courseBean);
        modelMap.put("cl",cl);
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("UserId");
        User us=eduServiceApi.queryuser(userId);
        modelMap.put("us",us);
        return cl;
    }

    @PutMapping("collect")
    @ResponseBody
    public void updateCollect(@RequestParam(value="courseId") String courseId) {
        eduServiceApi.updateCollect(courseId);
    }
}
