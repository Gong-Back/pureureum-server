package gongback.pureureumserver.infra.lock

import gongback.pureureumserver.service.LockTemplate
import org.redisson.api.RedissonClient
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.TimeUnit

private const val DEFAULT_REDIS_LOCK_TIMEOUT_MS = 3000L

@Component
class RedisLockTemplate(
    private val redisTemplate: RedisTemplate<String, String>,
    private val redissonClient: RedissonClient,
) : LockTemplate {

    override fun executeWithRetry(key: String, waitTimeMS: Long, leaseTimeMs: Long, execute: () -> Unit) {
        val lock = redissonClient.getLock(key)
        val available = lock.tryLock(
            waitTimeMS,
            leaseTimeMs,
            TimeUnit.MILLISECONDS,
        )
        try {
            if (!available) {
                throw RuntimeException("너무 많은 요청으로 인해 락 획득에 실패했습니다. 잠시 후 다시 시도해주세요")
            }
            execute()
        } finally {
            if (available) {
                lock.unlock()
            }
        }
    }

    override fun executeWithNoRetry(key: String, execute: () -> Unit) {
        val lock =
            redisTemplate.opsForValue().setIfAbsent(key, "lock", Duration.ofMillis(DEFAULT_REDIS_LOCK_TIMEOUT_MS))
                ?: false
        if (!lock) {
            throw RuntimeException("락 획득에 실패했습니다. 잠시 후 다시 시도해주세요")
        }

        try {
            execute()
        } finally {
            redisTemplate.delete(key)
        }
    }
}
