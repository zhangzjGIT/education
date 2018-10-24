package com.jk.service.adverMapper;

import com.jk.mapper.advertising.AdverMapper;
import com.jk.model.ResultPage;
import com.jk.model.TypeBean;
import com.jk.model.advertising.Advertising;
import com.jk.model.user.UserBean;
import com.jk.service.advertising.AdverServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdverServiceImpl implements AdverServiceApi {

    @Autowired
    private AdverMapper adverDao;



    @Override
    public Map<String, Object> queryadverPage(@RequestParam(value = "page")int page, @RequestParam(value = "limit") int limit) {
        int total=adverDao.queryadverTotal();
        //2.起始位置：（当前页 - 1） *  每页条数
        int start=(page-1)*limit;
        //3.结束位置 = 起始位置 +  每页条数
        int end = start+limit;
        //4.获取对象集合
        List<Advertising>dlist=adverDao.queryadver(start,end);
        //5.装进Map中，总条数，展示数据
        Map<String,Object>map=new HashMap<>();//实例化
        map.put("total",total);
        map.put("rows",dlist);
        map.put("page",page);
        map.put("limit",limit);
        return map;
    }

    @Override
    @RequestMapping(value = "advertising/saveadver")
    public void saveAdver(@RequestBody Advertising adver) {
        adverDao.saveAdver(adver);
    }



    @Override
    @RequestMapping(value = "advertising/queryType")
     public List<TypeBean> queryType(@RequestBody TypeBean typeBean) {
        return adverDao.queryType(typeBean);
    }

    @Override
    @RequestMapping(value = "advertising/queryadverbyid")
    public Advertising queryadverbyid(@RequestParam(value = "imgid")Integer imgid) {
        return adverDao.queryadverbyid(imgid);
    }

    @Override
    @RequestMapping(value = "advertising/updateadver")
    public void updateadver(@RequestBody Advertising advertising) {
        adverDao.updateadver(advertising);
    }


    @Override
    @RequestMapping(value = "advertising/deladvertising")
    public void deladvertising(@RequestParam(value = "imgid")Integer imgid) {
        adverDao.deladvertising(imgid);
    }




}
