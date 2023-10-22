package gongback.pureureumserver.domain.auth

import gongback.pureureumserver.support.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class SmsLog(
    @Column
    val receiver: String,

    isSuccess: Boolean = false
) : BaseTimeEntity() {
    var isSuccess: Boolean = isSuccess
        protected set
}
