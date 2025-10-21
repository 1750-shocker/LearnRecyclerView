package com.gta.learnrecyclerview.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.gta.learnrecyclerview.customlist.CustomListView
import com.gta.learnrecyclerview.customlist.ItemDecoration

/**
 * 分割线装饰器示例
 */
class DividerItemDecoration(
    private val dividerHeight: Int = 2,
    private val dividerColor: Int = 0xFFE0E0E0.toInt()
) : ItemDecoration() {
    
    private val paint = Paint().apply {
        color = dividerColor
        style = Paint.Style.FILL
    }
    
    override fun onDraw(canvas: Canvas, parent: CustomListView) {
        // 这里可以绘制分割线，但由于我们使用margin实现，暂时留空
    }
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: CustomListView, position: Int) {
        // 添加底部间距作为分割线
        outRect.bottom = dividerHeight
    }
}