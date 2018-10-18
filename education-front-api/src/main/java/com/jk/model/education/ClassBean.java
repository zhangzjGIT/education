package com.jk.model.education;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: yangjianguang
 * @Date: 2018/10/17 10:03
 * @Description:
 */
public class ClassBean {
    private Integer id;

    private String name;
    @DateTimeFormat
    @JsonFormat
    private Date createTime;

    private String creater;//创建者

    private String classType;//类型

    private String defaultReadPoint;//热度，点击量

    private String groupPrice;//价格

    private String img;//封面


    /**
     * 业务字段
     * @return
     */
    private String courseName;

    private String stuCount;

    private String minPrice;

    private String maxPrice;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStuCount() {
        return stuCount;
    }

    public void setStuCount(String stuCount) {
        this.stuCount = stuCount;
    }

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

    public String getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(String groupPrice) {
        this.groupPrice = groupPrice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}
