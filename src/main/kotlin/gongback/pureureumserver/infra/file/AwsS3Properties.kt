package gongback.pureureumserver.infra.file

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile

@Profile("!test")
@ConfigurationProperties(prefix = "s3")
data class AwsS3Properties(
    val bucket: String,
    val region: String,
    val accessKey: String,
    val secretKey: String,
    val expirationTime: Long,
) {
    val awsCredentials: AWSCredentials = BasicAWSCredentials(accessKey, secretKey)
}
