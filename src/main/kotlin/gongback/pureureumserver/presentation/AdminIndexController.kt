package gongback.pureureumserver.presentation

import gongback.pureureumserver.application.AdminIndexService
import gongback.pureureumserver.application.dto.IndexDeleteRequest
import gongback.pureureumserver.application.dto.IndexPropertyDeleteRequest
import gongback.pureureumserver.application.dto.IndexResultResponse
import gongback.pureureumserver.application.dto.IndexSaveRequest
import gongback.pureureumserver.application.dto.IndexSaveResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/index")
class AdminIndexController(
    private val adminIndexService: AdminIndexService
) {

    @PostMapping
    fun createIndex(@RequestBody indexSaveRequest: IndexSaveRequest): ResponseEntity<IndexSaveResponse> {
        val response = adminIndexService.createIndex(indexSaveRequest)
        return ResponseEntity.ok(response)
    }

    @PutMapping
    fun updateIndex(@RequestBody indexSaveRequest: IndexSaveRequest): ResponseEntity<IndexResultResponse> {
        val response = adminIndexService.updateIndexProps(indexSaveRequest)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/props")
    fun deleteIndexProperty(@RequestBody indexPropertyDeleteRequest: IndexPropertyDeleteRequest): ResponseEntity<Unit> {
        adminIndexService.deleteIndexProp(indexPropertyDeleteRequest)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteIndex(@RequestBody indexDeleteRequest: IndexDeleteRequest): ResponseEntity<IndexResultResponse> {
        val response = adminIndexService.deleteIndex(indexDeleteRequest)
        return ResponseEntity.ok(response)
    }
}
