package org.example.Service;

import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedissonService {

    private static final Logger logger = LoggerFactory.getLogger(RedissonService.class);

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 设置Map中的键值对
     */
    public void setKeyValue(String mapName, String key, String value) {
        try {
            RMap<String, String> map = redissonClient.getMap(mapName);
            map.put(key, value);
        } catch (Exception e) {
            logger.error("Error setting key-value in map: {}", e.getMessage(), e);
        }
    }

    /**
     * 获取Map中的值
     */
    public Optional<String> getKeyValue(String mapName, String key) {
        try {
            RMap<String, String> map = redissonClient.getMap(mapName);
            return Optional.ofNullable(map.get(key));
        } catch (Exception e) {
            logger.error("Error getting value for key from map: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * 设置单个值
     */
    public void setSingleValue(String key, String value) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key);
            bucket.set(value);
        } catch (Exception e) {
            logger.error("Error setting single value: {}", e.getMessage(), e);
        }
    }

    /**
     * 设置带有过期时间的单个值
     */
    public void setSingleValueWithExpire(String key, String value, long ttl, TimeUnit timeUnit) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key);
            bucket.set(value, ttl, timeUnit);
        } catch (Exception e) {
            logger.error("Error setting single value with TTL: {}", e.getMessage(), e);
        }
    }

    /**
     * 获取单个值
     */
    public Optional<String> getSingleValue(String key) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key);
            return Optional.ofNullable(bucket.get());
        } catch (Exception e) {
            logger.error("Error getting single value: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * 删除键
     */
    public boolean deleteKey(String key) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key);
            return bucket.delete();
        } catch (Exception e) {
            logger.error("Error deleting key: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 检查键是否存在
     */
    public boolean isKeyExist(String key) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key);
            return bucket.isExists();
        } catch (Exception e) {
            logger.error("Error checking if key exists: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 设置Map中的键值对并设置过期时间
     */
    public void setKeyValueWithExpire(String mapName, String key, String value, long ttl, TimeUnit timeUnit) {
        try {
            RMap<String, String> map = redissonClient.getMap(mapName);
            map.fastPut(key, value);
            // Set the expiration on the entire map or individual keys as needed.
            // Here we assume you want to set the expiration on the entire map.
            map.expire(ttl, timeUnit);
        } catch (Exception e) {
            logger.error("Error setting key-value with TTL in map: {}", e.getMessage(), e);
        }
    }

    /**
     * 获取Map的大小
     */
    public long getMapSize(String mapName) {
        try {
            RMap<String, String> map = redissonClient.getMap(mapName);
            return map.size();
        } catch (Exception e) {
            logger.error("Error getting size of map: {}", e.getMessage(), e);
            return -1;
        }
    }

    /**
     * 清空Map中的所有数据
     */
    public void clearMap(String mapName) {
        try {
            RMap<String, String> map = redissonClient.getMap(mapName);
            map.clear();
        } catch (Exception e) {
            logger.error("Error clearing map: {}", e.getMessage(), e);
        }
    }
}