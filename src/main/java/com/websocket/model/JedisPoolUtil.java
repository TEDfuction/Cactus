package com.websocket.model;//package com.websocket.chat.model;
//
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//public class JedisPoolUtil {
//
//    private static JedisPool jedisPool = null;
//
//    // private->防止實例化
//    private JedisPoolUtil() {}
//
//    // 獲取 Jedis 連接池
//    public static JedisPool getJedisPool() {
//        if (jedisPool == null) {
//            synchronized (JedisPoolUtil.class) {
//                if (jedisPool == null) {
//                    JedisPoolConfig poolConfig = new JedisPoolConfig();
//                    poolConfig.setMaxTotal(8);
//                    poolConfig.setMaxIdle(8);
//                    poolConfig.setMaxWaitMillis(10000);
//                    jedisPool = new JedisPool(poolConfig, "localhost", 6379);
//                }
//            }
//        }
//        return jedisPool;
//    }
//
//    public static void shutdownJedisPool() {
//        if (jedisPool != null)
//            jedisPool.destroy();
//    }
//}
