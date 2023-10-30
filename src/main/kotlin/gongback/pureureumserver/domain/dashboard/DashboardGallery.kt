package gongback.pureureumserver.domain.dashboard

import gongback.pureureumserver.support.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class DashboardGallery(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "dashboard_user_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_board_gallery_dashboard_user_id"),
    )
    val dashboardUser: DashboardUser,

    @Column(nullable = false)
    val fileKey: String,

    @Column(nullable = false)
    val contentType: String,

    @Column(nullable = false)
    val originalFileName: String,

    val caption: String,
) : BaseEntity()
