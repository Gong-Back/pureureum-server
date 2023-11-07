package gongback.pureureumserver.support.constant

import java.util.Locale

enum class FilePackage(private val description: String) {
    PROFILE("프로필"), ;

    fun toLowercase(): String {
        return name.lowercase(Locale.getDefault())
    }
}
