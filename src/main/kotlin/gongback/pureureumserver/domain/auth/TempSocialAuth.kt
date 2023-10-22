package gongback.pureureumserver.domain.auth

import gongback.pureureumserver.support.domain.BaseTimeEntity
import gongback.pureureumserver.support.domain.Gender
import gongback.pureureumserver.support.domain.SocialType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(name = "temp_social_auth", indexes = [Index(name = "idx_email", columnList = "email")])
class TempSocialAuth(
    @Column(unique = true, nullable = false)
    val email: String,

    val name: String? = null,

    val birthday: String? = null,

    val phoneNumber: String? = null,

    val gender: Gender? = null,

    @Enumerated(EnumType.STRING)
    val socialType: SocialType? = null
) : BaseTimeEntity()
