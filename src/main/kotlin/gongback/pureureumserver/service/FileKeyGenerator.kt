package gongback.pureureumserver.service

import java.util.UUID
import gongback.pureureumserver.support.constant.FilePackage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class FileKeyGenerator {
    @Value("\${spring.profiles.active}")
    lateinit var activeProfile: String

    fun generate(filePackage: FilePackage, fileExtension: String): String {
        return "${getFilePackage(filePackage)}/${UUID.randomUUID()}.$fileExtension"
    }

    private fun getFilePackage(filePackage: FilePackage): String {
        return "$activeProfile-$filePackage"
    }
}
