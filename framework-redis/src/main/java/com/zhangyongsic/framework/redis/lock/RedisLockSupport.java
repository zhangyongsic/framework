package com.zhangyongsic.framework.redis.lock;


import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import sun.management.VMManagement;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


/**
 * redis锁
 *
 * @author fanchao
 */
@Component("redisLockSupport")
public class RedisLockSupport {

    private static final Long SUCCESS = 1L;

    private static final int LOCK_EXPIRE = 3600 * 100;

    private static final String LOCK_LUA_SCRIPT = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 " +
            "then return redis.call('pexpire', KEYS[1], ARGV[2]) " +
            "else return 0 end";

    private static final String UNLOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] " +
            "then return redis.call('del', KEYS[1]) " +
            "else return 0 end";

    @SuppressWarnings("rawtypes")
    @Autowired
    @Qualifier(value = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    // List<String> keys = Arrays.asList(key, ptId, String.valueOf(timeout));
    // DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
    // redisScript.setScriptText(LOCK_LUA_SCRIPT);
    // redisScript.setResultType(ReturnType.INTEGER);

    /**
     * 加锁
     */
    public void lock(String key) {
        if (!tryLock(key)) {
            throw new BusinessException(BaseCode.BUSY);
        }
    }

    /**
     * 加锁
     */
    public void lock(String region, String key) {
        if (!tryLock(region + key)) {
            throw new BusinessException(BaseCode.BUSY);
        }
    }

    /**
     * 重试+重入
     *
     * @param key
     * @return
     */
    private boolean tryLock(String key) {
        String ptId = ptId();
        if (ptId == null) {
            return false;
        }
        // 重试15次
        int retryTimes = 15;
        while (retryTimes > 0) {
            retryTimes--;
            // 执行脚本
            if (executeLockScript(key, ptId, LOCK_EXPIRE)) {
                return true;
            }
            String value = stringRedisTemplate.opsForValue().get(key);
            if (value != null && value.equals(ptId)) {
                return stringRedisTemplate.expire(key, LOCK_EXPIRE, TimeUnit.MILLISECONDS);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        // 获得锁失败
        return false;
    }

    private boolean executeLockScript(String key, String ptId, long expire) {
        Long result = (Long) stringRedisTemplate.execute((RedisConnection connection) -> connection.eval(
                LOCK_LUA_SCRIPT.getBytes(),
                ReturnType.INTEGER,
                1,
                key.getBytes(),
                ptId.getBytes(),
                String.valueOf(expire).getBytes())
        );
        return SUCCESS.equals(result);
    }

    /**
     * 解锁
     *
     * @param key
     * @return
     */
    public void unlock(String key) {
        String ptId = ptId();
        if (ptId == null) {
            return;
        }
        executeUnlockScript(key, ptId);
    }

    public void unlock(String region, String key) {
        String ptId = ptId();
        if (ptId == null) {
            return;
        }
        executeUnlockScript(region + key, ptId);
    }

    private void executeUnlockScript(String key, String ptId) {
        stringRedisTemplate.execute((RedisConnection connection) -> connection.eval(
                UNLOCK_LUA_SCRIPT.getBytes(),
                ReturnType.INTEGER,
                1,
                key.getBytes(),
                ptId.getBytes())
        );
    }

    /**
     * 获取pid + 线程id
     *
     * @return
     */
    private String ptId() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            Field jvm = runtime.getClass().getDeclaredField("jvm");
            jvm.setAccessible(true);
            VMManagement mgmt = (VMManagement) jvm.get(runtime);
            Method pidMethod = mgmt.getClass().getDeclaredMethod("getProcessId");
            pidMethod.setAccessible(true);
            Integer pid = (Integer) pidMethod.invoke(mgmt);
            String threadId = String.valueOf(Thread.currentThread().getId());
            return pid.toString() + threadId;
        } catch (Exception e) {
            return null;
        }
    }
}
