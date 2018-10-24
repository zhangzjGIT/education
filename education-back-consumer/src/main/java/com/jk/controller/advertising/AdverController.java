package com.jk.controller.advertising;

import com.alibaba.fastjson.JSONObject;
import com.jk.model.ResultPage;
import com.jk.model.TypeBean;
import com.jk.model.advertising.Advertising;
import com.jk.service.advertising.AdverServiceApi;
import com.jk.utils.OSSClientUtil;
import com.sun.java.browser.plugin2.liveconnect.v1.Result;
import javafx.scene.control.Cell;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.awt.print.Book;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("advertising")
public class AdverController {

    @Autowired
    private AdverServiceApi adverServiceApi;

    /**
     * 跳转到查询页面
     */
    @RequestMapping("toAdverPage")
    public String toAdverPage() {
        return "adver/adverList";
    }

    /**
     * 查询列表
     */
    @RequestMapping("queryadverPage")
    @ResponseBody
    public String queryadverPage(@RequestParam(value = "page", defaultValue = "1", required = true) int page, @RequestParam(value = "limit", defaultValue = "10", required = true) int limit) {

        Map<String, Object> map = adverServiceApi.queryadverPage(page, limit);
        List<Advertising> list = (List<Advertising>) map.get("rows");
        int count = (int) map.get("total");

        //list转成json
//		 JSONArray array =new JSONArray();
        JSONObject obj = new JSONObject();
        //前台通过key值获得对应的value值
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", count);
        obj.put("data", list);
        return obj.toString();
    }


    //查询类型
    @RequestMapping("queryType")
    @ResponseBody
    public List<TypeBean> queryType(TypeBean typeBean) {

        List<TypeBean> list = adverServiceApi.queryType(typeBean);
        return list;
    }


    /**
     * 新增图片
     */
//跳转新增页面
    @RequestMapping("toadd")
    public String toadd() {
        return "adver/addAdver";
    }

    //新增
    @RequestMapping("saveAdver")
    @ResponseBody
    public Boolean saveAdver(Advertising adver) {
        try {
            adverServiceApi.saveAdver(adver);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    //oss图片上传
    @RequestMapping("initiateMultipartUpload")
    @ResponseBody
    public HashMap<String, Object> headImgUpload(MultipartFile file) throws IOException {

        if (file == null || file.getSize() <= 0) {
            throw new IOException("file不能为空");
        }
        //获取文件的大小,单位/KB
        long size = file.getSize();
        OSSClientUtil ossClient = new OSSClientUtil();
        String name = ossClient.uploadImg2Oss(file);
        String imgUrl = ossClient.getImgUrl(name);
        HashMap<String, Object> map = new HashMap<>();
        //文件存储的路径
        map.put("name", imgUrl);
        return map;
    }


    /**
     * 修改
     */
    //回显跳转到修改页面
    @RequestMapping("queryadverbyid")
    public String queryadverbyid(Integer imgid,TypeBean typeBean, Model model) {
       List<TypeBean> list = adverServiceApi.queryType(typeBean);
        Advertising adver = adverServiceApi.queryadverbyid(imgid);
        model.addAttribute("adver", adver);
        model.addAttribute("list", list);
        return "adver/editAdver";
    }

    //修改
    @RequestMapping("updateadver")
    @ResponseBody
    public Boolean updateadver(Advertising advertising) {
        try {
            adverServiceApi.updateadver(advertising);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //删除
    @RequestMapping("deladvertising")
    @ResponseBody
    public Boolean deladvertising(Integer imgid) {
        try {
            adverServiceApi.deladvertising(imgid);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

}

}
