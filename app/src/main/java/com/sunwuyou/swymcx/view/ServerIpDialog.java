package com.sunwuyou.swymcx.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;

/**
 * Created by admin
 * 2018/7/14.
 * content  显示输入框dialog
 */

public class ServerIpDialog extends Dialog {

    private CallBack callBack;
    private Button mBtnOk;
    private Button mBtnCancel;
    private TextView mTitle;
    private EditText mInput;
    private Context mContext;
    private InputMethodManager manager;

    public ServerIpDialog(@NonNull Context context) {
        super(context, R.style.MyDialog_NoTitle);
        mContext = context;
        intiView();
    }

    private void intiView() {
        setContentView(R.layout.dialog_input);
        mTitle = findViewById(R.id.ed_service_title);
        mInput = findViewById(R.id.ed_service_input);
        mBtnOk = findViewById(R.id.ed_service_btn_ok);
        mBtnCancel = findViewById(R.id.ed_service_btn_cancel);
    }


    public String getmInput() {
        return mInput.getText().toString();
    }

    public void setmInput(EditText mInput) {
        this.mInput = mInput;
    }


    public ServerIpDialog showDialog(String title, String input, String btnOk, String btnCancel, final CallBack back) {
        mTitle.setText(title);
        mInput.setText(input);
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
        manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
