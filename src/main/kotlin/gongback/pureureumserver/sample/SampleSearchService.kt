package gongback.pureureumserver.sample

import gongback.pureureumserver.application.SearchService
import gongback.pureureumserver.application.dto.DeleteCondition
import gongback.pureureumserver.application.dto.OrderType
import gongback.pureureumserver.application.dto.PageCondition
import gongback.pureureumserver.application.dto.SaveCondition
import gongback.pureureumserver.application.dto.SearchCondition
import gongback.pureureumserver.application.dto.SortCondition
import gongback.pureureumserver.application.dto.UpdateCondition
import gongback.pureureumserver.domain.SearchResult
import org.springframework.stereotype.Service

@Service
class SampleSearchService(
    private val searchService: SearchService
) {
    fun save(saveRequest: SaveDomainRequest): DocIdResponse {
        val saveCondition = SaveCondition(saveRequest.indexName, saveRequest.sampleDomain)
        val savedDocId = searchService.save(saveCondition)
        return DocIdResponse(savedDocId)
    }

    fun searchExactly(searchRequest: SearchRequest): SearchResponse {
        val searchCondition = SearchCondition(
            index = searchRequest.index,
            field = searchRequest.field,
            value = searchRequest.value,
            returnType = SampleDomain::class.java,
            pageCondition = PageCondition(searchRequest.page, searchRequest.size)
        )
        val searchResult = searchService.searchEquals(searchCondition)
        return SearchResponse(searchResult)
    }

    fun searchContains(searchRequest: SearchRequest): SearchResponse {
        val searchCondition = SearchCondition(
            index = searchRequest.index,
            field = searchRequest.field,
            value = searchRequest.value,
            returnType = SampleDomain::class.java,
            pageCondition = PageCondition(searchRequest.page, searchRequest.size)
        )
        val searchResult = searchService.searchContains(searchCondition)
        return SearchResponse(searchResult)
    }

    fun searchSorted(sortRequest: SortRequest): SearchResponse {
        val searchCondition = SearchCondition(
            index = sortRequest.index,
            field = null,
            value = null,
            returnType = SampleDomain::class.java,
            // 다중 정렬 조건에 대해 사용할 수 있도록 리스트로 받기
            sortCondition = listOf(
                SortCondition(
                    field = sortRequest.field,
                    orderType = sortRequest.orderType
                ),
                // 두 번째 정렬 조건으로 year를 사용한다면
                SortCondition(
                    field = SampleDomain::year.name,
                    orderType = OrderType.DESC
                )
            ),
            pageCondition = PageCondition(sortRequest.page, sortRequest.size)
        )
        val searchResult = searchService.searchContains(searchCondition)
        return SearchResponse(searchResult)
    }

    fun update(updateDomainRequest: UpdateDomainRequest): DocIdResponse {
        val updateCondition = UpdateCondition(
            index = updateDomainRequest.indexName,
            docId = updateDomainRequest.docId,
            data = updateDomainRequest.sampleDomain
        )
        val updatedDocId = searchService.update(updateCondition)
        return DocIdResponse(updatedDocId)
    }

    fun delete(deleteDomainRequest: DeleteDomainRequest): DocIdResponse {
        val deleteCondition = DeleteCondition(
            index = deleteDomainRequest.indexName,
            docId = deleteDomainRequest.docId
        )
        val deletedDocId = searchService.delete(deleteCondition)
        return DocIdResponse(deletedDocId)
    }
}

data class SaveDomainRequest(
    val indexName: String,
    val sampleDomain: SampleDomain
)

data class SearchRequest(
    val index: String,
    val field: String?,
    val value: String?,
    val page: Int,
    val size: Int
)

data class SortRequest(
    val index: String,
    val field: String,
    val orderType: OrderType,
    val page: Int,
    val size: Int
)

data class UpdateDomainRequest(
    val indexName: String,
    val docId: String,
    val sampleDomain: SampleDomain
)

data class DeleteDomainRequest(
    val indexName: String,
    val docId: String
)

data class DocIdResponse(
    val docId: String
)

data class SearchResponse(
    val data: List<SearchResult>
)

