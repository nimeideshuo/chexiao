package com.sunwuyou.swycx.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.immo.libcomm.R;


/**
 * 作者： YaoChen
 * 日期： 2017/8/12 10:38
 * 描述： 公用头部的封装
 */
public class BaseFullHeadActivity extends BaseActivity {
    protected TextView titleTv;
    protected TextView titleRight;
    private ImageView titleBack;
    private ImageView titleMore;
    private Toolbar mToolbar;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.ctivity_basefullhead_layout);
        initBaseView();

    }

    @Override
    public View getLayoutID() {
        return null;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View.inflate(this, layoutResID, (ViewGroup) findViewById(R.id.base_content));
    }

    /**
     * 调用后 才能得到titleTv否则为空
     */
    private void initBaseView() {
        mToolbar = findViewById(R.id.base_tool_bar);
        setSupportActionBar((Toolbar) findViewById(R.id.base_tool_bar));
        titleTv = findViewById(R.id.base_toolbar_title);
        titleBack = findViewById(R.id.base_nav_back);
        titleRight = findViewById(R.id.base_nav_right);
        titleMore = findViewById(R.id.base_nav_right_img);
        ViewGroup.LayoutParams params = mToolbar.getLayoutParams();
        int statusHeight = getStatusBarHeight(this);
        System.out.println("高度为" + statusHeight);
        params.height += statusHeight;
        mToolbar.setLayoutParams(params);
        mToolbar.setPadding(mToolbar.getPaddingLeft(), mToolbar.getPaddingTop() + statusHeight,
                mToolbar.getPaddingRight(), mToolbar.getPaddingBottom());
        mToolbar.setTag(true);
        //toolbar默认有16dp的margin值,可以设置为0,或在布局中配置
        mToolbar.setContentInsetsRelative(0, 0);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏的flag
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        titleRight.setEnabled(true);
        BaseTitleClick baseTitleClick = new BaseTitleClick();
        titleBack.setOnClickListener(baseTitleClick);
        titleRight.setOnClickListener(baseTitleClick);
        titleMore.setOnClickListener(baseTitleClick);
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 由于全屏时转非全屏时界面中的布局大小不能改变,所以不能使用流行的在windows中添加一个
     * Status 高度的控件来填充内容,  直接使用toolbar设置padding效果
     *
     * @param activity
     * @param toolbar
     */
    public static void setStatusBarColor(Activity activity, Toolbar toolbar) {
        if (toolbar.getTag() == null) {
            ViewGroup.LayoutParams params = toolbar.getLayoutParams();
            int statusHeight = getStatusBarHeight(activity);
            System.out.println("高度为" + statusHeight);
            params.height += statusHeight;
            toolbar.setLayoutParams(params);
            toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusHeight,
                    toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.setTag(true);
            //toolbar默认有16dp的margin值,可以设置为0,或在布局中配置
            toolbar.setContentInsetsRelative(0, 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void setmToolbar(boolean enable, Activity activity) {
        if (!enable) {
            if (mToolbar.getVisibility() == View.VISIBLE) {
                mToolbar.setVisibility(View.VISIBLE);
                TranslateAnimation out = (TranslateAnimation) AnimationUtils.loadAnimation(activity, R.anim.down_out_top);
                mToolbar.startAnimation(out);
                setStatusBarColor(activity, mToolbar);
                mToolbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //清除非全屏的flag
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏的flag
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏的flag
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                mToolbar.setVisibility(View.GONE);
                TranslateAnimation in = (TranslateAnimation) AnimationUtils.loadAnimation(activity, R.anim.down_in_top);
                mToolbar.startAnimation(in);
                mToolbar.setVisibility(View.VISIBLE);

            }
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            TranslateAnimation out = (TranslateAnimation) AnimationUtils.loadAnimation(activity, R.anim.down_out_top);
            mToolbar.startAnimation(out);
            mToolbar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //清除非全屏的flag
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏的flag
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 返回事件
     */
    public void back() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置中间标题
     *
     * @param titleText
     */
    public void setTitle(String titleText) {
        if (titleText != null) {
            if (titleTv != null) {
                titleTv.setText(titleText);
            }
        }
    }

    /**
     * 设置返回按钮的可见性
     */
    public void setBackVisible() {
        titleBack.setVisibility(View.GONE);
    }

    /**
     * @param text
     * @param drawableRes 设置右侧的按钮，可显示文字或图片
     */
    public void setTitleRight(String text, Drawable drawableRes) {
        if (titleRight == null) {
            return;
        }
        if (text == null && drawableRes == null) {
            titleRight.setVisibility(View.GONE);
            titleMore.setVisibility(View.GONE);

        } else {


            if (text != null) {
                titleRight.setVisibility(View.VISIBLE);
                titleMore.setVisibility(View.GONE);
                final ViewGroup.LayoutParams lp = titleRight.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                titleRight.setText(text);
                titleRight.setBackgroundResource(R.color.colorTransparent);
            }
            if (drawableRes != null) {
                titleRight.setVisibility(View.GONE);
                titleMore.setVisibility(View.VISIBLE);
                titleMore.setImageDrawable(drawableRes);
            }
        }
    }

    /**
     * 标题按钮的点击事件
     */
    private class BaseTitleClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.base_nav_back) {
                onBackClick();
            } else if (id == R.id.base_nav_right) {
                onRightClick();
            } else if (id == R.id.base_nav_right_img) {
                onRightClick();

            }
        }
    }

    /**
     * 标题中右边的部分，
     */
    protected void onRightClick() {

    }

    /**
     * 返回按钮的点击事件
     */
    private void onBackClick() {
        back();
    }
}
