package gongback.pureureumserver.support.constant

import java.util.Locale

enum class FilePackage(private val description: String) {
    COMMON("공통"),
    BADGE("뱃지"),
    PROFILE("프로필"), ;

    fun toLowercase(): String {
        return name.lowercase(Locale.getDefault())
    }
}
