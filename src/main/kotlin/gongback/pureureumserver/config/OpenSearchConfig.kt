package gongback.pureureumserver.config

import gongback.pureureumserver.infra.props.OpenSearchProperties
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.opensearch.client.RestClient
import org.opensearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenSearchConfig(
    private val openSearchProperties: OpenSearchProperties
) {

    @Bean
    fun openSearchClient(): RestHighLevelClient {
        val builder = RestClient.builder(
            HttpHost(openSearchProperties.hostname, openSearchProperties.port, openSearchProperties.scheme)
        ).setHttpClientConfigCallback { httpClientBuilder ->
            httpClientBuilder
                .setDefaultCredentialsProvider(credentialsProvider())
        }.setRequestConfigCallback { requestConfigBuilder ->
            requestConfigBuilder
                .setConnectTimeout(10000)
                .setSocketTimeout(60000)
                .setConnectionRequestTimeout(0)
        }
        return RestHighLevelClient(builder)
    }

    @Bean
    fun credentialsProvider(): BasicCredentialsProvider {
        return BasicCredentialsProvider().apply {
            setCredentials(
                AuthScope.ANY,
                UsernamePasswordCredentials(openSearchProperties.username, openSearchProperties.password)
            )
        }
    }
}

