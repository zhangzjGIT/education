package com.jk.model.education;

import java.util.Date;

/**
 * @Auther: yangjianguang
 * @Date: 2018/10/17 10:03
 * @Description:
 */
public class ClassBean {
    private Integer id;

    private String name;

    private Date createTime;

    private String creater;

    private String classType;

    private String defaultReadPoint;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getDefaultReadPoint() {
        return defaultReadPoint;
    }

    public void setDefaultReadPoint(String defaultReadPoint) {
        this.defaultReadPoint = defaultReadPoint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
