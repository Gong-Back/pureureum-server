package gongback.pureureumserver.service

import org.springframework.web.multipart.MultipartFile

object MultipartFileExtractor {
    fun extractExtension(multipartFile: MultipartFile): String {
        val originalFileName = multipartFile.originalFilename
        require(originalFileName?.isNotBlank() == true) { "파일 이름이 존재하지 않습니다." }
        return originalFileName!!.substring(originalFileName.lastIndexOf(".") + 1)
    }
}
