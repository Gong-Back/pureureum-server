package gongback.pureureumserver.domain.culturecontent

import gongback.pureureumserver.support.domain.BaseUpdatedTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import java.time.LocalDate

@Entity
class CultureContent(
    @Embedded
    val cultureContentInformation: CultureContentInformation,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val status: CultureContentStatus = CultureContentStatus.ADMIN_REQUIRED,

    files: List<CultureContentFile> = emptyList(),

    comments: List<CultureContentComment> = emptyList(),
) : BaseUpdatedTimeEntity() {
    @Column(nullable = false)
    var likeCount: Int = 0
        protected set

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "culture_content_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_culture_content_file_culture_content_id"),
    )
    private val _files: MutableList<CultureContentFile> = files.toMutableList()

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "culture_content_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_culture_content_comment_culture_content_id"),
    )
    private val _comments: MutableList<CultureContentComment> = comments.toMutableList()

    val files: List<CultureContentFile>
        get() = _files

    val comments: List<CultureContentComment>
        get() = _comments

    val title: String
        get() = cultureContentInformation.title

    val introduction: String
        get() = cultureContentInformation.introduction

    val content: String
        get() = cultureContentInformation.content

    val startDate: LocalDate
        get() = cultureContentInformation.startDate

    val endDate: LocalDate
        get() = cultureContentInformation.endDate

    val commentEnabled: Boolean
        get() = cultureContentInformation.commentEnabled

    val city: String
        get() = cultureContentInformation.address.city

    val county: String
        get() = cultureContentInformation.address.county

    val district: String
        get() = cultureContentInformation.address.district

    val jibun: String
        get() = cultureContentInformation.address.jibun

    val detail: String
        get() = cultureContentInformation.address.detail

    val longitude: String
        get() = cultureContentInformation.address.longitude

    val latitude: String
        get() = cultureContentInformation.address.latitude
}
