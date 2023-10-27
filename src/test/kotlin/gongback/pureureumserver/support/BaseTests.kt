package gongback.pureureumserver.support

import gongback.pureureumserver.config.TestMockBeanConfig
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

class BaseTests {
    @ActiveProfiles("test")
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    @TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
    annotation class TestEnvironment

    @TestEnvironment
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    @DataJpaTest(properties = ["spring.jpa.hibernate.ddl-auto=none"])
    annotation class RepositoryTest

    @TestEnvironment
    @SpringBootTest
    @Import(TestMockBeanConfig::class)
    annotation class IntegrationTest
}
