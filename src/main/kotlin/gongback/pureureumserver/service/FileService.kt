package gongback.pureureumserver.service

import gongback.pureureumserver.domain.file.File
import gongback.pureureumserver.support.constant.FilePackage
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URL

@Service
class FileService(
    private val fileClient: FileClient,
    private val fileKeyGenerator: FileKeyGenerator,
) {
    fun uploadFile(multipartFile: MultipartFile): File {
        require(multipartFile.originalFilename != null) {
            "파일 이름이 존재하지 않습니다."
        }

        val extension = MultipartFileExtractor.extractExtension(multipartFile)
        val fileKey = fileKeyGenerator.generate(FilePackage.SUGGESTION, extension)
        fileClient.uploadFile(fileKey, multipartFile.inputStream, multipartFile.size, extension)
        return File(fileKey, multipartFile.originalFilename!!, extension)
    }

    fun getImageUrl(fileKey: String): URL = fileClient.getImageUrl(fileKey)

    fun deleteFile(fileKey: String) = fileClient.deleteFile(fileKey)
}
