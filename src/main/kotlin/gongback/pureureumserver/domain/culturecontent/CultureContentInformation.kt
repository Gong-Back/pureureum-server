package gongback.pureureumserver.domain.culturecontent

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import java.time.LocalDate

@Embeddable
data class CultureContentInformation(
    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val introduction: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(nullable = false)
    val startDate: LocalDate,

    @Column(nullable = false)
    val endDate: LocalDate,

    @Column(nullable = false)
    val commentEnabled: Boolean,

    @Embedded
    val address: CultureContentAddress,
)
