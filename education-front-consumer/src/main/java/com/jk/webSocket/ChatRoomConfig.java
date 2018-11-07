package com.jk.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
//开启使用STOMP协议来传输基于代理的消息，支持使用@MessageMapping(类似于@RequestMapping)
@EnableWebSocketMessageBroker
public class ChatRoomConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //注册STOMP协议节点，并映射指定的URL
        registry.addEndpoint("/endpointWisely").withSockJS(); //注册一个STOMP的endpint，并指定使用SocketJS协议
    }

}
