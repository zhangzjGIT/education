package com.jk.mapper.solr;


import com.jk.model.education.MessageBean;
import com.jk.model.solr.SearchResult;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Repository
public class SearchDaoImpl  implements  SearchDao {

    /*@Autowired
    private SolrClient solrServer;*/
    @Autowired
    private CloudSolrClient solrServer;
    @Override
    public SearchResult search(SolrQuery query) throws IOException, SolrServerException {
        //返回值对象
        SearchResult result = new SearchResult();
        //根据查询条件查询索引库
        QueryResponse queryResponse = solrServer.query("zzj2",query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //取查询结果总数量
        result.setRecordCount(solrDocumentList.getNumFound());
        //列表
        List<MessageBean> itemList = new ArrayList<>();
        //取高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //取商品列表
        for (SolrDocument solrDocument : solrDocumentList) {
            //创建一商品对象
            MessageBean item = new MessageBean();
            item.setCouId((String) solrDocument.get("id"));
            //取高亮显示的结果
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size()>0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            item.setCouTitle(title);
            item.setCouType((Integer) solrDocument.get("item_type"));
            item.setCouPrice((Integer) solrDocument.get("item_price"));
            item.setPriImg((String) solrDocument.get("item_image"));
            item.setCouClass((Integer)solrDocument.get("item_class"));
            item.setCouState((String) solrDocument.get("item_state"));
            item.setCouInfo((String) solrDocument.get("item_info"));
            item.setInfoName((String) solrDocument.get("item_info_name"));
            item.setStuCount((String) solrDocument.get("item_stu_count"));
            item.setAllCount((String) solrDocument.get("item_all_count"));
            //添加的商品列表
            itemList.add(item);
        }
        result.setMessList(itemList);
        return result;
    }

}
