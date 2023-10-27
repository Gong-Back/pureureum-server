package gongback.pureureumserver.controller.test

import io.swagger.v3.oas.annotations.tags.Tag
import org.redisson.api.RedissonClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "02. Redis Test", description = "레디스 테스트")
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
