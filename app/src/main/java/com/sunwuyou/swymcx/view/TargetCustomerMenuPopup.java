package com.sunwuyou.swymcx.view;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.VisitLineDAO;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.ui.CustomerSearchOnLineAct;
import com.sunwuyou.swymcx.ui.NewCustomerAddAct;
import com.sunwuyou.swymcx.ui.RegionListAct;
import com.sunwuyou.swymcx.ui.field.TargetCustomerActivity;
import com.sunwuyou.swymcx.ui.field.VisitLineListAct;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;


/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class TargetCustomerMenuPopup extends PopupWindow implements View.OnClickListener {
    private TargetCustomerActivity activity;
    private Button btnAddNew;
    private Button btnAll;
    private Button btnLineCus;
    private Button btnLoadCus;
    private Button btnRegionCus;
    private Button btnUpload;
    private View view;

    public TargetCustomerMenuPopup(TargetCustomerActivity paramTargetCustomerActivity) {
        super(paramTargetCustomerActivity);
        this.activity = paramTargetCustomerActivity;
        this.view = LayoutInflater.from(paramTargetCustomerActivity).inflate(R.layout.popup_menu_targetcustomer, null);
        setContentView(this.view);
        init();
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        paramTargetCustomerActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        int j = localDisplayMetrics.heightPixels;
        setWidth(i);
        setHeight(j / 6);
        setBackgroundDrawable(null);
    }

    private void init() {
        this.btnLoadCus = this.view.findViewById(R.id.btnLoadCus);
        this.btnLineCus = this.view.findViewById(R.id.btnLineCus);
        this.btnRegionCus = this.view.findViewById(R.id.btnRegionCus);
        this.btnAll = this.view.findViewById(R.id.btnAll);
        this.btnUpload = this.view.findViewById(R.id.btnUpload);
        this.btnAddNew = this.view.findViewById(R.id.btnAddNew);
        this.btnLoadCus.setOnClickListener(this);
        this.btnAddNew.setOnClickListener(this);
        this.btnAll.setOnClickListener(this);
        this.btnLineCus.setOnClickListener(this);
        this.btnRegionCus.setOnClickListener(this);
        this.btnUpload.setOnClickListener(this);
        if ((Utils.isDownloadCustomerByVisitLine) && (!TextUtils.isEmpty(SystemState.getObject("cu_user", User.class).getVisitLineId()))) {
            this.btnLoadCus.setVisibility(View.GONE);
            this.btnRegionCus.setVisibility(View.GONE);
            this.btnAll.setVisibility(View.GONE);
            this.view.findViewById(R.id.line1).setVisibility(View.GONE);
            this.view.findViewById(R.id.line2).setVisibility(View.GONE);
            this.view.findViewById(R.id.line3).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();
        WindowManager.LayoutParams attributes = this.activity.getWindow().getAttributes();
        attributes.alpha = 1.0F;
        this.activity.getWindow().setAttributes(attributes);
        switch (v.getId()) {
            case R.id.btnLoadCus:
                //客户
                if (!NetUtils.isConnected(this.activity)) {
                    PDH.showFail("当前无可用网络");
                    break;
                }
                this.activity.startActivityForResult(new Intent(this.activity, CustomerSearchOnLineAct.class), 2);
                break;
            case R.id.btnRegionCus:
                //地区
                if (!NetUtils.isConnected(this.activity)) {
                    PDH.showFail("当前无可用网络");
                    return;
                }
                this.activity.startActivityForResult(new Intent(this.activity, RegionListAct.class), 3);
                break;

            case R.id.btnLineCus:
                if (!NetUtils.isConnected(this.activity)) {
                    PDH.showFail("当前无可用网络");
                    return;
                }
//                if (Utils.isDownloadCustomerByVisitLine) {
                    final User paramView = SystemState.getObject("cu_user", User.class);
                    if (!TextUtils.isEmpty(paramView.getVisitLineId())) {
                        String visitLineName = new VisitLineDAO().getVisitLineName(paramView.getVisitLineId());
                        String content = "";
                        if (!visitLineName.isEmpty()) {
                            content = "是否同步线路【" + visitLineName + "】上的所有客户信息？";
                        } else {
                            content = "是否同步该线路上的客户信息？";
                        }
                        new MessageDialog(activity).showDialog("路线", content, null, null, new MessageDialog.CallBack() {
                            @Override
                            public void btnOk(View view) {
                                activity.updateCustomer(2, paramView.getVisitLineId());
                            }

                            @Override
                            public void btnCancel(View view) {

                            }
                        });
                        return;
                    }
                    this.activity.startActivityForResult(new Intent().setClass(this.activity, VisitLineListAct.class), 1);
//                }
                break;
            case R.id.btnAddNew:
                this.activity.startActivityForResult(new Intent(activity, NewCustomerAddAct.class), 0);
                break;
            case R.id.btnUpload:
                if (!NetUtils.isConnected(this.activity)) {
                    PDH.showFail("当前无可用网络");
                    return;
                }
                this.activity.updateCustomer(4, null);
                break;
            case R.id.btnAll:
                if (!NetUtils.isConnected(this.activity)) {
                    PDH.showFail("当前无可用网络");
                    return;
                }
                new MessageDialog(activity).showDialog("提示", "添加所有客户，是否继续?", null, null, new MessageDialog.CallBack() {
                    @Override
                    public void btnOk(View view) {
                        activity.updateCustomer(0, null);
                    }

                    @Override
                    public void btnCancel(View view) {

                    }
                }).show();
                break;
        }
    }
}
