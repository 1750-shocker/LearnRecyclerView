package com.gta.learnrecyclerview.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.gta.learnrecyclerview.customlist.CustomListView
import com.gta.learnrecyclerview.customlist.ItemDecoration

/**
 * 粘性头部装饰器 - 在特定位置绘制分组标题
 */
class StickyHeaderDecoration(
    private val headerHeight: Int = 80,
    private val headerColor: Int = 0xFFE3F2FD.toInt(),
    private val textColor: Int = 0xFF1976D2.toInt(),
    private val textSize: Float = 48f
) : ItemDecoration() {
    
    private val headerPaint = Paint().apply {
        color = headerColor
        style = Paint.Style.FILL
    }
    
    private val textPaint = Paint().apply {
        color = textColor
        textSize = this@StickyHeaderDecoration.textSize
        isAntiAlias = true
        isFakeBoldText = true
    }
    
    override fun onDraw(canvas: Canvas, parent: CustomListView) {
        // 在每5个item之前绘制分组标题
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = getChildAdapterPosition(parent, child)
            
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
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: CustomListView, position: Int) {
        // 每5个item之前添加头部空间
        if (position % 5 == 0 && position > 0) {
            outRect.top = headerHeight
        }
    }
    
    private fun getChildAdapterPosition(parent: CustomListView, child: View): Int {
        // 简化实现：通过child在parent中的索引来获取position
        for (i in 0 until parent.childCount) {
            if (parent.getChildAt(i) == child) {
                return i
            }
        }
        return -1
    }
}