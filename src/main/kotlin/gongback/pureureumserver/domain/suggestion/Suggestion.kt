package gongback.pureureumserver.domain.suggestion

import gongback.pureureumserver.domain.file.File
import gongback.pureureumserver.domain.user.User
import gongback.pureureumserver.support.domain.BaseUpdatableEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import java.time.LocalDate

@Entity
class Suggestion(
    information: SuggestionInformation,

    thumbnailFile: File,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    val user: User,

    suggestionVotes: List<SuggestionVote> = emptyList(),
) : BaseUpdatableEntity() {
    @Embedded
    var information: SuggestionInformation = information
        protected set

    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "file_id", nullable = false, updatable = false)
    val thumbnail: File = thumbnailFile

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

    val status: SuggestionStatus
        get() = information.status

    fun addSuggestionVotes(suggestionVotes: List<SuggestionVote>) {
        mutableSuggestionVotes.addAll(suggestionVotes)
    }

    fun changeStatus(status: SuggestionStatus) {
        this.information = information.copy(status = status)
    }
}
