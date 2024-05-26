package com.websocket.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
   @EnableWebSocketMessageBroker:宣告啟用STOMP協定，Controller就能使用@MessageMapping
   registerStompEndpoints:註冊了一個節點,用來映射指定URL,註冊一個STOMP的endpoint,指定使用SockJS協定
   configureMessageBroker:配製訊息代理(Message broker)

 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //添加一個服務端點，接收客戶端的連接
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/chat").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //發送消息目的地的前綴，與控制器的@MessageMapping有關連，例如: /app/chat.send
        //也就是客戶端接收地址的前綴
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/queue", "/topic"); //消息發送到/queue(特定客戶端)的目的地
        registry.setUserDestinationPrefix("/user");//客戶端發送地址的前綴
    }

}
