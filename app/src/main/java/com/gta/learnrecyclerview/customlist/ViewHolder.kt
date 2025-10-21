package com.gta.learnrecyclerview.customlist

import android.view.View

/**
 * 自定义ViewHolder基类，模仿RecyclerView.ViewHolder
 */
abstract class ViewHolder(val itemView: View) {
    var adapterPosition: Int = -1
        internal set
    
    var itemViewType: Int = 0
        internal set
}