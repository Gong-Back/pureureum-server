package gongback.pureureumserver.domain.culturecontent

import gongback.pureureumserver.support.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class CultureContentComment(
    @Column(nullable = false)
    val userId: Long,

    @Column(length = 200, nullable = false)
    val content: String,

    @Column(nullable = false)
    val parentId: Long
) : BaseTimeEntity() {
    @Column(nullable = false)
    var likeCount: Int = 0
        protected set

    @Column(nullable = false)
    var hateCount: Int = 0
        protected set
}
