package gongback.pureureumserver.application.dto

data class SearchCondition(
    val index: String,
    val field: String?,
    val value: String?,
    val returnType: Class<*>,
    val sortCondition: List<SortCondition>? = null,
    val pageCondition: PageCondition? = null,
)

data class SortCondition(
    val field: String,
    val orderType: OrderType = OrderType.DESC,
)

data class PageCondition(
    val page: Int,
    val size: Int
) {
    fun getFrom(): Int {
        return size * (page - 1)
    }
}

data class SaveCondition(
    val index: String,
    val data: Any,
)

data class UpdateCondition(
    val index: String,
    val docId: String,
    val data: Any,
)

data class DeleteCondition(
    val index: String,
    val docId: String,
)

enum class OrderType {
    ASC,
    DESC,
}
