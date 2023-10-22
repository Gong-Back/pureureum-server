package gongback.pureureumserver.domain.dashboard

import gongback.pureureumserver.support.domain.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity
class Dashboard(

    @Column(nullable = false)
    val cultureContentId: Long,

    @Column
    val introduction: String,

    calendars: List<DashboardCalendar> = emptyList(),

    users: List<DashboardUser> = emptyList(),

    galleries: List<DashboardGallery> = emptyList(),

    boards: List<DashboardBoard> = emptyList(),

    ) : BaseTimeEntity() {
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "dashboard_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_calendar_dashboard_id")
    )
    private val _calendars: MutableList<DashboardCalendar> = calendars.toMutableList()

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "dashboard_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_user_dashboard_id")
    )
    private val _users: MutableList<DashboardUser> = users.toMutableList()

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "dashboard_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_gallery_dashboard_id")
    )
    private val _galleries: MutableList<DashboardGallery> = galleries.toMutableList()

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "dashboard_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_bulletin_board_dashboard_id_ref_dashboard_id")
    )
    private val _boards: MutableList<DashboardBoard> = boards.toMutableList()

    val calendars: MutableList<DashboardCalendar>
        get() = _calendars

    val users: MutableList<DashboardUser>
        get() = _users

    val galleries: MutableList<DashboardGallery>
        get() = _galleries

    val boards: MutableList<DashboardBoard>
        get() = _boards
}
