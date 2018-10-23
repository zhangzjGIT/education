package com.jk.controller.deils;

import com.jk.OSSClientUtil;
import com.jk.model.education.MessageBean;
import com.jk.service.deils.DeilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("deils")
public class DeilsController {

    @Autowired
    private DeilsService deilsService;

    @RequestMapping("querydeils")
    public String querydeils(MessageBean messageBean, ModelMap modelMap,HttpServletRequest request){
        MessageBean cl = deilsService.querydeils(messageBean);
        modelMap.put("cl",cl);
        /*HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("UserId");
        User us=deilsService.queryuser(userId);
        modelMap.put("us",us);*/
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

    @RequestMapping("enroll")
    public String enroll(ModelMap modelMap,HttpServletRequest request){
        HttpSession session = request.getSession();
        String couTitleId = (String) session.getAttribute(session.getId());
        MessageBean messageBean=deilsService.queryMess(couTitleId);
        modelMap.put("me",messageBean);
        return "deils/enrollThor";
    }

    @RequestMapping("upImg")
    @ResponseBody
    public HashMap<String, Object> headImgUpload(MultipartFile file) throws IOException {

        if (file == null || file.getSize() <= 0) {
            throw new IOException("file不能为空");
        }
        file.getSize();
        OSSClientUtil ossClient=new OSSClientUtil();
        String name = ossClient.uploadImg2Oss(file);
        String imgUrl = ossClient.getImgUrl(name);
        HashMap<String, Object> map=new HashMap<>();
        //文件存储的路径
        map.put("aaa", imgUrl);
        return map ;
    }

    @RequestMapping("toPersonal")
    public String toPersonal(ModelMap mm){
        /*HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("UserId");
        User us=deilsService.queryuser(userId);
        modelMap.put("user",us);*/
        return "deils/personal";
    }

    @RequestMapping("updateMessage")
    @ResponseBody
    public Boolean updateMessage(MessageBean messageBean,HttpServletRequest request){
        try {
            deilsService.updateMessage(messageBean);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping("addCourse")
    @ResponseBody
    public Boolean addCourse(MessageBean messageBean,HttpServletRequest request){
        try {
            deilsService.addCourse(messageBean);
            HttpSession session = request.getSession();
            session.setAttribute(session.getId(),messageBean.getCouTitle());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
