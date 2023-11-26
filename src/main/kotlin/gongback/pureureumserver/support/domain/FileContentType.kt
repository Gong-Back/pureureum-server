package gongback.pureureumserver.support.domain

import java.util.Locale

enum class FileContentType(
    val contentType: String,
) {
    SVG("image/svg+xml"),
    JPG("image/jpeg"),
    PNG("image/png"),
    DEFAULT("image/*"),

    ;

    companion object {
        fun fromExtension(extension: String): FileContentType {
            return values().find { it.name.lowercase(Locale.getDefault()) == extension } ?: DEFAULT
        }
    }
}
