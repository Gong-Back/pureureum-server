package gongback.pureureumserver.domain.culturecontent

import gongback.pureureumserver.support.domain.BaseTimeEntity
import gongback.pureureumserver.support.domain.FileType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class CultureContentFile(
    @Column(nullable = false)
    val fileKey: String,

    @Column(nullable = false)
    val contentType: String,

    @Column(nullable = false)
    val originalFileName: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val fileType: FileType = FileType.COMMON
) : BaseTimeEntity()
