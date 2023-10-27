package gongback.pureureumserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PureureumServerApplication

fun main(args: Array<String>) {
    runApplication<PureureumServerApplication>(*args)
}
