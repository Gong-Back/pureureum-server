package gongback.pureureumserver.application

import gongback.pureureumserver.application.dto.IndexDeleteRequest
import gongback.pureureumserver.application.dto.IndexPropertyDeleteRequest
import gongback.pureureumserver.application.dto.IndexResultResponse
import gongback.pureureumserver.application.dto.IndexSaveRequest
import gongback.pureureumserver.application.dto.IndexSaveResponse
import org.springframework.stereotype.Service

@Service
class AdminIndexService(
    private val indexService: IndexService
) {

    fun createIndex(indexSaveRequest: IndexSaveRequest): IndexSaveResponse {
        val savedIndexName = indexService.createIndex(indexSaveRequest)
        return IndexSaveResponse(savedIndexName)
    }

    fun updateIndexProps(indexSaveRequest: IndexSaveRequest): IndexResultResponse {
        val isSuccess = indexService.updateIndexProperties(indexSaveRequest)
        return IndexResultResponse(isSuccess)
    }

    fun deleteIndexProp(indexPropertyDeleteRequest: IndexPropertyDeleteRequest) {
        indexService.deleteIndexProperty(indexPropertyDeleteRequest)
    }

    fun deleteIndex(indexDeleteRequest: IndexDeleteRequest): IndexResultResponse {
        val isSuccess = indexService.deleteIndex(indexDeleteRequest)
        return IndexResultResponse(isSuccess)
    }
}
