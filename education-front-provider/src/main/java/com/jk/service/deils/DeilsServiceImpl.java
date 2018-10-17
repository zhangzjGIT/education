package com.jk.service.deils;

import com.jk.mapper.deils.DeilsMapper;
import com.jk.model.education.ClassBean;
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
}
