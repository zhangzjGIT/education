package com.jk.service.solr;

import com.jk.model.solr.SearchResult;
import com.jk.model.solr.SolrResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public interface MessServiceApi {

    @RequestMapping(value = "/queryMessList",method = RequestMethod.POST)
    SolrResult queryMessList() throws IOException, Exception;

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    SearchResult search(@RequestParam(value = "queryString") String queryString, @RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows) throws IOException, Exception;
}
