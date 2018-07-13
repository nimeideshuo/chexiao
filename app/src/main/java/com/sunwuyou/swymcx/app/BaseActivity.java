package com.sunwuyou.swymcx.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * 作者：
 * 日期： 2017/8/12 10:29
 * 描述： 基础类的封装
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * toast的提示的封装
     *
     * @param s 提示内容
     */
    public void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getLayoutID());
        initView();
        initData();
    }

    public abstract int getLayoutID();

    public abstract void initView();

    public abstract void initData();


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
