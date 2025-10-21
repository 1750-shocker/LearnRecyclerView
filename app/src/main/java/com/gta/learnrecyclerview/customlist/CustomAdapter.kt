package com.gta.learnrecyclerview.customlist

import android.view.ViewGroup

/**
 * 自定义适配器基类，模仿RecyclerView.Adapter
 */
abstract class CustomAdapter<VH : ViewHolder> {
    
    private var customListView: CustomListView? = null
    
    /**
     * 创建ViewHolder
     */
    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    
    /**
     * 绑定数据到ViewHolder
     */
    abstract fun onBindViewHolder(holder: VH, position: Int)
    
    /**
     * 获取数据项数量
     */
    abstract fun getItemCount(): Int
    
    /**
     * 获取指定位置的ViewType
     */
    open fun getItemViewType(position: Int): Int = 0
    
    /**
     * 通知数据变化
     */
    fun notifyDataSetChanged() {
        customListView?.refreshData()
    }
    
    /**
     * 通知指定位置数据变化
     */
    fun notifyItemChanged(position: Int) {
        customListView?.refreshItem(position)
    }
    
    /**
     * 通知插入数据
     */
    fun notifyItemInserted(position: Int) {
        customListView?.itemInserted(position)
    }
    
    /**
     * 通知删除数据
     */
    fun notifyItemRemoved(position: Int) {
        customListView?.itemRemoved(position)
    }
    
    internal fun attachToCustomListView(customListView: CustomListView) {
        this.customListView = customListView
    }
    
    internal fun detachFromCustomListView() {
        this.customListView = null
    }
}