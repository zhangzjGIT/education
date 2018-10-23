package com.jk.controller.deils;

import com.jk.model.education.MessageBean;
import com.jk.service.education.EduServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("deils")
public class DeilsController {

    @Autowired
    private EduServiceApi eduServiceApi;

    @PostMapping("querydeils")
    @ResponseBody
    public MessageBean querydeils(@RequestBody MessageBean messageBean, HttpServletRequest request, ModelMap modelMap) {
        MessageBean cl = eduServiceApi.querydeils(messageBean);
        modelMap.put("cl",cl);
        /*HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("UserId");
        User us=eduServiceApi.queryuser(userId);
        modelMap.put("us",us);*/
        return cl;
    }

    @PutMapping("collect")
    @ResponseBody
    public void updateCollect(@RequestParam(value="courseId") String courseId) {
        eduServiceApi.updateCollect(courseId);
    }

    @PutMapping("updateMessage")
    @ResponseBody
    public void updateMessage(@RequestBody MessageBean messageBean) {
        eduServiceApi.updateMessage(messageBean);
    }

    @RequestMapping("addCourse")
    @ResponseBody
    public void addCourse(@RequestBody MessageBean messageBean) {
        eduServiceApi.addCourse(messageBean);
    }

    @PostMapping("queryMess")
    @ResponseBody
    public MessageBean queryMess(@RequestParam(value="couTitleId") String  couTitleId) {
        return eduServiceApi.queryMess(couTitleId);
    }
}
