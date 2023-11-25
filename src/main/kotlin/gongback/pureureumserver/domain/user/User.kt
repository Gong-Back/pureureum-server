package gongback.pureureumserver.domain.user

import gongback.pureureumserver.support.domain.BaseUpdatableEntity
import gongback.pureureumserver.support.domain.Gender
import jakarta.persistence.AttributeOverride
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "users")
class User(
    @Embedded
    val information: UserInformation,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "password", nullable = false))
    var password: Password,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    val userRole: UserRole = UserRole.ROLE_USER,

    profile: Profile = Profile.defaultProfile(),

    badges: List<UserBadge> = emptyList(),
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

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        orphanRemoval = true,
    )
    @JoinColumn(
        name = "user_id",
        foreignKey = ForeignKey(name = "fk_badge_user_id"),
        updatable = false,
        nullable = false,
    )
    private val _badges: MutableList<UserBadge> = badges.toMutableList()

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

    val badges: List<UserBadge>
        get() = _badges

    constructor(
        password: Password,
        name: String,
        birthday: LocalDate,
        email: String,
        gender: Gender,
        nickname: String,
    ) : this(
        information = UserInformation(
            name = name,
            birthday = birthday,
            email = email,
            gender = gender,
            nickname = nickname,
            hasCitizenship = false,
        ),
        password = password,
    )

    fun authenticate(password: Password) {
        require(this.password == password) { "비밀번호가 일치하지 않습니다" }
    }

    fun updatePassword(password: Password) {
        if (password.value.isNotBlank()) {
            this.password = password
        }
    }

    fun updateNickname(nickname: String) {
        if (nickname.isNotBlank()) {
            information.nickname = nickname
        }
    }

    fun updateProfile(newProfile: Profile) {
        profile = newProfile
    }
}
