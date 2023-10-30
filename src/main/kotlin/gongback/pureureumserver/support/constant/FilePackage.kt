package gongback.pureureumserver.support.constant

import java.util.Locale

enum class FilePackage(private val description: String) {
    COMMON("공통");

    override fun toString(): String {
        return name.lowercase(Locale.getDefault())
    }
}
