package com.kinglin.pet.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author huangjl
 * @description
 * @since 2024-03-19 19:55
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisUtil() {

    }

    public static final long REDIS_DEFAULT_EXPIRE_TIME = 60 * 60;
    public static final TimeUnit REDIS_DEFAULT_EXPIRE_TIMEUNIT = TimeUnit.SECONDS;

    // region 通用相关操作

    /**
     * 返回指定key的剩余存活时间，单位 秒
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 设置指定key的存活时间，单位 秒
     * @param key
     * @param time
     */
    public void setExpire(String key, Integer time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 清楚指定key的缓存
     * @param key
     */
    public void removeCache(String key){
        redisTemplate.delete(key);
    }

    /**
     * 查询key是否存在
     *
     * @param redisKey
     * @return
     */
    public boolean isExist(String redisKey) {
        return redisTemplate.hasKey(redisKey);
    }

    // endregion

    // region zSET相关操作
    /**
     * 取出整个set的所有记录
     * @param key
     * @param expireSec 过期时间 单位是秒
     * @return
     */
    public Set<Object> zgetAllMembers(String key, long expireSec) {
        long now = System.currentTimeMillis();
        // 指定获取从该expireSec时间之后的数据
        long tts = now - expireSec * 1000;
        return redisTemplate.opsForZSet().rangeByScore(key, tts+1, Long.MAX_VALUE);
    }

    /**
     * 取出set中符合条件的指定数量的记录
     * @param key
     * @param expireSec 过期时间 单位是秒
     * @param offset    开始位置
     * @param count 数量
     * @return
     */
    public Set<Object> zgetMembersWithLimit(String key, long expireSec, long offset, long count) {
        long now = System.currentTimeMillis();
        // 指定获取从该expireSec时间之后的数据
        long tts = now - expireSec * 1000;
        return redisTemplate.opsForZSet().rangeByScore(key, tts+1, Long.MAX_VALUE, offset, count);
    }

    /**
     * 分数从大到小取排行榜
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<Object> zReverange(String key, Long start, Long stop) {
        return redisTemplate.opsForZSet().reverseRange(key, start, stop);
    }

    /**
     * 获取指定对象的排名
     * @param key
     * @param member
     * @return
     */
    public Long reverseRank(String key, Object member) {
        Long longValue = redisTemplate.opsForZSet().reverseRank(key, member);
        if (null != longValue) {
            return longValue;
        }
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 存入一条数据到sorted set    默认按时间排序
     * @param key
     * @param object
     */
    public boolean zset(String key, Object object){
        long now = System.currentTimeMillis();
        return this.zsetWithScore(key, object, now);
    }

    /**
     * 存入一条数据到sorted set    按分数排序
     * @param key
     * @param object
     */
    public boolean zsetWithScore(String key, Object object, long score){
        return redisTemplate.opsForZSet().add(key, object, score);
    }

    /**
     * 查看匹配数目
     * @param key
     * @param expireSec 过期时间 单位是秒
     * @return
     */
    public long zCount(String key, long expireSec){
        long now = System.currentTimeMillis();
        long tts = now - expireSec * 1000;
        return redisTemplate.opsForZSet().count(key, tts+1, Long.MAX_VALUE);
    }

    /**
     * 删除Set指定key下的对象
     * @param key
     * @param value
     */
    public void zsetDelMember(String key, Object value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 集合zset中是否存在目标对象
     * @param key
     * @param value
     * @return
     */
    public Boolean zsetScore(String key, Object value) {
        if (null!=redisTemplate.opsForZSet().score(key, value) && redisTemplate.opsForZSet().score(key, value) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean zsetByLimit(String key, Object value, Integer limit) {
        Boolean result = this.zset(key, value);
        // 存入数据后，查询zset中的数量
        Long count = redisTemplate.opsForZSet().zCard(key);
        // 如果数量大于limit的两倍，则进行清除操作，清除之前的数据
        if (count > limit * 2) {
            redisTemplate.opsForZSet().removeRange(key, 0, count-limit-1);
        }
        return result;
    }

    // endregion

    // region SET相关操作

    /**
     * set集合获取
     * @param key
     * @return
     */
    public Set<Object> getMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 集合set中是否存在目标对象
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean isMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 向SET中添加无过期时间的对象
     * @param key
     * @param value
     */
    public void addMember(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 向SET中添加有过期时间的对象
     * @param key
     * @param value
     * @param time 设置指定key的存活时间，单位 秒
     */
    public void addExMember(String key, String value, Integer time) {
        redisTemplate.opsForSet().add(key, value);
        setExpire(key, time);
    }

    /**
     * 删除SET中的数据
     * @param key
     * @param value
     */
    public void delMember(String key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 查询SET大小
     * @param key
     * @return
     */
    public Object scard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 获取SET中的所有对象
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // endregion

    // region String相关操作
    /**
     * 存储简单数据类型
     * 不用更新的缓存信息
     * @param key
     * @param value
     */
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 实体类转换成string再进行操作
     * 不用更新的缓存信息
     * @param key
     * @param value
     */
    public void setObjectValue(String key, Object value) {
        String jsonString = JSON.toJSONString(value);
        setValue(key, jsonString);
    }

    /**
     * 使用 默认有效期 和 默认时间单位 存储简单数据类型
     * @param key
     * @param value
     */
    public void setExValue(String key, Object value) {
        setExValue(key, value, REDIS_DEFAULT_EXPIRE_TIME, REDIS_DEFAULT_EXPIRE_TIMEUNIT);
    }

    /**
     * 使用 指定有效期 和 默认时间单位 存储简单数据类型
     * @param key
     * @param value
     * @param time
     */
    public void setExValue(String key, Object value, long time) {
        setExValue(key, value, time, REDIS_DEFAULT_EXPIRE_TIMEUNIT);
    }

    /**
     * 使用 指定有效期 和 指定时间单位 存储简单数据类型
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void setExValue(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 使用默认有效期存储实体类
     * @param key
     * @param value
     */
    public void setExObjectValue(String key, Object value) {
        String jsonString = JSON.toJSONString(value);
        setExValue(key, jsonString);
    }

    /**
     * 使用指定有效期存储实体类
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void setExObjectValue(String key, Object value, long time, TimeUnit timeUnit) {
        String jsonString = JSON.toJSONString(value);
        setExValue(key, jsonString, time, timeUnit);
    }

    /**
     * 获取简单数据类型
     * @param key
     * @return
     */
    public Object getValue(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取实体类的JSONString
     * @param key
     * @return
     */
    public String getObjectString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据传入的类型获取实体类
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String objectString = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(objectString)) {
            return JSONObject.parseObject(objectString, clazz);
        }
        return null;
    }

    /**
     * 递增
     * @param key
     */
    public void incr(String key) {
        redisTemplate.opsForValue().increment(key, 1);
    }
    /**
     * 递减
     * @param key
     */
    public void decr(String key) {
        redisTemplate.opsForValue().decrement(key, 1);
    }
    /**
     * 删除简单数据类型或实体类
     * @param key
     */
    public void delValue(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

    // endregion

    // region Hash相关操作
    /**
     * 从redis中获取map数据
     *
     * @param key
     * @return
     */
    public Map hmGet(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取map中的指定hashKey对应的数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, String hashKey) {
        try {
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把map存到redis中
     *
     * @param key
     * @param map
     */
    public void hmPut(String key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 把数据存储在map中指定hashKey的value
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmPut(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 删除map中指定hashKey
     * @param key
     * @param hashKeys
     * @return
     */
    public Long hDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }
    // endregion

    // region List相关操作
    /**
     * 把list存入redis
     * @param key
     * @return
     */
    public Long setAllList(String key, List list) {
        List<String> dataList = new ArrayList<>();
        for (Object temp : list) {
            dataList.add(JSON.toJSONString(temp));
        }
        return this.redisTemplate.opsForList().rightPushAll(key, dataList);
    }

    /**
     * 获取list中全部数据
     * @param key
     * @param clazz
     * @return
     */
    public <T> List<T> getAllList(String key, Class<T> clazz) {
        List list = this.redisTemplate.opsForList().range(key, 0, -1);
        List<T> resultList = new ArrayList<>();
        for (Object temp : list) {
            resultList.add(JSON.parseObject((String) temp, clazz));
        }
        return resultList;
    }
    // endregion

    /**
     * 设置一个有效期至午夜12点的缓存
     * @param key
     * @param value
     */
    public void setExValueForToday(String key, Object value) {
        //获取当天剩余秒数
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long remainTime = ChronoUnit.SECONDS.between(LocalDateTime.now(),midnight);
        log.info("当天剩余秒数：{}", remainTime);
        redisTemplate.opsForValue().set(key, value, remainTime, TimeUnit.SECONDS);
    }

    /**
     * 设置一个有效期至周日午夜12点的缓存
     * @param key
     * @param value
     */
    public void setExValueForWeekend(String key, Object value) {
        //获取本周剩余秒数
        LocalDateTime midnight = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long remainTime = ChronoUnit.SECONDS.between(LocalDateTime.now(),midnight);
        log.info("本周剩余秒数：{}", remainTime);
        redisTemplate.opsForValue().set(key, value, remainTime, TimeUnit.SECONDS);
    }
}
