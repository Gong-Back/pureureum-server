package gongback.pureureumserver.controller

import gongback.pureureumserver.domain.suggestion.SuggestionSortType
import gongback.pureureumserver.domain.suggestion.SuggestionStatus
import gongback.pureureumserver.security.LoginUserId
import gongback.pureureumserver.service.SuggestionFacade
import gongback.pureureumserver.service.dto.SuggestionRequest
import gongback.pureureumserver.service.dto.SuggestionResponse
import gongback.pureureumserver.service.dto.SuggestionSliceResponse
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@RestController
@RequestMapping("/api/v1/suggestions")
class SuggestionControllerImpl(
    private val suggestionFacade: SuggestionFacade,
) : SuggestionController {
    @PostMapping
    override fun createSuggestion(
        @LoginUserId loginUserId: Long,
        @Validated @RequestPart suggestionRequest: SuggestionRequest,
        @RequestPart("thumbnail") thumbnail: MultipartFile,
    ): ResponseEntity<Void> {
        val suggestionId = suggestionFacade.createSuggestion(suggestionRequest, thumbnail, loginUserId)
        val readSuggestionApiUrl = "/api/v1/suggestion/$suggestionId"
        return ResponseEntity.created(URI.create(readSuggestionApiUrl)).build()
    }

    @GetMapping("/{suggestionId}")
    override fun getSuggestion(
        @Positive(message = "suggestionId는 0보다 커야 합니다.")
        @PathVariable("suggestionId") suggestionId: Long,
        @LoginUserId(required = false)
        loginUserId: Long?,
    ): ResponseEntity<SuggestionResponse> {
        val response = suggestionFacade.getSuggestion(suggestionId, loginUserId)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    override fun getSuggestions(
        @Positive(message = "사이즈 값은 양수여야 합니다.")
        @RequestParam("size", defaultValue = "10", required = false)
        size: Int,
        @RequestParam("lastId", required = false)
        lastId: Long?,
        @RequestParam("sortType", defaultValue = "LATEST", required = false)
        sortType: SuggestionSortType,
        @RequestParam("status", defaultValue = "IN_PROGRESS", required = true)
        status: SuggestionStatus,
    ): ResponseEntity<SuggestionSliceResponse> {
        val response = suggestionFacade.getSuggestions(size, lastId, sortType, status)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{suggestionId}")
    override fun deleteSuggestion(
        @Positive(message = "suggestionId는 0보다 커야 합니다.")
        @PathVariable("suggestionId") suggestionId: Long,
        @LoginUserId loginUserId: Long,
    ): ResponseEntity<Void> {
        suggestionFacade.deleteSuggestion(suggestionId, loginUserId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{suggestionId}/{suggestionVoteId}/votes")
    override fun addSuggestionVote(
        @Positive(message = "suggestionId는 0보다 커야 합니다.")
        @PathVariable("suggestionId") suggestionId: Long,
        @Positive(message = "suggestionVoteId는 0보다 커야 합니다.")
        @PathVariable("suggestionVoteId") suggestionVoteId: Long,
        @LoginUserId loginUserId: Long,
    ): ResponseEntity<Void> {
        suggestionFacade.addVote(suggestionId, suggestionVoteId, loginUserId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{suggestionId}/{suggestionVoteId}/votes")
    override fun cancelSuggestionVote(
        @Positive(message = "suggestionId는 0보다 커야 합니다.")
        @PathVariable("suggestionId") suggestionId: Long,
        @Positive(message = "suggestionVoteId는 0보다 커야 합니다.")
        @PathVariable("suggestionVoteId") suggestionVoteId: Long,
        @LoginUserId loginUserId: Long,
    ): ResponseEntity<Void> {
        suggestionFacade.cancelVote(suggestionId, suggestionVoteId, loginUserId)
        return ResponseEntity.noContent().build()
    }
}
