package gongback.pureureumserver.domain.suggestion

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class SuggestionVoteInformation(
    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,
)
