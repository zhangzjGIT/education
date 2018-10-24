package com.jk.model.advertising;

import com.jk.model.Page;

import java.io.Serializable;

public class Advertising extends Page implements Serializable {

    /**
     * id
     */
    private Integer imgid;

    /**
     * 图片标题
     */
    private String imgtitle;

    /**
     * 图片简介
     */
    private String imginfo;

    /**
     * 图片类型
     */
    private Integer imgtype;

    /**
     * 跳转路径
     */
    private String imghref;

    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getImgid() {
        return imgid;
    }

    public void setImgid(Integer imgid) {
        this.imgid = imgid;
    }

    public String getImgtitle() {
        return imgtitle;
    }

    public void setImgtitle(String imgtitle) {
        this.imgtitle = imgtitle;
    }

    public String getImginfo() {
        return imginfo;
    }

    public void setImginfo(String imginfo) {
        this.imginfo = imginfo;
    }

    public Integer getImgtype() {
        return imgtype;
    }

    public void setImgtype(Integer imgtype) {
        this.imgtype = imgtype;
    }

    public String getImghref() {
        return imghref;
    }

    public void setImghref(String imghref) {
        this.imghref = imghref;
    }

    @Override
    public String toString() {
        return "Advertising{" +
                "imgid=" + imgid +
                ", imgtitle='" + imgtitle + '\'' +
                ", imginfo='" + imginfo + '\'' +
                ", imgtype='" + imgtype + '\'' +
                ", imghref='" + imghref + '\'' +
                '}';
    }
}
