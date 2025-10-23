package com.gta.learnrecyclerview.realdecorations

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 真正的RecyclerView粘性头部装饰器
 */
class RealStickyHeaderDecoration(
    private val headerHeight: Int = 80,
    private val headerColor: Int = 0xFFE3F2FD.toInt(),
    private val textColor: Int = 0xFF1976D2.toInt(),
    private val textSize: Float = 48f
) : RecyclerView.ItemDecoration() {
    
    private val headerPaint = Paint().apply {
        color = headerColor
        style = Paint.Style.FILL
    }
    
    private val textPaint = Paint().apply {
        color = textColor
        textSize = this@RealStickyHeaderDecoration.textSize
        isAntiAlias = true
        isFakeBoldText = true
    }
    
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 在每5个item之前绘制分组标题
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            
            if (position % 5 == 0 && position > 0) {
                val groupNumber = position / 5
                drawHeader(canvas, child, "第 $groupNumber 组")
            }
        }
    }
    
    private fun drawHeader(canvas: Canvas, child: View, text: String) {
        val left = child.left.toFloat()
        val right = child.right.toFloat()
        val top = child.top - headerHeight.toFloat()
        val bottom = child.top.toFloat()
        
        // 绘制头部背景
        canvas.drawRect(left, top, right, bottom, headerPaint)
        
        // 绘制文字
        val textX = left + 32f
        val textY = top + headerHeight / 2f + textSize / 3f
        canvas.drawText(text, textX, textY, textPaint)
    }
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        // 每5个item之前添加头部空间
        if (position % 5 == 0 && position > 0) {
            outRect.top = headerHeight
        }
    }
}