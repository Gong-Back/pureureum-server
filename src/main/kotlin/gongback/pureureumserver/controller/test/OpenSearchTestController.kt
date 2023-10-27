package gongback.pureureumserver.controller.test

import gongback.pureureumserver.domain.user.UserDocument
import gongback.pureureumserver.domain.user.UserDocumentRepository
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "01. OpenSearch Test", description = "오픈서치 테스트")
class OpenSearchTestController(
    private val userDocumentRepository: UserDocumentRepository,
) {
    @GetMapping("/open-search-test")
    fun test(): String {
        val testUserDocument = UserDocument(2L, "test2")
        userDocumentRepository.save(testUserDocument)

        val findTestUserDocument = userDocumentRepository.findById(testUserDocument.id).get()
        return findTestUserDocument.name
    }
}
