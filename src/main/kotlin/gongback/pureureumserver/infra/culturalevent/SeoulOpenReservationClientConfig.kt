package gongback.pureureumserver.infra.culturalevent

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

private const val DEFAULT_TIME_OUT_MS = 3000L
private const val BASE_URL = "https://yeyak.seoul.go.kr"
private const val FILE_MAX_SIZE = 10 * 1024 * 1024

@Profile("!test")
@Configuration(proxyBeanMethods = false)
class SeoulOpenReservationClientConfig {

    @Bean
    fun culturalEventInfoClient(): SeoulOpenReservationClient {
        return HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(getWebClient())).build()
            .createClient(SeoulOpenReservationClient::class.java)
    }

    private fun getWebClient() = WebClient.builder()
        .uriBuilderFactory(uriBuilderFactory())
        .exchangeStrategies(exchangeStrategies())
        .clientConnector(ReactorClientHttpConnector(httpClient()))
        .build()

    private fun uriBuilderFactory() = DefaultUriBuilderFactory().apply {
        encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE
    }

    private fun exchangeStrategies() = ExchangeStrategies.builder()
        .codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(FILE_MAX_SIZE) }
        .build()

    private fun httpClient() = HttpClient.create()
        .baseUrl(BASE_URL)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_TIME_OUT_MS.toInt())
        .responseTimeout(Duration.ofMillis(DEFAULT_TIME_OUT_MS))
        .doOnConnected { connection ->
            connection
                .addHandlerFirst(ReadTimeoutHandler(DEFAULT_TIME_OUT_MS, TimeUnit.MILLISECONDS))
                .addHandlerLast(WriteTimeoutHandler(DEFAULT_TIME_OUT_MS, TimeUnit.MILLISECONDS))
        }
}
