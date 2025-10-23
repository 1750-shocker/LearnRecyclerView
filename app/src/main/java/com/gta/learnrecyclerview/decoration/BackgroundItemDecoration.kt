package com.gta.learnrecyclerview.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.gta.learnrecyclerview.customlist.CustomListView
import com.gta.learnrecyclerview.customlist.ItemDecoration

/**
 * 背景装饰器 - 为奇偶行设置不同的背景色
 */
class BackgroundItemDecoration(
    private val evenColor: Int = 0xFFF5F5F5.toInt(),
    private val oddColor: Int = 0xFFFFFFFF.toInt(),
    private val cornerRadius: Float = 8f
) : ItemDecoration() {
    
    private val evenPaint = Paint().apply {
        color = evenColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    
    private val oddPaint = Paint().apply {
        color = oddColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    
    override fun onDraw(canvas: Canvas, parent: CustomListView) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = getChildAdapterPosition(parent, child)
            
            // 跳过Header和Footer
            if (position == 0 || position == parent.childCount - 1) {
                continue
            }
            
            val paint = if (position % 2 == 0) evenPaint else oddPaint
            
            val left = child.left.toFloat()
            val top = child.top.toFloat()
            val right = child.right.toFloat()
            val bottom = child.bottom.toFloat()
            
            // 绘制圆角背景
            canvas.drawRoundRect(
                left + 8f,
                top + 4f,
                right - 8f,
                bottom - 4f,
                cornerRadius,
                cornerRadius,
                paint
            )
        }
    }
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: CustomListView, position: Int) {
        // 为背景留出一些边距
        if (position != 0 && position != parent.childCount - 1) {
            outRect.set(8, 4, 8, 4)
        }
    }
    
    private fun getChildAdapterPosition(parent: CustomListView, child: View): Int {
        for (i in 0 until parent.childCount) {
            if (parent.getChildAt(i) == child) {
                return i
            }
        }
        return -1
    }
}