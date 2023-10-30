package gongback.pureureumserver.service

import java.io.InputStream
import java.net.URL

interface FileClient {
    fun getPreassignedUrl(fileKey: String): URL

    fun uploadFile(fileKey: String, fileStream: InputStream, fileSize: Long, fileExtension: String)

    fun deleteFile(fileKey: String)
}
