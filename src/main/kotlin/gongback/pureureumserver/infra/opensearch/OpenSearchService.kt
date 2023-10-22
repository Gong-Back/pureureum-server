package gongback.pureureumserver.infra.opensearch

import com.fasterxml.jackson.databind.ObjectMapper
import gongback.pureureumserver.application.SearchService
import gongback.pureureumserver.application.dto.DeleteCondition
import gongback.pureureumserver.application.dto.PageCondition
import gongback.pureureumserver.application.dto.SaveCondition
import gongback.pureureumserver.application.dto.SearchCondition
import gongback.pureureumserver.application.dto.SortCondition
import gongback.pureureumserver.application.dto.UpdateCondition
import gongback.pureureumserver.domain.SearchResult
import org.opensearch.action.delete.DeleteRequest
import org.opensearch.action.index.IndexRequest
import org.opensearch.action.search.SearchRequest
import org.opensearch.index.query.QueryBuilders
import org.opensearch.search.SearchHit
import org.opensearch.search.builder.SearchSourceBuilder
import org.opensearch.search.sort.SortOrder
import org.springframework.stereotype.Service
import kotlin.reflect.full.memberProperties

@Service
class OpenSearchService(
    private val objectMapper: ObjectMapper,
    private val openSearchOperator: OpenSearchOperator,
) : SearchService {

    override fun searchEquals(searchCondition: SearchCondition): List<SearchResult> {
        val sourceBuilder = SearchSourceBuilder()
            .match(searchCondition)
            .sortedBy(searchCondition.sortCondition)
            .pagination(searchCondition.pageCondition)

        val searchResult = searchHits(searchCondition, sourceBuilder)
        return convertResult(searchResult, searchCondition.returnType)
    }

    override fun searchContains(searchCondition: SearchCondition): List<SearchResult> {
        val sourceBuilder = SearchSourceBuilder()
            .contains(searchCondition)
            .sortedBy(searchCondition.sortCondition)
            .pagination(searchCondition.pageCondition)

        val searchResult = searchHits(searchCondition, sourceBuilder)
        return convertResult(searchResult, searchCondition.returnType)
    }

    override fun save(saveCondition: SaveCondition): String {
        val indexRequest = IndexRequest(saveCondition.index)
            .source(saveCondition.data.toMap())

        val saveResponse = openSearchOperator.saveData(indexRequest)
        return saveResponse.id
    }

    override fun delete(deleteCondition: DeleteCondition): String {
        val deleteRequest = DeleteRequest(deleteCondition.index, deleteCondition.docId)
        val deleteResponse = openSearchOperator.deleteData(deleteRequest)
        return deleteResponse.id
    }

    override fun update(updateCondition: UpdateCondition): String {
        val indexRequest = IndexRequest(updateCondition.index)
            .id(updateCondition.docId)
            .source(updateCondition.data.toMap())

        val saveResponse = openSearchOperator.saveData(indexRequest)
        return saveResponse.id
    }

    private fun searchHits(
        searchCondition: SearchCondition,
        sourceBuilder: SearchSourceBuilder
    ): Array<out SearchHit> {
        val searchRequest = SearchRequest(searchCondition.index).source(sourceBuilder)
        val searchResponse = openSearchOperator.searchData(searchRequest)
        return searchResponse.internalResponse.hits().hits
    }

    private fun convertResult(searchResult: Array<out SearchHit>, targetType: Class<*>): List<SearchResult> {
        return searchResult.map {
            val result = objectMapper.readValue(it.sourceAsString, targetType)
            SearchResult(result, it.id)
        }
    }
}

fun Any.toMap(): Map<String, Any?> {
    return this::class.memberProperties.associate { it.name to it.call(this) }
}

fun SearchSourceBuilder.match(searchCondition: SearchCondition): SearchSourceBuilder {
    if (searchCondition.field == null || searchCondition.value == null) {
        return this
    }
    return this.query(QueryBuilders.matchQuery(searchCondition.field, searchCondition.value))
}

fun SearchSourceBuilder.contains(searchCondition: SearchCondition): SearchSourceBuilder {
    if (searchCondition.field == null || searchCondition.value == null) {
        return this
    }
    return this.query(QueryBuilders.wildcardQuery(searchCondition.field, "*${searchCondition.value}*"))
}

fun SearchSourceBuilder.sortedBy(sortConditions: List<SortCondition>?): SearchSourceBuilder {
    sortConditions?.forEach {
        sort(it.field, SortOrder.valueOf(it.orderType.name))
    }
    return this
}

fun SearchSourceBuilder.pagination(pageCondition: PageCondition?): SearchSourceBuilder {
    return pageCondition?.let {
        from(it.getFrom()).size(it.size)
    } ?: this
}
