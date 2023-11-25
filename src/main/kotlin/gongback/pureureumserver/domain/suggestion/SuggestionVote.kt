package gongback.pureureumserver.domain.suggestion

import gongback.pureureumserver.support.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity

@Entity
class SuggestionVote(
    @Embedded
    val information: SuggestionVoteInformation,

    voteCount: Int = 0,
) : BaseEntity() {
    @Column(nullable = false)
    var voteCount: Int = voteCount
        protected set
    val content: String
        get() = information.content

    fun increaseVoteCount() {
        voteCount += 1
    }

    fun decreaseVoteCount() {
        if (voteCount == 0) {
            return
        }
        voteCount -= 1
    }
}
