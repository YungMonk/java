package com.ifchange.rpc.position.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @ClassName Redis
 * @Description: TODO
 * @Author Yung
 * @Date 2020/1/19
 * @Version V1.0
 **/

@Component
public class Redis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @MethodName: setRedisTemplate
     * @Description: 创建 Redis 链接
     * @Param: redisTemplate
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @MethodName: getRedisTemplate
     * @Description: 获取 Redis 链接
     * @Param:
     * @Return: org.springframework.data.redis.core.StringRedisTemplate
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public StringRedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    // -------------------事务相关操作-------------------- //
    /**
     * @MethodName: multi
     * @Description: 标记事务开始
     * @Param:
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void multi(){
        redisTemplate.multi();
    }

    /**
     * @MethodName: exec
     * @Description: 执行所有multi之后的命令
     * @Param:
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void exec(){
        redisTemplate.exec();
    }

    /**
     * @MethodName: unwatch
     * @Description: 取消事务命令
     * @Param:
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void unwatch(){
        redisTemplate.unwatch();
    }

    /**
     * @MethodName: watch
     * @Description: 锁定key直到执行了multi/exec命令
     * @Param: key
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void watch(String key){
        redisTemplate.watch(key);
    }

    /**
     * @MethodName: watch
     * @Description: 锁定key直到执行了multi/exec命令
     * @Param: keys
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void watch(Collection<String> keys){
        redisTemplate.watch(keys);
    }

    // -------------------key相关操作--------------------- //

    /***
     * @MethodName: delete
     * @Description: 删除key
     * @Param: key
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * @MethodName: delete
     * @Description: 批量删除key
     * @Param: keys
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void del(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * @MethodName: dump
     * @Description: 序列化key
     * @Param: key
     * @Return: byte[]
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * @MethodName: exists
     * @Description: 是否存在key
     * @Param: key
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @MethodName: expire
     * @Description: 设置过期时间（多长时间后过期）
     * @Param: key
     * @Param: timeout
     * @Param: unit
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * @MethodName: expireAt
     * @Description: 设置过期时间（什么时候过期）
     * @Param: key
     * @Param: date
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * @MethodName: keys
     * @Description: 查找匹配的 key(生产禁用)
     * @Param: pattern
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * @MethodName: scan
     * @Description: TODO
     * @Param: pattern  表达式
     * @Param: consumer 对迭代到的key进行操作
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void scan(String pattern, Consumer<byte[]> consumer) {
        redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * @MethodName: move
     * @Description: 将当前数据库的 key 移动到给定的数据库 db 当中
     * @Param: key
     * @Param: dbIndex
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * @MethodName: persist
     * @Description: 移除 key 的过期时间，key 将持久保持
     * @Param: key
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * @MethodName: ttl
     * @Description: 返回 key 的剩余的过期时间
     * @Param: key
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * @MethodName: pttl
     * @Description: 返回 key 的剩余的过期时间
     * @Param: key
     * @Param: unit
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long pttl(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * @MethodName: randomKey
     * @Description: 从当前数据库中随机返回一个 key
     * @Param:
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * @MethodName: rename
     * @Description: 修改 key 的名称
     * @Param: oldKey
     * @Param: newKey
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * @MethodName: renameNx
     * @Description: 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     * @Param: oldKey
     * @Param: newKey
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean renameNx(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * @MethodName: type
     * @Description: 返回 key 所储存的值的类型
     * @Param: key
     * @Return: org.springframework.data.redis.connection.DataType
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public DataType type(String key) {
        return redisTemplate.type(key);
    }
    // ------------------- string相关操作 --------------------- //

    /**
     * @MethodName: set
     * @Description: 设置指定 key 的值
     * @Param: key
     * @Param: value
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @MethodName: get
     * @Description: 获取指定 key 的值
     * @Param: key
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @MethodName: mGet
     * @Description: 批量获取
     * @Param: keys
     * @Return: java.util.List<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public List<String> mGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * @MethodName: getRange
     * @Description: 返回 key 中字符串值的子字符
     * @Param: key
     * @Param: start
     * @Param: end
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * @MethodName: getSet
     * @Description: 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     * @Param: key
     * @Param: value
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public String getSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * @MethodName: setEx
     * @Description: 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     * @Param: key
     * @Param: value
     * @Param: timeout 过期时间
     * @Param: unit 时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES 秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * @MethodName: setNx
     * @Description: 只有在 key 不存在时设置 key 的值
     * @Param: key
     * @Param: value
     * @Return: boolean 之前已经存在返回false, 不存在返回true
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public boolean setNx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * @MethodName: mSetNx
     * @Description: 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     * @Param: maps
     * @Return: boolean 之前已经存在返回false, 不存在返回true
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public boolean mSetNx(Map<String, String> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * @MethodName: setBit
     * @Description: 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     * @Param: key
     * @Param: offset
     * @Param: value
     * @Return: boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * @MethodName: getBit
     * @Description: 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     * @Param: key
     * @Param: offset
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * @MethodName: setRange
     * @Description: 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     * @Param: key
     * @Param: value
     * @Param: offset 从指定位置开始覆写
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public void setRange(String key, String value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * @MethodName: strlen
     * @Description: key的string类型value的长度
     * @Param: key
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long strlen(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * @MethodName: mSet
     * @Description: 批量添加
     * @Param: maps
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public void mSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * @MethodName: incrBy
     * @Description: 增加(自增长), 负数则为自减
     * @Param: key
     * @Param: increment
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * @MethodName: incrBy
     * @Description: 增加(自增长), 负数则为自减
     * @Param: key
     * @Param: increment
     * @Return: java.lang.Double
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Double incrBy(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * @MethodName: append
     * @Description: 追加到末尾
     * @Param: key
     * @Param: value
     * @Return: java.lang.Integer
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    // -------------------hash相关操作------------------------- //

    /**
     * @MethodName: hGet
     * @Description: 获取存储在哈希表中指定字段的值
     * @Param: key
     * @Param: field
     * @Return: java.lang.Object
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * @MethodName: hMGet
     * @Description: 获取所有给定字段的值
     * @Param: key
     * @Param: fields
     * @Return: java.util.List<java.lang.Object>
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public List<Object> hMGet(String key, Collection<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * @MethodName: hGetAll
     * @Description: 获取所有给定字段的值
     * @Param: key
     * @Return: java.util.Map<java.lang.Object, java.lang.Object>
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @MethodName: hSet
     * @Description: 单条插入
     * @Param: key
     * @Param: hashKey
     * @Param: value
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public void hSet(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * @MethodName: hMSet
     * @Description: 多条插入
     * @Param: key
     * @Param: maps
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public void hMSet(String key, Map<String, String> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * @MethodName: hSetNX
     * @Description: 仅当hashKey不存在时才设置
     * @Param: key
     * @Param: hashKey
     * @Param: value
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Boolean hSetNX(String key, String hashKey, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * @MethodName: hDel
     * @Description: 删除一个或多个哈希表字段
     * @Param: key
     * @Param: fields
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long hDel(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * @MethodName: hExists
     * @Description: 查看哈希表 key 中，指定的字段是否存在
     * @Param: key
     * @Param: field
     * @Return: boolean
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * @MethodName: hIncrBy
     * @Description: 为哈希表 key 中的指定字段的整数值加上增量 increment
     * @Param: key
     * @Param: field
     * @Param: increment
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long hIncrBy(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * @MethodName: hIncrByFloat
     * @Description: 为哈希表 key 中的指定字段的整数值加上增量 increment
     * @Param: key
     * @Param: field
     * @Param: delta
     * @Return: java.lang.Double
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Double hIncrByFloat(String key, Object field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * @MethodName: hKeys
     * @Description: 获取所有哈希表中的字段
     * @Param: key
     * @Return: java.util.Set<java.lang.Object>
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * @MethodName: hLen
     * @Description: 获取哈希表中字段的数量
     * @Param: key
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long hLen(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * @MethodName: hVals
     * @Description: 获取哈希表中所有值
     * @Param: key
     * @Return: java.util.List<java.lang.Object>
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public List<Object> hVals(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * @MethodName: hScan
     * @Description: 迭代哈希表中的键值对
     * @Param: key
     * @Param: options
     * @Return: org.springframework.data.redis.core.Cursor<java.util.Map.Entry < java.lang.Object, java.lang.Object>>
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Cursor<Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    // ------------------------list相关操作---------------------------- //

    /**
     * @MethodName: lIndex
     * @Description: 通过索引获取列表中的元素
     * @Param: key
     * @Param: index
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * @MethodName: lRange
     * @Description: 获取列表指定范围内的元素
     * @Param: key
     * @Param: start 开始位置, 0是开始位置
     * @Param: end   结束位置, -1返回所有
     * @Return: java.util.List<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public List<String> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * @MethodName: lPush
     * @Description:
     * @Param: key
     * @Param: value
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long lPush(String key, String... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * @MethodName: lPush
     * @Description: 存储在list头部
     * @Param: key
     * @Param: value
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long lPush(String key, Collection<String> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * @MethodName: lPushX
     * @Description: 当list存在的时候才加入
     * @Param: key
     * @Param: value
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long lPushX(String key, String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * @MethodName: lInsertBefore
     * @Description: 在列表中定位元素的左边插入一个元素
     * @Param: key
     * @Param: pivot 定位元素
     * @Param: value 插入元素
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long lInsertBefore(String key, String pivot, String value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * @MethodName: rPush
     * @Description: 从队列的右边入队一个或多个元素
     * @Param: key
     * @Param: value
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long rPush(String key, String... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * @MethodName: rPush
     * @Description: 从队列的右边入队一个或多个元素
     * @Param: key
     * @Param: value
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long rPush(String key, Collection<String> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * @MethodName: rPushX
     * @Description: 从队列的右边入队一个元素，仅队列存在时有效
     * @Param: key
     * @Param: value
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long rPushX(String key, String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * @MethodName: lInsertAfter
     * @Description: 在列表中定位元素的后边插入一个元素
     * @Param: key
     * @Param: pivot 定位元素
     * @Param: value 插入元素
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long lInsertAfter(String key, String pivot, String value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * @MethodName: lSet
     * @Description: 通过索引设置列表元素的值
     * @Param: key
     * @Param: index
     * @Param: value
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public void lSet(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * @MethodName: lPop
     * @Description: 从队列的左边出队一个元素
     * @Param: key
     * @Return: java.lang.String 出队的元素
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * @MethodName: bLPop
     * @Description: 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @Param: key
     * @Param: timeout 等待时间
     * @Param: unit    时间单位
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String bLPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * @MethodName: rPop
     * @Description: 从队列的右边出队一个元素
     * @Param: key
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * @MethodName: bRPop
     * @Description: 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @Param: key
     * @Param: timeout
     * @Param: unit
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String bRPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * @MethodName: rPopLPush
     * @Description: 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * @Param: sourceKey
     * @Param: destinationKey
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String rPopLPush(String sourceKey, String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * @MethodName: bRPopLPush
     * @Description: 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @Param: sourceKey
     * @Param: destinationKey
     * @Param: timeout
     * @Param: unit
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String bRPopLPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * @MethodName: lRem
     * @Description: 删除集合中值等于value得元素
     * @Param: key
     * @Param: index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素; index<0, 从尾部开始删除第一个值等于value的元素;
     * @Param: value
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long lRem(String key, long index, String value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * @MethodName: lTrim
     * @Description: 修剪指定范围的清单
     * @Param: key
     * @Param: start
     * @Param: end
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * @MethodName: lLen
     * @Description: 获取列表长度
     * @Param: key
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    // --------------------set相关操作-------------------------- //

    /**
     * @MethodName: sAdd
     * @Description: 添加一个或者多个元素到集合（set）里
     * @Param: key
     * @Param: values
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long sAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * @MethodName: sRem
     * @Description: 从集合删除一个或多个元素
     * @Param: key
     * @Param: values
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long sRem(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * @MethodName: sPop
     * @Description: 删除并获取一个集合里的元素
     * @Param: key
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * @MethodName: sMove
     * @Description: 移动集合里面的一个元素到另一个集合
     * @Param: key
     * @Param: value
     * @Param: destKey
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Boolean sMove(String key, String value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * @MethodName: sCard
     * @Description: 获取集合里面的元素数量
     * @Param: key
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public Long sCard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * @MethodName: sIsMember
     * @Description: 确定一个给定的值是一个集合的成员
     * @Param: key
     * @Param: value
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * @MethodName: sInter
     * @Description: 获取两个集合的交集
     * @Param: key
     * @Param: otherKey
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sInter(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * @MethodName: sInter
     * @Description: 获取 key集合与多个集合的交集
     * @Param: key
     * @Param: otherKeys
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sInter(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * @MethodName: sInter
     * @Description: 获取多个集合的交集
     * @Param: keys
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sInter(Collection<String> keys) {
        return redisTemplate.opsForSet().intersect(keys);
    }

    /**
     * @MethodName: sInterStore
     * @Description: key集合与otherKey集合的交集存储到destKey集合中
     * @Param: key
     * @Param: otherKey
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sInterStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * @MethodName: sIntersectAndStore
     * @Description: key集合与多个集合的交集存储到destKey集合中
     * @Param: key
     * @Param: otherKeys
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sInterStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * @MethodName: sInterStore
     * @Description: 多个集合的交集存储到destKey集合中
     * @Param: keys
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sInterStore(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(keys, destKey);
    }

    /**
     * @MethodName: sUnion
     * @Description: 获取两个集合的并集
     * @Param: key
     * @Param: otherKeys
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sUnion(String key, String otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * @MethodName: sUnion
     * @Description: 获取key集合与多个集合的并集
     * @Param: key
     * @Param: otherKeys
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * @MethodName: sUnion
     * @Description: 多个集合的并集
     * @Param: keys 不存在的 key可以认为是空的集合
     * @Return: java.util.Set<java.lang.String> 返回给定的多个集合的并集中的所有成员
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sUnion(Collection<String> keys) {
        return redisTemplate.opsForSet().union(keys);
    }

    /**
     * @MethodName: sUnionStore
     * @Description: key集合与otherKey集合的并集存储到destKey中
     * @Param: key
     * @Param: otherKey
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sUnionStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * @MethodName: sUnionAndStore
     * @Description: key集合与多个集合的并集存储到destKey中
     * @Param: key
     * @Param: otherKeys
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sUnionStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * @MethodName: sUnionStore
     * @Description: 多个集合的并集存储到destKey中
     * @Param: keys
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sUnionStore(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(keys, destKey);
    }

    /**
     * @MethodName: sDiff
     * @Description: 获取两个集合的差集
     * @Param: key
     * @Param: otherKey
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sDiff(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * @MethodName: sDiff
     * @Description: 获取key集合与多个集合的差集
     * @Param: key
     * @Param: otherKeys
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sDiff(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * @MethodName: sDiff
     * @Description: 多个集合的差集
     * @Param: keys
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sDiff(Collection<String> keys) {
        return redisTemplate.opsForSet().difference(keys);
    }

    /**
     * @MethodName: sDiffStore
     * @Description: key集合与otherKey集合的差集存储到destKey中
     * @Param: key
     * @Param: otherKey
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sDiffStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey,
                destKey);
    }

    /**
     * @MethodName: sDiffStore
     * @Description: key集合与多个集合的差集存储到destKey中
     * @Param: key
     * @Param: otherKeys
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sDiffStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * @MethodName: sDiffStore
     * @Description: 多个集合的差集存储到destKey中
     * @Param: keys
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long sDiffStore(Collection<String> keys, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(keys, destKey);
    }

    /**
     * @MethodName: sMembers
     * @Description: 获取集合所有元素
     * @Param: key
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * @MethodName: sRandMember
     * @Description: 随机获取集合中的一个元素
     * @Param: key
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public String sRandMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * @MethodName: sRandMember
     * @Description: 随机获取集合中count个元素
     * @Param: key
     * @Param: count
     * @Return: java.util.List<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public List<String> sRandMember(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * @MethodName: sDistinctRandomMembers
     * @Description: 随机获取集合中count个元素并且去除重复的
     * @Param: key
     * @Param: count
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> sDistinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * @MethodName: sScan
     * @Description: 迭代一个集合元素
     * @Param: key
     * @Param: options
     * @Return: org.springframework.data.redis.core.Cursor<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Cursor<String> sScan(String key, ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    //------------------zSet相关操作--------------------------------//

    /**
     * @MethodName: zAdd
     * @Description: 添加一个元素
     * @Param: key
     * @Param: value
     * @Param: score
     * @Return: java.lang.Boolean
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Boolean zAdd(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @MethodName: zAdd
     * @Description: 添加多个元素
     * @Param: key
     * @Param: values
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zAdd(String key, Set<TypedTuple<String>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * @MethodName: zIncrBy
     * @Description: 增加元素的score值，并返回增加后的值
     * @Param: key
     * @Param: member
     * @Param: score
     * @Return: java.lang.Double
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Double zIncrBy(String key, String member, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, member, score);
    }

    /**
     * @MethodName: zRank
     * @Description: 返回元素在集合的排名, 有序集合是按照元素的score值由小到大排列
     * @Param: key
     * @Param: member
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zRank(String key, Object member) {
        return redisTemplate.opsForZSet().rank(key, member);
    }

    /**
     * @MethodName: zRange
     * @Description: 获取集合的元素, 从小到大排序
     * @Param: key
     * @Param: start
     * @Param: end
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * @MethodName: zRangeWithScore
     * @Description: 获取集合元素, 并且把score值也获取
     * @Param: key
     * @Param: start
     * @Param: end
     * @Return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple < java.lang.String>>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<TypedTuple<String>> zRangeWithScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * @MethodName: zRangeByScore
     * @Description: 根据Score值查询集合元素
     * @Param: key
     * @Param: min
     * @Param: max
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * @MethodName: zRangeByScore
     * @Description: 根据Score值查询集合元素
     * @Param: key
     * @Param: min
     * @Param: max
     * @Param: start
     * @Param: end
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> zRangeByScore(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, start, end);
    }

    /**
     * @MethodName: zRangeByScoreWithScores
     * @Description: 根据Score值查询集合元素，从小到大排序，并返回成员的评分
     * @Param: key
     * @Param: min
     * @Param: max
     * @Return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple < java.lang.String>>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * @MethodName: zRangeByScoreWithScores
     * @Description: 根据Score值查询集合元素，从小到大排序，并返回成员的评分
     * @Param: key
     * @Param: min
     * @Param: max
     * @Param: start
     * @Param: end
     * @Return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple < java.lang.String>>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * @MethodName: zRevRank
     * @Description: 返回元素在集合的排名, 按元素的score值由大到小排列
     * @Param: key
     * @Param: member
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zRevRank(String key, Object member) {
        return redisTemplate.opsForZSet().reverseRank(key, member);
    }

    /**
     * @MethodName: zRevRange
     * @Description: 获取集合的元素, 从大到小排序
     * @Param: key
     * @Param: start
     * @Param: end
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> zRevRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * @MethodName: zRevRangeWithScores
     * @Description: 获取集合的元素, 从大到小排序, 并返回score值
     * @Param: key
     * @Param: start
     * @Param: end
     * @Return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple < java.lang.String>>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<TypedTuple<String>> zRevRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * @MethodName: zRevRangeByScore
     * @Description: 根据Score值查询集合元素, 从大到小排序
     * @Param: key
     * @Param: min
     * @Param: max
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> zRevRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * @MethodName: zRevRangeByScore
     * @Description: 根据Score值查询集合元素, 从大到小排序
     * @Param: key
     * @Param: min
     * @Param: max
     * @Param: start
     * @Param: end
     * @Return: java.util.Set<java.lang.String>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<String> zRevRangeByScore(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end);
    }

    /**
     * @MethodName: zRevRangeByScoreWithScores
     * @Description: 根据Score值查询集合元素, 从大到小排序
     * @Param: key
     * @Param: min
     * @Param: max
     * @Return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple < java.lang.String>>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<TypedTuple<String>> zRevRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * @MethodName: zRevRangeByScoreWithScores
     * @Description: 根据Score值查询集合元素, 从大到小排序
     * @Param: key
     * @Param: min
     * @Param: max
     * @Param: start
     * @Param: end
     * @Return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple < java.lang.String>>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Set<TypedTuple<String>> zRevRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * @MethodName: zCount
     * @Description: 根据score值获取集合元素数量
     * @Param: key
     * @Param: min
     * @Param: max
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * @MethodName: zCard
     * @Description: 获取集合大小
     * @Param: key
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * @MethodName: zScore
     * @Description: 获取集合中member成员的score值
     * @Param: key
     * @Param: value
     * @Return: java.lang.Double
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Double zScore(String key, Object member) {
        return redisTemplate.opsForZSet().score(key, member);
    }

    /**
     * @MethodName: zRem
     * @Description: 从集合中移除一个或多个元素
     * @Param: key
     * @Param: values
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zRem(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * @MethodName: zRemRangeByRank
     * @Description: 移除指定索引位置的成员
     * @Param: key
     * @Param: start
     * @Param: end
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zRemRangeByRank(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * @MethodName: zRemRangeByScore
     * @Description: 根据指定的score值的范围来移除成员
     * @Param: key
     * @Param: min
     * @Param: max
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zRemRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * @MethodName: zUnionStore
     * @Description: 获取key和otherKey的并集并存储在destKey中
     * @Param: key
     * @Param: otherKey
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zUnionStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * @MethodName: zUnionStore
     * @Description: 获取key和otherKey的并集并存储在destKey中
     * @Param: key
     * @Param: otherKeys
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zUnionStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * @MethodName: zInterStore
     * @Description: 交集
     * @Param: key
     * @Param: otherKey
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zInterStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * @MethodName: zInterStore
     * @Description: 交集
     * @Param: key
     * @Param: otherKeys
     * @Param: destKey
     * @Return: java.lang.Long
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Long zInterStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * @MethodName: zScan
     * @Description: 迭代集合中的元素
     * @Param: key
     * @Param: options
     * @Return: org.springframework.data.redis.core.Cursor<org.springframework.data.redis.core.ZSetOperations.TypedTuple < java.lang.String>>
     * @Author: Yung
     * @Date: 2020/1/20
     **/
    public Cursor<TypedTuple<String>> zScan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }
}
