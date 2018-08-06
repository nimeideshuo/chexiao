package com.sunwuyou.swymcx.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseActivity;
import com.sunwuyou.swymcx.app.MyApplication;

/**
 * Created by liupiao on
 * 2018/8/6.
 * content
 */
public class AboutActivity extends BaseActivity {

    public static int height;
    public static int width;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.act_about);
        TextView version = findViewById(R.id.version);
        version.setText(MyApplication.getInstance().getVersionName());
        DisplayMetrics v0 = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(v0);
        width = v0.widthPixels;
        height = v0.heightPixels;
    }
}
