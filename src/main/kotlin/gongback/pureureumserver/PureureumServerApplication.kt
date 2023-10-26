package gongback.pureureumserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication(exclude = [ElasticsearchDataAutoConfiguration::class])
class PureureumServerApplication

fun main(args: Array<String>) {
	runApplication<PureureumServerApplication>(*args)
}
