package gongback.pureureumserver.domain.community

import gongback.pureureumserver.support.domain.BaseUpdatedTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class CommunityComment(
    @Column(nullable = false)
    val userId: Long,

    @Column(length = 200, nullable = false)
    val content: String,

    @Column(nullable = false)
    val parentCommentId: Long,
) : BaseUpdatedTimeEntity()
