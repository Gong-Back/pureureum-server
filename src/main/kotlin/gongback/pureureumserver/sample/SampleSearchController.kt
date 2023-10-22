package gongback.pureureumserver.sample

import gongback.pureureumserver.application.dto.OrderType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * OpenSearch Sample Controller (TEST)
 */
@RestController
@RequestMapping("/sample/search")
class SampleSearchController(
    private val sampleSearchService: SampleSearchService
) {

    /**
     * 인덱스에 데이터 삽입
     */
    @PostMapping("/index")
    fun create(
        @RequestBody saveDomainRequest: SaveDomainRequest
    ): ResponseEntity<DocIdResponse> {
        val saveResponse = sampleSearchService.save(saveDomainRequest)
        return ResponseEntity.ok(saveResponse)
    }

    /**
     * 인덱스의 특정 필드에 대해 값이 정확하게 일치할 경우
     */
    @GetMapping("/exactly")
    fun searchExactly(
        @RequestParam index: String,
        @RequestParam(required = false) field: String?,
        @RequestParam(required = false) value: String?,
        @RequestParam page: Int = 1, // 1부터 시작
        @RequestParam size: Int = 10,
    ): ResponseEntity<SearchResponse> {
        val searchRequest = SearchRequest(index, field, value, page, size)
        val searchResponse = sampleSearchService.searchExactly(searchRequest)
        return ResponseEntity.ok(searchResponse)
    }

    /**
     * 인덱스의 특정 필드에 대해 값이 포함될 경우
     */
    @GetMapping("/contains")
    fun searchContains(
        @RequestParam index: String,
        @RequestParam(required = false) field: String?,
        @RequestParam(required = false) value: String?,
        @RequestParam page: Int = 1, // 1부터 시작
        @RequestParam size: Int = 10,
    ): ResponseEntity<SearchResponse> {
        val searchRequest = SearchRequest(index, field, value, page, size)
        val searchResponse = sampleSearchService.searchContains(searchRequest)
        return ResponseEntity.ok(searchResponse)
    }

    /**
     * 데이터 정렬 테스트
     */
    @GetMapping("/sort")
    fun searchSorted(
        @RequestParam index: String,
        @RequestParam field: String, // 정렬 대상 필드
        @RequestParam orderType: OrderType, // 정렬 방식
        @RequestParam page: Int = 1, // 1부터 시작
        @RequestParam size: Int = 10,
    ): ResponseEntity<SearchResponse> {
        val sortRequest = SortRequest(index, field, orderType, page, size)
        val searchResponse = sampleSearchService.searchSorted(sortRequest)
        return ResponseEntity.ok(searchResponse)
    }

    /**
     * 데이터 수정할 경우 (PUT 방식)
     */
    @PutMapping
    fun update(
        @RequestBody updateDomainRequest: UpdateDomainRequest
    ): ResponseEntity<DocIdResponse> {
        val updateResponse = sampleSearchService.update(updateDomainRequest)
        return ResponseEntity.ok(updateResponse)
    }

    /**
     * 데이터 제거할 경우
     */
    @DeleteMapping
    fun delete(
        @RequestBody deleteDomainRequest: DeleteDomainRequest
    ): ResponseEntity<DocIdResponse> {
        val deleteResponse = sampleSearchService.delete(deleteDomainRequest)
        return ResponseEntity.ok(deleteResponse)
    }
}
