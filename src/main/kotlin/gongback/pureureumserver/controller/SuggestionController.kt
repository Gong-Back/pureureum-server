package gongback.pureureumserver.controller

import gongback.pureureumserver.domain.suggestion.SuggestionSortType
import gongback.pureureumserver.domain.suggestion.SuggestionStatus
import gongback.pureureumserver.service.dto.SuggestionRequest
import gongback.pureureumserver.service.dto.SuggestionResponse
import gongback.pureureumserver.service.dto.SuggestionSliceResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface SuggestionController {
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "제안 생성 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청을 보냈을 경우",
        ),
    )
    fun createSuggestion(
        @Parameter(
            description = "제안을 생성할 사용자 id",
            required = true,
        )
        loginUserId: Long,
        @Parameter(
            description = "제안 요청 정보",
            required = true,
        )
        suggestionRequest: SuggestionRequest,
        @Parameter(
            description = "제안 썸네일",
            required = true,
        )
        thumbnail: MultipartFile,
    ): ResponseEntity<Void>

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "제안 세부 조회 성공",
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청을 보냈을 경우",
        ),
        ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 제안을 조회하려고 할 경우",
        ),
    )
    fun getSuggestion(
        @Parameter(
            description = "조회할 제안 id",
            required = true,
        )
        suggestionId: Long,
        @Parameter(
            description = "제안을 조회할 사용자 id",
            required = false,
        )
        loginUserId: Long?,
    ): ResponseEntity<SuggestionResponse>

    fun getSuggestions(
        @Parameter(
            description = "조회할 제안 목록 사이즈",
            required = false,
        )
        size: Int,
        @Parameter(
            description = "마지막 제안 id",
            required = false,
        )
        lastId: Long?,
        @Parameter(
            description = "제안 정렬 방식",
            required = false,
            example = "LATEST(최신순), HIGHTEST_VOTE_COUNT(최다 투표순), LOWEST_VOTE_COUNT(최소 투표순)",
        )
        sortType: SuggestionSortType,
        @Parameter(
            description = "제안을 조회할 사용자 id",
            required = false,
        )
        @Parameter(
            description = "제안 상태",
            required = true,
            example = "IN_PROGRESS(진행중), COMPLETED(완료)",
        )
        status: SuggestionStatus,
    ): ResponseEntity<SuggestionSliceResponse>

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "제안 삭제 성공",
        ),
        ApiResponse(
            responseCode = "403",
            description = "권한이 없는 사용자가 삭제하려고 할 경우",
        ),
        ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 제안을 삭제하려고 할 경우",
        ),
    )
    fun deleteSuggestion(
        @Parameter(
            description = "삭제할 제안 id",
            required = true,
        )
        suggestionId: Long,
        @Parameter(
            description = "삭제할 사용자 id",
            required = true,
        )
        loginUserId: Long,
    ): ResponseEntity<Void>

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "제안 투표 성공",
        ),
        ApiResponse(
            responseCode = "403",
            description = "이미 투표한 사용자가 투표하려고 할 경우",
        ),
        ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 제안을 투표하려고 할 경우",
        ),
    )
    fun addSuggestionVote(
        @Parameter(
            description = "투표할 제안 id",
            required = true,
        )
        suggestionId: Long,
        @Parameter(
            description = "투표할 제안 요청 id",
            required = true,
        )
        suggestionVoteId: Long,
        @Parameter(
            description = "투표할 사용자 id",
            required = true,
        )
        loginUserId: Long,
    ): ResponseEntity<Void>

    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "투표 취소 성공",
        ),
        ApiResponse(
            responseCode = "403",
            description = "투표하지 않은 사용자가 투표 취소하려고 할 경우",
        ),
        ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 제안을 투표하려고 할 경우",
        ),
    )
    fun cancelSuggestionVote(
        @Parameter(
            description = "투표할 제안 id",
            required = true,
        )
        suggestionId: Long,
        @Parameter(
            description = "투표할 제안 요청 id",
            required = true,
        )
        suggestionVoteId: Long,
        @Parameter(
            description = "투표 취소할 사용자 id",
            required = true,
        )
        loginUserId: Long,
    ): ResponseEntity<Void>
}
