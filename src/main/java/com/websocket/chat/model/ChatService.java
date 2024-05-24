package com.websocket.chat.model;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service
public class ChatService {

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    private static StringRedisTemplate staticChatRedisTemplate;
    @Autowired
    @Qualifier("chatStrStr")
    private StringRedisTemplate chatRedisTemplate;

    @PostConstruct
    public void init() {
        staticChatRedisTemplate = this.chatRedisTemplate;
    }

    private final Gson gson = new Gson();

    // 保存聊天訊息到 Redis
    public void saveChatContent(ChatMessage content) {
        String key = "chat:" + content.getSender() + ":" + content.getReceiver();
        String messageJson = gson.toJson(content);
        chatRedisTemplate.opsForList().rightPush(key, messageJson);
        System.out.println("保存消息到 Redis: " + content + "，Key：" + key);
    }

    // 獲取聊天訊息
    public List<ChatMessage> getChatHistory(String sender, String receiver) {
        String key = "chat:" + sender + ":" + receiver;

        //從redis獲取儲存在列表中的訊息
        List<String> messageStrings = chatRedisTemplate.opsForList().range(key, 0, -1);
        //轉為ChatMessage
        List<ChatMessage> messages = new ArrayList<>();
        if (messageStrings != null) {
            for (String messagesJson : messageStrings) {
                messages.add(gson.fromJson(messagesJson, ChatMessage.class));
            }
        }
        return messages;
    }
}
