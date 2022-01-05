package com.pd.photo_of_the_day_nasa.view.recycler

const val TYPE_TODO = 0
const val TYPE_BUY = 1
const val TYPE_HEADER = 3

data class Data(
//    val id: Int,
    val label: String = "Text",
    val description: String? = "Description",
    val type: Int = TYPE_BUY // тип ячейки
)


data class Change<out T>(
    val oldData: T,
    val newData: T
)

fun <T> createCombinedPayload(payloads: List<Change<T>>): Change<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()

    return Change(firstChange.oldData, lastChange.newData)
}