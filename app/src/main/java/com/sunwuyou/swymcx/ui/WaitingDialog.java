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

public class WaitingDialog extends Dialog {
    public WaitingDialog(Activity paramActivity) {
        super(paramActivity, R.style.Transparent);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dia_loading);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void setText(String paramString) {
        ((TextView) findViewById(R.id.textView)).setText(paramString);
    }

    @SuppressLint("WrongViewCast")
    public void show() {
        super.show();
        ((AnimationDrawable) findViewById(R.id.imageView).getBackground()).stop();
        ((AnimationDrawable) findViewById(R.id.imageView).getBackground()).start();
    }
}
