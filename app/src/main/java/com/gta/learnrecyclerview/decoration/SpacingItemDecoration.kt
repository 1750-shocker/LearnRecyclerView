package com.gta.learnrecyclerview.decoration

import android.graphics.Rect
import android.view.View
import com.gta.learnrecyclerview.customlist.CustomListView
import com.gta.learnrecyclerview.customlist.ItemDecoration

/**
 * 间距装饰器 - 为不同类型的item设置不同的间距
 */
class SpacingItemDecoration(
    private val headerSpacing: Int = 0,
    private val normalSpacing: Int = 16,
    private val footerSpacing: Int = 32
) : ItemDecoration() {
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: CustomListView, position: Int) {
        // 根据position判断item类型并设置不同间距
        when {
            position == 0 -> {
                // Header - 只设置底部间距
                outRect.set(0, 0, 0, headerSpacing)
            }
            isFooter(parent, position) -> {
                // Footer - 设置顶部间距
                outRect.set(0, footerSpacing, 0, 0)
            }
            else -> {
                // Normal items - 设置左右和底部间距
                outRect.set(normalSpacing, normalSpacing / 2, normalSpacing, normalSpacing / 2)
            }
        }
    }
    
    private fun isFooter(parent: CustomListView, position: Int): Boolean {
        // 简化判断：假设最后一个item是footer
        return position == parent.childCount - 1
    }
}