package gongback.pureureumserver.service

import gongback.pureureumserver.support.constant.FilePackage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class FileKeyGenerator {
    @Value("\${spring.profiles.active}")
    lateinit var activeProfile: String

    fun generate(filePackage: FilePackage, fileExtension: String): String {
        val fileKey = "${getFilePackage(filePackage)}/${UUID.randomUUID()}.$fileExtension"
        return fileKey
    }

    private fun getFilePackage(filePackage: FilePackage): String {
        return "$activeProfile-$filePackage"
    }
}
