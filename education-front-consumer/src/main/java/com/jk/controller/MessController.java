package com.jk.controller;

import com.jk.model.education.MessageBean;
import com.jk.model.education.Teacher;
import com.jk.model.education.TypeBean;
import com.jk.model.education.User;
import com.jk.model.solr.SearchResult;
import com.jk.model.solr.SolrResult;
import com.jk.service.education.EduServiceApi;
import com.jk.service.solr.MessServiceApi;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类描述： 课程查询solo
 * 创建人：LDW
 * 创建时间：2018/10/25 15:59
 * 修改人：LDW
 * 修改时间：2018/10/25 15:59
 * 修改备注：
 *
 * @version ：1.0
 */
@Controller
@RequestMapping("mess")
public class MessController {

    @Autowired
    private MessServiceApi messServiceApi;
    @Autowired
    private EduServiceApi eduService;

    /**
     * 导入课程数据到数据库
     */
    @RequestMapping("queryMessList")
    @ResponseBody
    public SolrResult queryMessList() throws Exception {
        SolrResult solrResult = messServiceApi.queryMessList();
        return solrResult;
    }

    //课程查询M
 /* @RequestMapping("search")
    @ResponseBody
    public SolrResult  search(@RequestParam("q")String queryString,
                               @RequestParam(defaultValue="1")Integer page,
                               @RequestParam(defaultValue="60")Integer rows) throws Exception {
        //查询条件不能为空
        if (StringUtils.isBlank(queryString)) {
            return SolrResult.build(400, "查询条件不能为空");
        }
        SearchResult searchResult = null;
        queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        searchResult = messServiceApi.search(queryString, page, rows);
        return SolrResult.ok(searchResult);
    }*/
    @RequestMapping("search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "60") Integer rows, HttpServletRequest request, ModelMap md) throws Exception {

        //查询条件不能为空
        if (StringUtils.isBlank(queryString)) {
            return "redirect:/user/tomain";
        }
        SearchResult searchResult = null;
        //  queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        searchResult = messServiceApi.search(queryString, page, rows);
        List<MessageBean> messList = searchResult.getMessList();
        md.put("mesList", messList);
        List<TypeBean> typelist = eduService.queryCLassTypeList();
        md.put("typelist", typelist);
        User user = (User) request.getSession().getAttribute("userInfo");
        String teaId = user.getId();
        Teacher teacher = eduService.queryTeacherById(teaId);
        md.put("tea", teacher);
        if (user != null) {
            md.put("codes", 1);
            md.put("user", user);
        } else {
            User uu = new User();
            uu.setImg("../images/logo.jpg");
            uu.setUserName("无");
            md.put("codes", 2);
            md.put("user", uu);

        }
        return "index2";
    }


}
