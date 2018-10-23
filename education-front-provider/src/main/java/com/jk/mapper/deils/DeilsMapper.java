package com.jk.mapper.deils;

import com.jk.model.education.MessageBean;
import com.jk.model.education.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeilsMapper {

    MessageBean querydeils(MessageBean messageBean);

    void updateCollect(String courseId);

    User queryuser(String userId);

    void updateMessage(MessageBean messageBean);

    void addCourse(MessageBean messageBean);

    MessageBean queryMess(String couTitleId);

}
