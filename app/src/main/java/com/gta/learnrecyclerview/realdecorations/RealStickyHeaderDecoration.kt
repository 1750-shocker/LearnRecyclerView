package com.gta.learnrecyclerview.realdecorations

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 真正的RecyclerView粘性头部装饰器（吸顶效果）
 * 分组规则：跳过列表第一个（整体Header）和最后一个（Footer），其余每5个item为一组。
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
        // 在每组起始item之前预留空间并绘制组内标题（非吸顶，仅组内）
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            if (isGroupStart(position, state.itemCount)) {
                drawHeaderForChild(canvas, child, getGroupTitle(position))
            }
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 吸顶绘制：根据第一个可见item所属分组绘制顶部标题，并在遇到下个分组时上推
        if (parent.childCount == 0) return
        val topChild = parent.getChildAt(0)
        val topPosition = parent.getChildAdapterPosition(topChild)
        val itemCount = state.itemCount

        // 找到当前应显示的分组标题（基于第一个可见item）
        val currentGroupTitle = getCurrentGroupTitle(topPosition)
        if (currentGroupTitle == null) return

        // 默认吸顶位置
        var headerTop = 0f

        // 检测下一个分组的标题区域，如果它即将进入视野，则上推当前header
        val nextGroupHeaderTop = findNextGroupHeaderTop(parent, itemCount)
        if (nextGroupHeaderTop != null && nextGroupHeaderTop < headerHeight) {
            // 当下一个分组标题区域的顶部小于当前header高度时，开始上推
            headerTop = nextGroupHeaderTop - headerHeight
        }

        // 在顶部绘制吸顶标题（全宽）
        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()
        val top = headerTop
        val bottom = headerTop + headerHeight

        canvas.drawRect(left, top, right, bottom, headerPaint)
        val textX = left + 32f
        val textY = top + headerHeight / 2f + textSize / 3f
        canvas.drawText(currentGroupTitle, textX, textY, textPaint)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (isGroupStart(position, state.itemCount)) {
            outRect.top = headerHeight
        }
    }

    private fun isGroupStart(position: Int, itemCount: Int): Boolean {
        if (position <= 0) return false // 跳过整体Header
        if (position >= itemCount - 1) return false // 跳过Footer
        // 每5个为一组的起始（例如：1,6,11,...）
        return position % 5 == 1
    }

    private fun getGroupTitle(position: Int): String? {
        return when {
            position <= 0 -> null
            else -> {
                // 基于每5个一组，组号从1开始
                val groupIndex = (position - 1) / 5 + 1
                "第 $groupIndex 组"
            }
        }
    }
    
    private fun getCurrentGroupTitle(position: Int): String? {
        if (position <= 0) return null
        // 找到当前position所属的分组
        val groupIndex = (position - 1) / 5 + 1
        return "第 $groupIndex 组"
    }

    private fun drawHeaderForChild(canvas: Canvas, child: View, title: String?) {
        if (title == null) return
        val left = child.left.toFloat()
        val right = child.right.toFloat()
        val top = child.top - headerHeight.toFloat()
        val bottom = child.top.toFloat()
        canvas.drawRect(left, top, right, bottom, headerPaint)
        val textX = left + 32f
        val textY = top + headerHeight / 2f + textSize / 3f
        canvas.drawText(title, textX, textY, textPaint)
    }

    private fun findNextGroupHeaderTop(parent: RecyclerView, itemCount: Int): Float? {
        val topChild = parent.getChildAt(0)
        val topPosition = parent.getChildAdapterPosition(topChild)
        val currentGroupIndex = (topPosition - 1) / 5 + 1
        
        // 遍历可见child，找到下一个分组的起始item
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            
            if (isGroupStart(position, itemCount)) {
                val childGroupIndex = (position - 1) / 5 + 1
                // 找到比当前分组更大的分组
                if (childGroupIndex > currentGroupIndex) {
                    // 返回该分组标题区域的顶部位置（即child.top - headerHeight）
                    return child.top.toFloat() - headerHeight
                }
            }
        }
        return null
    }
}