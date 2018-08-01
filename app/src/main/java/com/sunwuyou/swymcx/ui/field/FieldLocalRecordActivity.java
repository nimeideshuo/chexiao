package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.immo.libcomm.utils.TextUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.FieldSalePayTypeDAO;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.FieldSaleThin;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.popupmenu.FieldLocalRecordMenuPopup;
import com.sunwuyou.swymcx.service.ServiceCustomer;
import com.sunwuyou.swymcx.service.ServiceUser;
import com.sunwuyou.swymcx.ui.NewCustomerAddAct;
import com.sunwuyou.swymcx.utils.InfoDialog;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.NetUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.UpLoadUtils;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.List;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class FieldLocalRecordActivity extends BaseHeadActivity implements View.OnTouchListener {
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //            if (menuPopup == null || !menuPopup.isShowing()) {
            //                startActivity(new Intent(FieldLocalRecordActivity.this, FieldEditActivity.class).putExtra("fieldsaleid", tasks.get(position).getId()));
            //            } else {
            //                menuPopup.dismiss();
            //                WindowManager.LayoutParams v1 = getWindow().getAttributes();
            //                v1.alpha = 1f;
            //                getWindow().setAttributes(v1);
            //            }
        }
    };
    private View root;
    private ListView listView;
    private FieldLocalRecordAdapter adapter;
    private ProgressDialog progressDialog;
    private FieldSaleDAO fieldSaleDAO;
    private List<FieldSaleThin> tasks;
    private int customerindex;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.setData(tasks);
            listView.setAdapter(adapter);
            //            refreshUI();
        }
    };
    private FieldLocalRecordMenuPopup menuPopup;
    private List<FieldSaleThin> selectItmes;
    @SuppressLint("HandlerLeak")
    private Handler handlerProgress = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                progressDialog.show();
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
            } else if (msg.what == -2) {
                progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
            } else if (msg.what == -3) {
                progressDialog.cancel();
            } else {
                progressDialog.setProgress(msg.what);
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handlerUpdate = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handlerProgress.sendEmptyMessage(-3);
            switch (msg.what) {
                case 0:
                    InfoDialog.showError(FieldLocalRecordActivity.this, msg.obj.toString());
                    break;
                case 1:
                    InfoDialog.showMessage(FieldLocalRecordActivity.this, "单据上传成功");
                    break;
                case 2:
                    refresh();
                    break;
                case 10:
                    String v1 = String.valueOf(msg.obj.toString());
                    if (TextUtils.isEmptyS(v1)) {
                        PDH.showSuccess("上传成功");
                        refresh();
                        return;
                    }
                    if ("sameid".equals(v1)) {
                        PDH.showFail("客户编号已经存在，请修改");
                        FieldLocalRecordActivity.this.startActivityForResult(new Intent().setClass(FieldLocalRecordActivity.this, NewCustomerAddAct.class).putExtra("updatecustomerid", FieldLocalRecordActivity.this.selectItmes.get(FieldLocalRecordActivity.this.customerindex).getCustomerid()), 0);
                        return;
                    }
                    break;
            }

        }
    };
    private AbsListView.MultiChoiceModeListener muliChoiceModeLisener = new AbsListView.MultiChoiceModeListener() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (menuPopup == null || !menuPopup.isShowing()) {


                selectItmes = adapter.getSelectList();
                switch (item.getItemId()) {
                    case R.id.btnDelete: {
                        if (selectItmes.size() == 1 && selectItmes.get(0).getStatus() == 1) {
                            PDH.showMessage("单据已处理，不能被删除");
                            return true;
                        }
                        deleteSelected(selectItmes);
                        return false;
                    }
                    case R.id.btnUpload: {
                        if (!NetUtils.isConnected(FieldLocalRecordActivity.this)) {
                            PDH.showFail("当前无可用网络");
                            return true;
                        }
                        if (selectItmes.size() == 1) {
                            if (selectItmes.get(0).getStatus() == 0) {
                                PDH.showMessage("单据未处理，不允许上传");
                                return true;
                            } else if (selectItmes.get(0).getStatus() == 2) {
                                PDH.showMessage("单据已上传");
                                return true;
                            }
                        }
                        handlerProgress.sendEmptyMessage(-1);
                        new Thread() {
                            public void run() {
                                uploadAll(selectItmes, true, false);
                            }
                        }.start();
                        mode.finish();
                        return false;
                    }
                    //                    case R.id.btnSelectAll:
                    //                        adapter.setSelectAll();
                    //                        mode.setSubtitle("选中" + adapter.getCount() + "条");
                    //                        return false;
                }
            } else {
                menuPopup.dismiss();
                WindowManager.LayoutParams v1 = getWindow().getAttributes();
                v1.alpha = 1f;
                getWindow().setAttributes(v1);
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }
    };

    private void uploadAll(List<FieldSaleThin> arg19, boolean arg20, boolean arg21) {
        UpLoadUtils v17 = new UpLoadUtils();
        CustomerDAO v10 = new CustomerDAO();
        FieldSaleDAO v12 = new FieldSaleDAO();
        String v16 = new ServiceUser().usr_CheckAuthority("NewXundian");
        if (!RequestHelper.isSuccess(v16)) {
            if ("forbid".equals(v16)) {
                v16 = "请联系系统管理员获取授权！";
            }
            handlerUpdate.sendMessage(handlerUpdate.obtainMessage(0, v16));
        } else {

            //TODO 循环递减 此处必须修改

            for (int i = 0; i < arg19.size(); i++) {
                FieldSaleThin saleThin = arg19.get(i);
                if ((new FieldSaleItemDAO().getFieldSaleItems(saleThin.getId()).size() != 0 || new FieldSalePayTypeDAO().getPayTypeAmount(saleThin.getId()) == 0) && 1 == saleThin.getStatus()) {
                    String v13 = v12.getZeroSalePriceGoods(saleThin.getId());
                    if (v13 != null) {
                        this.handlerUpdate.sendMessage(this.handlerUpdate.obtainMessage(0, "上传失败，客户【" + saleThin.getCustomername() + " 】存在【" + v13 + "】等商品销售价格为零"));
                        return;
                    }
                }
            }
            //TODO 循环递减 此处必须修改
            this.handlerProgress.sendMessage(this.handlerProgress.obtainMessage(-2, 0));
            for (int i = 0; i < arg19.size(); i++) {
                FieldSaleThin saleThin = arg19.get(i);
                if (1 == saleThin.getStatus()) {
                    if (!TextUtils.isEmptyS(saleThin.getCustomerid())) {
                        Customer v9 = v10.getCustomer(saleThin.getCustomerid(), saleThin.isIsnewcustomer());
                        if (v9 != null && (v9.getIsNew())) {
                            v16 = new ServiceCustomer().cu_AddCustomer(v10.getCustomer(v9.getId(), v9.getIsNew()), SystemState.getObject("cu_user", User.class).getId(), true, false, false, false);
                            if (RequestHelper.isSuccess(v16)) {
                                v10.updateCustomerValue(saleThin.getCustomerid(), "isnew", "0");
                            } else {
                                MLog.d("新增客户失败");
                                this.customerindex = i;
                                this.handlerUpdate.sendMessage(this.handlerUpdate.obtainMessage(10, v16));
                                return;
                            }
                        }
                        v16 = v17.uploadCheXiao(saleThin);
                        //TODO 循环递减 此处必须修改
                        if(v8 == 1) {
                            if(v16 != null) {
                                this.handlerUpdate.sendMessage(this.handlerUpdate.obtainMessage(0, v16));
                            }
                            else {
                                this.handlerUpdate.sendEmptyMessage(1);
                            }
                        }

                    }
                }


            }




        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.act_field_local_records;
    }

    @Override
    public void initView() {
        setTitleRight("菜单", null);
        this.root = findViewById(R.id.root);
        this.root.setOnTouchListener(this);
        listView = findViewById(R.id.listView);
        listView.setOnTouchListener(this);
        adapter = new FieldLocalRecordAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(itemClickListener);
        this.listView.setChoiceMode(3);
        this.listView.setMultiChoiceModeListener(this.muliChoiceModeLisener);
        this.refresh();
        progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setMessage("正在上传中...");
        this.progressDialog.setCancelable(false);
        fieldSaleDAO = new FieldSaleDAO();

    }

    private void deleteSelected(final List<FieldSaleThin> items) {
        new MessageDialog(FieldLocalRecordActivity.this).showDialog("提示", "确定删除吗？", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                PDH.show(FieldLocalRecordActivity.this, "正在删除...", new PDH.ProgressCallBack() {
                    @Override
                    public void action() {
                        CustomerDAO v0 = new CustomerDAO();
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getStatus() != 1) {
                                fieldSaleDAO.deleteFieldsale(items.get(i).getId());
                                if (!TextUtils.isEmptyS(items.get(i).getCustomerid())) {
                                    v0.updateCustomerValue(items.get(i).getCustomerid(), "isfinish", "0");
                                }
                            }
                        }
                        mHandler.post(new Runnable() {
                            public void run() {
                                PDH.showSuccess("删除成功");
                                refresh();
                            }
                        });
                    }
                });


            }

            @Override
            public void btnCancel(View view) {

            }
        }).showDialog();
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (this.menuPopup != null && (this.menuPopup.isShowing())) {
            return;
        }
        menuPopup = new FieldLocalRecordMenuPopup(this);
        this.menuPopup.showAtLocation(this.root, 80, 0, 0);
        WindowManager.LayoutParams v0 = this.getWindow().getAttributes();
        v0.alpha = 0.8f;
        this.getWindow().setAttributes(v0);
    }

    @Override
    public void initData() {

    }

    protected void onResume() {
        super.onResume();
        this.refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            Customer v0 = (Customer) data.getSerializableExtra("newcustomer");
            this.selectItmes.get(this.customerindex).setCustomerid(v0.getId());
            this.selectItmes.get(this.customerindex).setCustomername(v0.getName());
            PDH.show(this, "正在上传...", new PDH.ProgressCallBack() {
                public void action() {
                    uploadAll(selectItmes, false, false);
                }
            });
        }
    }

    public void refresh() {
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                tasks = new FieldSaleDAO().queryAllFields();
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (menuPopup != null && (menuPopup.isShowing())) {
            menuPopup.dismiss();
            WindowManager.LayoutParams v0 = getWindow().getAttributes();
            v0.alpha = 1f;
            getWindow().setAttributes(v0);
        }
        return false;
    }

    public boolean onKeyDown(int arg3, KeyEvent arg4) {
        boolean v1;
        if (this.menuPopup == null || !this.menuPopup.isShowing()) {
            v1 = super.onKeyDown(arg3, arg4);
        } else {
            this.menuPopup.dismiss();
            WindowManager.LayoutParams v0 = this.getWindow().getAttributes();
            v0.alpha = 1f;
            this.getWindow().setAttributes(v0);
            v1 = true;
        }
        return v1;
    }

    @Override
    public void setActionBarText() {
        setTitle("我的销售");
    }
}
