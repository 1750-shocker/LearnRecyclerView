package com.gta.learnrecyclerview.customlist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView

/**
 * 自定义列表组件，模仿RecyclerView的核心功能
 */
class CustomListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private var adapter: CustomAdapter<ViewHolder>? = null
    private val itemDecorations = mutableListOf<ItemDecoration>()
    
    // ViewHolder缓存池，按ViewType分类
    private val recycledViewPool = mutableMapOf<Int, MutableList<ViewHolder>>()
    
    // 当前显示的ViewHolder
    private val viewHolders = mutableListOf<ViewHolder>()
    
    // 容器ViewGroup
    private val containerView: LinearLayout
    
    init {
        // 创建容器ViewGroup
        containerView = object : LinearLayout(context) {
            init {
                orientation = VERTICAL
            }
            
            override fun dispatchDraw(canvas: Canvas) {
                // 绘制ItemDecoration
                for (decoration in itemDecorations) {
                    decoration.onDraw(canvas, this@CustomListView)
                }
                
                super.dispatchDraw(canvas)
                
                // 绘制ItemDecoration overlay
                for (decoration in itemDecorations) {
                    decoration.onDrawOver(canvas, this@CustomListView)
                }
            }
        }
        
        addView(containerView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
    }
    
    /**
     * 设置适配器
     */
    fun <VH : ViewHolder> setAdapter(adapter: CustomAdapter<VH>?) {
        this.adapter?.detachFromCustomListView()
        @Suppress("UNCHECKED_CAST")
        this.adapter = adapter as? CustomAdapter<ViewHolder>
        adapter?.attachToCustomListView(this)
        refreshData()
    }
    
    /**
     * 添加ItemDecoration
     */
    fun addItemDecoration(decoration: ItemDecoration) {
        itemDecorations.add(decoration)
        invalidate()
    }
    
    /**
     * 移除ItemDecoration
     */
    fun removeItemDecoration(decoration: ItemDecoration) {
        itemDecorations.remove(decoration)
        invalidate()
    }
    
    /**
     * 刷新所有数据
     */
    internal fun refreshData() {
        // 回收所有ViewHolder
        recycleAllViewHolders()
        viewHolders.clear()
        containerView.removeAllViews()
        
        // 重新创建所有ViewHolder
        createAllViewHolders()
    }
    
    /**
     * 刷新指定位置的item
     */
    internal fun refreshItem(position: Int) {
        val viewHolder = viewHolders.find { it.adapterPosition == position }
        viewHolder?.let {
            adapter?.onBindViewHolder(it, position)
        }
    }
    
    /**
     * 通知item插入
     */
    internal fun itemInserted(position: Int) {
        // 更新位置大于等于插入位置的ViewHolder的position
        viewHolders.forEach { holder ->
            if (holder.adapterPosition >= position) {
                holder.adapterPosition++
            }
        }
        refreshData()
    }
    
    /**
     * 通知item删除
     */
    internal fun itemRemoved(position: Int) {
        // 移除对应位置的ViewHolder
        val removedHolder = viewHolders.find { it.adapterPosition == position }
        removedHolder?.let {
            viewHolders.remove(it)
            containerView.removeView(it.itemView)
            recycleViewHolder(it)
        }
        
        // 更新位置大于删除位置的ViewHolder的position
        viewHolders.forEach { holder ->
            if (holder.adapterPosition > position) {
                holder.adapterPosition--
            }
        }
        refreshData()
    }
    
    /**
     * 创建所有ViewHolder
     */
    private fun createAllViewHolders() {
        val adapter = this.adapter ?: return
        val itemCount = adapter.getItemCount()
        
        for (position in 0 until itemCount) {
            val viewType = adapter.getItemViewType(position)
            val holder = getViewHolder(viewType, position)
            adapter.onBindViewHolder(holder, position)
            viewHolders.add(holder)
            
            // 应用ItemDecoration的偏移
            val rect = Rect()
            for (decoration in itemDecorations) {
                decoration.getItemOffsets(rect, holder.itemView, this, position)
            }
            
            // 设置布局参数
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(rect.left, rect.top, rect.right, rect.bottom)
            holder.itemView.layoutParams = layoutParams
            
            containerView.addView(holder.itemView)
        }
    }
    
    /**
     * 获取ViewHolder（从缓存池或创建新的）
     */
    private fun getViewHolder(viewType: Int, position: Int): ViewHolder {
        val recycledList = recycledViewPool[viewType]
        val holder = if (recycledList?.isNotEmpty() == true) {
            recycledList.removeAt(recycledList.size - 1)
        } else {
            adapter?.onCreateViewHolder(containerView, viewType) ?: throw IllegalStateException("Adapter is null")
        }
        
        holder.adapterPosition = position
        holder.itemViewType = viewType
        return holder
    }
    
    /**
     * 回收ViewHolder到缓存池
     */
    private fun recycleViewHolder(holder: ViewHolder) {
        val viewType = holder.itemViewType
        val recycledList = recycledViewPool.getOrPut(viewType) { mutableListOf() }
        
        // 限制缓存池大小
        if (recycledList.size < 5) {
            recycledList.add(holder)
        }
    }
    
    /**
     * 回收所有ViewHolder
     */
    private fun recycleAllViewHolders() {
        viewHolders.forEach { recycleViewHolder(it) }
    }
}