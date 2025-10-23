package com.gta.learnrecyclerview.realdecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gta.learnrecyclerview.adapter.RealRecyclerAdapter

/**
 * 真正的RecyclerView间距装饰器
 */
class RealSpacingItemDecoration(
    private val headerSpacing: Int = 0,
    private val normalSpacing: Int = 16,
    private val footerSpacing: Int = 32
) : RecyclerView.ItemDecoration() {
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter
        
        when {
            position == 0 -> {
                // Header - 只设置底部间距
                outRect.set(0, 0, 0, headerSpacing)
            }
            position == state.itemCount - 1 -> {
                // Footer - 设置顶部间距
                outRect.set(0, footerSpacing, 0, 0)
            }
            else -> {
                // Normal items - 设置左右和底部间距
                outRect.set(normalSpacing, normalSpacing / 2, normalSpacing, normalSpacing / 2)
            }
        }
    }
}