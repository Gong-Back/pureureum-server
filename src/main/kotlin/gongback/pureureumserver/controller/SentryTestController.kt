package gongback.pureureumserver.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SentryTestController {
    @GetMapping("/sentry-test")
    fun test() {
        throw Exception("sentry test!")
    }
}
