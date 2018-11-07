package com.jk.model.education;

public class ChatContent {

 /**
  * 时间
  */
 public String date;
 /**
  * 内容
  */
 public String content;
 /**
  * 发送消息的人
  */
 public String sendUser;
 public String getDate() {
  return date;
 }
 public void setDate(String date) {
  this.date = date;
 }
 public String getContent() {
  return content;
 }
 public void setContent(String content) {
  this.content = content;
 }
 public String getSendUser() {
  return sendUser;
 }
 public void setSendUser(String sendUser) {
  this.sendUser = sendUser;
 }


}
