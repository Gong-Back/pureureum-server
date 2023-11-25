package gongback.pureureumserver.service

import org.springframework.web.multipart.MultipartFile

object MultipartFileExtractor {

    private const val IMAGE_PREFIX = "image/"

    fun extractExtension(multipartFile: MultipartFile): String {
        val originalFileName = multipartFile.originalFilename
        require(originalFileName?.isNotBlank() == true) { "파일 이름이 존재하지 않습니다." }
        return originalFileName!!.substring(originalFileName.lastIndexOf(".") + 1)
    }

    fun validateFileType(multipartFile: MultipartFile): String = multipartFile.contentType.apply {
        require(this != null) {
            "파일 형식이 유효하지 않습니다"
        }
        validateImageType(this)
    }!!

    private fun validateImageType(contentType: String) =
        require(contentType.startsWith(IMAGE_PREFIX)) { "이미지 형식의 파일만 가능합니다" }
}
