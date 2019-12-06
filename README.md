# Gradle
[![](https://jitpack.io/v/zj565061763/statelayout.svg)](https://jitpack.io/#zj565061763/statelayout)

# Example
```xml
<com.sd.lib.statelayout.FAutoEmptyStateLayout
    android:id="@+id/view_state"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/lv_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</com.sd.lib.statelayout.FAutoEmptyStateLayout>
```

```java
public class MainActivity extends AppCompatActivity
{
    private FAutoEmptyStateLayout view_state;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_state = findViewById(R.id.view_state);

        // 设置自动空布局策略，监听ListView或者RecyclerView的内容自动展示空布局
        view_state.autoEmpty();
    }

    private void setEmptyStrategy()
    {
        ListView listView = findViewById(R.id.lv_content);
        // 如果自动策略不满足需求，可以手动设置空布局策略，也可以自定义空布局策略
        view_state.setEmptyStrategy(new AdapterViewEmptyStrategy(listView));
    }
}
```

# 配置全局参数
```xml
<resources>
    <!-- 全局配置的空数据布局名称 -->
    <string name="lib_statelayout_empty_layout"></string>

    <!-- 全局配置的错误状态布局名称 -->
    <string name="lib_statelayout_error_layout"></string>
</resources>
```