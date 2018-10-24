package com.jk.model.course;

public class MessageBean {

    private Integer couId;

    private String couState;// 课程说名

    private String couTitle;

    private Integer couType;

    private Integer couClass;

    private Integer couPrice;

    private String prilmg;  //方面

    private String coulnfo; //视频

    private String infoName; //视频名称

    private Integer teaId;

    private String allCount; //总数量

    private String stuCount; //学生数

    private String teacherName;

    public String getCouState() {
        return couState;
    }

    public void setCouState(String couState) {
        this.couState = couState;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getStuCount() {
        return stuCount;
    }

    public void setStuCount(String stuCount) {
        this.stuCount = stuCount;
    }

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public Integer getCouId() {
        return couId;
    }

    public void setCouId(Integer couId) {
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

    public Integer getCouPrice() {
        return couPrice;
    }

    public void setCouPrice(Integer couPrice) {
        this.couPrice = couPrice;
    }

    public String getPrilmg() {
        return prilmg;
    }

    public void setPrilmg(String prilmg) {
        this.prilmg = prilmg;
    }

    public String getCoulnfo() {
        return coulnfo;
    }

    public void setCoulnfo(String coulnfo) {
        this.coulnfo = coulnfo;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }
}
