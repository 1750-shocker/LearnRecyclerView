package com.gta.learnrecyclerview.realdecorations

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 真正的RecyclerView阴影装饰器
 */
class RealShadowItemDecoration(
    private val shadowRadius: Float = 8f,
    private val shadowColor: Int = 0x40000000,
    private val offsetX: Float = 0f,
    private val offsetY: Float = 4f,
    private val cornerRadius: Float = 12f
) : RecyclerView.ItemDecoration() {
    
    private val shadowPaint = Paint().apply {
        isAntiAlias = true
        setShadowLayer(shadowRadius, offsetX, offsetY, shadowColor)
    }
    
    private val backgroundPaint = Paint().apply {
        color = 0xFFFFFFFF.toInt()
        isAntiAlias = true
    }
    
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val shadowMargin = shadowRadius + Math.abs(offsetY)
            
            val rectF = RectF(
                child.left + shadowMargin / 2,
                child.top + shadowMargin / 2,
                child.right - shadowMargin / 2,
                child.bottom - shadowMargin / 2
            )
            
            // 绘制阴影背景
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, shadowPaint)
            // 绘制白色背景
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, backgroundPaint)
        }
    }
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val margin = (shadowRadius + Math.abs(offsetY)).toInt()
        outRect.set(margin, margin, margin, margin)
    }
}