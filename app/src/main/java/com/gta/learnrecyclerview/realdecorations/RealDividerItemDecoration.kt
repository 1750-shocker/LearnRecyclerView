package com.gta.learnrecyclerview.realdecorations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 真正的RecyclerView分割线装饰器
 */
class RealDividerItemDecoration(
    private val context: Context,
    private val dividerHeight: Int = 2,
    private val dividerColor: Int = 0xFFE0E0E0.toInt()
) : RecyclerView.ItemDecoration() {
    
    private val paint = Paint().apply {
        color = dividerColor
        style = Paint.Style.FILL
    }
    
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerHeight
            
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
    
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position < state.itemCount - 1) {
            outRect.bottom = dividerHeight
        }
    }
}