package com.sunwuyou.swymcx.ui.center;

import android.content.Intent;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.print.BTPrinter;
import com.sunwuyou.swymcx.ui.PrefsFragment;
import com.sunwuyou.swymcx.utils.TextUtils;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class SettingActivity extends BaseHeadActivity {

    private PrefsFragment pref;
    private AccountPreference ap;

    @Override
    public int getLayoutID() {
        return R.layout.act_setting_top;
    }

    @Override
    public void initView() {
        pref = new PrefsFragment();
        getFragmentManager().beginTransaction().replace(R.id.base_fragment, pref).commit();
        ap = new AccountPreference();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            BTPrinter v0 = this.ap.getPrinter();
            if (v0 != null) {
                this.pref.getMyPreference(R.string.default_printer).setSummary(String.valueOf(v0.getName()) + "[" + v0.getAddress() + "]");
            } else {
                this.pref.getMyPreference(R.string.default_printer).setSummary("无打印机");
            }
        }
    }

    protected void onResume() {
        super.onResume();
        if(this.pref == null) {
            this.pref = new PrefsFragment();
        }

        this.pref.getCustomerPre().setSummary(TextUtils.setTextStyle("上次同步时间：", this.ap.getValue("customer_data_updateime", "未同步")));
    }

    public void setActionBarText() {
        setTitle("设置");
    }

    //    @BindView(R.id.setting_select_department)
    //    TextView settingSelectDepartment;
    //    @BindView(R.id.setting_select_warehouse)
    //    TextView settingSelectWarehouse;
    //    @BindView(R.id.setting_select_customer)
    //    TextView settingSelectCustomer;
    //    @BindView(R.id.setting_select_goods)
    //    TextView settingSelectGoods;
    //    @BindView(R.id.setting_select_goods_all)
    //    TextView settingSelectGoodsAll;
    //    @BindView(R.id.setting_net)
    //    TextView settingNet;
    //    @BindView(R.id.setting_goods_sort)
    //    TextView settingGoodsSort;
    //    private AccountPreference ap;
    //    private int departmentposition;
    //    private int warehouseposition;
    //    private ProgressDialog progressDialog;
    //    @SuppressLint("HandlerLeak")
    //    Handler handler = new Handler() {
    //        public void handleMessage(android.os.Message msg) {
    //            switch (msg.what) {
    //                case 0:
    //                    progressDialog.cancel();
    //                    toast("同步成功");
    //                    break;
    //                case 2:
    //                    toast("同步失败，请重试");
    //                    break;
    //            }
    //        }
    //
    //        ;
    //    };
    //    // 数据更新
    //    @SuppressLint("HandlerLeak")
    //    private Handler handlerProgress = new Handler() {
    //        public void handleMessage(android.os.Message msg) {
    //            switch (msg.what) {
    //
    //                case -1:
    //                    progressDialog.setProgress(0);
    //                    progressDialog.setMessage("数据同步中");
    //                    progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
    //                    progressDialog.show();
    //                    break;
    //                case -2:
    //                    progressDialog.setProgress(0);
    //                    progressDialog.setMessage("商品图片同步中");
    //                    progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
    //                    progressDialog.show();
    //                    break;
    //                case -3:
    //                    progressDialog.cancel();
    //                    break;
    //            }
    //            progressDialog.setProgress(msg.what);
    //        }
    //    };
    //
    //    @Override
    //    public int getLayoutID() {
    //        return R.layout.act_setting;
    //    }
    //
    //    @Override
    //    public void initView() {
    //        setTitle("设置");
    //    }
    //
    //    @Override
    //    public void initData() {
    //        ap = new AccountPreference();
    //
    //        Department department = SystemState.getDepartment();
    //        if (department != null) {
    //            settingSelectDepartment.setText(department.getDname());
    //        }
    //        Warehouse warehouse = SystemState.getWarehouse();
    //        if (warehouse != null) {
    //            settingSelectWarehouse.setText(warehouse.getName());
    //        }
    //        progressDialog = new ProgressDialog(this);
    //        progressDialog.setProgressStyle(1);
    //        progressDialog.setCancelable(false);
    //    }
    //
    //    @OnClick({R.id.setting_about_app, R.id.setting_clear, R.id.setting_detault_updata, R.id.setting_select_department_ll, R.id.setting_select_warehouse_ll, R.id.setting_select_customer_ll, R.id.setting_select_goods_ll, R.id.setting_select_goods_all_ll, R.id.setting_net_ll, R.id.setting_goods_sort_ll})
    //    public void onViewClicked(View view) {
    //        switch (view.getId()) {
    //            case R.id.setting_detault_updata:
    //                //同步基本数据
    //                getMaxRVersion();
    //                break;
    //            case R.id.setting_clear:
    //                //清空数据
    //                clearDataBase();
    //                //                DialogUIUtils.showMdAlert(this, "清除数据", "你确定用户和基本清除数据?", new DialogUIListener() {
    //                //                    @Override
    //                //                    public void onPositive() {
    //                //                        SystemDBHelper.conn().clearAllUser();
    //                //                        ap.setValue("max_rversion", "0");
    //                //                        toast("清除完毕!");
    //                //                    }
    //                //
    //                //                    @Override
    //                //                    public void onNegative() {
    //                //
    //                //                    }
    //                //                }).show();
    //                break;
    //            case R.id.setting_about_app:
    //                toast("点击");
    //                break;
    //            case R.id.setting_select_department_ll:
    //                setDepartment();
    //                break;
    //            case R.id.setting_select_warehouse_ll:
    //                setWarehouse();
    //                break;
    //            case R.id.setting_select_customer_ll:
    //
    //                break;
    //            case R.id.setting_select_goods_ll:
    //                break;
    //            case R.id.setting_select_goods_all_ll:
    //                break;
    //            case R.id.setting_net_ll:
    //                break;
    //            case R.id.setting_goods_sort_ll:
    //                break;
    //        }
    //    }
    //
    //    public void getMaxRVersion() {
    //        long max_rversion = Long.parseLong(new AccountPreference().getValue("max_rversion", "0"));
    //        List<ReqSynUpdateInfo> listReqSyn = new ServiceSynchronize().syn_QueryUpdateInfo(max_rversion);
    //        if (listReqSyn != null) {
    //            long rversion = new SwyUtils().getPagesFromUpdateInfo(listReqSyn, "rversion");
    //            if (rversion >= 0) {
    //                ap.setValue("basic_data_updatitme", Utils.formatDate(new Date().getTime()));
    //                if (rversion != max_rversion) {
    //                    if (new UpdateUtils().executeUpdate(this.handlerProgress, listReqSyn, max_rversion)) {
    //                        ap.setValue("max_rversion", String.valueOf(rversion));
    //                        handler.sendEmptyMessage(0);
    //                    } else {
    //                        handler.sendEmptyMessage(2);
    //                    }
    //                    handlerProgress.sendEmptyMessage(-3);
    //                }
    //            }
    //
    //        }
    //    }
    //
    //    private void clearDataBase() {
    //        ClearLocalDataDialog v0 = new ClearLocalDataDialog(this);
    //        v0.setEmptyDo(new EmptyDo(v0) {
    //            public void doAction() {
    //                if (this.val$clearLocalDataDialog.isChecked()) {
    //                    PrefsFragment.this.ap.setValue("basic_data_updatitme", "未同步");
    //                    PrefsFragment.this.ap.setValue("customer_data_updateime", "未同步");
    //                    PrefsFragment.this.ap.setValue("max_rversion", "0");
    //                } else {
    //                    PrefsFragment.this.ap.setValue("customer_data_updateime", "未同步");
    //                }
    //
    //                PrefsFragment.this.getMyPreference(2131361832).setSummary(TextUtils.setTextStyle("上次同步时间：", PrefsFragment.this.ap.getValue("basic_data_updatitme", "未同步")));
    //                PrefsFragment.this.getMyPreference(2131361833).setSummary(TextUtils.setTextStyle("上次同步时间：", PrefsFragment.this.ap.getValue("customer_data_updateime", "未同步")));
    //            }
    //        });
    //        v0.show();
    //    }
    //
    //    private void setWarehouse() {
    //        warehouseposition = -1;
    //        final List<Warehouse> listWarehouse = new WarehouseDAO().getAllWarehouses(true);
    //        String[] v2 = new String[listWarehouse.size()];
    //        String v3 = "";
    //        if (SystemState.getWarehouse() != null) {
    //            v3 = SystemState.getWarehouse().getId();
    //        }
    //        for (int i = 0; i < listWarehouse.size(); i++) {
    //            v2[i] = listWarehouse.get(i).getName();
    //            if (listWarehouse.get(i).getId().equals(v3)) {
    //                warehouseposition = i;
    //            }
    //        }
    //        AlertDialog.Builder v0 = new AlertDialog.Builder(this);
    //        v0.setTitle("默认仓库");
    //        v0.setSingleChoiceItems(v2, warehouseposition, new DialogInterface.OnClickListener() {
    //            public void onClick(DialogInterface dialog, int which) {
    //                warehouseposition = which;
    //            }
    //        });
    //        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    //
    //            @Override
    //            public void onClick(DialogInterface dialog, int which) {
    //                dialog.dismiss();
    //                if (warehouseposition >= 0) {
    //                    Warehouse warehouse = listWarehouse.get(warehouseposition);
    //                    SystemState.saveObject("warehouse", warehouse);
    //                    settingSelectWarehouse.setText(warehouse.getName());
    //                }
    //            }
    //        });
    //        v0.show();
    //
    //
    //    }
    //
    //    private void setDepartment() {
    //        departmentposition = -1;
    //        final List<Department> listDepartments = new DepartmentDAO().getAllDepartment();
    //        String departmentId = SystemState.getDepartment().getDid();
    //        String[] departments = new String[listDepartments.size()];
    //        for (int i = 0; i < listDepartments.size(); ++i) {
    //            departments[i] = listDepartments.get(i).getDname();
    //            if (listDepartments.get(i).getDid().equals(departmentId)) {
    //                departmentposition = i;
    //            }
    //        }
    //        AlertDialog.Builder departmentBuilder = new AlertDialog.Builder(this);
    //        departmentBuilder.setTitle("工作部门");
    //        departmentBuilder.setSingleChoiceItems(departments, departmentposition, new DialogInterface.OnClickListener() {
    //            public void onClick(DialogInterface dialog, int which) {
    //                departmentposition = which;
    //            }
    //        });
    //        departmentBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    //
    //            @Override
    //            public void onClick(DialogInterface dialog, int which) {
    //                dialog.dismiss();
    //                if (departmentposition >= 0) {
    //                    Department department = listDepartments.get(departmentposition);
    //                    SystemState.saveObject("department", department);
    //                    settingSelectDepartment.setText(department.getDname());
    //                }
    //            }
    //        });
    //        departmentBuilder.show();
    //    }

}
