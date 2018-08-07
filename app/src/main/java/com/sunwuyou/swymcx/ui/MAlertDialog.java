package com.sunwuyou.swymcx.ui;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class MAlertDialog extends TitleDialog {
    private View.OnClickListener defaultClickListener;
    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
    public MAlertDialog(Activity activity) {
        super(activity);
        this.setView(R.layout.dia_alert);
        this.setConfirmButton(this.defaultClickListener);
        this.setCancelButton(this.defaultClickListener);
        this.setTitleText("提示");
    }

    public MAlertDialog(Activity activity, int i) {
        super(activity);
        this.setView(R.layout.dia_alert);
        this.setConfirmButton(this.defaultClickListener);
        this.setCancelButton(this.defaultClickListener);
        this.setTitleText("提示");
    }

    public void dismiss() {
        super.dismiss();
        if (this.cancelListener != null) {

        }
    }

    public void dismiss2() {
        super.dismiss();
    }

    public void setCancelListener(View.OnClickListener arg1) {
        this.cancelListener = arg1;
        this.setCancelButton(arg1);
    }

    public void setComfirmListener(View.OnClickListener arg1) {
        this.setConfirmButton(arg1);
    }

    public void setContentText(String arg2) {
        TextView textView = (TextView) findView(R.id.textView);
        textView.setText(arg2);
    }

    public void simpleShow(String arg1) {
        this.setContentText(arg1);
        this.show();
    }
}
