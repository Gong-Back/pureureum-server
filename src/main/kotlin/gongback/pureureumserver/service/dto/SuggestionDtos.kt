package gongback.pureureumserver.service.dto

import gongback.pureureumserver.domain.suggestion.Suggestion
import gongback.pureureumserver.domain.suggestion.SuggestionInformation
import gongback.pureureumserver.domain.suggestion.SuggestionStatus
import gongback.pureureumserver.domain.suggestion.SuggestionVote
import gongback.pureureumserver.domain.suggestion.SuggestionVoteInformation
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.time.LocalDateTime

@Schema(description = "제안 생성 요청")
data class SuggestionRequest(
    @field:NotBlank
    @Schema(description = "제안 제목")
    val title: String,
    @field:NotBlank
    @Schema(description = "제안 내용")
    val content: String,
    @Schema(description = "제안 투표 생성 요청 목록")
    val suggestionVotes: List<SuggestionVoteRequest>,
) {
    fun toSuggestionInformation() = SuggestionInformation(
        title = title,
        content = content,
    )

    fun toSuggestionVotes(): List<SuggestionVote> =
        suggestionVotes.map { SuggestionVote(it.toSuggestionVoteInformation()) }
}

@Schema(description = "제안 투표 생성 요청")
data class SuggestionVoteRequest(
    @field:NotBlank
    @Schema(description = "제안 투표 내용")
    val content: String,
) {
    fun toSuggestionVoteInformation() = SuggestionVoteInformation(
        content = content,
    )
}

@Schema(description = "제안 세부 응답")
data class SuggestionResponse(
    @Schema(description = "제안 id")
    val id: Long,
    @Schema(description = "제안 제목")
    val title: String,
    @Schema(description = "제안 내용")
    val content: String,
    @Schema(description = "제안 시작 일자")
    val startDate: LocalDate,
    @Schema(description = "제안 종료 일자")
    val endDate: LocalDate,
    @Schema(description = "제안 투표 총 투표 수")
    val totalVoteCount: Int,
    @Schema(description = "제안 생성일")
    val createdDate: LocalDateTime,
    @Schema(description = "제안 투표 목록")
    val suggestionVotes: List<SuggestionVoteResponse>,
) {
    constructor(suggestion: Suggestion) : this(
        suggestion.id,
        suggestion.title,
        suggestion.content,
        suggestion.startDate,
        suggestion.endDate,
        suggestion.totalVoteCount,
        suggestion.createdDate,
        suggestion.suggestionVotes.map { SuggestionVoteResponse(it) },
    )
}

@Schema(description = "제안 투표 세부 응답")
data class SuggestionVoteResponse(
    @Schema(description = "제안 투표 id")
    val id: Long,
    @Schema(description = "제안 투표 내용")
    val content: String,
    @Schema(description = "제안 투표 투표 수")
    val voteCount: Int,
) {
    constructor(suggestionVote: SuggestionVote) : this(
        suggestionVote.id,
        suggestionVote.content,
        suggestionVote.voteCount,
    )
}

@Schema(description = "제안 요약 응답")
data class SuggestionSummaryResponse(
    @Schema(description = "제안 id")
    val id: Long,
    @Schema(description = "제안 제목")
    val title: String,
    @Schema(description = "제안 시작 일자")
    val startDate: LocalDate,
    @Schema(description = "제안 종료 일자")
    val endDate: LocalDate,
    @Schema(description = "제안 상태")
    val status: SuggestionStatus,
    @Schema(description = "제안 투표 여부")
    val isVoted: Boolean,
    @Schema(description = "제안 투표 총 투표 수")
    val totalVoteCount: Int,
    @Schema(description = "제안 생성일")
    val createdDate: LocalDateTime,
) {
    constructor(suggestion: Suggestion, isVoted: Boolean) : this(
        suggestion.id,
        suggestion.title,
        suggestion.startDate,
        suggestion.endDate,
        suggestion.status,
        isVoted,
        suggestion.totalVoteCount,
        suggestion.createdDate,
    )
}

@Schema(description = "제안 슬라이스 응답")
data class SuggestionSliceResponse(
    @Schema(description = "마지막 문화 행사의 id")
    val lastId: Long?,
    @Schema(description = "마지막 문화 행사의 총 투표 수")
    val totalVoteCount: Int?,
    @Schema(description = "마지막 문화 행사 시간")
    override val hasNext: Boolean,
    @Schema(description = "문화 행사 목록")
    override val content: List<SuggestionSummaryResponse>,
) : SliceResponse<SuggestionSummaryResponse>(hasNext, content) {
    constructor(hasNext: Boolean, content: List<SuggestionSummaryResponse>) : this(
        null,
        null,
        hasNext,
        content,
    )
}
