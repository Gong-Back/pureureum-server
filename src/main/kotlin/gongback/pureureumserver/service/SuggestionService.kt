package gongback.pureureumserver.service

import gongback.pureureumserver.domain.file.File
import gongback.pureureumserver.domain.suggestion.Suggestion
import gongback.pureureumserver.domain.suggestion.SuggestionRepository
import gongback.pureureumserver.domain.suggestion.SuggestionSortType
import gongback.pureureumserver.domain.suggestion.SuggestionStatus
import gongback.pureureumserver.domain.suggestion.getSuggestionById
import gongback.pureureumserver.domain.suggestionvoterecord.SuggestionVoteRecord
import gongback.pureureumserver.domain.suggestionvoterecord.SuggestionVoteRecordRepository
import gongback.pureureumserver.domain.user.UserRepository
import gongback.pureureumserver.service.dto.SuggestionRequest
import gongback.pureureumserver.service.dto.SuggestionResponse
import gongback.pureureumserver.service.dto.SuggestionSliceResponse
import gongback.pureureumserver.service.dto.SuggestionSummaryResponse
import gongback.pureureumserver.service.dto.SuggestionUserVotedResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class SuggestionService(
    private val suggestionRepository: SuggestionRepository,
    private val suggestionVoteRecordRepository: SuggestionVoteRecordRepository,
    private val userRepository: UserRepository,
    private val fileService: FileService,
) {
    @Transactional
    fun createSuggestion(suggestionRequest: SuggestionRequest, thumbnail: File, loginUserId: Long): Long {
        validateSuggestionRequest(suggestionRequest)

        val loginUser = userRepository.getReferenceById(loginUserId)
        val suggestion = Suggestion(suggestionRequest.toSuggestionInformation(), thumbnail, loginUser)
        suggestion.addSuggestionVotes(suggestionRequest.toSuggestionVotes())
        return suggestionRepository.save(suggestion).id
    }

    @Transactional(readOnly = true)
    fun getSuggestion(suggestionId: Long, loginUserId: Long?): SuggestionResponse {
        val suggestion = suggestionRepository.getSuggestionById(suggestionId)
        val thumbnailUrl = fileService.getImageUrl(suggestion.thumbnail.fileKey)
        val suggestionUserVotedResponse = createSuggestionUserVotedResponse(loginUserId, suggestionId)
        return SuggestionResponse(suggestion, thumbnailUrl.toString(), suggestionUserVotedResponse)
    }

    @Transactional(readOnly = true)
    fun getSuggestions(
        size: Int,
        lastId: Long?,
        sortType: SuggestionSortType,
        status: SuggestionStatus,
    ): SuggestionSliceResponse {
        val suggestionSlice = suggestionRepository.findSliceBy(size, lastId, sortType, status)
        return getSuggestionSliceResponse(size, suggestionSlice)
    }

    @Transactional
    fun deleteSuggestion(suggestionId: Long, loginUserId: Long) {
        val suggestion = suggestionRepository.getSuggestionById(suggestionId)

        if (suggestion.user.id != loginUserId) {
            throw ForbiddenException("해당 제안($suggestionId)을 삭제할 권한이 없습니다.")
        }
        suggestionRepository.delete(suggestion)
    }

    @Transactional
    fun addSuggestionVote(suggestionId: Long, suggestionVoteId: Long, loginUserId: Long) {
        val suggestion = suggestionRepository.getSuggestionById(suggestionId)
        val loginUser = userRepository.getReferenceById(loginUserId)
        val suggestionVote = suggestion.suggestionVotes.find { it.id == suggestionVoteId }
            ?: throw NoSuchElementException("해당 제안 투표 항목($suggestionVoteId)은 존재하지 않습니다.")
        if (suggestionVoteRecordRepository.existsBySuggestionIdAndUserId(suggestionId, loginUser.id)) {
            throw ForbiddenException("이미 해당 제안($suggestionId)에 투표하였습니다.")
        }
        suggestionVote.increaseVoteCount()
        suggestionVoteRecordRepository.save(SuggestionVoteRecord(loginUser, suggestion, suggestionVote))
    }

    @Transactional
    fun cancelSuggestionVote(suggestionId: Long, suggestionVoteId: Long, loginUserId: Long) {
        val suggestion = suggestionRepository.getSuggestionById(suggestionId)
        val suggestionVote = suggestion.suggestionVotes.find { it.id == suggestionVoteId }
            ?: throw NoSuchElementException("해당 제안 투표($suggestionVoteId)는 존재하지 않습니다.")
        val suggestionVoteRecord =
            suggestionVoteRecordRepository.findBySuggestionIdAndUserId(suggestionId, loginUserId)
                ?: throw ForbiddenException("해당 제안($suggestionId)에 투표한 기록이 없습니다.")
        suggestionVote.decreaseVoteCount()
        suggestionVoteRecordRepository.delete(suggestionVoteRecord)
    }

    fun uploadSuggestionThumbnail(thumbnail: MultipartFile) = fileService.uploadFile(thumbnail)

    fun deleteSuggestionThumbnail(thumbnailFileKey: String) = fileService.deleteFile(thumbnailFileKey)

    private fun createSuggestionUserVotedResponse(
        loginUserId: Long?,
        suggestionId: Long,
    ): SuggestionUserVotedResponse {
        val suggestionVoteRecord = loginUserId?.let { userId ->
            suggestionVoteRecordRepository.findBySuggestionIdAndUserId(suggestionId, userId)
        }
        val isVoted = suggestionVoteRecord != null
        val suggestionVoteId = suggestionVoteRecord?.suggestionVote?.id
        return SuggestionUserVotedResponse(isVoted, suggestionVoteId)
    }

    private fun getSuggestionSliceResponse(
        size: Int,
        suggestionSlice: List<Suggestion>,
    ): SuggestionSliceResponse {
        val hasNext = suggestionSlice.size > size
        val suggestionContent = getSuggestionContentBySlice(hasNext, suggestionSlice)
        return createSuggestionSliceResponse(hasNext, suggestionContent)
    }

    private fun getSuggestionContentBySlice(
        hasNext: Boolean,
        suggestionSlice: List<Suggestion>,
    ): List<SuggestionSummaryResponse> {
        val content = if (hasNext) suggestionSlice.dropLast(1) else suggestionSlice
        return content.map {
            val thumbnailUrl = fileService.getImageUrl(it.thumbnail.fileKey)
            SuggestionSummaryResponse(it, thumbnailUrl.toString())
        }
    }

    private fun createSuggestionSliceResponse(
        hasNext: Boolean,
        content: List<SuggestionSummaryResponse>,
    ): SuggestionSliceResponse {
        if (content.isNotEmpty()) {
            val lastSuggestion = content.last()
            val lastId = lastSuggestion.id
            val totalVoteCount = lastSuggestion.totalVoteCount
            return SuggestionSliceResponse(lastId, totalVoteCount, hasNext, content)
        }
        return SuggestionSliceResponse(hasNext, content)
    }

    private fun validateSuggestionRequest(suggestionRequest: SuggestionRequest) {
        require(suggestionRequest.suggestionVotes.isNotEmpty()) {
            "제안 투표 생성 요청 목록은 최소 1개 이상이어야 합니다."
        }
    }
}
