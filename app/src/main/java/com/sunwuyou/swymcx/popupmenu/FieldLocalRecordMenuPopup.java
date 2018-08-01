package com.sunwuyou.swymcx.popupmenu;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.ui.field.FieldLocalRecordActivity;
import com.sunwuyou.swymcx.ui.field.FieldSaleCustomerStatActivity;
import com.sunwuyou.swymcx.ui.field.FieldSaleGoodsStatActivity;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class FieldLocalRecordMenuPopup extends PopupWindow implements View.OnClickListener {
    private FieldLocalRecordActivity activity;
    private Button btnCustomerStat;
    private Button btnGoodsStat;
    private View root;

    public FieldLocalRecordMenuPopup(FieldLocalRecordActivity activity) {
        super(activity);
        this.activity = activity;
        this.root = LayoutInflater.from(activity).inflate(R.layout.popup_fieldlocalrecord, null);
        this.setContentView(this.root);
        this.init();
        this.setAnimationStyle(R.style.buttom_in_out);
        DisplayMetrics v1 = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(v1);
        int v2 = v1.widthPixels;
        int v0 = v1.heightPixels;
        this.setWidth(v2);
        this.setHeight(v0 / 12);
        this.setBackgroundDrawable(null);
    }

    private void init() {
        this.btnGoodsStat = this.root.findViewById(R.id.btnGoodsStat);
        this.btnCustomerStat = this.root.findViewById(R.id.btnCustomerStat);
        this.btnGoodsStat.setOnClickListener(this);
        this.btnCustomerStat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        WindowManager.LayoutParams attributes = this.activity.getWindow().getAttributes();
        attributes.alpha = 1f;
        this.activity.getWindow().setAttributes(attributes);
        switch (v.getId()) {
            case R.id.btnGoodsStat:
                this.activity.startActivity(new Intent(this.activity, FieldSaleGoodsStatActivity.class));
                break;
            case R.id.btnCustomerStat:
                this.activity.startActivity(new Intent(this.activity, FieldSaleCustomerStatActivity.class));
                break;
        }
    }
}
