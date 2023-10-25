package gongback.pureureumserver.domain.community

import gongback.pureureumserver.support.domain.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity
class Community(
    @Column(nullable = false)
    val userId: Long,

    @Column(length = 50, nullable = false)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    tags: List<CommunityTag> = emptyList(),

    comments: List<CommunityComment> = emptyList(),

    files: List<CommunityFile> = emptyList()

) : BaseTimeEntity() {
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "community_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_community_tag_community_id")
    )
    private val _tags: MutableList<CommunityTag> = tags.toMutableList()

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "community_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_community_comment_community_id")
    )
    private val _comments: MutableList<CommunityComment> = comments.toMutableList()

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(
        name = "community_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(name = "fk_community_file_community_id")
    )
    private val _files: MutableList<CommunityFile> = files.toMutableList()

    val tags: MutableList<CommunityTag>
        get() = _tags

    val comments: MutableList<CommunityComment>
        get() = _comments

    val files: MutableList<CommunityFile>
        get() = _files
}