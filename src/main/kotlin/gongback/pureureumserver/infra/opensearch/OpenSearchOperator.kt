package gongback.pureureumserver.infra.opensearch

import gongback.pureureumserver.exception.OpenSearchException
import org.opensearch.action.admin.indices.delete.DeleteIndexRequest
import org.opensearch.action.delete.DeleteRequest
import org.opensearch.action.delete.DeleteResponse
import org.opensearch.action.index.IndexRequest
import org.opensearch.action.index.IndexResponse
import org.opensearch.action.search.SearchRequest
import org.opensearch.action.search.SearchResponse
import org.opensearch.action.support.master.AcknowledgedResponse
import org.opensearch.client.RequestOptions
import org.opensearch.client.RestHighLevelClient
import org.opensearch.client.indices.CreateIndexRequest
import org.opensearch.client.indices.CreateIndexResponse
import org.opensearch.client.indices.PutMappingRequest
import org.opensearch.index.reindex.ReindexRequest
import org.springframework.stereotype.Service

@Service
class OpenSearchOperator(
    private val client: RestHighLevelClient,
) {

    fun saveData(indexRequest: IndexRequest): IndexResponse {
        return throwIfFail {
            client.index(indexRequest, RequestOptions.DEFAULT)
        }
    }

    fun deleteData(deleteRequest: DeleteRequest): DeleteResponse {
        return throwIfFail {
            client.delete(deleteRequest, RequestOptions.DEFAULT)
        }
    }

    fun searchData(searchRequest: SearchRequest): SearchResponse {
        return throwIfFail {
            client.search(searchRequest, RequestOptions.DEFAULT)
        }
    }

    fun createIndex(createIndexRequest: CreateIndexRequest): CreateIndexResponse {
        return throwIfFail {
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT)
        }
    }

    fun deleteIndex(deleteIndexRequest: DeleteIndexRequest): AcknowledgedResponse {
        return throwIfFail {
            client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT)
        }
    }

    fun updateIndexProperties(updatePropRequest: PutMappingRequest): AcknowledgedResponse {
        return throwIfFail {
            client.indices().putMapping(updatePropRequest, RequestOptions.DEFAULT)
        }
    }

    fun reindex(reindexRequest: ReindexRequest) {
        return throwIfFail {
            client.reindex(reindexRequest, RequestOptions.DEFAULT)
        }
    }

    private fun <T> throwIfFail(operation: () -> T): T {
        return runCatching { operation() }
            .getOrElse { throw OpenSearchException(it) }
    }
}
