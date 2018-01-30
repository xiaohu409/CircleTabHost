package com.example.hutao.circletabhost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hutao.circletabhost.fragment.BlankFragment1;
import com.example.hutao.circletabhost.fragment.BlankFragment2;
import com.example.hutao.circletabhost.fragment.BlankFragment3;
import com.example.hutao.circletabhost.fragment.BlankFragment4;
import com.example.hutao.circletabhost.fragment.BlankFragment5;
import com.example.hutao.circletabhost.util.ToastUtil;
import com.hutao.circletabhost.widget.CircleTabHost;

public class MainActivity extends AppCompatActivity implements CircleTabHost.OnTabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiUI();
    }

    private void intiUI() {
        int[] tabIco = {R.drawable.home_sel, R.drawable.tfaccount_sel,
                R.drawable.discover_sel, R.drawable.cart_sel,
                R.drawable.account_sel};

        String[] tabTitle = getResources().getStringArray(R.array.tab_title);

        Class<?>[] tabFragment = {BlankFragment1.class, BlankFragment2.class,
                BlankFragment3.class, BlankFragment4.class, BlankFragment5.class};

        CircleTabHost circleTabHost = findViewById(R.id.circle_tab_id);

        for (int i = 0; i < tabTitle.length; i++) {
            CircleTabHost.TabParam tabParam = new CircleTabHost.TabParam(tabIco[i], tabTitle[i], tabFragment[i]);
            circleTabHost.addTab(tabParam);
        }

        circleTabHost.setup(getSupportFragmentManager(), R.id.content_id);
        circleTabHost.setOnTabListener(this);
        circleTabHost.setCornerMark(2, "11");
        circleTabHost.setCornerMark(3, "8");
    }

    @Override
    public void onTabChange(int index, CircleTabHost.TabParam param) {
        ToastUtil.showShort(this, "index:" + index);
    }
}
