package gongback.pureureumserver.domain.file

import gongback.pureureumserver.support.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "file")
class File(
    fileKey: String,
    contentType: String,
    originalFileName: String,
) : BaseEntity() {

    @Column(nullable = false)
    var fileKey: String = fileKey
        protected set

    @Column(nullable = false)
    var contentType: String = contentType
        protected set

    @Column(nullable = false)
    var originalFileName: String = originalFileName
        protected set
}
