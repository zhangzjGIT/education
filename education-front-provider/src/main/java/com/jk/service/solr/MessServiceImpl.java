package com.jk.service.solr;


import com.jk.mapper.solr.MessMapper;
import com.jk.mapper.solr.SearchDao;
import com.jk.model.education.MessageBean;
import com.jk.model.solr.SearchResult;
import com.jk.model.solr.SolrResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MessServiceImpl implements MessServiceApi {

    @Autowired
    private MessMapper messMapper;

    @Autowired
    private SearchDao searchDao;

   /* @Autowired
    private SolrClient client;*/
    @Autowired
    private CloudSolrClient client;


    @Override
    @RequestMapping(value = "queryMessList")
    public SolrResult queryMessList() throws IOException, SolrServerException {

            //查询商品列表
            List<MessageBean> messList = messMapper.queryMessList();

            for (MessageBean item : messList) {
                //创建一个SolrInputDocument对象
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", item.getCouId());
                document.setField("item_title", item.getCouTitle());
                document.setField("item_type", item.getCouType());
                document.setField("item_price", item.getCouPrice());
                document.setField("item_image", item.getPriImg());
                document.setField("item_class", item.getCouClass());
                document.setField("item_state", item.getCouState());
                document.setField("item_info", item.getCouInfo());
                document.setField("item_info_name", item.getInfoName());
                document.setField("item_stu_count", item.getStuCount());
                document.setField("item_all_count", item.getAllCount());
                //写入库
                    client.add("zzj2",document);
            } //提交修改
                client.commit("zzj2");
            return SolrResult.ok();
    }

    @Override
    @RequestMapping(value = "search")
    public SearchResult search(@RequestParam (value="queryString")String queryString, @RequestParam(value="page")Integer page, @RequestParam(value="rows")Integer rows) throws IOException, SolrServerException {
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(queryString);
        //设置分页
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        //设置默认搜素域
        query.set("df", "item_title");
        //设置高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult searchResult = searchDao.search(query);
        //计算查询结果总页数
        long recordCount = searchResult.getRecordCount();
        long pageCount = recordCount / rows;
        if (recordCount % rows > 0) {
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurPage(page);

        return searchResult;
    }

}
