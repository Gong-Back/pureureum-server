package gongback.pureureumserver.domain.culturalevent

import gongback.pureureumserver.support.domain.BaseUpdatableEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class CulturalEvent(
    information: CulturalEventInformation,
) : BaseUpdatableEntity() {
    @Embedded
    var information: CulturalEventInformation = information
        protected set

    val culturalEventId: String
        get() = information.culturalEventId

    val className: String?
        get() = information.className

    val region: String?
        get() = information.region

    val state: String?
        get() = information.state

    val content: String?
        get() = information.content

    val paymentMethod: String?
        get() = information.paymentMethod

    val placeName: String?
        get() = information.placeName

    val target: String?
        get() = information.target

    val serviceUrl: String?
        get() = information.serviceUrl

    val coordinates: CulturalEventCoordinates?
        get() = information.coordinates

    val serviceStartDateTime: LocalDateTime
        get() = information.serviceStartDateTime

    val serviceEndDateTime: LocalDateTime
        get() = information.serviceEndDateTime

    val registerStartDateTime: LocalDateTime
        get() = information.registerStartDateTime

    val registerEndDateTime: LocalDateTime
        get() = information.registerEndDateTime

    fun updateInformation(culturalEventInformation: CulturalEventInformation) {
        this.information = culturalEventInformation
    }
}
