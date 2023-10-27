package gongback.pureureumserver.domain.user

import gongback.pureureumserver.support.BaseTests.RepositoryTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@RepositoryTest
class UserRepositoryTest(
    private val userRepository: UserRepository,
) {
    @Test
    fun `user save test`() {
        userRepository.save(User(name = "test"))
        assertThat(userRepository.findAll().size).isEqualTo(1)
    }
}
