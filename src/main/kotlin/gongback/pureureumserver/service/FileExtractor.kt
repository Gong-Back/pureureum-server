package gongback.pureureumserver.service

class FileExtractor private constructor() {
    companion object {
        fun extractExtension(originalFileName: String): String =
            originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
    }
}
