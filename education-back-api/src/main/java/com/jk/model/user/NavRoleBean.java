package com.jk.model.user;

import java.io.Serializable;

public class NavRoleBean implements Serializable {

 private Integer id;

 private Integer navId;

 private Integer roleId;

 public Integer getId() {
  return id;
 }

 public void setId(Integer id) {
  this.id = id;
 }

 public Integer getNavId() {
  return navId;
 }

 public void setNavId(Integer navId) {
  this.navId = navId;
 }

 public Integer getRoleId() {
  return roleId;
 }

 public void setRoleId(Integer roleId) {
  this.roleId = roleId;
 }
}
