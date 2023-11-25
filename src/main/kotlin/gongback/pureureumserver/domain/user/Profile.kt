package gongback.pureureumserver.domain.user

import gongback.pureureumserver.domain.file.File
import gongback.pureureumserver.support.constant.FilePackage
import gongback.pureureumserver.support.domain.FileContentType
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
@Table(name = "profile")
class Profile(
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

    constructor(
        fileKey: String,
        contentType: String,
        originalFileName: String,
    ) : this(
        file = File(
            fileKey = fileKey,
            contentType = contentType,
            originalFileName = originalFileName,
        ),
    )

    companion object {
        const val DEFAULT_PROFILE_NAME = "default_profile.svg"

        fun defaultProfile(): Profile {
            return Profile(
                file = File(
                    fileKey = "${FilePackage.COMMON.toLowercase()}/${FilePackage.PROFILE.toLowercase()}/$DEFAULT_PROFILE_NAME",
                    contentType = FileContentType.SVG.contentType,
                    originalFileName = DEFAULT_PROFILE_NAME,
                ),
            )
        }
    }
}
