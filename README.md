# CircleTabHost
[![](https://jitpack.io/v/xiaohu409/CircleTabHost.svg)](https://jitpack.io/#xiaohu409/CircleTabHost)

### 概述
这是一个带数字角标的FragmentTabHost库

效果图如下：

![效果图](device-2018-01-30-224828.png)

### 使用教程
1.在module的build.gradle引用类库
```groovy 
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')

    implementation project(':circletabhost')
}
```
或
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    implementation 'com.github.xiaohu409:CircleTabHost:1.0'
}
```

2.在xml布局文件里使用CircleTabHost控件
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <FrameLayout
        android:id="@+id/content_id"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="@android:color/darker_gray"/>

    <com.hutao.circletabhost.widget.CircleTabHost
        android:id="@+id/circle_tab_id"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tab_text_size="11dp"
        app:tab_text_color="#5D646F"
        app:tab_text_select_color="#FF5001" />
</LinearLayout>
```
3.在代码里实例化控件并添加Tab，设置角标
```java
//图标
int[] tabIco = {R.drawable.home_sel, R.drawable.tfaccount_sel,
        R.drawable.discover_sel, R.drawable.cart_sel,
        R.drawable.account_sel};
//文本
String[] tabTitle = getResources().getStringArray(R.array.tab_title);
//Fragment
Class<?>[] tabFragment = {BlankFragment1.class, BlankFragment2.class,
        BlankFragment3.class, BlankFragment4.class, BlankFragment5.class};
//实例化TabHost
CircleTabHost circleTabHost = findViewById(R.id.circle_tab_id);
for (int i = 0; i < tabTitle.length; i++) {
    //实例化Tab
    CircleTabHost.TabParam tabParam = new CircleTabHost.TabParam(tabIco[i], tabTitle[i], tabFragment[i]);
    //添加Tab
    circleTabHost.addTab(tabParam);
}
//配置FragmentManager和Content id
circleTabHost.setup(getSupportFragmentManager(), R.id.content_id);
//回调方法
circleTabHost.setOnTabListener(new CircleTabHost.OnTabListener() {
    @Override
    public void onTabChange(int index, CircleTabHost.TabParam param) {

    }
});
//设置角标
circleTabHost.setCornerMark(2, "11");
circleTabHost.setCornerMark(3, "8");
```

## Licens
    Copyright 2018 xiaohu409

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
