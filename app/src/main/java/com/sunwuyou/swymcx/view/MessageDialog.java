package com.sunwuyou.swymcx.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;

/**
 * Created by admin
 * 2018/7/14.
 * content  显示输入框dialog
 */

public class MessageDialog extends Dialog {

    private CallBack callBack;
    private Button mBtnOk;
    private Button mBtnCancel;
    private TextView mTitle;
    private TextView mCoutent;

    public MessageDialog(@NonNull Context context) {
        super(context, R.style.MyDialog_NoTitle);
        intiView();
    }

    private void intiView() {
        setContentView(R.layout.dialog_message);
        mTitle = findViewById(R.id.ed_message_title);
        mCoutent = findViewById(R.id.ed_message_coutent);
        mBtnOk = findViewById(R.id.ed_message_btn_ok);
        mBtnCancel = findViewById(R.id.ed_message_btn_cancel);
    }

    public MessageDialog showDialog(String title, String input, String btnOk, String btnCancel, final CallBack back) {
        mTitle.setText(title);
        mCoutent.setText(input);
        if (!TextUtils.isEmpty(btnOk)) {
            mBtnOk.setText(btnOk);
        }
        if (!TextUtils.isEmpty(btnCancel)) {
            mBtnCancel.setText(btnCancel);
        }
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) {
                    back.btnOk(v);
                }
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (back != null) back.btnCancel(v);
            }
        });
        show();
        return this;
    }

    public MessageDialog showDialog() {
        show();
        return this;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void btnOk(View view);

        void btnCancel(View view);
    }
}
