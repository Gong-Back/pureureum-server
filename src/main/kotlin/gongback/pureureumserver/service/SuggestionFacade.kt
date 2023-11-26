package gongback.pureureumserver.service

import gongback.pureureumserver.domain.suggestion.SuggestionSortType
import gongback.pureureumserver.domain.suggestion.SuggestionStatus
import gongback.pureureumserver.service.dto.SuggestionRequest
import gongback.pureureumserver.service.dto.SuggestionResponse
import gongback.pureureumserver.service.dto.SuggestionSliceResponse
import org.springframework.stereotype.Component

@Component
class SuggestionFacade(
    private val suggestionService: SuggestionService,
    private val lockTemplate: LockTemplate,
) {
    fun createSuggestion(suggestionRequest: SuggestionRequest, loginUserId: Long) =
        suggestionService.createSuggestion(suggestionRequest, loginUserId)

    fun getSuggestion(suggestionId: Long, loginUserId: Long?): SuggestionResponse =
        suggestionService.getSuggestion(suggestionId, loginUserId)

    fun getSuggestions(
        size: Int,
        lastId: Long?,
        sortType: SuggestionSortType,
        status: SuggestionStatus,
    ): SuggestionSliceResponse =
        suggestionService.getSuggestions(size, lastId, sortType, status)

    fun deleteSuggestion(suggestionId: Long, loginUserId: Long) {
        suggestionService.deleteSuggestion(suggestionId, loginUserId)
    }

    fun addVote(suggestionId: Long, suggestionVoteId: Long, loginUserId: Long) {
        lockTemplate.executeWithRetry(suggestionVoteId.toString()) {
            suggestionService.addSuggestionVote(suggestionId, suggestionVoteId, loginUserId)
        }
    }

    fun cancelVote(suggestionId: Long, suggestionVoteId: Long, loginUserId: Long) {
        lockTemplate.executeWithRetry(suggestionVoteId.toString()) {
            suggestionService.cancelSuggestionVote(suggestionId, suggestionVoteId, loginUserId)
        }
    }
}
