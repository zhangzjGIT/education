package com.jk.mapper.solr;


import com.jk.model.education.MessageBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component("messMapper")
public interface MessMapper {

    List<MessageBean> queryMessList();


}
