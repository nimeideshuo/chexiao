package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.model.CustomerThin;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.TargetCustomerMenuPopup;

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

public class TargetCustomerActivity extends BaseHeadActivity {
    @BindView(R.id.base_list) RecyclerView baseList;
    private Madapter madapter;
    private List<CustomerThin> customers;
    private CustomerDAO customerDAO;
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
                madapter.setNewData(customers);
            }
        }
    };
    private TargetCustomerMenuPopup menuPopup;

    @Override
    public int getLayoutID() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initView() {
        setTitle("客户中心");
        setTitleRight("编辑", null);
        setTitleRight1("菜单");
        baseList.setLayoutManager(new LinearLayoutManager(this));
        madapter = new Madapter();
        madapter.bindToRecyclerView(baseList);
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        toast("编辑");
    }

    @Override
    protected void onRightClick1() {
        super.onRightClick1();
        toast("菜单");
        if (menuPopup == null) {
            menuPopup = new TargetCustomerMenuPopup(this);
        }
        this.menuPopup.showAtLocation(baseList, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
        attributes.alpha = 0.8f;
        this.getWindow().setAttributes(attributes);
    }

    @Override
    public void initData() {
        refresh();
    }

    public void updateCustomer(final int paramInt, final String paramString) {
        PDH.show(this, "正在同步客户信息...", new PDH.ProgressCallBack() {
            public void action() {
                if (paramInt == 4) {
                    new AccountPreference().setValue("customer_data_updateime", Utils.formatDate(new Date().getTime()));
                }
                if (new UpdateUtils().executeCustomerUpdate(handler, paramInt, paramString)) {
                    handler.sendMessage(handler.obtainMessage(0, "客户信息同步成功"));
                }
            }
        });
    }

    public void refresh() {
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                customers = customerDAO.getCustomerThin(false);
                handler.sendEmptyMessage(2);
            }
        });
    }

    private class Madapter extends BaseQuickAdapter<CustomerThin, BaseViewHolder> {

        public boolean issort=true;
        private  HashMap<Integer,Boolean> status;

        Madapter() {
            super(R.layout.item_field_customer, customers);
            status = new HashMap<Integer, Boolean>();
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomerThin arg10) {
            helper.setText(R.id.tvCustomerName, "[" + arg10.getId() + "] " + arg10.getName());
            helper.getView(R.id.tvIsNew).setVisibility(arg10.getIsNew() ? View.VISIBLE : View.GONE);

            String v2 = arg10.getContactMoblie();
            if (!TextUtils.isEmpty(arg10.getTelephone())) {
                v2 = TextUtils.isEmpty(v2) ? arg10.getTelephone() : String.valueOf(v2) + "，" + arg10.getTelephone();
            }
            helper.getView(R.id.tvTelephone).setVisibility(TextUtils.isEmptyS(v2) ? View.GONE : View.VISIBLE);
            helper.setText(R.id.tvTelephone, v2);
            String v0 = arg10.getAddress();
            helper.getView(R.id.tvCusAddress).setVisibility(TextUtils.isEmptyS(v0) ? View.GONE : View.VISIBLE);
            helper.setText(R.id.tvCusAddress, v0);
            if (arg10.getExhibitionTerm() > 0) {
                helper.getView(R.id.tvExhibition).setVisibility(View.VISIBLE);
                String v1 = "每月" + arg10.getExhibitionTerm() + "日送陈列";
                if (arg10.getLastExhibition() > 0) {
                    v1 = String.valueOf(v1) + "，最近一次" + Utils.formatDate(arg10.getLastExhibition(), "yyyy-MM-dd");
                }
                helper.setText(R.id.tvExhibition, v1);
                helper.setTextColor(R.id.tvExhibition, -65536);
            } else {
                helper.getView(R.id.tvExhibition).setVisibility(View.GONE);
            }

            if (issort) {
                helper.getView(R.id.lin1).setVisibility(View.VISIBLE);
                helper.getView(R.id.llCheckBox).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.lin1).setVisibility(View.GONE);
                helper.getView(R.id.llCheckBox).setVisibility(View.GONE);
            }
            helper.addOnClickListener(R.id.btnBefore);
            helper.addOnClickListener(R.id.btnBottom);
            helper.addOnClickListener(R.id.btnNext);
            helper.addOnClickListener(R.id.btnTop);
            CheckBox cb = helper.getView(R.id.checkBox);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 status.put(Integer.valueOf(buttonView.getTag(2131296314).toString()),isChecked);
                }
            });
        }

    }

}
