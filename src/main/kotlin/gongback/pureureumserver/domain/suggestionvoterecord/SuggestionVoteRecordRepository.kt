package gongback.pureureumserver.domain.suggestionvoterecord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SuggestionVoteRecordRepository : JpaRepository<SuggestionVoteRecord, Long> {
    fun existsBySuggestionIdAndUserId(suggestionId: Long, userId: Long): Boolean

    fun findBySuggestionIdAndUserId(suggestionId: Long, userId: Long): SuggestionVoteRecord?
}
