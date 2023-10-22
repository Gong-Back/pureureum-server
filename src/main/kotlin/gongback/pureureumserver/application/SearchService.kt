package gongback.pureureumserver.application

import gongback.pureureumserver.application.dto.DeleteCondition
import gongback.pureureumserver.application.dto.SaveCondition
import gongback.pureureumserver.application.dto.SearchCondition
import gongback.pureureumserver.application.dto.UpdateCondition
import gongback.pureureumserver.domain.SearchResult

interface SearchService {
    fun searchEquals(searchCondition: SearchCondition): List<SearchResult>
    fun searchContains(searchCondition: SearchCondition): List<SearchResult>
    fun save(saveCondition: SaveCondition): String
    fun delete(deleteCondition: DeleteCondition): String
    fun update(updateCondition: UpdateCondition): String
}
