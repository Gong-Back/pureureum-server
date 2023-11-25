package gongback.pureureumserver.service

import gongback.pureureumserver.support.constant.FilePackage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@Profile("!test")
class FileKeyGenerator {
    @Value("\${spring.profiles.active}")
    lateinit var activeProfile: String

    fun generate(filePackage: FilePackage, fileExtension: String): String {
        val uploadFilePath = getUploadFilePath(filePackage)
        val randomKey = UUID.randomUUID()
        return "$uploadFilePath/$randomKey.$fileExtension"
    }

    private fun getUploadFilePath(filePackage: FilePackage): String {
        return "$activeProfile-$filePackage"
    }
}
