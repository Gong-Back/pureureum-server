package gongback.pureureumserver.support.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDate

@MappedSuperclass
abstract class BaseTimeEntity(
    @Column(nullable = false, updatable = false)
    val createdDate: LocalDate = LocalDate.now()
) : BaseEntity()

