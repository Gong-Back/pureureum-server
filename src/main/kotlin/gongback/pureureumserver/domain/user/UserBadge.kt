package gongback.pureureumserver.domain.user

import gongback.pureureumserver.domain.file.File
import gongback.pureureumserver.support.constant.FilePackage
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_badge")
class UserBadge(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @OneToOne(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        orphanRemoval = true,
    )
    @JoinColumn(name = "file_id", nullable = false, updatable = false)
    val file: File,
) {
    companion object {
        private const val LOCK_BADGE_FILE_NAME = "badge-lock.svg"

        fun getLockBadgeFileKey(): String {
            return "${FilePackage.COMMON}/${FilePackage.BADGE}/$LOCK_BADGE_FILE_NAME"
        }
    }
}
