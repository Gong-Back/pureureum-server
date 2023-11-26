package gongback.pureureumserver.domain.culturalevent

import gongback.pureureumserver.domain.file.File
import gongback.pureureumserver.support.constant.FilePackage
import gongback.pureureumserver.support.domain.BaseUpdatableEntity
import gongback.pureureumserver.support.domain.FileContentType
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@Entity
class CulturalEvent(
    information: CulturalEventInformation,
) : BaseUpdatableEntity() {

    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        orphanRemoval = true,
    )
    @JoinColumn(
        name = "file_id",
        nullable = false,
        unique = true,
        foreignKey = ForeignKey(name = "fk_cultural_event_file_id"),
    )
    var thumbnail: File = defaultThumbnail()
        protected set

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

    fun updateThumbnail(thumbnail: File?) {
        if (thumbnail != null) {
            this.thumbnail = thumbnail
        }
    }

    companion object {
        private const val DEFAULT_THUMBNAIL_FILE_NAME = "default_cultural_event_thumbnail.jpg"

        private fun defaultThumbnail(): File {
            return File(
                fileKey = "${FilePackage.COMMON.toLowercase()}/${FilePackage.CULTURAL_EVENT.toLowercase()}/$DEFAULT_THUMBNAIL_FILE_NAME",
                contentType = FileContentType.JPG.contentType,
                originalFileName = DEFAULT_THUMBNAIL_FILE_NAME,
            )
        }
    }
}
