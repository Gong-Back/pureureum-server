package gongback.pureureumserver.support.constant

import java.util.Locale

enum class FilePackage(private val description: String) {
    COMMON("공통"),
    BADGE("뱃지"),
    PROFILE("프로필"),
    SUGGESTION("제안"),
    CULTURAL_EVENT("문화 컨텐츠"),
    ;

    fun toLowercase(): String {
        return name.lowercase(Locale.getDefault())
    }
}
