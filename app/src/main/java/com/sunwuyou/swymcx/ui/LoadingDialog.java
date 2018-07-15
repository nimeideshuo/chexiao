package com.sunwuyou.swymcx.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class LoadingDialog extends Dialog {
    public TextView textView;

    public LoadingDialog(Activity activity) {
        super(activity, R.style.Transparent);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dia_loading);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void setText(String strText) {
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(strText);
    }

    @SuppressLint("WrongViewCast")
    public void show(String strText) {
        super.show();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(strText);
        ((AnimationDrawable) findViewById(R.id.imageView).getBackground()).stop();
        ((AnimationDrawable) findViewById(R.id.imageView).getBackground()).start();
    }
}
