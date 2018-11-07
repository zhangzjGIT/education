package com.jk.controller;

import com.jk.model.education.CacheBean;
import com.jk.model.education.ChatContent;
import com.jk.model.education.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("web")
public class ChatRoomController {

 @Autowired
 private SimpMessagingTemplate template;

 @RequestMapping("toweb")
 public String toweb (){
  return "/chatroom";
 }

 /**
  * 聊天
  * @param msg
  * @return
  */
 @MessageMapping("/talk")//浏览器映射地址
 @SendTo("/refreshchatwindow")
 public ChatContent talk(ChatContent content) {
  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
  content.setDate(sdf.format(new Date()));
  return content;
 }

 /**
  * 下线
  * @param msg
  * @return
  */
 @MessageMapping("/downLine")
 private void downLine(User msg) {
  List<User> list = CacheBean.clientList;
  list.remove(msg);
  //服务器端通知客户端刷新当前登录人列表
  refreshLoginList();
 }

 /**
  * 上线
  * @param msg
  * @return
  */
 @MessageMapping("/upLine")
 private void upLine(User msg) {
  //内存中将用户加入进来
  List<User> list = CacheBean.clientList;
  list.add(msg);
  //服务器端通知客户端刷新当前登录人列表
  refreshLoginList();
 }

 /**
  * 服务器端通知客户端刷新当前登录人列表
  * @param list
  */
 @MessageMapping("/refreshLoginList")
 public void refreshLoginList( ){
  template.convertAndSend("/refreshloginlist",CacheBean.clientList);
 }

}
