package gongback.pureureumserver.domain.culturalevent

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class CulturalEventCoordinates(
    @Column(length = 20)
    val latitude: String?,
    @Column(length = 20)
    val longitude: String?,
) {
    init {
        require((latitude != null && longitude != null) || (latitude == null && longitude == null)) {
            "위도($latitude) 경도($longitude) 둘 다 null이거나 둘 다 null이 아니어야 합니다."
        }
    }
}
