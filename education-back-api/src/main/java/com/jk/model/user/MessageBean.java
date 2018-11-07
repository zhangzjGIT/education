package com.jk.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MessageBean {

    private String couId;

    private String couTitle;

    private Integer couType;

    private Integer couClass;

    private String couPrice;

    private String couState;

    private String priImg;

    private String couInfo;

    private String infoName;

    private String stuCount;

    private String allCount;

    private Integer status;

    private String teaId;

    //业务字段
    private String teacherName;

    private Integer userMesId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    public Integer getUserMesId() {
        return userMesId;
    }

    public void setUserMesId(Integer userMesId) {
        this.userMesId = userMesId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }

    public String getStuCount() {
        return stuCount;
    }

    public void setStuCount(String stuCount) {
        this.stuCount = stuCount;
    }

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getCouId() {
        return couId;
    }

    public void setCouId(String couId) {
        this.couId = couId;
    }

    public String getCouTitle() {
        return couTitle;
    }

    public void setCouTitle(String couTitle) {
        this.couTitle = couTitle;
    }

    public Integer getCouType() {
        return couType;
    }

    public void setCouType(Integer couType) {
        this.couType = couType;
    }

    public Integer getCouClass() {
        return couClass;
    }

    public void setCouClass(Integer couClass) {
        this.couClass = couClass;
    }

    public String getCouPrice() {
        return couPrice;
    }

    public void setCouPrice(String couPrice) {
        this.couPrice = couPrice;
    }

    public String getCouState() {
        return couState;
    }

    public void setCouState(String couState) {
        this.couState = couState;
    }

    public String getPriImg() {
        return priImg;
    }

    public void setPriImg(String priImg) {
        this.priImg = priImg;
    }

    public String getCouInfo() {
        return couInfo;
    }

    public void setCouInfo(String couInfo) {
        this.couInfo = couInfo;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

