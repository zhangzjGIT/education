package com.jk.mapper.advertising;

import com.jk.model.TypeBean;
import com.jk.model.advertising.Advertising;
import com.jk.model.user.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("adverDao")
public interface AdverMapper {





    int queryadverTotal();

    List<Advertising> queryadver(@Param("start") int start,@Param("end") int end);

    void saveAdver(Advertising adver);

    List<TypeBean> queryType(TypeBean typeBean);

    Advertising queryadverbyid(@Param("imgid") Integer imgid);

    void updateadver(Advertising advertising);

    void deladvertising(Integer imgid);






}
