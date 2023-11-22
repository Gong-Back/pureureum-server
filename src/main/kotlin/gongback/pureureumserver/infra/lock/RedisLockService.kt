package gongback.pureureumserver.infra.lock

import gongback.pureureumserver.service.LockService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

private const val DEFAULT_REDIS_LOCK_TIMEOUT_MS = 3000L

@Repository
class RedisLockService(
    private val redisTemplate: RedisTemplate<String, String>,
) : LockService {
    override fun lock(key: String): Boolean {
        return redisTemplate.opsForValue().setIfAbsent(key, "lock", Duration.ofMillis(DEFAULT_REDIS_LOCK_TIMEOUT_MS)) ?: false
    }

    override fun unlock(key: String) {
        redisTemplate.delete(key)
    }
}
