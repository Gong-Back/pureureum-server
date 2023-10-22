package gongback.pureureumserver.domain.dashboard

import gongback.pureureumserver.support.domain.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class DashboardBoard(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "dashboard_user_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_board_board_user_id")
    )
    val dashboardUser: DashboardUser,

    @Column(length = 50, nullable = false)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    val isNotice: Boolean = false,

    comments: List<DashboardBoardComment> = emptyList(),

    files: List<DashboardBoardFile> = emptyList()

) : BaseTimeEntity() {
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "dashboard_board_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_board_comment_dashboard_board_id")
    )
    private val _comments: MutableList<DashboardBoardComment> = comments.toMutableList()

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "dashboard_board_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_dashboard_board_file_dashboard_board_id")
    )
    private val _files: MutableList<DashboardBoardFile> = files.toMutableList()

    val comments: MutableList<DashboardBoardComment>
        get() = _comments

    val files: MutableList<DashboardBoardFile>
        get() = _files
}
