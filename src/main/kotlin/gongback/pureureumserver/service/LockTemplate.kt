package gongback.pureureumserver.service

import org.springframework.stereotype.Component

private const val DEFAULT_COMMUNITY_LIKE_COUNT_LOCK_WAIT_TIME_MS = 10000L
private const val DEFAULT_COMMUNITY_LIKE_COUNT_LOCK_LEASE_TIME_MS = 3000L

@Component
interface LockTemplate {
    fun executeWithRetry(
        key: String,
        waitTimeMS: Long = DEFAULT_COMMUNITY_LIKE_COUNT_LOCK_WAIT_TIME_MS,
        leaseTimeMs: Long = DEFAULT_COMMUNITY_LIKE_COUNT_LOCK_LEASE_TIME_MS,
        execute: () -> Unit,
    )

    fun executeWithNoRetry(key: String, execute: () -> Unit)
}
