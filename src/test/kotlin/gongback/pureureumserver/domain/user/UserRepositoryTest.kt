package gongback.pureureumserver.domain.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@ActiveProfiles("test")
@DataJpaTest(properties = ["spring.jpa.hibernate.ddl-auto=none"])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserRepositoryTest(
    private val userRepository: UserRepository,
) {
    @Test
    fun `user save test`() {
        userRepository.save(User(name = "test"))
        assertThat(userRepository.findAll().size).isEqualTo(1)
    }
}
