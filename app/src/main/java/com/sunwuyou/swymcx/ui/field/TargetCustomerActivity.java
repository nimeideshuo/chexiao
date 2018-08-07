package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.CustomerThin;
import com.sunwuyou.swymcx.ui.MAlertDialog;
import com.sunwuyou.swymcx.ui.NewCustomerAddOnlyForShowAct;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.MessageDialog;
import com.sunwuyou.swymcx.view.TargetCustomerMenuPopup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/18.
 * content 客户中心
 */

public class TargetCustomerActivity extends BaseHeadActivity implements View.OnTouchListener {
    public boolean issort;
    private TargetCustomerMenuPopup menuPopup;
    private CustomerDAO customerDAO;
    private View root;
    private ListView listView;
    private Button btnSelectAll;
    private List<CustomerThin> customers;
    private CustomerItemAdapter adapter;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                PDH.showSuccess(msg.obj.toString());
                refresh();
            } else if (msg.what == 1) {
                PDH.showSuccess(msg.obj.toString());
            } else if (msg.what == 2) {
                adapter.setData(customers);
                listView.setAdapter(adapter);
            }
        }
    };
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int v2 = (int) v.getTag(2131296314);
            if (menuPopup == null || !menuPopup.isShowing()) {
                CustomerThin v0 = customers.get(v2);
                if (v.getId() == R.id.btnTop) {
                    if (v2 != 0) {
                        customerDAO.moveToFirst(v0);
                        refresh();
                    }
                } else if (v.getId() == R.id.btnBottom) {
                    if (v2 != customers.size() - 1) {
                        customerDAO.moveToLast(v0);
                        refresh();
                    }
                } else if (v.getId() == R.id.btnBefore) {
                    if (v2 != 0) {
                        customerDAO.exchangeOrderNo(v0, customers.get(v2 - 1));
                        refresh();
                    }
                } else if (v.getId() == R.id.btnNext) {
                    if (v2 != TargetCustomerActivity.this.customers.size() - 1) {
                        customerDAO.exchangeOrderNo(v0, customers.get(v2 + 1));
                        refresh();
                    }
                } else if (!customers.get(v2).getIsNew()) {
                    startActivityForResult(new Intent().setClass(TargetCustomerActivity.this, NewCustomerAddOnlyForShowAct.class).putExtra("updatecustomerid", TargetCustomerActivity.this.customers.get(v2).getId()), 0);
                }


            } else {
                menuPopup.dismiss();
                WindowManager.LayoutParams v1 = getWindow().getAttributes();
                v1.alpha = 1f;
                getWindow().setAttributes(v1);
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler deleteHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (Boolean.parseBoolean(msg.obj.toString())) {
                PDH.showSuccess("删除成功");
                CheckBox cb = findViewById(R.id.checkBox);
                cb.setChecked(false);
                refresh();
            } else {
                PDH.showFail("删除失败，请重试");
            }
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_field_customers;
    }

    @Override
    public void initView() {
        setTitleRight("编辑", null);
        setTitleRight1("菜单");
        customerDAO = new CustomerDAO();
        root = this.findViewById(R.id.root);
        this.root.setOnTouchListener(this);
        listView = this.findViewById(R.id.listView);
        btnSelectAll = this.findViewById(R.id.btnSelectAll);
        this.btnSelectAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                selectAll();
            }
        });
        Button btnDelete = this.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!ClickUtils.isFastDoubleClick()) {
                    delete();
                }
            }
        });
        adapter = new CustomerItemAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.adapter.setOnClickListener(this.listener);
        this.refresh();
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (this.menuPopup == null || !this.menuPopup.isShowing()) {
            if (issort) {
                issort = false;
                setTitleRight("编辑", null);
                this.btnSelectAll.setText("全选");
                this.findViewById(R.id.llButtons).setVisibility(View.GONE);
                this.adapter.setSelectAll(false);
            } else {
                if (this.customers != null && this.customers.size() != 0) {
                    issort = true;
                    setTitleRight("完成", null);
                    this.findViewById(R.id.llButtons).setVisibility(View.VISIBLE);
                } else {
                    PDH.showMessage("当前列表为空");
                }
            }
            this.adapter.notifyDataSetChanged();
        } else {
            this.menuPopup.dismiss();
            WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
            attributes.alpha = 1f;
            this.getWindow().setAttributes(attributes);
        }
    }

    @Override
    protected void onRightClick1() {
        super.onRightClick1();
        if (this.menuPopup == null || !this.menuPopup.isShowing()) {
            issort = false;
            setTitleRight("编辑", null);
            this.btnSelectAll.setText("全选");
            this.findViewById(R.id.llButtons).setVisibility(View.GONE);
            if (this.menuPopup == null) {
                this.menuPopup = new TargetCustomerMenuPopup(this);
            }
            this.menuPopup.showAtLocation(this.root, 80, 0, 0);
            WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
            attributes.alpha = 0.8f;
            this.getWindow().setAttributes(attributes);
        } else {
            this.menuPopup.dismiss();
            WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
            attributes.alpha = 1f;
            this.getWindow().setAttributes(attributes);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    final MAlertDialog dialog = new MAlertDialog(this);
                    dialog.simpleShow("是否同步该线路上的客户信息？");
                    dialog.setCancelButton(null);
                    dialog.setConfirmButton(new View.OnClickListener() {
                        public void onClick(View arg5) {
                            dialog.dismiss();
                            updateCustomer(2, data.getStringExtra("visitlineid"));
                        }
                    });
                    dialog.show();
                    break;

                case 2:
                    Customer customer = (Customer) data.getSerializableExtra("customer");
                    this.updateCustomer(1, customer.getId());
                    break;
                case 3:
                    final MAlertDialog dialogRegionid = new MAlertDialog(this);
                    dialogRegionid.simpleShow("是否同步该地区的客户信息？");
                    dialogRegionid.setCancelButton(null);
                    dialogRegionid.setConfirmButton(new View.OnClickListener() {
                        public void onClick(View arg5) {
                            dialogRegionid.dismiss();
                            updateCustomer(3, data.getStringExtra("regionid"));
                        }
                    });
                    dialogRegionid.show();
                    break;
            }
        }
        if (resultCode == RESULT_OK && (data.getBooleanExtra("refresh", false))) {
            this.refresh();
        }
    }

    public void delete() {
        final List<CustomerThin> selectItems = this.adapter.getSelectCustomers();
        if (selectItems.size() == 0) {
            PDH.showFail("请选择删除的项");
        } else {
            if (selectItems.size() == 1 && (this.customerDAO.isCustomerHasDoc(selectItems.get(0).getId()))) {
                PDH.showMessage("该客户在本地存在业务单据，不能删除");
                return;
            }
            new MessageDialog(this).showDialog("提示", "确定删除选中的项？", null, null, new MessageDialog.CallBack() {
                @Override
                public void btnOk(View view) {
                    PDH.show(TargetCustomerActivity.this, "正在删除...", new PDH.ProgressCallBack() {
                        public void action() {
                            deleteHandler.sendMessage(deleteHandler.obtainMessage(0, customerDAO.delete(selectItems)));
                        }
                    });
                }

                @Override
                public void btnCancel(View view) {

                }
            }).showDialog();
        }
    }

    public void refresh() {
        PDH.show(this, "正在刷新...", new PDH.ProgressCallBack() {

            public void action() {
                customers = customerDAO.getCustomerThin(false);
                handler.sendEmptyMessage(2);
            }
        });
    }

    public void selectAll() {
        if ("全选".equals(this.btnSelectAll.getText().toString())) {
            this.adapter.setSelectAll(true);
            this.btnSelectAll.setText("全不选");
        } else {
            this.adapter.setSelectAll(false);
            this.btnSelectAll.setText("全选");
        }
    }

    public void setActionBarText() {
        setTitle("客户中心");
    }

    @Override
    public void initData() {

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

    public void updateCustomer(final int type, final String id) {
        PDH.show(this, "正在同步客户信息...", new PDH.ProgressCallBack() {
            public void action() {
                if (type == 4) {
                    new AccountPreference().setValue("customer_data_updateime", Utils.formatDate(new Date().getTime()));
                }
                if (new UpdateUtils().executeCustomerUpdate(handler, type, id)) {
                    handler.sendMessage(handler.obtainMessage(0, "客户信息同步成功"));
                }
            }
        });
    }

    class CustomerItemAdapter extends BaseAdapter {
        int v4 = 2131296314;
        private Activity activity;
        private HashMap<Integer, Boolean> status;
        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                status.put(Integer.valueOf(buttonView.getTag(v4).toString()), isChecked);
            }
        };
        private List<CustomerThin> customers;
        private View.OnClickListener onClickListener;

        public CustomerItemAdapter(Activity activity) {
            this.activity = activity;
            this.status = new HashMap<>();
        }

        public void setSelectAll(boolean arg5) {
            for (int i = 0; i < customers.size(); i++) {
                this.status.put(i, arg5);
            }
            this.notifyDataSetChanged();
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public int getCount() {
            return this.customers == null ? 0 : this.customers.size();
        }

        public void removeItem(int position) {
            this.customers.remove(position);
            this.notifyDataSetChanged();
        }

        public void setData(List<CustomerThin> customers) {
            this.status.clear();
            this.customers = customers;
            this.notifyDataSetChanged();
        }

        @Override
        public CustomerThin getItem(int position) {
            return this.customers.get(position);
        }

        public List<CustomerThin> getSelectCustomers() {
            ArrayList<CustomerThin> v1 = new ArrayList<>();
            for (int i = 0; i < customers.size(); i++) {
                if (this.status.get(i) != null && (this.status.get(i))) {
                    v1.add(this.customers.get(i));
                }
            }
            return v1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder v0;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.activity).inflate(R.layout.item_field_customer, null);
                v0 = new Holder(convertView);
                convertView.setTag(v0);
            } else {
                v0 = (Holder) convertView.getTag();
            }
            v0.checkBox.setTag(v4, position);
            v0.btnBefore.setTag(v4, position);
            v0.btnBottom.setTag(v4, position);
            v0.btnNext.setTag(v4, position);
            v0.btnTop.setTag(v4, position);
            convertView.setTag(v4, position);
            v0.tvSerialid.setText(String.valueOf(position + 1));
            v0.setValue(this.customers.get(position));
            if (this.status.get(position) == null) {
                v0.checkBox.setChecked(false);
            } else {
                v0.checkBox.setChecked(status.get(position));
            }
            convertView.setOnClickListener(this.onClickListener);
            return convertView;
        }

        class Holder {
            public Button btnBefore;
            public Button btnBottom;
            public Button btnNext;
            public Button btnTop;
            public CheckBox checkBox;
            public LinearLayout lin1;
            public LinearLayout llCheckBox;
            public TextView tvCusAddress;
            public TextView tvCustomerName;
            public TextView tvExhibition;
            public TextView tvIsNew;
            public TextView tvSerialid;
            public TextView tvTelephone;

            public Holder(View arg3) {
                this.tvSerialid = arg3.findViewById(R.id.tvSerialid);
                this.tvCustomerName = arg3.findViewById(R.id.tvCustomerName);
                this.tvIsNew = arg3.findViewById(R.id.tvIsNew);
                this.tvTelephone = arg3.findViewById(R.id.tvTelephone);
                this.tvCusAddress = arg3.findViewById(R.id.tvCusAddress);
                this.tvExhibition = arg3.findViewById(R.id.tvExhibition);
                this.checkBox = arg3.findViewById(R.id.checkBox);
                this.btnTop = arg3.findViewById(R.id.btnTop);
                this.btnBottom = arg3.findViewById(R.id.btnBottom);
                this.btnNext = arg3.findViewById(R.id.btnNext);
                this.btnBefore = arg3.findViewById(R.id.btnBefore);
                this.llCheckBox = arg3.findViewById(R.id.llCheckBox);
                this.lin1 = arg3.findViewById(R.id.lin1);
            }

            @SuppressLint("SetTextI18n")
            public void setValue(CustomerThin arg10) {
                int v8 = 8;
                this.tvCustomerName.setText("[" + arg10.getId() + "] " + arg10.getName());
                if (arg10.getIsNew()) {
                    this.tvIsNew.setVisibility(View.VISIBLE);
                } else {
                    this.tvIsNew.setVisibility(View.GONE);
                }

                String v2 = arg10.getContactMoblie();
                if (!TextUtils.isEmpty(arg10.getTelephone())) {
                    v2 = TextUtils.isEmpty(v2) ? arg10.getTelephone() : String.valueOf(v2) + "，" + arg10.getTelephone();
                }

                if (TextUtils.isEmptyS(v2)) {
                    this.tvTelephone.setVisibility(View.GONE);
                } else {
                    this.tvTelephone.setVisibility(View.VISIBLE);
                    this.tvTelephone.setText(v2);
                }

                String v0 = arg10.getAddress();
                if (TextUtils.isEmptyS(v0)) {
                    this.tvCusAddress.setVisibility(View.GONE);
                } else {
                    this.tvCusAddress.setVisibility(View.VISIBLE);
                    this.tvCusAddress.setText(v0);
                }

                if (arg10.getExhibitionTerm() > 0) {
                    this.tvExhibition.setVisibility(View.VISIBLE);
                    String v1 = "每月" + arg10.getExhibitionTerm() + "日送陈列";
                    if (arg10.getLastExhibition() > 0) {
                        v1 = String.valueOf(v1) + "，最近一次" + Utils.formatDate(arg10.getLastExhibition(), "yyyy-MM-dd");
                    }

                    this.tvExhibition.setText(v1);
                    this.tvExhibition.setTextColor(-65536);
                } else {
                    this.tvExhibition.setVisibility(View.GONE);
                }

                if (issort) {
                    this.lin1.setVisibility(View.VISIBLE);
                    this.llCheckBox.setVisibility(View.VISIBLE);
                } else {
                    this.lin1.setVisibility(View.GONE);
                    this.llCheckBox.setVisibility(View.GONE);
                }

                this.btnBefore.setOnClickListener(onClickListener);
                this.btnBottom.setOnClickListener(onClickListener);
                this.btnNext.setOnClickListener(onClickListener);
                this.btnTop.setOnClickListener(onClickListener);
                this.checkBox.setOnCheckedChangeListener(checkListener);//
            }
        }
    }
}
