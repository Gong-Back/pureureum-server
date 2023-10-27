package gongback.pureureumserver.domain.user

import gongback.pureureumserver.support.domain.BaseUpdatableEntity
import gongback.pureureumserver.support.domain.Gender
import gongback.pureureumserver.support.domain.SocialType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDate

@Entity
class User(
    @Embedded
    val information: UserInformation,

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    val socialType: SocialType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    val userRole: UserRole,

    profile: Profile = Profile.defaultProfile(),
) : BaseUpdatableEntity() {
    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        orphanRemoval = true,
    )
    @JoinColumn(
        name = "profile_id",
        nullable = false,
        unique = true,
        foreignKey = ForeignKey(name = "fk_user_profile_id"),
    )
    var profile: Profile = profile
        protected set

    val name: String
        get() = information.name

    val email: String
        get() = information.email

    val birthday: LocalDate
        get() = information.birthday

    val gender: Gender
        get() = information.gender

    val nickname: String
        get() = information.nickname

    val hasCitizenship: Boolean
        get() = information.hasCitizenship
}
