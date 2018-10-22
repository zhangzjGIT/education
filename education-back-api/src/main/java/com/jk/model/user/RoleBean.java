package com.jk.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jk.model.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleBean extends Page implements Serializable {

   private Integer roleId;

   private String roleName;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @JsonFormat(pattern = "yyyy-mm-dd",timezone = "GMT+8")
   private Date createTime;

   private List<String> power;

 public Date getCreateTime() {
  return createTime;
 }

 public void setCreateTime(Date createTime) {
  this.createTime = createTime;
 }

 public Integer getRoleId() {
  return roleId;
 }

 public void setRoleId(Integer roleId) {
  this.roleId = roleId;
 }

 public String getRoleName() {
  return roleName;
 }

 public void setRoleName(String roleName) {
  this.roleName = roleName;
 }

 public List<String> getPower() {
  return power;
 }

 public void setPower(List<String> power) {
  this.power = power;
 }
}
