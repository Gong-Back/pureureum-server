package gongback.pureureumserver.domain.suggestion

import gongback.pureureumserver.domain.user.User
import gongback.pureureumserver.support.domain.BaseUpdatableEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDate

@Entity
class Suggestion(
    @Embedded
    val information: SuggestionInformation,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    val user: User,

    suggestionVotes: List<SuggestionVote> = emptyList(),
) : BaseUpdatableEntity() {
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "suggestion_id", nullable = false, updatable = false)
    private val mutableSuggestionVotes: MutableList<SuggestionVote> = suggestionVotes.toMutableList()
    val suggestionVotes: List<SuggestionVote>
        get() = mutableSuggestionVotes

    val title: String
        get() = information.title

    val content: String
        get() = information.content

    val totalVoteCount: Int
        get() = suggestionVotes.sumOf { it.voteCount }

    val startDate: LocalDate
        get() = information.startDate

    val endDate: LocalDate
        get() = information.endDate

    fun addSuggestionVotes(suggestionVotes: List<SuggestionVote>) {
        mutableSuggestionVotes.addAll(suggestionVotes)
    }
}
