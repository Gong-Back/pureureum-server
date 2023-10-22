package gongback.pureureumserver.infra.opensearch

import gongback.pureureumserver.application.IndexService
import gongback.pureureumserver.application.dto.IndexDeleteRequest
import gongback.pureureumserver.application.dto.IndexPropertyDeleteRequest
import gongback.pureureumserver.application.dto.IndexSaveRequest
import java.util.Locale
import org.opensearch.action.admin.indices.delete.DeleteIndexRequest
import org.opensearch.client.indices.CreateIndexRequest
import org.opensearch.client.indices.PutMappingRequest
import org.opensearch.index.reindex.ReindexRequest
import org.opensearch.script.Script
import org.springframework.stereotype.Service


/**
 * OpenSearch 어드민 서비스
 * - 인덱스 생성
 * - 특정 인덱스의 전체 프로퍼티 업데이트
 * - 특정 인덱스의 특정 프로퍼티 제거
 * - 인덱스 제거
 */
@Service
class OpenSearchAdminService(
    private val openSearchOperator: OpenSearchOperator
) : IndexService {

    override fun createIndex(indexSaveRequest: IndexSaveRequest): String {
        val createIndexRequest = CreateIndexRequest(indexSaveRequest.index).apply {
            val properties = createProperties(indexSaveRequest)
            mapping(properties)
        }
        val createIndexResponse = openSearchOperator.createIndex(createIndexRequest)
        return createIndexResponse.index()
    }

    override fun updateIndexProperties(indexSaveRequest: IndexSaveRequest): Boolean {
        val updatePropRequest = PutMappingRequest(indexSaveRequest.index).apply {
            val properties = createProperties(indexSaveRequest)
            source(properties)
        }
        val updatePropResponse = openSearchOperator.updateIndexProperties(updatePropRequest)
        return updatePropResponse.isAcknowledged
    }

    override fun deleteIndexProperty(indexPropertyDeleteRequest: IndexPropertyDeleteRequest) {
        val reindexRequest = ReindexRequest().apply {
            setSourceIndices(indexPropertyDeleteRequest.baseIndex)
            setDestIndex(indexPropertyDeleteRequest.newIndex)
            setScript(createReIndexScript(indexPropertyDeleteRequest.propertyName))
        }

        // 기존의 인덱스 내용을 새로운 인덱스로 복사한다.
        openSearchOperator.reindex(reindexRequest)

        // 기존 인덱스를 제거한다.
//        openSearchOperator.deleteIndex(DeleteIndexRequest(indexPropertyDeleteRequest.baseIndex))
    }

    override fun deleteIndex(indexDeleteRequest: IndexDeleteRequest): Boolean {
        val deleteIndexRequest = DeleteIndexRequest(indexDeleteRequest.index)
        val deleteIndex = openSearchOperator.deleteIndex(deleteIndexRequest)
        return deleteIndex.isAcknowledged
    }

    private fun createProperties(indexSaveRequest: IndexSaveRequest): Map<String, Map<String, Map<String, String>>> {
        val fieldMapping = indexSaveRequest.fields.mapValues { fieldType ->
            mapOf(TypeMapping.TYPE.toLowerCase() to fieldType.value.toLowerCase())
        }
        return mapOf(TypeMapping.PROPERTIES.toLowerCase() to fieldMapping)
    }

    private fun createReIndexScript(propertyName: String) = Script(
        """
        if (ctx._source.containsKey("$propertyName")) {
            ctx._source.remove("$propertyName")
        }
        """.trimIndent()
    )

    enum class TypeMapping(
        val description: String
    ) {
        TYPE("필드 타입 정의"),
        PROPERTIES("필드 집합 정의")
        ;

        fun toLowerCase(): String {
            return this.name.lowercase(Locale.getDefault())
        }
    }
}
