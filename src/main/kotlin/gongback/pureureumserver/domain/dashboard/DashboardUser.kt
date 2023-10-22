package gongback.pureureumserver.domain.dashboard

import gongback.pureureumserver.support.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class DashboardUser(

    @Column(nullable = false, name = "user_id")
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    val role: DashboardUserRole
) : BaseTimeEntity()
