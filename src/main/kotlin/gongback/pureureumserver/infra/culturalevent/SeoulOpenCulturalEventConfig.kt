package gongback.pureureumserver.infra.culturalevent

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

private const val DEFAULT_TIME_OUT_MS = 3000L

@Profile("!test")
@Configuration(proxyBeanMethods = false)
class SeoulOpenCulturalEventConfig(
    private val seoulOpenCulturalEventProperties: SeoulOpenCulturalEventProperties,
) {
    @Bean
    fun culturalEventClient(): SeoulOpenDataClient {
        return HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(getWebClient())).build()
            .createClient(SeoulOpenDataClient::class.java)
    }

    private fun getWebClient() = WebClient.builder()
        .baseUrl(seoulOpenCulturalEventProperties.baseUrl)
        .clientConnector(ReactorClientHttpConnector(httpClient()))
        .build()

    private fun httpClient() = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_TIME_OUT_MS.toInt())
        .responseTimeout(Duration.ofMillis(DEFAULT_TIME_OUT_MS))
        .doOnConnected { connection ->
            connection
                .addHandlerFirst(ReadTimeoutHandler(DEFAULT_TIME_OUT_MS, TimeUnit.MILLISECONDS))
                .addHandlerLast(WriteTimeoutHandler(DEFAULT_TIME_OUT_MS, TimeUnit.MILLISECONDS))
        }
}
