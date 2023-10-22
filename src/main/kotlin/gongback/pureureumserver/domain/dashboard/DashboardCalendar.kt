package gongback.pureureumserver.domain.dashboard

import gongback.pureureumserver.support.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class DashboardCalendar(

    @Column(nullable = false)
    val date: LocalDateTime,

    @Column(nullable = false)
    val content: String
) : BaseEntity()
