package gongback.pureureumserver.domain.suggestion

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate

private const val DEFAULT_PLUS_END_DATE_DAYS = 7L

@Embeddable
data class SuggestionInformation(
    @Column(nullable = false, length = 50)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    val startDate: LocalDate = LocalDate.now(),

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    val endDate: LocalDate = LocalDate.now().plusDays(DEFAULT_PLUS_END_DATE_DAYS),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: SuggestionStatus = SuggestionStatus.IN_PROGRESS,
)
