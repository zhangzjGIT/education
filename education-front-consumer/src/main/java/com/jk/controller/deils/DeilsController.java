package com.jk.controller.deils;

import com.jk.model.education.CourseBean;
import com.jk.model.education.User;
import com.jk.service.deils.DeilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("deils")
public class DeilsController {

    @Autowired
    private DeilsService deilsService;

    @RequestMapping("querydeils")
    public String querydeils(CourseBean courseBean, ModelMap modelMap,HttpServletRequest request){
        CourseBean cl = deilsService.querydeils(courseBean);
        modelMap.put("cl",cl);
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("UserId");
        User us=deilsService.queryuser(userId);
        modelMap.put("us",us);
        return "deils/deils";
    }

    @RequestMapping("quitId")
    public String quitId(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        return "";
    }

    @RequestMapping("collect")
    @ResponseBody
    public Boolean updateCollect(String courseId){
         try {
             deilsService.updateCollect(courseId);
         }catch (Exception e){
             e.printStackTrace();
             return false;
         }
         return true;
    }

}
