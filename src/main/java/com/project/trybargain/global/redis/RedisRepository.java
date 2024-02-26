package com.project.trybargain.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, String value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void saveSet(String key, String value) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, value);
    }

    public String getValue(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return (String) valueOperations.get(key);
    }

    public Set<Object> getSetValues(String key) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.members(key);
    }

    public void deleteSetValue(String key, String value) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        setOperations.remove(key, value);
    }
}
