package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.utils.PDH;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class DocRemarkDialog extends Dialog {
    private Activity activity;
    private EditText etRemark;
    private FieldSale fieldSale;
    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (Boolean.parseBoolean(msg.obj.toString())) {
               dismiss();
                PDH.showSuccess("修改成功");
            } else {
                PDH.showFail("修改失败，请退出重试");
            }
        }
    };

    public DocRemarkDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
        setContentView(R.layout.dia_doc_remark);
//        this.setTitleText("单据备注");
        this.etRemark = this.findViewById(R.id.etRemark);
        this.fieldSale = new FieldSaleDAO().getFieldsale(activity.getIntent().getLongExtra("fieldsaleid", -1));
    }

    public void show() {
        super.show();
        this.fieldSale = new FieldSaleDAO().getFieldsale(this.fieldSale.getId());
        this.etRemark.setText(this.fieldSale.getRemark());
    }

}
