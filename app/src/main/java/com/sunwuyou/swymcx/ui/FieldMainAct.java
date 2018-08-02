package com.sunwuyou.swymcx.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.popupmenu.MainMenuPopup;
import com.sunwuyou.swymcx.request.ReqSupQueryDepartment;
import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;
import com.sunwuyou.swymcx.service.ServiceSynchronize;
import com.sunwuyou.swymcx.ui.field.FieldDocOpenAct;
import com.sunwuyou.swymcx.ui.field.FieldLocalRecordActivity;
import com.sunwuyou.swymcx.ui.field.TargetCustomerActivity;
import com.sunwuyou.swymcx.ui.field.TruckStockActivity;
import com.sunwuyou.swymcx.ui.settleup.SettleupOpenAct;
import com.sunwuyou.swymcx.ui.transfer.TransferLocalRecordActivity;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.SwyUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.MessageDialog;
import com.sunwuyou.swymcx.view.SelectDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class FieldMainAct extends BaseHeadActivity {
    @BindView(R.id.root)
    TableLayout root;
    private MainMenuPopup menuPopup;
    private AccountPreference ap;
    private ProgressDialog progressDialog;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    progressDialog.cancel();
                    toast("同步成功");
                    break;
                case 2:
                    toast("同步失败，请重试");
                    break;
            }
        }

        ;
    };
    // 数据更新
    @SuppressLint("HandlerLeak")
    private Handler handlerProgress = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case -1:
                    progressDialog.setProgress(0);
                    progressDialog.setMessage("数据同步中");
                    progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
                    progressDialog.show();
                    break;
                case -2:
                    progressDialog.setProgress(0);
                    progressDialog.setMessage("商品图片同步中");
                    progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
                    progressDialog.show();
                    break;
                case -3:
                    progressDialog.cancel();
                    break;
            }
            progressDialog.setProgress(msg.what);
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_field_main;
    }

    @Override
    public void initView() {
        setBackVisibility(false);
        setTitleRight("菜单", null);
        setTitle("競商勿忧车销");
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (this.menuPopup == null) {
            menuPopup = new MainMenuPopup(this);
        }
        this.menuPopup.showAtLocation(this.root, 80, 0, 0);
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.alpha = 0.8F;
        getWindow().setAttributes(localLayoutParams);
    }

    @Override
    public void initData() {
        ap = new AccountPreference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(1);
        progressDialog.setCancelable(false);
        if (SystemState.getDepartment() == null) {
            queryDepartmentNet();
        } else {
            if (NetUtils.isConnected(this)) {
                String time = Utils.formatDate(this.ap.getValue("customer_data_updateime", "1990-01-01 00:00:00"), "yyyy-MM-dd");
                if (!Utils.formatDate(new Date().getTime(), "yyyy-MM-dd").equals(time)) {
                    PDH.show(this, new PDH.ProgressCallBack() {
                        @Override
                        public void action() {
                            getMaxRVersion();
                        }
                    });
                }
            }
        }

    }

    @OnClick({R.id.field_fields_open, R.id.field_settleup_open, R.id.field_transfer_open, R.id.field_fields_record, R.id.field_settleup_record, R.id.field_transfer_record, R.id.fieldsale_customer, R.id.field_truckstock, R.id.field_local_goods, R.id.root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.field_fields_open:
                //销售
                if (SystemState.getWarehouse() == null) {
                    toast("请先设置车销仓库");
                    return;
                }
                if (new GoodsDAO().getTruckGoodsCount() == 0) {
                    toast("请先装车");
                    return;
                }
                startActivity(new Intent(this, FieldDocOpenAct.class));
                break;
            case R.id.field_settleup_open:
                //结算
                break;
            case R.id.field_transfer_open:
                //调拨 TODO 未写完
                startActivity(new Intent(this, TransferLocalRecordActivity.class));
                break;
            case R.id.field_fields_record:
                //我的销售  TODO 未写完
                startActivity(new Intent(this, FieldLocalRecordActivity.class));
                break;
            case R.id.field_settleup_record:
                //我的结算
//                startActivity(new Intent(this, SettleupOpenAct.class));
                startActivity(new Intent(this,  SettletupActivity.class));
                break;
            case R.id.field_transfer_record:
                //我的调拨
                startActivity(new Intent(this, TransferLocalRecordActivity.class));
                break;
            case R.id.fieldsale_customer:
                //客户中心
                startActivity(new Intent(this, TargetCustomerActivity.class));
                break;
            case R.id.field_truckstock:
                if (SystemState.getWarehouse() == null) {
                    toast("请选择仓库");
                    return;
                }
                //库存
                startActivity(new Intent(this, TruckStockActivity.class));
                break;
            case R.id.field_local_goods:
                //产品手册

                break;
            case R.id.root:
                if ((this.menuPopup != null) && (this.menuPopup.isShowing())) {
                    this.menuPopup.dismiss();
                    WindowManager.LayoutParams attributes = getWindow().getAttributes();
                    attributes.alpha = 1.0F;
                    getWindow().setAttributes(attributes);
                }
                break;
        }
    }

    //查询部门信息
    private void queryDepartmentNet() {
        HashMap<String, String> map = new HashMap<>();
        map.put("parameter", JSON.toJSONString(new ReqSupQueryDepartment(false, null)));
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                final List<Department> departmentList = JSON.parseArray(response, Department.class);
                ArrayList<String> listData = new ArrayList<>();
                for (Department department : departmentList) {
                    listData.add(department.getDname());
                }
                new SelectDialog(FieldMainAct.this, "部门选择", listData).onItemClickListener(new SelectDialog.onItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        SystemState.setValue("department", JSON.toJSONString(departmentList.get(position)));
                        PDH.show(FieldMainAct.this, new PDH.ProgressCallBack() {
                            @Override
                            public void action() {
                                getMaxRVersion();
                            }
                        });
                    }
                }).setCancelables(false).showDialog();

            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SUPPORT_QUERYDEPARTMENT), this, map, null, null, true, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((this.menuPopup != null) && (this.menuPopup.isShowing())) {
            this.menuPopup.dismiss();
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.alpha = 1.0F;
            getWindow().setAttributes(attributes);
            return true;
        }


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new MessageDialog(this).showDialog("提示", "确定退出当前程序?", null, null, new MessageDialog.CallBack() {
                @Override
                public void btnOk(View view) {
                    finish();
                }

                @Override
                public void btnCancel(View view) {

                }
            });
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void getMaxRVersion() {
        long max_rversion = Long.parseLong(new AccountPreference().getValue("max_rversion", "0"));
        List<ReqSynUpdateInfo> listReqSyn = new ServiceSynchronize().syn_QueryUpdateInfo(max_rversion);
        if (listReqSyn != null) {
            long rversion = new SwyUtils().getPagesFromUpdateInfo(listReqSyn, "rversion");
            if (rversion >= 0) {
                ap.setValue("basic_data_updatitme", Utils.formatDate(new Date().getTime()));
                if (rversion != max_rversion) {
                    if (new UpdateUtils().executeUpdate(this.handlerProgress, listReqSyn, max_rversion)) {
                        ap.setValue("max_rversion", String.valueOf(rversion));
                        handler.sendEmptyMessage(0);
                    } else {
                        handler.sendEmptyMessage(2);
                    }
                    handlerProgress.sendEmptyMessage(-3);
                }
            }

        }
    }
}
