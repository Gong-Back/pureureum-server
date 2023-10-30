package gongback.pureureumserver.domain.user

import gongback.pureureumserver.support.domain.Gender
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate

@Embeddable
data class UserInformation(
    @Column(nullable = false, length = 20)
    val name: String,

    @Column(unique = true, nullable = false, length = 30)
    val email: String,

    @Column(nullable = false)
    val birthday: LocalDate,

    @Column(nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(unique = true, nullable = false, length = 30)
    var nickname: String,

    @Column(nullable = false)
    val hasCitizenship: Boolean,
)
