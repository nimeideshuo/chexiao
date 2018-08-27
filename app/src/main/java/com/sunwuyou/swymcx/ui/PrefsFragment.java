package com.sunwuyou.swymcx.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.View;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.DepartmentDAO;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.PricesystemDAO;
import com.sunwuyou.swymcx.dao.TransferDocDAO;
import com.sunwuyou.swymcx.dao.WarehouseDAO;
import com.sunwuyou.swymcx.in.EmptyDo;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.model.Warehouse;
import com.sunwuyou.swymcx.print.BTPrintHelper;
import com.sunwuyou.swymcx.print.BTPrinter;
import com.sunwuyou.swymcx.print.BTdeviceListAct;
import com.sunwuyou.swymcx.print.PrintData;
import com.sunwuyou.swymcx.print.PrintMode;
import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;
import com.sunwuyou.swymcx.service.ServiceSynchronize;
import com.sunwuyou.swymcx.utils.InfoDialog;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.SwyUtils;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class PrefsFragment extends PreferenceFragment {
    String[] orders;
    private AccountPreference ap;
    private int departmentposition;
    private int itemorderposition;
    private int warehouseposition;
    @SuppressLint("HandlerLeak")
    private Handler handlerUpdateCustomer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                PDH.showSuccess(msg.obj.toString());
                PrefsFragment.this.getMyPreference(R.string.load_customer_data).setSummary(TextUtils.setTextStyle(PrefsFragment.this.getResources().getString(R.string.last_load_time), PrefsFragment.this.ap.getValue("customer_data_updateime", "未同步")));
            } else if (msg.what == 1) {
                InfoDialog.showError(PrefsFragment.this.getActivity(), msg.obj.toString());
            }

        }
    };
    private ProgressDialog progressDialog;
    @SuppressLint("HandlerLeak")
    private Handler handlerUpdate = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                PDH.showSuccess("同步成功");
                PrefsFragment.this.getMyPreference(R.string.load_basic_data).setSummary(TextUtils.setTextStyle("上次同步时间：", ap.getValue("basic_data_updatitme", "未同步")));
                if (new PricesystemDAO().getCount() == 0) {
                    PDH.showMessage("无可用的车销价格体系");
                }
            } else if (msg.what == 2) {
                InfoDialog.showError(PrefsFragment.this.getActivity(), "同步失败，请重试");
            } else if (msg.what == -1) {
                MyApplication.getInstance().exit();
            }

        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handlerProgress = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                PrefsFragment.this.progressDialog.setProgress(0);
                PrefsFragment.this.progressDialog.setMessage("数据同步中");
                PrefsFragment.this.progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
                PrefsFragment.this.progressDialog.show();
            } else if (msg.what == -2) {
                PrefsFragment.this.progressDialog.setProgress(0);
                PrefsFragment.this.progressDialog.setMessage("商品图片同步中");
                PrefsFragment.this.progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
                PrefsFragment.this.progressDialog.show();
            } else if (msg.what == -3) {
                PrefsFragment.this.progressDialog.cancel();
            } else {
                PrefsFragment.this.progressDialog.setProgress(msg.what);
            }
        }
    };
    private Preference.OnPreferenceClickListener preferenceClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference paramAnonymousPreference) {
            if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.default_printer))) {
                printsetting();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.clear_database))) {
                clearDataBase();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.printer_model))) {
                setPrinterModel();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.print_mode))) {
                //                startActivity(new Intent(PrefsFragment.this.getActivity(), PrintModeEditAvtivity.class));
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.clearlastzero))) {
                setClearLastZero();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.minustuihuo))) {
                setMinusTuiHuo();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.printbarcode))) {
                setPrintBarCode();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.load_basic_data))) {
                loadData();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.load_customer_data))) {
                updateCustomer();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.goods_check_select))) {
                goodsCheckSet();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.customer_check_select))) {
                customerCheckSet();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.work_department))) {
                selectDepartment();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.work_warehouse))) {
                if (new FieldSaleDAO().getNotUploadCount() > 0) {
                    PDH.showMessage("存在未上传的销售单，不能切换车销仓库");
                } else if (new TransferDocDAO().getNotUploadCount() > 0) {
                    PDH.showMessage("存在未上传的调拨单，不能切换车销仓库");
                } else if (new GoodsDAO().getTruckGoodsCount() > 0) {
                    PDH.showMessage("请先执行卸车，再更改车销仓库");
                } else {
                    selectWarehouse();
                }
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.instruckment))) {
                startActivity(new Intent(PrefsFragment.this.getActivity(), AboutActivity.class));
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.net_setting))) {
                netSet();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.item_order))) {
                docItemOrder();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.goods_result_select))) {
                setGoodsSearch();
            } else if (paramAnonymousPreference.getKey().equals(PrefsFragment.this.getString(R.string.goods_select_more_add))) {
                goodsSelectMore();
            }
            return false;
        }
    };

    public PrefsFragment() {
        super();
        this.departmentposition = 0;
        this.warehouseposition = 0;
        this.itemorderposition = 0;
        this.orders = new String[]{"默认", "编号", "名称", "条码", "类别"};
    }

    private void clearDataBase() {
        final ClearLocalDataDialog clearLocalDataDialogis = new ClearLocalDataDialog(getActivity());
        clearLocalDataDialogis.setEmptyDo(new EmptyDo() {
            public void doAction() {
                if (clearLocalDataDialogis.isChecked()) {
                    ap.setValue("basic_data_updatitme", "未同步");
                    ap.setValue("customer_data_updateime", "未同步");
                    ap.setValue("max_rversion", "0");
                } else {
                    ap.setValue("customer_data_updateime", "未同步");
                }

                getMyPreference(R.string.load_basic_data).setSummary(TextUtils.setTextStyle("上次同步时间：", PrefsFragment.this.ap.getValue("basic_data_updatitme", "未同步")));
                getMyPreference(R.string.load_customer_data).setSummary(TextUtils.setTextStyle("上次同步时间：", PrefsFragment.this.ap.getValue("customer_data_updateime", "未同步")));
            }
        });
        clearLocalDataDialogis.show();
    }

    private void goodsCheckSet() {
        final boolean[] choice = new boolean[4];
        String v5 = PrefsFragment.this.ap.getValue("goods_check_select");
        if (!TextUtils.isEmptyS(v5)) {
            String[] v4 = v5.split(",");
            for (int i = 0; i < v4.length; i++) {
                for (int j = 0; j < SystemState.goods_select_keys.length; j++) {
                    if (v4[i].equals(SystemState.goods_select_keys[j])) {
                        choice[j] = true;
                    }
                }
            }
        } else {
            choice[0] = true;
            choice[1] = true;
            choice[2] = true;
            choice[3] = true;
        }

        AlertDialog.Builder v0 = new AlertDialog.Builder(getActivity());
        v0.setTitle("商品检索方式");
        v0.setMultiChoiceItems(SystemState.goods_select_items, choice, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                choice[which] = isChecked;
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg11, int arg12) {
                StringBuilder v3 = new StringBuilder();
                StringBuilder v2 = new StringBuilder();
                Field v1;
                for (int i = 0; i < choice.length; i++) {
                    if(choice[i]) {
                        v2.append(String.valueOf(SystemState.goods_select_items[i]) + "、");
                        v3.append(String.valueOf(SystemState.goods_select_keys[i]) + ",");
                    }
                }

                if (TextUtils.isEmptyS(v3.toString())) {
                    PDH.showMessage("请至少选择一项检索关键字");
                    try {
                        v1 = arg11.getClass().getSuperclass().getDeclaredField("mShowing");
                        v1.setAccessible(true);
                        v1.set(arg11, false);
                    } catch (Exception v0) {
                        v0.printStackTrace();
                    }
                    return;
                }
                try {
                    v1 = arg11.getClass().getSuperclass().getDeclaredField("mShowing");
                    v1.setAccessible(true);
                    v1.set(arg11, true);
                } catch (Exception v0) {
                    v0.printStackTrace();
                }
                getMyPreference(R.string.goods_check_select).setSummary(TextUtils.setTextStyle(getResources().getString(R.string.goods_search_key), v2.toString().substring(0, v2.length() - 1)));
                ap.setValue("goods_check_select", v3.substring(0, v3.length() - 1));
                Utils.GOODS_CHECK_SELECT = ap.getValue("goods_check_select");
            }
        });
        v0.show();
    }

    private void customerCheckSet() {
        final boolean[] choice = new boolean[3];
        String v5 = PrefsFragment.this.ap.getValue("customer_check_select");
        if (!TextUtils.isEmptyS(v5)) {
            String[] v4 = v5.split(",");
            for (int i = 0; i < v4.length; i++) {
                for (int j = 0; j < SystemState.goods_select_keys.length; j++) {
                    if (v4[i].equals(SystemState.goods_select_keys[j])) {
                        choice[j] = true;
                    }
                }
            }
        } else {
            choice[0] = true;
            choice[1] = true;
            choice[2] = true;
        }
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("客户检索方式");
        v0.setMultiChoiceItems(SystemState.customer_select_items, choice, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                choice[which] = isChecked;
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg11, int arg12) {
                Field v3;
                StringBuilder v1 = new StringBuilder();
                StringBuilder v0 = new StringBuilder();


                for (int i = 0; i < choice.length; i++) {
                    if (choice[i]) {
                        v0.append(String.valueOf(SystemState.customer_select_items[i]) + "、");
                        v1.append(String.valueOf(SystemState.customer_select_keys[i]) + ",");
                    }
                }

                if (TextUtils.isEmptyS(v1.toString())) {
                    PDH.showMessage("请至少选择一项检索关键字。");
                    try {
                        v3 = arg11.getClass().getSuperclass().getDeclaredField("mShowing");
                        v3.setAccessible(true);
                        v3.set(arg11, false);
                    } catch (Exception v2) {
                        v2.printStackTrace();
                    }
                    return;
                }

                try {
                    v3 = arg11.getClass().getSuperclass().getDeclaredField("mShowing");
                    v3.setAccessible(true);
                    v3.set(arg11, true);
                } catch (Exception v2) {
                    v2.printStackTrace();
                }
                getMyPreference(R.string.customer_check_select).setSummary(TextUtils.setTextStyle(getResources().getString(R.string.customer_search_key), v0.toString().substring(0, v0.length() - 1)));
                ap.setValue("customer_check_select", v1.substring(0, v1.length() - 1));
                Utils.CUSTOMER_CHECK_SELECT = ap.getValue("customer_check_select");
            }
        });
        v0.show();
    }

    private void selectDepartment() {
        final List<Department> departments = new DepartmentDAO().getAllDepartment();
        String v1 = SystemState.getDepartment().getDid();
        departmentposition = -1;
        String[] v4 = new String[departments.size()];

        for (int i = 0; i < departments.size(); i++) {
            v4[i] = departments.get(i).getDname();
            if (departments.get(i).getDid().equals(v1)) {
                departmentposition = i;
            }
        }
        AlertDialog.Builder v0 = new AlertDialog.Builder(getActivity());
        v0.setTitle("工作部门");
        v0.setSingleChoiceItems(v4, departmentposition, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg2, int arg3) {
                departmentposition = arg3;
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg5, int arg6) {
                arg5.dismiss();
                if (departmentposition >= 0) {
                    SystemState.saveObject("department", departments.get(departmentposition));
                    getMyPreference(R.string.work_department).setSummary(TextUtils.setTextStyle(getResources().getString(R.string.work_department_set), departments.get(departmentposition).getDname()));
                }
            }
        });
        v0.show();
    }

    private void selectWarehouse() {
        final List<Warehouse> warehouses = new WarehouseDAO().getAllWarehouses(true);
        String[] v2 = new String[warehouses.size()];
        PrefsFragment.this.warehouseposition = -1;
        Object v3 = null;
        String v3_1 = null;
        if (SystemState.getWarehouse() != null) {
            v3_1 = SystemState.getWarehouse().getId();
        }

        for (int i = 0; i < warehouses.size(); i++) {
            v2[i] = warehouses.get(i).getName();
            if (warehouses.get(i).getId().equals(v3_1)) {
                this.warehouseposition = i;
            }
        }
        AlertDialog.Builder v0 = new AlertDialog.Builder(getActivity());
        v0.setTitle("车销仓库");
        v0.setSingleChoiceItems(v2, warehouseposition, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg2, int arg3) {
                warehouseposition = arg3;
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg5, int arg6) {
                arg5.dismiss();
                if (warehouseposition >= 0) {
                    SystemState.saveObject("warehouse", warehouses.get(warehouseposition));
                    getMyPreference(R.string.work_warehouse).setSummary(TextUtils.setTextStyle(getResources().getString(R.string.work_warehouse_set), warehouses.get(warehouseposition).getName()));
                }
            }
        });
        v0.show();
    }

    private void netSet() {
        int v1 = 1;
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("网络设置");
        String[] v3 = new String[]{"不允许（推荐）", "允许"};
        if (!"1".equals(PrefsFragment.this.ap.getValue("net_setting"))) {
            v1 = 0;
        }

        v0.setSingleChoiceItems(v3, v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg5, int arg6) {
                ap.setValue("net_setting", String.valueOf(arg6));
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg6, int arg7) {
                String v2 = getResources().getString(R.string.net_select);
                String v0 = "1".equals(ap.getValue("net_setting")) ? "允许" : "不允许";
                getMyPreference(R.string.net_setting).setSummary(TextUtils.setTextStyle(v2, v0));
            }
        });
        v0.show();
    }

    private void docItemOrder() {
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("单据商品排序");
        v0.setSingleChoiceItems(orders, itemorderposition, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg2, int arg3) {
                itemorderposition = arg3;
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg7, int arg8) {
                arg7.dismiss();
                ap.setValue("item_order", String.valueOf(itemorderposition));
                getMyPreference(R.string.item_order).setSummary(TextUtils.setTextStyle(getResources().getString(R.string.doc_item_order_key), orders[Utils.getInteger(ap.getValue("item_order", "0"))]));
            }
        });
        v0.show();
    }

    private void setGoodsSearch() {
        int v1 = 1;
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("商品检索范围");
        String[] v3 = new String[]{"全部显示", "只显示有库存的商品"};
        if (!"1".equals(PrefsFragment.this.ap.getValue("goods_result_select"))) {
            v1 = 0;
        }
        v0.setSingleChoiceItems(v3, v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg5, int arg6) {
                ap.setValue("goods_result_select", String.valueOf(arg6));
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg6, int arg7) {
                String v2 = getResources().getString(R.string.goods_search_result);
                String v0 = "1".equals(ap.getValue("goods_result_select")) ? "只显示有库存的商品" : "全部显示";
                getMyPreference(R.string.goods_result_select).setSummary(TextUtils.setTextStyle(v2, v0));
            }
        });
        v0.show();
    }

    private void goodsSelectMore() {
        int v1 = 1;
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("商品多选模式");
        String[] v3 = new String[]{"自由输入", "统一输入"};
        if (!"1".equals(PrefsFragment.this.ap.getValue("goods_select_more"))) {
            v1 = 0;
        }

        v0.setSingleChoiceItems(v3, v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg5, int arg6) {
                ap.setValue("goods_select_more", String.valueOf(arg6));
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg6, int arg7) {
                String v2 = getResources().getString(R.string.goods_select_more_add_string);
                String v0 = "1".equals(ap.getValue("goods_select_more")) ? "统一输入" : "自由输入";
                getMyPreference(R.string.goods_select_more_add).setSummary(TextUtils.setTextStyle(v2, v0));
            }
        });
        v0.show();
    }

    private void setMinusTuiHuo() {
        int v1 = 0;
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("数值打印");
        String[] v3 = new String[]{"显示为负数", "显示为正数"};
        if (!"0".equals(PrefsFragment.this.ap.getValue("minustuihuo", "0"))) {
            v1 = 1;
        }

        v0.setSingleChoiceItems(v3, v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg4, int arg5) {
                ap.setValue("minustuihuo", arg5 == 0 ? "0" : "1");
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg7, int arg8) {
                Preference v1 = getMyPreference(R.string.minustuihuo);
                String v2 = getResources().getString(R.string.cancel_goods_show);
                String v0 = "0".equals(ap.getValue("minustuihuo", "0")) ? "显示为负数" : "显示为正数";
                v1.setSummary(TextUtils.setTextStyle(v2, v0));
            }
        });
        v0.show();
    }

    private void setPrintBarCode() {
        int v1 = 0;
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("条码打印");
        String[] v3 = new String[]{"不打印", "打印"};
        if (!"0".equals(PrefsFragment.this.ap.getValue("printbarcode", "0"))) {
            v1 = 1;
        }

        v0.setSingleChoiceItems(v3, v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg4, int arg5) {
                ap.setValue("printbarcode", arg5 == 0 ? "0" : "1");
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg7, int arg8) {
                Preference v1 = getMyPreference(R.string.printbarcode);
                String v2 = getResources().getString(R.string.print_barcode_set);
                String v0 = "0".equals(ap.getValue("printbarcode", "0")) ? "不打印" : "打印";
                v1.setSummary(TextUtils.setTextStyle(v2, v0));
            }
        });
        v0.show();
    }

    private void loadData() {
        if (!NetUtils.isConnected(PrefsFragment.this.getActivity())) {
            PDH.showFail("当前无可用网络");
        } else if (new GoodsDAO().getTruckGoodsCount() > 0) {
            PDH.showMessage("同步前请先执行卸车");
        } else {
            final MAlertDialog v0 = new MAlertDialog(getActivity(), 300);
            v0.setContentText("请选择同步方式");
            v0.setConfirmButton("全部同步", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v0.dismiss();
                    PDH.show(getActivity(), "正在检查更新...", new PDH.ProgressCallBack() {
                        public void action() {
                            getMaxRVersion(true);
                        }
                    });
                }
            });
            if (!"0".equals(PrefsFragment.this.ap.getValue("max_rversion", "0"))) {
                v0.setCancelButton("增量同步", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v0.dismiss();
                        PDH.show(getActivity(), "正在检查更新...", new PDH.ProgressCallBack() {
                            public void action() {
                                getMaxRVersion(false);
                            }
                        });
                    }
                });
            }
            v0.show();
        }
    }

    private void updateCustomer() {
        if (!NetUtils.isConnected(PrefsFragment.this.getActivity())) {
            PDH.showFail("当前无可用网络");
        } else {
            PDH.show(PrefsFragment.this.getActivity(), "正在同步客户信息...", new PDH.ProgressCallBack() {
                public void action() {
                    new AccountPreference().setValue("customer_data_updateime", Utils.formatDate(new Date().getTime()));
                    if (new UpdateUtils().executeCustomerUpdate(handlerUpdateCustomer, 4, null)) {
                        handlerUpdateCustomer.sendMessage(handlerUpdateCustomer.obtainMessage(0, "客户信息同步成功"));
                    }
                }
            });
        }
    }

    private void getMaxRVersion(boolean arg13) {
        int v11 = 2;
        long v2 = 0;
        if (!arg13) {
            try {
                v2 = Long.parseLong(new AccountPreference().getValue("max_rversion", "0"));
            } catch (Exception v0) {
                v0.printStackTrace();
            }
        }
        List<ReqSynUpdateInfo> v1 = new ServiceSynchronize().syn_QueryUpdateInfo(v2);
        if (v1 == null) {
            this.handlerUpdate.sendEmptyMessage(v11);
        } else {
            long v4 = new SwyUtils().getPagesFromUpdateInfo(v1, "rversion");
            if (v4 >= 0) {
                this.ap.setValue("basic_data_updatitme", Utils.formatDate(new Date().getTime()));
                if (v4 != v2) {
                    if (new UpdateUtils().executeUpdate(this.handlerProgress, v1, v2)) {
                        this.ap.setValue("max_rversion", String.valueOf(v4));
                        this.handlerUpdate.sendEmptyMessage(0);
                    } else {
                        this.handlerUpdate.sendEmptyMessage(v11);
                    }

                    this.handlerProgress.sendEmptyMessage(-3);
                } else {
                    this.handlerUpdate.sendEmptyMessage(0);
                }
            }
        }
    }

    private void setClearLastZero() {
        int v1 = 0;
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("数值打印");
        String[] v3 = new String[]{"不保留末尾零", "保留末尾零"};
        if (!"0".equals(PrefsFragment.this.ap.getValue("clearlastzero", "0"))) {
            v1 = 1;
        }
        v0.setSingleChoiceItems(v3, v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg4, int arg5) {
                ap.setValue("clearlastzero", arg5 == 0 ? "0" : "1");
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg7, int arg8) {
                Preference v1 = getMyPreference(R.string.clearlastzero);
                String v2 = getResources().getString(R.string.clear_last_zero);
                String v0 = "0".equals(ap.getValue("clearlastzero", "0")) ? "不保留末尾零" : "保留末尾零";
                v1.setSummary(TextUtils.setTextStyle(v2, v0));
            }
        });
        v0.show();
    }

    private void setPrinterModel() {
        int v1 = 0;
        AlertDialog.Builder v0 = new AlertDialog.Builder(PrefsFragment.this.getActivity());
        v0.setTitle("设置打印机型号");
        String[] v3 = new String[]{"标准型号", "打印机型号一"};
        if (!"epson".equals(PrefsFragment.this.ap.getValue("printermodel_default", "epson"))) {
            v1 = 1;
        }

        v0.setSingleChoiceItems(v3, v1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg4, int arg5) {
                ap.setValue("printermodel_default", arg5 == 0 ? "epson" : "qunsuo");
            }
        });
        v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg7, int arg8) {
                Preference v1 = getMyPreference(R.string.printer_model);
                String v2 = getResources().getString(R.string.printer_model_select);
                String v0 = "epson".equals(ap.getValue("printermodel_default", "epson")) ? "标准型号" : "打印机型号一";
                v1.setSummary(TextUtils.setTextStyle(v2, v0));
            }
        });
        v0.show();
    }

    private void printsetting() {
        if (ap.getPrinter() != null) {
            this.printOperation();
        } else {
            getActivity().startActivityForResult(new Intent().setClass(getActivity(), BTdeviceListAct.class).putExtra("type", 2), 0);
        }
    }

    private void printOperation() {
        AlertDialog.Builder v0 = new AlertDialog.Builder(getActivity());
        v0.setItems(new String[]{"重新设定", "打印测试"}, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg8, int arg9) {
                BTPrintHelper printHelper = new BTPrintHelper(getActivity());
                if (arg9 == 0) {
                    getActivity().startActivityForResult(new Intent().setClass(getActivity(), BTdeviceListAct.class).putExtra("type", 2), 0);
                } else if (arg9 == 1) {
                    PrintMode printMode = PrintMode.getPrintMode();
                    if (printMode != null) {
                        PrintData printData = new PrintData();
                        printMode.setDatainfo(printData.getTestData());
                        printMode.setDocInfo(printData.getTestInfo());
                        printHelper.setMode(printMode);
                        printHelper.print(ap.getPrinter());
                    }
                }
            }
        });
        v0.create().show();
    }

    public Preference getCustomerPre() {
        return this.getMyPreference(R.string.load_customer_data);
    }

    public Preference getMyPreference(int arg3) {
        return this.getPreferenceManager().findPreference(this.getString(arg3));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        progressDialog = new ProgressDialog(getActivity());
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setCancelable(false);
        this.ap = new AccountPreference();
        initView();
        initValue();
    }

    private void initView() {
        this.getMyPreference(R.string.clear_database).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.net_setting).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.default_printer).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.printer_model).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.print_mode).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.clearlastzero).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.minustuihuo).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.printbarcode).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.goods_select_more_add).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.load_basic_data).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.load_customer_data).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.goods_check_select).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.goods_result_select).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.customer_check_select).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.work_department).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.work_warehouse).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.instruckment).setOnPreferenceClickListener(this.preferenceClickListener);
        this.getMyPreference(R.string.item_order).setOnPreferenceClickListener(this.preferenceClickListener);
    }

    private void initValue() {
        this.getMyPreference(R.string.load_basic_data).setSummary(TextUtils.setTextStyle(this.getResources().getString(R.string.last_load_time), this.ap.getValue("basic_data_updatitme", "未同步")));
        this.getMyPreference(R.string.load_customer_data).setSummary(TextUtils.setTextStyle(this.getResources().getString(R.string.last_load_time), this.ap.getValue("customer_data_updateime", "未同步")));
        Preference v8 = this.getMyPreference(R.string.net_setting);
        String v9 = this.getResources().getString(R.string.net_select);
        String v7 = "1".equals(this.ap.getValue("net_setting")) ? "允许" : "不允许";
        v8.setSummary(TextUtils.setTextStyle(v9, v7));
        v8 = this.getMyPreference(R.string.goods_result_select);
        v9 = this.getResources().getString(R.string.goods_search_result);
        v7 = "1".equals(this.ap.getValue("goods_result_select")) ? "只显示有库存的商品" : "全部显示";
        v8.setSummary(TextUtils.setTextStyle(v9, v7));
        StringBuilder v2 = new StringBuilder();
        String v6 = this.ap.getValue("goods_check_select");
        if (!TextUtils.isEmptyS(v6)) {
            String[] v5 = v6.split(",");
            for (int i = 0; i < v5.length; i++) {
                for (int j = 0; j < SystemState.goods_select_keys.length; j++) {
                    if (v5[i].equals(SystemState.goods_select_keys[j])) {
                        v2.append(String.valueOf(SystemState.goods_select_items[j]) + "、");
                    }
                }
            }
        } else {
            v2.append("编号、名称、拼音、条码,");
        }

        this.getMyPreference(R.string.goods_check_select).setSummary(TextUtils.setTextStyle(this.getResources().getString(R.string.goods_search_key), v2.substring(0, v2.length() - 1)));
        StringBuilder v0 = new StringBuilder();
        String v1 = this.ap.getValue("customer_check_select");
        if (!TextUtils.isEmptyS(v1)) {
            String[] v5 = v1.split(",");
            for (int i = 0; i < v5.length; i++) {
                for (int j = 0; j < SystemState.customer_select_keys.length; j++) {

                    if (v5[i].equals(SystemState.customer_select_keys[j])) {
                        v0.append(String.valueOf(SystemState.customer_select_items[j]) + "、");
                    }
                }
            }
        } else {
            v0.append("编号、名称、拼音,");
        }
        this.getMyPreference(R.string.customer_check_select).setSummary(TextUtils.setTextStyle(this.getResources().getString(R.string.customer_search_key), v0.substring(0, v0.length() - 1)));
        v8 = this.getMyPreference(R.string.goods_select_more_add);
        v9 = this.getResources().getString(R.string.goods_select_more_add_string);
        v7 = "1".equals(this.ap.getValue("goods_select_more", "0")) ? "统一输入" : "自由输入";
        v8.setSummary(TextUtils.setTextStyle(v9, v7));
        this.getMyPreference(R.string.work_department).setSummary(TextUtils.setTextStyle(this.getResources().getString(R.string.work_department_set), SystemState.getObject("department", Department.class).getDname()));
        if (SystemState.getObject("warehouse", Warehouse.class) != null) {
            this.getMyPreference(R.string.work_warehouse).setSummary(TextUtils.setTextStyle(this.getResources().getString(R.string.work_warehouse_set), SystemState.getObject("warehouse", Warehouse.class).getName()));
        } else {
            this.getMyPreference(R.string.work_warehouse).setSummary(TextUtils.setTextStyle(this.getResources().getString(R.string.work_warehouse_set), "未设置"));
        }
        this.itemorderposition = Utils.getInteger(this.ap.getValue("item_order", "0"));
        this.getMyPreference(R.string.item_order).setSummary(TextUtils.setTextStyle(this.getResources().getString(R.string.doc_item_order_key), this.orders[this.itemorderposition]));
        v8 = this.getMyPreference(R.string.printer_model);
        v9 = this.getResources().getString(R.string.printer_model_select);
        v7 = "epson".equals(this.ap.getValue("printermodel_default", "epson")) ? "标准型号" : "打印机型号一";
        v8.setSummary(TextUtils.setTextStyle(v9, v7));
        v8 = this.getMyPreference(R.string.clearlastzero);
        v9 = this.getResources().getString(R.string.clear_last_zero);
        v7 = "0".equals(this.ap.getValue("clearlastzero", "0")) ? "不保留末尾零" : "保留末尾零";
        v8.setSummary(TextUtils.setTextStyle(v9, v7));
        v8 = this.getMyPreference(R.string.minustuihuo);
        v9 = this.getResources().getString(R.string.cancel_goods_show);
        v7 = "0".equals(this.ap.getValue("minustuihuo", "0")) ? "显示为负数" : "显示为正数";
        v8.setSummary(TextUtils.setTextStyle(v9, v7));
        v8 = this.getMyPreference(R.string.printbarcode);
        v9 = this.getResources().getString(R.string.print_barcode_set);
        v7 = "0".equals(this.ap.getValue("printbarcode", "0")) ? "不打印" : "打印";
        v8.setSummary(TextUtils.setTextStyle(v9, v7));
        this.resetPrint();
    }

    public void resetPrint() {
        BTPrinter v0 = this.ap.getPrinter();
        if (v0 != null) {
            this.getMyPreference(R.string.default_printer).setSummary(String.valueOf(v0.getName()) + "[" + v0.getAddress() + "]");
        } else {
            this.getMyPreference(R.string.default_printer).setSummary("无打印机");
        }
    }
}
