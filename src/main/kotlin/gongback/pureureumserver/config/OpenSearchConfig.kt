package gongback.pureureumserver.config

import gongback.pureureumserver.config.props.OpenSearchProperties
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.opensearch.client.RestClient
import org.opensearch.client.RestClientBuilder
import org.opensearch.client.RestHighLevelClient
import org.opensearch.data.client.orhlc.AbstractOpenSearchConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Profile("!test")
@Configuration(proxyBeanMethods = false)
@EnableElasticsearchRepositories
class OpenSearchConfig(
    private val openSearchProperties: OpenSearchProperties,
) : AbstractOpenSearchConfiguration() {
    @Bean
    override fun opensearchClient(): RestHighLevelClient {
        val credentialsProvider = BasicCredentialsProvider().apply {
            setCredentials(
                AuthScope.ANY,
                UsernamePasswordCredentials(
                    openSearchProperties.username,
                    openSearchProperties.password,
                ),
            )
        }
        val restClientBuilder: RestClientBuilder = RestClient.builder(
            HttpHost(
                openSearchProperties.hostname,
                openSearchProperties.port,
                openSearchProperties.scheme,
            ),
        ).setHttpClientConfigCallback { httpClientBuilder ->
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
        }
        return RestHighLevelClient(restClientBuilder)
    }
}
