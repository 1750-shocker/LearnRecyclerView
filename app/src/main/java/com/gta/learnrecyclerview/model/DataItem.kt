package com.gta.learnrecyclerview.model

/**
 * 示例数据模型
 */
data class DataItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val type: ItemType = ItemType.NORMAL
)

enum class ItemType {
    HEADER,
    NORMAL,
    FOOTER
}