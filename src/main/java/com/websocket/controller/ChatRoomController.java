package com.websocket.controller;//package com.websocket.chat.controller;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.google.gson.Gson;
//import com.websocket.chat.model.ChatMessage;
//import com.websocket.chat.model.ChatService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Controller
//public class ChatRoomController {
//
//
//    private final Gson gson = new Gson();
//    //將消息發送到webSocket目的地
//    //使用它的方法convertAndSendToUser發送消息
////    @Autowired
////    private  SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    private ChatService chatService;
//
//
//    @GetMapping("/chat")
//    public String chatIndex(Model model) {
//        // 假设当前用户为 sender，接收者 receiver 还需要根据实际情况获取
//        String sender = "sender";
//        String receiver = "receiver";
//        model.addAttribute("sender", sender);
//        model.addAttribute("receiver", receiver);
//        // 获取聊天记录
//        List<ChatMessage> chatHistory = chatService.getChatHistory(sender, receiver);
//        if (chatHistory.isEmpty()) {
//            chatHistory.add(new ChatMessage(sender, receiver, "Hello!", LocalDateTime.now().toString()));
//            chatHistory.add(new ChatMessage(receiver, sender, "Hi there!", LocalDateTime.now().plusMinutes(1).toString()));
//        }
//
//        model.addAttribute("chatHistory", chatHistory);
//        return "chatIndex";
//    }
//
//    @MessageMapping("/chat.send")
//    public void sendMessage(ChatMessage message) {
//        // 保存消息到 Redis
//        chatService.saveChatContent(message);
//    }
//
//    @GetMapping("/chat/history")
//    @ResponseBody
//    public List<ChatMessage> getChatHistory(@RequestParam String sender, @RequestParam String receiver) {
//        return chatService.getChatHistory(sender, receiver);
//    }
//
//}
