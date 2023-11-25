package gongback.pureureumserver.domain.suggestionvoterecord

import gongback.pureureumserver.domain.suggestion.Suggestion
import gongback.pureureumserver.domain.user.User
import gongback.pureureumserver.support.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class SuggestionVoteRecord(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggestion_id")
    val suggestion: Suggestion,
) : BaseEntity()
