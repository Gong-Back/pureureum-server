package gongback.pureureumserver.controller.test

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "03. Sentry Test", description = "센트리 테스트")
class SentryTestController {
    @GetMapping("/sentry-test")
    fun test() {
        throw Exception("sentry test!")
    }
}
