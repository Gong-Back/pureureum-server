package gongback.pureureumserver.controller.test

import org.redisson.api.RedissonClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisTestController(
    private val redissonClient: RedissonClient,
) {
    @GetMapping("/redis-test")
    fun test(): String {
        val id = 1L
        val key = "test:$id"
        val testBucket = redissonClient.getBucket<String>(key)
        testBucket.set("test")

        return redissonClient.getBucket<String>(key).get()
    }
}
