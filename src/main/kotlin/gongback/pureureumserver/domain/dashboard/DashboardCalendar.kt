package gongback.pureureumserver.domain.dashboard

import gongback.pureureumserver.support.domain.BaseUpdatedTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class DashboardCalendar(

    @Column(nullable = false)
    val date: LocalDateTime,

    @Column(nullable = false)
    val content: String,
) : BaseUpdatedTimeEntity()
