package com.github.xiaohu409.demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.xiaohu409.demo.R;
import com.github.xiaohu409.demo.fragment.BlankFragment1;
import com.github.xiaohu409.demo.fragment.BlankFragment2;
import com.github.xiaohu409.demo.fragment.BlankFragment3;
import com.github.xiaohu409.demo.fragment.BlankFragment4;
import com.github.xiaohu409.demo.fragment.BlankFragment5;
import com.github.xiaohu409.circletabhost.widget.CircleTabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiUI();
    }

    private void intiUI() {
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
    }

}
