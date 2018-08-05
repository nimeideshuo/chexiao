package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.sunwuyou.swymcx.model.CustomerThin;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.MessageDialog;
import com.sunwuyou.swymcx.view.TargetCustomerMenuPopup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * 2018/7/18.
 * content 客户中心
 */

public class TargetCustomerActivity extends BaseHeadActivity implements View.OnTouchListener {


        private TargetCustomerMenuPopup menuPopup;
//
//    @Override
//    public int getLayoutID() {
//        return R.layout.activity_base_recyclerview;
//    }
//
//    @Override
//    public void initView() {
//        setTitle("客户中心");
//        setTitleRight("编辑", null);
//        setTitleRight1("菜单");
//        baseList.setLayoutManager(new LinearLayoutManager(this));
//        madapter = new Madapter();
//        madapter.bindToRecyclerView(baseList);
//        customerDAO = new CustomerDAO();
//    }
//
//    @Override
//    protected void onRightClick() {
//        super.onRightClick();
////        toast("编辑");
//        //TODO 未完成
//    }
//
//    @Override
//    protected void onRightClick1() {
//        super.onRightClick1();
//        if (menuPopup == null) {
//            menuPopup = new TargetCustomerMenuPopup(this);
//        }
//        this.menuPopup.showAtLocation(baseList, Gravity.BOTTOM, 0, 0);
//        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
//        attributes.alpha = 0.8f;
//        this.getWindow().setAttributes(attributes);
//    }
//
//    @Override
//    public void initData() {
//        refresh();
//    }
//
    public boolean issort;
    private CustomerDAO customerDAO;
    private View root;
    private ListView listView;
    private Button btnSelectAll;
    private List<CustomerThin> customers;
    //    @BindView(R.id.base_list) RecyclerView baseList;
//    private Madapter madapter;
//    private List<CustomerThin> customers;
//    private CustomerDAO customerDAO;
    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                PDH.showSuccess(msg.obj.toString());
                refresh();
            } else if (msg.what == 1) {
                PDH.showSuccess(msg.obj.toString());
            } else if (msg.what == 2) {

            }
        }
    };
    private Button btnDelete;
    private CustomerItemAdapter adapter;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int v2 = (int) v.getTag(2131296314);
            if (menuPopup == null || !menuPopup.isShowing()) {


            } else {
                menuPopup.dismiss();
                WindowManager.LayoutParams v1 = getWindow().getAttributes();
                v1.alpha = 1f;
                TargetCustomerActivity.this.getWindow().setAttributes(v1);

            }


            switch (v.getId()) {
                case 1:


                    break;
            }

        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_field_customers;
    }

    @Override
    public void initView() {
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
        btnDelete = this.findViewById(R.id.btnDelete);
        this.btnDelete.setOnClickListener(new View.OnClickListener() {
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

    public void delete() {
        List<CustomerThin> selectItems = this.adapter.getSelectCustomers();
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
                            deleteHandler.sendMessage(deleteHandler.obtainMessage(0, Boolean.valueOf(customerDAO.delete(selectItems))));
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
        if ("全选".equals(this.btnSelectAll.getText())) {
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
//
//    public void refresh() {
//        PDH.show(this, new PDH.ProgressCallBack() {
//            public void action() {
//                customers = customerDAO.getCustomerThin(false);
//                handler.sendEmptyMessage(2);
//            }
//        });
//    }
//
//    private class Madapter extends BaseQuickAdapter<CustomerThin, BaseViewHolder> {
//
//        public boolean issort=true;
//        private  HashMap<Integer,Boolean> status;
//
//        Madapter() {
//            super(R.layout.item_field_customer, customers);
//            status = new HashMap<Integer, Boolean>();
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, CustomerThin arg10) {
//            helper.setText(R.id.tvCustomerName, "[" + arg10.getId() + "] " + arg10.getName());
//            helper.getView(R.id.tvIsNew).setVisibility(arg10.getIsNew() ? View.VISIBLE : View.GONE);
//
//            String v2 = arg10.getContactMoblie();
//            if (!TextUtils.isEmpty(arg10.getTelephone())) {
//                v2 = TextUtils.isEmpty(v2) ? arg10.getTelephone() : String.valueOf(v2) + "，" + arg10.getTelephone();
//            }
//            helper.getView(R.id.tvTelephone).setVisibility(TextUtils.isEmptyS(v2) ? View.GONE : View.VISIBLE);
//            helper.setText(R.id.tvTelephone, v2);
//            String v0 = arg10.getAddress();
//            helper.getView(R.id.tvCusAddress).setVisibility(TextUtils.isEmptyS(v0) ? View.GONE : View.VISIBLE);
//            helper.setText(R.id.tvCusAddress, v0);
//            if (arg10.getExhibitionTerm() > 0) {
//                helper.getView(R.id.tvExhibition).setVisibility(View.VISIBLE);
//                String v1 = "每月" + arg10.getExhibitionTerm() + "日送陈列";
//                if (arg10.getLastExhibition() > 0) {
//                    v1 = String.valueOf(v1) + "，最近一次" + Utils.formatDate(arg10.getLastExhibition(), "yyyy-MM-dd");
//                }
//                helper.setText(R.id.tvExhibition, v1);
//                helper.setTextColor(R.id.tvExhibition, -65536);
//            } else {
//                helper.getView(R.id.tvExhibition).setVisibility(View.GONE);
//            }
//
//            if (issort) {
//                helper.getView(R.id.lin1).setVisibility(View.VISIBLE);
//                helper.getView(R.id.llCheckBox).setVisibility(View.VISIBLE);
//            } else {
//                helper.getView(R.id.lin1).setVisibility(View.GONE);
//                helper.getView(R.id.llCheckBox).setVisibility(View.GONE);
//            }
//            helper.addOnClickListener(R.id.btnBefore);
//            helper.addOnClickListener(R.id.btnBottom);
//            helper.addOnClickListener(R.id.btnNext);
//            helper.addOnClickListener(R.id.btnTop);
//            CheckBox cb = helper.getView(R.id.checkBox);
//            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                 status.put(Integer.valueOf(buttonView.getTag(2131296314).toString()),isChecked);
//                }
//            });
//        }
//
//    }

}
