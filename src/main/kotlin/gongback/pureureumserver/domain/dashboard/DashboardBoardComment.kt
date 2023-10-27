package gongback.pureureumserver.domain.dashboard

import gongback.pureureumserver.support.domain.BaseUpdatedTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class DashboardBoardComment(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "dashboard_user_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_board_comment_dashboard_user_id"),
    )
    val dashboardUser: DashboardUser,

    @Column(length = 200, nullable = false)
    val content: String,

    @Column(nullable = false)
    val parentId: Long,
) : BaseUpdatedTimeEntity()
