package gongback.pureureumserver.domain.community

import gongback.pureureumserver.support.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class CommunityTag(
    @Column(nullable = false)
    val name: String,
) : BaseEntity()
