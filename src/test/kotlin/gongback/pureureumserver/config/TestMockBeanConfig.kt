package gongback.pureureumserver.config

import gongback.pureureumserver.infra.culturalevent.SeoulOpenDataClient
import gongback.pureureumserver.infra.file.AwsS3Client
import gongback.pureureumserver.security.JwtTokenProvider
import gongback.pureureumserver.service.FileKeyGenerator
import org.redisson.api.RedissonClient
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean

@TestConfiguration
class TestMockBeanConfig {

    @MockBean
    lateinit var redissonClient: RedissonClient

    @MockBean
    lateinit var awsS3Client: AwsS3Client

    @MockBean
    lateinit var seoulOpenDataClient: SeoulOpenDataClient

    @MockBean
    lateinit var jwtTokenProvider: JwtTokenProvider

    @MockBean
    lateinit var fileKeyGenerator: FileKeyGenerator
}
