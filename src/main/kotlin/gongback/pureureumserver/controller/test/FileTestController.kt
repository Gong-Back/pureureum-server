package gongback.pureureumserver.controller.test

import gongback.pureureumserver.service.FileClient
import gongback.pureureumserver.service.FileKeyGenerator
import gongback.pureureumserver.service.MultipartFileExtractor
import gongback.pureureumserver.support.constant.FilePackage
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@Tag(name = "04. File Test", description = "파일 테스트")
class FileTestController(
    private val fileClient: FileClient,
    private val fileKeyGenerator: FileKeyGenerator,
) {
    @PostMapping("/file-upload-test", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun fileUploadTest(@RequestPart("file") multipartFile: MultipartFile): ResponseEntity<String> {
        val fileExtension = MultipartFileExtractor.extractExtension(multipartFile)
        val fileKey = fileKeyGenerator.generate(FilePackage.PROFILE, fileExtension)
        fileClient.uploadFile(fileKey, multipartFile.inputStream, multipartFile.size, fileExtension)

        val preassignedUrl = fileClient.getImageUrl(fileKey)
        return ResponseEntity.ok(preassignedUrl.toString())
    }

    @DeleteMapping("/file-delete-test")
    fun fileDeleteTest(@RequestParam fileKey: String): ResponseEntity<Unit> {
        fileClient.deleteFile(fileKey)
        return ResponseEntity.noContent().build()
    }
}