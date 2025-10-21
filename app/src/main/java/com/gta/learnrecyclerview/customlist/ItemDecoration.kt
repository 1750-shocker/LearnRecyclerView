package com.gta.learnrecyclerview.customlist

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View

/**
 * 自定义ItemDecoration，模仿RecyclerView.ItemDecoration
 */
abstract class ItemDecoration {
    
    /**
     * 绘制分割线等装饰
     */
    open fun onDraw(canvas: Canvas, parent: CustomListView) {}
    
    /**
     * 绘制在item之上的装饰
     */
    open fun onDrawOver(canvas: Canvas, parent: CustomListView) {}
    
    /**
     * 设置item的偏移量
     */
    open fun getItemOffsets(outRect: Rect, view: View, parent: CustomListView, position: Int) {}
}