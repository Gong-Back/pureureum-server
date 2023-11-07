package gongback.pureureumserver.infra.file

import com.amazonaws.HttpMethod
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import gongback.pureureumserver.service.FileClient
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.io.InputStream
import java.net.URL
import java.util.Date

@Profile("!test")
@Component
class AwsS3Client(
    private val awsS3Properties: AwsS3Properties,
) : FileClient {
    private val amazonS3: AmazonS3 = AmazonS3Client.builder()
        .withRegion(awsS3Properties.region)
        .withCredentials(AWSStaticCredentialsProvider(awsS3Properties.awsCredentials))
        .build()

    override fun getImageUrl(fileKey: String): URL = execute {
        amazonS3.generatePresignedUrl(getGeneratePreSignedUrlRequest(fileKey))
    }

    override fun uploadFile(fileKey: String, fileStream: InputStream, fileSize: Long, fileExtension: String) {
        val objectMetadata = ObjectMetadata().apply {
            contentLength = fileSize
            contentType = getImageContentType(fileExtension)
        }
        execute {
            amazonS3.putObject(awsS3Properties.bucket, fileKey, fileStream, objectMetadata)
        }
    }

    override fun deleteFile(fileKey: String) {
        execute {
            amazonS3.deleteObject(awsS3Properties.bucket, fileKey)
        }
    }

    private fun getGeneratePreSignedUrlRequest(fileKey: String): GeneratePresignedUrlRequest =
        GeneratePresignedUrlRequest(awsS3Properties.bucket, fileKey)
            .withMethod(HttpMethod.GET)
            .withExpiration(getExpirationDate())
            .apply {
                addRequestParameter(
                    Headers.S3_CANNED_ACL,
                    CannedAccessControlList.PublicRead.toString(),
                )
            }

    private fun getExpirationDate(): Date {
        val expirationTime = awsS3Properties.expirationTime
        return Date(System.currentTimeMillis() + expirationTime)
    }

    private fun getImageContentType(fileExtension: String) = "image/$fileExtension"

    private fun <T> execute(operation: () -> T): T {
        return runCatching { operation() }
            .getOrElse { throw RuntimeException("파일 처리 중 예기지 못한 에러가 발생하였습니다.", it) }
    }
}
