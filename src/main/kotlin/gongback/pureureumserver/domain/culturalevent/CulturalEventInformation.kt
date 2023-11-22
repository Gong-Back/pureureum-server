package gongback.pureureumserver.domain.culturalevent

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import java.time.LocalDateTime

/**
 * culturalEventId 서비스ID
 * className 분류명
 * region 지역
 * state 서비스상태
 * content 내용
 * paymentMethod 결제방법
 * placeName 장소명
 * target 서비스대상
 * serviceUrl 바로가기URL
 * coordinates 좌표
 * serviceStartDateTime 서비스개시시작일시
 * serviceEndDateTime 서비스개시종료일시
 * registerStartDateTime 접수시작일시
 * registerEndDateTime 접수종료일시
 */
@Embeddable
data class CulturalEventInformation(
    @Column(unique = true, nullable = false, length = 100)
    val culturalEventId: String,

    @Column(length = 30)
    val className: String?,

    @Column(length = 100)
    val region: String?,

    @Column(length = 30)
    val state: String?,

    @Column(length = 255)
    val content: String?,

    @Column(length = 50)
    val paymentMethod: String?,

    @Column(length = 100)
    val placeName: String?,

    @Column(length = 255)
    val target: String?,

    @Column(length = 100)
    val serviceUrl: String?,

    @Embedded
    val coordinates: CulturalEventCoordinates?,

    @Column
    val serviceStartDateTime: LocalDateTime,

    @Column
    val serviceEndDateTime: LocalDateTime,

    @Column
    val registerStartDateTime: LocalDateTime,

    @Column
    val registerEndDateTime: LocalDateTime,
)
