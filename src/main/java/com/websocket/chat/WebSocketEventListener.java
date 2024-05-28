package com.websocket.chat;//package com.websocket.chat;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class WebSocketEventListener {
//
//    /*
//    * SimpMessagingTemplate的接口:SimpMessageSendingOperations
//    * 可以任意發送訊息，不須先接收一個訊息
//    */
//    private final SimpMessageSendingOperations messageTemplate;
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(
//            SessionDisconnectEvent event
//    ){
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username =(String) headerAccessor.getSessionAttributes().get("username");
//        if(username != null){
//            log.info("User disconnected: {}", username);
//            var chatMessage = ChatMessage.builder()
//                    .type(MessageType.LEAVE).sender(username)
//                    .build();
//            messageTemplate.convertAndSend("/topic/public", chatMessage);
//        }
//    }
//}
