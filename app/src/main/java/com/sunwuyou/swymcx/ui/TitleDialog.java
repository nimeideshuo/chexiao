package com.sunwuyou.swymcx.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.utils.TextUtils;

/**
 * Created by admin
 * 2018/8/2.
 * content
 */

public class TitleDialog extends Dialog {
    protected Activity mContext;
    protected LinearLayout contentLayout;
    private View.OnClickListener defaultListener = new View.OnClickListener() {
        public void onClick(View view) {
            dismiss();
        }
    };
    private Button btnCancel;
    private Button btnConfirm;
    private LinearLayout buttons;
    private TextView tvTitle;
    private View content;

    public TitleDialog(Activity activity) {
        super(activity, R.style.m_titledialog);
        this.mContext = activity;
        this.setContentView(R.layout.title_dialog);
        this.contentLayout = findViewById(R.id.content);
        this.buttons = this.findViewById(R.id.buttons);
        this.tvTitle = findViewById(R.id.tvTitle);
        this.btnConfirm = this.findViewById(R.id.btnConfirm);
        this.btnCancel = this.findViewById(R.id.btnCancel);
    }

    public View findView(int arg2) {
        return this.content.findViewById(arg2);
    }

    protected Activity getActivity() {
        return this.mContext;
    }

    public String getString(int arg2) {
        return this.getContext().getResources().getString(arg2);
    }

    public boolean onKeyDown(int arg2, KeyEvent arg3) {
        return arg2 == 4 || super.onKeyDown(arg2, arg3);
    }

    public void setCancelButton(View.OnClickListener arg2) {
        this.setCancelButton(null, arg2);
    }

    public void setCancelButton(String arg4, View.OnClickListener arg5) {
        Button v0 = this.btnCancel;
        if (TextUtils.isEmpty(arg4)) {
            arg4 = this.getString(R.string.cancel);
        }
        v0.setText(arg4);
        v0 = this.btnCancel;
        if (arg5 == null) {
            arg5 = this.defaultListener;
        }
        v0.setOnClickListener(arg5);
        this.btnCancel.setVisibility(View.VISIBLE);
        this.buttons.setVisibility(View.VISIBLE);
    }

    public void setConfirmButton(View.OnClickListener view) {
        this.setConfirmButton(null, view);
    }

    public void setConfirmButton(String str, View.OnClickListener arg5) {
        if (TextUtils.isEmpty(str)) {
            str = this.getString(R.string.confirm);
        }
        btnConfirm.setText(str);
        if (arg5 == null) {
            arg5 = this.defaultListener;
        }
        btnConfirm.setOnClickListener(arg5);
        this.btnConfirm.setVisibility(View.VISIBLE);
        this.buttons.setVisibility(View.VISIBLE);
    }

    public void setTitleText(int arg3) {
        this.tvTitle.setText(arg3);
        this.tvTitle.setVisibility(View.VISIBLE);
    }

    public void setTitleText(String arg3) {
        this.tvTitle.setText(arg3);
        this.tvTitle.setVisibility(View.VISIBLE);
        this.buttons.setVisibility(View.VISIBLE);
    }

    public void setView(int arg3) {
        this.setView(LayoutInflater.from(this.getContext()).inflate(arg3, null));
    }

    public void setView(View content) {
        this.content = content;
        this.contentLayout.addView(content);
    }

}
