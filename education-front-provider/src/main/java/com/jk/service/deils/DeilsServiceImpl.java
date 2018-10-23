package com.jk.service.deils;

import com.jk.mapper.deils.DeilsMapper;
import com.jk.model.education.ClassBean;
import com.jk.model.education.MessageBean;
import com.jk.model.education.User;
import com.jk.service.education.EduServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeilsServiceImpl implements EduServiceApi {

    @Autowired
    private DeilsMapper deilsMapper;

    @Override
    public List<ClassBean> queryVideoList(ClassBean classBean) {
        return null;
    }

    @Override
    public MessageBean querydeils(MessageBean messageBean) {
        return deilsMapper.querydeils(messageBean);
    }

    @Override
    public void updateCollect(String courseId) {
        deilsMapper.updateCollect(courseId);
    }

    @Override
    public User queryuser(String userId) {
        return deilsMapper.queryuser(userId);
    }

    @Override
    public void updateMessage(MessageBean messageBean) {
        deilsMapper.updateMessage(messageBean);
    }

    @Override
    public void addCourse(MessageBean messageBean) {
        deilsMapper.addCourse(messageBean);
    }

    @Override
    public MessageBean queryMess(String couTitleId) {
        return deilsMapper.queryMess(couTitleId);
    }
}
