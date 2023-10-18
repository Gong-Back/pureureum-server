package gongback.pureureumserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class PureureumServerApplication

fun main(args: Array<String>) {
	runApplication<PureureumServerApplication>(*args)
}
