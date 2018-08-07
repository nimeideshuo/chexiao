package com.immo.libcomm.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.immo.libcomm.R;

/**
 * @author Administrator
 * @content
 * @date 2017/12/27
 */

public class MyDialog02 extends Dialog {
    private Button yes;//确定按钮  
    private Button no;//取消按钮  
    private TextView messageTv;//消息提示文本  
    private String messageStr;//从外界设置的消息文本  
    private String yesStr, noStr;
    private MyDialog.onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器  
    private MyDialog.onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器  
    private boolean showYes = true, showNo = true;
    private Context context;
    private String content;

    public MyDialog02 setNoOnclickListener(String str, MyDialog.onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
        return this;
    }

    public MyDialog02 setYesOnclickListener(String str, MyDialog.onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
        return this;
    }

    public MyDialog02(Context context) {
        super(context, R.style.MyDialog);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout);
        setCanceledOnTouchOutside(true);
        initView();
        initData();
        initEvent();
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        lp.height = display.getHeight();
        getWindow().setAttributes(lp);
    }

    public void setButtonVisible(boolean yesVisible, boolean cancelVisible) {
        this.showYes = yesVisible;
        this.showNo = cancelVisible;
        if (yes != null) {
            yes.setVisibility(showYes ? View.VISIBLE : View.GONE);
        }
        if (no != null) {
            no.setVisibility(showNo ? View.VISIBLE : View.GONE);
        }
    }

    private void initEvent() {
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();

                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();

                }
            }
        });
    }

    private void initData() {

        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    private void initView() {
        yes = findViewById(R.id.btn_true);
        no = findViewById(R.id.btn_cancle);
        yes.setVisibility(showYes ? View.VISIBLE : View.GONE);
        no.setVisibility(showNo ? View.VISIBLE : View.GONE);
        messageTv = findViewById(R.id.dialogui_tv_title);
    }


    public MyDialog02 setMessage(String message) {
        messageStr = message;
        return this;
    }

    public interface onYesOnclickListener {
        void onYesClick();
    }

    public interface onNoOnclickListener {
        void onNoClick();
    }

    public static void dialogShow(final Context context, String content, String cancleTxt, String okTxt,final MyDialog.DialogBtnListener dialogBtnListener){
        final MyDialog selfDialog = new MyDialog(context);
        selfDialog.setMessage(""+content);
        selfDialog.setYesOnclickListener(okTxt, new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialogBtnListener.yesClick();
            }
        });
        selfDialog.setNoOnclickListener(cancleTxt, new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialogBtnListener.noClick();
            }
        });
        selfDialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isShowing()){
            dismiss();
        }
    }

    public interface DialogBtnListener {
        void yesClick();
        void noClick();
    }
}
