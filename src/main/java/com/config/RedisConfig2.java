//package com.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//@Configuration
//public class RedisConfig2 {
//
//    @Bean("chatDataBase")
//    public LettuceConnectionFactory chatRedisConnectionFactory() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//        config.setHostName("localhost");
//        config.setPort(6379);
//        config.setDatabase(8);  // 設定使用的 Redis database 索引
//        return new LettuceConnectionFactory(config);
//    }
//
//    //ForService存取資料
//    @Bean("chatStrStr")
//    public StringRedisTemplate chatStrRedisTemplate(
//            @Qualifier("chatDataBase") RedisConnectionFactory connectionFactory) {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(connectionFactory);
//        return stringRedisTemplate;
//    }
