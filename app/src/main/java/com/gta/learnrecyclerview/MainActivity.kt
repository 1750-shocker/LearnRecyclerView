package com.gta.learnrecyclerview

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gta.learnrecyclerview.adapter.DemoAdapter
import com.gta.learnrecyclerview.customlist.CustomListView
import com.gta.learnrecyclerview.decoration.DividerItemDecoration
import com.gta.learnrecyclerview.model.DataItem
import com.gta.learnrecyclerview.model.ItemType

class MainActivity : AppCompatActivity() {
    
    private lateinit var customListView: CustomListView
    private lateinit var adapter: DemoAdapter
    private lateinit var btnAddItem: Button
    private lateinit var btnRemoveItem: Button
    
    private val dataList = mutableListOf<DataItem>()
    private var itemCounter = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initViews()
        setupCustomListView()
        setupClickListeners()
    }
    
    private fun initViews() {
        customListView = findViewById(R.id.custom_list_view)
        btnAddItem = findViewById(R.id.btn_add_item)
        btnRemoveItem = findViewById(R.id.btn_remove_item)
    }
    
    private fun setupCustomListView() {
        // 初始化数据
        initData()
        
        // 创建适配器
        adapter = DemoAdapter(dataList)
        
        // 设置适配器
        customListView.setAdapter(adapter)
        
        // 添加分割线装饰器
        val dividerDecoration = DividerItemDecoration(
            dividerHeight = 4,
            dividerColor = 0xFFCCCCCC.toInt()
        )
        customListView.addItemDecoration(dividerDecoration)
    }
    
    private fun initData() {
        // 添加Header
        dataList.add(DataItem(
            id = itemCounter++,
            title = "自定义列表组件Demo",
            subtitle = "",
            type = ItemType.HEADER
        ))
        
        // 添加普通items
        repeat(20) { index ->
            dataList.add(DataItem(
                id = itemCounter++,
                title = "列表项 ${index + 1}",
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
                    adapter.removeItem(lastNormalIndex)
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
}