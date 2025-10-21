# 自制列表组件 (CustomListView)

这是一个模仿RecyclerView核心功能的自制列表组件，实现了以下特性：

## 核心功能

### 1. 复用机制 (ViewHolder Recycling)
- **ViewHolder缓存池**: 按ViewType分类缓存ViewHolder，避免重复创建
- **智能复用**: 当ViewHolder不再需要时自动回收到缓存池
- **内存优化**: 限制缓存池大小，防止内存泄漏

### 2. ItemDecoration机制
- **装饰器接口**: 提供`ItemDecoration`抽象类
- **绘制支持**: 支持`onDraw()`和`onDrawOver()`方法
- **偏移设置**: 通过`getItemOffsets()`设置item间距
- **示例实现**: `DividerItemDecoration`分割线装饰器

### 3. ViewType支持
- **多类型布局**: 支持不同ViewType显示不同布局
- **类型识别**: 通过`getItemViewType()`方法识别类型
- **独立缓存**: 不同ViewType使用独立的缓存池

### 4. 数据更新通知
- **全量刷新**: `notifyDataSetChanged()`
- **单项更新**: `notifyItemChanged(position)`
- **插入通知**: `notifyItemInserted(position)`
- **删除通知**: `notifyItemRemoved(position)`

## 组件架构

```
CustomListView
├── CustomAdapter<VH : ViewHolder>     # 适配器基类
├── ViewHolder                         # ViewHolder基类
├── ItemDecoration                     # 装饰器基类
└── DividerItemDecoration             # 分割线装饰器实现
```

## 使用示例

### 1. 创建数据模型
```kotlin
data class DataItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val type: ItemType = ItemType.NORMAL
)

enum class ItemType {
    HEADER, NORMAL, FOOTER
}
```

### 2. 实现适配器
```kotlin
class DemoAdapter(private var dataList: MutableList<DataItem>) : CustomAdapter<DemoAdapter.BaseViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        // 根据viewType创建不同的ViewHolder
    }
    
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // 绑定数据到ViewHolder
    }
    
    override fun getItemCount(): Int = dataList.size
    
    override fun getItemViewType(position: Int): Int {
        // 返回对应位置的ViewType
    }
}
```

### 3. 设置列表
```kotlin
val customListView = findViewById<CustomListView>(R.id.custom_list_view)
val adapter = DemoAdapter(dataList)

// 设置适配器
customListView.setAdapter(adapter)

// 添加分割线
val dividerDecoration = DividerItemDecoration(
    dividerHeight = 4,
    dividerColor = 0xFFCCCCCC.toInt()
)
customListView.addItemDecoration(dividerDecoration)
```

## Demo功能

当前Demo展示了以下功能：

1. **多种ViewType**: Header、Normal、Footer三种类型
2. **动态添加**: 点击"添加Item"按钮动态添加列表项
3. **动态删除**: 点击"删除Item"按钮删除最后一个普通项
4. **分割线装饰**: 使用`DividerItemDecoration`添加分割线
5. **数据更新**: 实时更新Footer显示的项目数量

## 技术特点

- **简化实现**: 相比RecyclerView，去除了复杂的布局管理器
- **核心功能**: 保留了最重要的复用、装饰、多类型支持
- **易于理解**: 代码结构清晰，便于学习RecyclerView原理
- **可扩展**: 提供了良好的扩展接口，可以添加更多功能

## 运行项目

1. 在Android Studio中打开项目
2. 连接Android设备或启动模拟器
3. 点击运行按钮
4. 在应用中体验自制列表组件的功能