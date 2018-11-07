package com.jk.mapper.solr;

import com.jk.model.solr.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface SearchDao {

    SearchResult search(SolrQuery query) throws IOException, SolrServerException;
}
