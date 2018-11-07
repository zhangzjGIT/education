package com.jk.service;

import com.jk.model.solr.SearchResult;
import com.jk.model.solr.SolrResult;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 类描述：solr课程的错误类
 * 创建人：LDW
 * 创建时间：2018/10/29 10:50
 * 修改人：LDW
 * 修改时间：2018/10/29 10:50
 * 修改备注：
 *
 * @version ：1.0
 */
@Component
public class MessError implements MessService {
    @Override
    public SolrResult queryMessList() throws IOException, Exception {
        return SolrResult.build(400, "出现错误！！");
    }

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws IOException, Exception {
        return null;
    }
}
