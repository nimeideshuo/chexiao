package com.sunwuyou.swymcx.ui.settleup;

import android.app.Activity;
import android.view.View;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.ui.TitleDialog;
import com.sunwuyou.swymcx.view.EditButtonView;

/**
 * Created by admin
 * 2018/8/5.
 * content
 */

public class SettleupReceivedDialog extends TitleDialog {

    private final EditButtonView etMoney;
    private ConfirmDo confirmDo;
    View.OnClickListener confirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Double v0 = Double.parseDouble(etMoney.getText().toString());
                if (confirmDo != null) {
                    confirmDo.received(v0);
                }
                dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public SettleupReceivedDialog(Activity activity) {
        super(activity);
        this.setView(R.layout.dia_settleup_received);
        etMoney = (EditButtonView) findView(R.id.etMoney);

        this.setConfirmButton("确定", confirmListener);
        this.setCancelButton(null);
        this.setTitleText("单据收款");
    }

    public void setConfirmDo(ConfirmDo arg1) {
        this.confirmDo = arg1;
    }

    public void setDefaultMoney(String arg2) {
        this.etMoney.setText(arg2);
    }

    public interface ConfirmDo {
        void received(double arg1);
    }
}
