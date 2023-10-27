package gongback.pureureumserver.support.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseUpdatedTimeEntity : BaseEntity() {

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedDate: LocalDateTime = LocalDateTime.now()
}
