package com.sunwuyou.swymcx.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.popupmenu.MainMenuPopup;
import com.sunwuyou.swymcx.request.ReqSupQueryDepartment;
import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.SwyUtils;
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
    @BindView(R.id.root) TableLayout root;
    private MainMenuPopup menuPopup;
    private AccountPreference ap;
    private ProgressDialog progressDialog;

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
                    toast("今天还未同步客户信息");
                    getMaxRVersion();
                }
            }
        }

    }

    @OnClick({R.id.field_fields_open, R.id.field_settleup_open, R.id.field_transfer_open, R.id.field_fields_record, R.id.field_settleup_record, R.id.field_transfer_record, R.id.fieldsale_customer, R.id.field_truckstock, R.id.field_local_goods, R.id.root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.field_fields_open:
                //销售
                break;
            case R.id.field_settleup_open:
                //结算
                break;
            case R.id.field_transfer_open:
                //调拨
                break;
            case R.id.field_fields_record:
                //我的销售
                break;
            case R.id.field_settleup_record:
                //我的结算

                break;
            case R.id.field_transfer_record:
                //我的调拨

                break;
            case R.id.fieldsale_customer:
                //客户中心

                break;
            case R.id.field_truckstock:
                //库存

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
        long rversion = Long.parseLong(new AccountPreference().getValue("max_rversion", "0"));
        getUpdata(rversion);
//        if(){
//            this.ap.setValue("basic_data_updatitme", Utils.formatDate(new Date().getTime()));
//        }
    }

    public void getUpdata(final Long rv) {
        ArrayList<ReqSynUpdateInfo> localArrayList = new ArrayList<ReqSynUpdateInfo>();
        localArrayList.add(new ReqSynUpdateInfo("log_deleterecord", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_unit", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_department", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_warehouse", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_paytype", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_account", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_pricesystem", 0L));
        localArrayList.add(new ReqSynUpdateInfo("cu_customertype", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_region", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_visitline", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_goods", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_goodsunit", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_goodsprice", 0L));
        localArrayList.add(new ReqSynUpdateInfo("sz_goodsimage", 0L));
        localArrayList.add(new ReqSynUpdateInfo("rversion", rv));
        localArrayList.add(new ReqSynUpdateInfo("pagesize", 1000L));
        HashMap<String, String> map = new HashMap<>();
        map.put("parameter", JSON.toJSONString(localArrayList));
        map.put("ischexiao", "" + true);
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                List<ReqSynUpdateInfo> departmentList = JSON.parseArray(response, ReqSynUpdateInfo.class);
                long rversion = new SwyUtils().getPagesFromUpdateInfo(departmentList, "rversion");
                if (rversion < 0) {
                    ap.setValue("basic_data_updatitme", Utils.formatDate(new Date().getTime()));
                    return;
                }
                if (rversion != rv) {
                    //执行更新

                }

            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SYNCHRONIZE_QUERYUPDATEINFO), this, map, null, null, true, 0);


    }
}
