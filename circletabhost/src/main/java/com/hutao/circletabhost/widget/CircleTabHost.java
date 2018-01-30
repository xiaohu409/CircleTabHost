package com.hutao.circletabhost.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hutao.circletabhost.R;
import com.hutao.circletabhost.util.DrawableUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * CircleTabHost
 */
public class CircleTabHost extends LinearLayout {

    /*字体大小*/
    private int mTextSize;
    /*字体颜色*/
    private int mTextColor;
    /*选择字体颜色*/
    private int mTextSelectColor;
    /*角标字体大小*/
    private int mCornerMarkTextSize;
    /*角标字体颜色*/
    private int mCornerMarkTextColor;
    /*角标背景色*/
    private int mCornerMarkTextBgColor;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Holder> mHolderList;
    private int mCurrentIndex;
    private FragmentManager mFragmentManager;
    private int mContentId;
    private OnTabListener onTabListener;

    public CircleTabHost(Context context) {
        this(context, null);
    }

    public CircleTabHost(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTabHost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 11, getResources().getDisplayMetrics());
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleTabHost);
        mTextSize = array.getDimensionPixelSize(R.styleable.CircleTabHost_tab_text_size, mTextSize);
        mTextColor = array.getColor(R.styleable.CircleTabHost_tab_text_color, Color.BLACK);
        mTextSelectColor = array.getColor(R.styleable.CircleTabHost_tab_text_select_color, Color.BLACK);
        mCornerMarkTextSize = array.getDimensionPixelSize(R.styleable.CircleTabHost_corner_mark_text_size, mTextSize);
        mCornerMarkTextColor = array.getColor(R.styleable.CircleTabHost_corner_mark_text_color, Color.WHITE);
        mCornerMarkTextBgColor = array.getColor(R.styleable.CircleTabHost_corner_mark_text_background_color, Color.RED);
        array.recycle();
        init(context);
    }

    /**
     * 初始化布局
     */
    private void init(Context context) {
        this.mContext = context;
        //设置线性布局为水平排列
        setOrientation(LinearLayout.HORIZONTAL);
        //实例化一个布局填充工具
        mLayoutInflater = LayoutInflater.from(context);
        mHolderList = new ArrayList<>();
        mCurrentIndex = -1;
    }

    /**
     * 添加Tab到视图容器
     * @param param
     */
    public void addTab(TabParam param) {
        //为什么这样写而不是这样写：
        //LinearLayout parentView = (LinearLayout) mLayoutInflater.inflate(R.layout.tab_view, null);
        //这样写会导致tab_view 的layout属性失效
        LinearLayout parentView = (LinearLayout) mLayoutInflater.inflate(R.layout.tab_view, this, false);
        parentView.setOnClickListener(new OnTabClick(mHolderList.size(), param));
        //tab
        TextView tabView = parentView.findViewById(R.id.tab_id);
        tabView.setText(param.getTabTitle());
        tabView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        Drawable tabIco = DrawableUtil.getDrawable(mContext, param.getTabIco());
        tabView.setCompoundDrawables(null, tabIco, null, null);
        //角标
        TextView cornerMarkView = parentView.findViewById(R.id.corner_mark_id);
        cornerMarkView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCornerMarkTextSize);
        cornerMarkView.setTextColor(mCornerMarkTextColor);
        GradientDrawable gradientDrawable = (GradientDrawable) cornerMarkView.getBackground();
        gradientDrawable.setColor(mCornerMarkTextBgColor);

        Holder holder = new Holder(mHolderList.size(), param.getCls(), param.getTabTitle(), parentView);
        mHolderList.add(holder);
        //被添加的view 本身就是根view 不可以有父view 否则会报错
        addView(parentView);
    }

    /**
     * 设置Fragment manager 以及显示布局的Content id
     * @param fragmentManager
     * @param contentId
     */
    public void setup(FragmentManager fragmentManager, int contentId) {
        this.mFragmentManager = fragmentManager;
        this.mContentId = contentId;
    }

    /**
     * 在显示布局的时候显示默认Tab
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        showDefaultTab();
    }

    /**
     * 显示默认的Tab
     * 索引为0是默认的tab
     */
    private void showDefaultTab() {
        showTab(0);
    }

    /**
     * 显示指定index的Tab
     * @param index
     */
    private void showTab(int index) {
        mCurrentIndex = index;
        for (Holder holder : mHolderList) {
            if (holder.getIndex() == index) {
                TextView tabView = holder.getView().findViewById(R.id.tab_id);
                tabView.setSelected(true);
                tabView.setTextColor(mTextSelectColor);
                showFragment(holder);
            }
            else {
                TextView tabView = holder.getView().findViewById(R.id.tab_id);
                tabView.setSelected(false);
                tabView.setTextColor(mTextColor);
                hideFragment(holder);
            }
        }
    }

    /**
     * 显示Fragment
     * @param holder
     */
    private void showFragment(Holder holder) {
        if (mFragmentManager == null) {
            return;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (holder.getFragment() == null) {
            holder.fragment = Fragment.instantiate(mContext, holder.getCls().getName());
            transaction.add(mContentId, holder.fragment);
        }
        else {
            transaction.show(holder.getFragment());
        }
        transaction.commit();
    }

    /**
     * 隐藏Fragment
     * @param holder
     */
    private void hideFragment(Holder holder) {
        if (mFragmentManager == null) {
            return;
        }
        if (holder.getFragment() != null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.hide(holder.getFragment());
            transaction.commit();
        }

    }

    /**
     * Tab类
     */
    public static class TabParam {

        private int tabIco;
        private String tabTitle;
        private Class<?> cls;

        public TabParam(int tabIco, String tabTitle, Class<?> cls) {
            this.tabIco = tabIco;
            this.tabTitle = tabTitle;
            this.cls = cls;
        }

        int getTabIco() {
            return tabIco;
        }

        String getTabTitle() {
            return tabTitle;
        }

        Class<?> getCls() {
            return cls;
        }
    }

    private static class Holder {
        private int index;
        private Class<?> cls;
        private String title;
        private View view;
        private Fragment fragment;

        Holder(int index, Class<?> cls, String title, View view) {
            this.index = index;
            this.cls = cls;
            this.title = title;
            this.view = view;
        }

        int getIndex() {
            return index;
        }

        Class<?> getCls() {
            return cls;
        }

        public String getTitle() {
            return title;
        }

        View getView() {
            return view;
        }

        Fragment getFragment() {
            return fragment;
        }
    }

    private class OnTabClick implements OnClickListener {
        private int index;
        private TabParam tabParam;

        OnTabClick(int index, TabParam tabParam) {
            this.index = index;
            this.tabParam = tabParam;
        }

        @Override
        public void onClick(View v) {
            showTab(index);
            if (onTabListener != null) {
                onTabListener.onTabChange(index, tabParam);
            }
        }
    }

    public interface OnTabListener {
        void onTabChange(int index, TabParam param);
    }

    public void setOnTabListener(OnTabListener onTabListener) {
        this.onTabListener = onTabListener;
    }

    /**
     * 设置角标
     * @param index
     * @param num
     */
    public void setCornerMark(int index, String num) {
        for (Holder holder : mHolderList) {
            if (holder.getIndex() == index) {
                TextView cornerMarkView = holder.getView().findViewById(R.id.corner_mark_id);
                cornerMarkView.setText(num);
                cornerMarkView.setVisibility(TextUtils.isEmpty(num) ? GONE : VISIBLE);
                break;
            }
        }
    }
}
