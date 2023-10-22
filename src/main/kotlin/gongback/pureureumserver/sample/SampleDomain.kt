package gongback.pureureumserver.sample

import java.time.LocalDateTime

data class SampleDomain(
    val director: String = "",
    val title: String = "",
    val year: Int = 0,
    val isPlaying: Boolean = false,
    val releaseDate: LocalDateTime = LocalDateTime.now()
)
