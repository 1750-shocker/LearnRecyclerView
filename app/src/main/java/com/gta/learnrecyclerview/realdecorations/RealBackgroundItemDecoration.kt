package com.gta.learnrecyclerview.realdecorations

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 真正的RecyclerView背景装饰器
 */
class RealBackgroundItemDecoration(
    private val evenColor: Int = 0xFFF5F5F5.toInt(),
    private val oddColor: Int = 0xFFFFFFFF.toInt(),
    private val cornerRadius: Float = 8f
) : RecyclerView.ItemDecoration() {
    
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
    
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            
            // 跳过Header和Footer
            if (position == 0 || position == state.itemCount - 1) {
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
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        // 为背景留出一些边距
        if (position != 0 && position != state.itemCount - 1) {
            outRect.set(8, 4, 8, 4)
        }
    }
}