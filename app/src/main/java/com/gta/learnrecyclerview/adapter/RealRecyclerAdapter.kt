package com.gta.learnrecyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gta.learnrecyclerview.R
import com.gta.learnrecyclerview.model.DataItem
import com.gta.learnrecyclerview.model.ItemType

/**
 * 真正的RecyclerView适配器实现
 */
class RealRecyclerAdapter(private var dataList: MutableList<DataItem>) : RecyclerView.Adapter<RealRecyclerAdapter.BaseViewHolder>() {
    
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_NORMAL = 1
        private const val TYPE_FOOTER = 2
    }
    
    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    
    class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.tv_header_title)
    }
    
    class NormalViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.tv_title)
        val subtitleText: TextView = itemView.findViewById(R.id.tv_subtitle)
    }
    
    class FooterViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val footerText: TextView = itemView.findViewById(R.id.tv_footer)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> {
                val view = inflater.inflate(R.layout.item_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_FOOTER -> {
                val view = inflater.inflate(R.layout.item_footer, parent, false)
                FooterViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_normal, parent, false)
                NormalViewHolder(view)
            }
        }
    }
    
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is HeaderViewHolder -> {
                holder.titleText.text = item.title
            }
            is NormalViewHolder -> {
                holder.titleText.text = item.title
                holder.subtitleText.text = item.subtitle
            }
            is FooterViewHolder -> {
                holder.footerText.text = item.title
            }
        }
    }
    
    override fun getItemCount(): Int = dataList.size
    
    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].type) {
            ItemType.HEADER -> TYPE_HEADER
            ItemType.FOOTER -> TYPE_FOOTER
            ItemType.NORMAL -> TYPE_NORMAL
        }
    }
}