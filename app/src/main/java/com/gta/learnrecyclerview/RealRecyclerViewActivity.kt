package com.gta.learnrecyclerview

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gta.learnrecyclerview.adapter.RealRecyclerAdapter
import com.gta.learnrecyclerview.model.DataItem
import com.gta.learnrecyclerview.model.ItemType
import com.gta.learnrecyclerview.realdecorations.*

class RealRecyclerViewActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RealRecyclerAdapter
    private lateinit var tvCurrentDecoration: TextView
    
    // ItemDecoration 按钮
    private lateinit var btnDivider: Button
    private lateinit var btnGrid: Button
    private lateinit var btnShadow: Button
    private lateinit var btnSticky: Button
    private lateinit var btnSpacing: Button
    private lateinit var btnBackground: Button
    private lateinit var btnClear: Button
    private lateinit var btnAddItem: Button
    private lateinit var btnRemoveItem: Button
    
    private val dataList = mutableListOf<DataItem>()
    private var itemCounter = 0
    
    // 当前应用的装饰器
    private var currentDecoration: RecyclerView.ItemDecoration? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_recyclerview)
        
        initViews()
        setupRecyclerView()
        setupClickListeners()
        setupDecorationListeners()
    }
    
    private fun initViews() {
        recyclerView = findViewById(R.id.real_recycler_view)
        tvCurrentDecoration = findViewById(R.id.tv_current_decoration)
        
        // 数据操作按钮
        btnAddItem = findViewById(R.id.btn_add_item)
        btnRemoveItem = findViewById(R.id.btn_remove_item)
        
        // ItemDecoration 按钮
        btnDivider = findViewById(R.id.btn_divider)
        btnGrid = findViewById(R.id.btn_grid)
        btnShadow = findViewById(R.id.btn_shadow)
        btnSticky = findViewById(R.id.btn_sticky)
        btnSpacing = findViewById(R.id.btn_spacing)
        btnBackground = findViewById(R.id.btn_background)
        btnClear = findViewById(R.id.btn_clear)
    }
    
    private fun setupRecyclerView() {
        // 初始化数据
        initData()
        
        // 创建适配器
        adapter = RealRecyclerAdapter(dataList)
        
        // 设置RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        
        // 默认添加分割线装饰器
        applyDividerDecoration()
    }
    
    private fun initData() {
        // 添加Header
        dataList.add(DataItem(
            id = itemCounter++,
            title = "真正的RecyclerView Demo",
            subtitle = "ItemDecoration效果展示",
            type = ItemType.HEADER
        ))
        
        // 添加普通items
        repeat(20) { index ->
            dataList.add(DataItem(
                id = itemCounter++,
                title = "RecyclerView 列表项 ${index + 1}",
                subtitle = "这是第 ${index + 1} 个列表项的副标题",
                type = ItemType.NORMAL
            ))
        }
        
        // 添加Footer
        dataList.add(DataItem(
            id = itemCounter++,
            title = "共 ${dataList.size - 1} 个项目",
            subtitle = "",
            type = ItemType.FOOTER
        ))
    }
    
    private fun setupClickListeners() {
        btnAddItem.setOnClickListener {
            val newItem = DataItem(
                id = itemCounter++,
                title = "新添加的项目 $itemCounter",
                subtitle = "这是动态添加的列表项",
                type = ItemType.NORMAL
            )
            
            // 在Footer之前插入
            val insertPosition = dataList.size - 1
            dataList.add(insertPosition, newItem)
            adapter.notifyItemInserted(insertPosition)
            
            // 更新Footer文本
            updateFooter()
        }
        
        btnRemoveItem.setOnClickListener {
            // 删除最后一个普通item（Footer之前的item）
            val normalItems = dataList.filter { it.type == ItemType.NORMAL }
            if (normalItems.isNotEmpty()) {
                val lastNormalIndex = dataList.indexOfLast { it.type == ItemType.NORMAL }
                if (lastNormalIndex >= 0) {
                    dataList.removeAt(lastNormalIndex)
                    adapter.notifyItemRemoved(lastNormalIndex)
                    updateFooter()
                }
            }
        }
    }
    
    private fun updateFooter() {
        val footerIndex = dataList.indexOfFirst { it.type == ItemType.FOOTER }
        if (footerIndex >= 0) {
            val normalItemCount = dataList.count { it.type == ItemType.NORMAL }
            dataList[footerIndex] = dataList[footerIndex].copy(
                title = "共 $normalItemCount 个项目"
            )
            adapter.notifyItemChanged(footerIndex)
        }
    }
    
    private fun setupDecorationListeners() {
        btnDivider.setOnClickListener { applyDividerDecoration() }
        btnGrid.setOnClickListener { applyGridDecoration() }
        btnShadow.setOnClickListener { applyShadowDecoration() }
        btnSticky.setOnClickListener { applyStickyDecoration() }
        btnSpacing.setOnClickListener { applySpacingDecoration() }
        btnBackground.setOnClickListener { applyBackgroundDecoration() }
        btnClear.setOnClickListener { clearDecorations() }
    }
    
    private fun clearCurrentDecoration() {
        currentDecoration?.let {
            recyclerView.removeItemDecoration(it)
        }
    }
    
    private fun applyDividerDecoration() {
        clearCurrentDecoration()
        currentDecoration = RealDividerItemDecoration(
            this,
            dividerHeight = 2,
            dividerColor = 0xFFE0E0E0.toInt()
        )
        recyclerView.addItemDecoration(currentDecoration!!)
        tvCurrentDecoration.text = "当前效果：分割线 - 在item之间添加简单的分割线"
    }
    
    private fun applyGridDecoration() {
        clearCurrentDecoration()
        currentDecoration = RealGridItemDecoration(
            borderWidth = 3,
            borderColor = 0xFF2196F3.toInt(),
            cornerRadius = 12f
        )
        recyclerView.addItemDecoration(currentDecoration!!)
        tvCurrentDecoration.text = "当前效果：网格边框 - 为每个item添加圆角边框"
    }
    
    private fun applyShadowDecoration() {
        clearCurrentDecoration()
        currentDecoration = RealShadowItemDecoration(
            shadowRadius = 12f,
            shadowColor = 0x60000000,
            offsetX = 0f,
            offsetY = 6f,
            cornerRadius = 16f
        )
        recyclerView.addItemDecoration(currentDecoration!!)
        tvCurrentDecoration.text = "当前效果：阴影效果 - 为每个item添加阴影和圆角背景"
    }
    
    private fun applyStickyDecoration() {
        clearCurrentDecoration()
        currentDecoration = RealStickyHeaderDecoration(
            headerHeight = 80,
            headerColor = 0xFFE3F2FD.toInt(),
            textColor = 0xFF1976D2.toInt(),
            textSize = 42f
        )
        recyclerView.addItemDecoration(currentDecoration!!)
        tvCurrentDecoration.text = "当前效果：分组标题 - 每5个item显示一个分组标题"
    }
    
    private fun applySpacingDecoration() {
        clearCurrentDecoration()
        currentDecoration = RealSpacingItemDecoration(
            headerSpacing = 8,
            normalSpacing = 24,
            footerSpacing = 48
        )
        recyclerView.addItemDecoration(currentDecoration!!)
        tvCurrentDecoration.text = "当前效果：智能间距 - 根据item类型设置不同间距"
    }
    
    private fun applyBackgroundDecoration() {
        clearCurrentDecoration()
        currentDecoration = RealBackgroundItemDecoration(
            evenColor = 0xFFF3E5F5.toInt(),
            oddColor = 0xFFE8F5E8.toInt(),
            cornerRadius = 12f
        )
        recyclerView.addItemDecoration(currentDecoration!!)
        tvCurrentDecoration.text = "当前效果：交替背景 - 奇偶行显示不同的背景颜色"
    }
    
    private fun clearDecorations() {
        clearCurrentDecoration()
        currentDecoration = null
        tvCurrentDecoration.text = "当前效果：无装饰 - 清除所有ItemDecoration效果"
    }
}