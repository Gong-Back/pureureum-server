package gongback.pureureumserver.application

import gongback.pureureumserver.application.dto.IndexDeleteRequest
import gongback.pureureumserver.application.dto.IndexPropertyDeleteRequest
import gongback.pureureumserver.application.dto.IndexSaveRequest

interface IndexService {
    fun createIndex(indexSaveRequest: IndexSaveRequest): String
    fun updateIndexProperties(indexSaveRequest: IndexSaveRequest): Boolean
    fun deleteIndexProperty(indexPropertyDeleteRequest: IndexPropertyDeleteRequest)
    fun deleteIndex(indexDeleteRequest: IndexDeleteRequest): Boolean
}
