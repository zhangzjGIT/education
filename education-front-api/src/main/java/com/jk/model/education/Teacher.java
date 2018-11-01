package com.jk.model.education;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述：教师实体
 * 创建人：LDW
 * 创建时间：2018/10/18 11:23
 * 修改人：LDW
 * 修改时间：2018/10/18 11:23
 * 修改备注：
 * @version ：1.0
 */
public class Teacher implements Serializable {

    //
    private String teacherId;

    //姓名
    private String teacherName;

    //性别
    private Integer teaSex;

    //身份证号
    private String idcard;

    //毕业院校
    private String school;

    //所读专业
    private String career;

    //从业时间
    private Integer teacherTime;

    //居住地址
    private String address;

    //邮箱
    private String email;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date  createTime;

    private Integer status;

    private Integer messageCount;

    private String teaInfo;

    public String getTeaInfo() {
        return teaInfo;
    }

    public void setTeaInfo(String teaInfo) {
        this.teaInfo = teaInfo;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Integer getTeaSex() {
        return teaSex;
    }

    public void setTeaSex(Integer teaSex) {
        this.teaSex = teaSex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public Integer getTeacherTime() {
        return teacherTime;
    }

    public void setTeacherTime(Integer teacherTime) {
        this.teacherTime = teacherTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
