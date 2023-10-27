package gongback.pureureumserver.config

import gongback.pureureumserver.domain.user.UserDocumentRepository
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean

@TestConfiguration
class TestMockBeanConfig {
    @MockBean
    lateinit var userDocumentRepository: UserDocumentRepository
}
