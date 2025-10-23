package com.gta.learnrecyclerview.realdecorations

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 真正的RecyclerView网格边框装饰器
 */
class RealGridItemDecoration(
    private val borderWidth: Int = 2,
    private val borderColor: Int = 0xFF2196F3.toInt(),
    private val cornerRadius: Float = 8f
) : RecyclerView.ItemDecoration() {
    
    private val borderPaint = Paint().apply {
        color = borderColor
        style = Paint.Style.STROKE
        strokeWidth = borderWidth.toFloat()
        isAntiAlias = true
    }
    
    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val left = child.left.toFloat()
            val top = child.top.toFloat()
            val right = child.right.toFloat()
            val bottom = child.bottom.toFloat()
            
            // 绘制圆角矩形边框
            canvas.drawRoundRect(
                left + borderWidth / 2f,
                top + borderWidth / 2f,
                right - borderWidth / 2f,
                bottom - borderWidth / 2f,
                cornerRadius,
                cornerRadius,
                borderPaint
            )
        }
    }
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(borderWidth, borderWidth, borderWidth, borderWidth)
    }
}