package com.sunwuyou.swymcx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sunwuyou.swymcx.R;

/**
 * Created by admin
 * 2018/7/28.
 * content
 *             android:background="@drawable/login_panel_edit_bg"

 */

public class EditButtonView extends android.support.v7.widget.AppCompatEditText{
    public EditButtonView(Context context) {
        super(context);
    }

    public EditButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(getResources().getDrawable(R.drawable.login_panel_edit_bg));

    }

    public void setDecNum(int decNum) {
    }

    public void setMode(int mode) {
    }
}
