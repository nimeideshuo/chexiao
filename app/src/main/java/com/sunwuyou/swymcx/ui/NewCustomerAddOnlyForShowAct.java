package com.sunwuyou.swymcx.ui;

import android.widget.EditText;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.dao.PricesystemDAO;
import com.sunwuyou.swymcx.dao.PromotionDAO;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.utils.TextUtils;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class NewCustomerAddOnlyForShowAct extends BaseHeadActivity {

    private EditText etCusId;
    private EditText etAddress;
    private EditText etContact;
    private EditText etContactMoblie;
    private EditText etCusName;
    private EditText etPriceSystem;
    private EditText etPromotion;
    private EditText etTelephone;
    private Customer updateCustomer;

    @Override
    public int getLayoutID() {
        return R.layout.add_customer_onlyforshow_dialog;
    }

    @Override
    public void initView() {
        updateCustomer = new CustomerDAO().getCustomer(this.getIntent().getStringExtra("updatecustomerid"), false);
        init();
        if (updateCustomer != null) {
            this.loadData();
        }
    }

    private void init() {
        etCusId = this.findViewById(R.id.etCusId);
        this.etCusName = this.findViewById(R.id.etCusName);
        this.etContact = this.findViewById(R.id.etContact);
        this.etContactMoblie = this.findViewById(R.id.etContactMoblie);
        this.etTelephone = this.findViewById(R.id.etTelephone);
        this.etPriceSystem = this.findViewById(R.id.etPriceSystem);
        this.etPromotion = this.findViewById(R.id.etPromotion);
        this.etAddress = this.findViewById(R.id.etAddress);
        this.etCusId.setCursorVisible(false);
        this.etCusId.setFocusable(false);
        this.etCusId.setFocusableInTouchMode(false);
        this.etCusName.setCursorVisible(false);
        this.etCusName.setFocusable(false);
        this.etCusName.setFocusableInTouchMode(false);
        this.etContact.setCursorVisible(false);
        this.etContact.setFocusable(false);
        this.etContact.setFocusableInTouchMode(false);
        this.etContactMoblie.setCursorVisible(false);
        this.etContactMoblie.setFocusable(false);
        this.etContactMoblie.setFocusableInTouchMode(false);
        this.etTelephone.setCursorVisible(false);
        this.etTelephone.setFocusable(false);
        this.etTelephone.setFocusableInTouchMode(false);
        this.etPriceSystem.setCursorVisible(false);
        this.etPriceSystem.setFocusable(false);
        this.etPriceSystem.setFocusableInTouchMode(false);
        this.etPromotion.setCursorVisible(false);
        this.etPromotion.setFocusable(false);
        this.etPromotion.setFocusableInTouchMode(false);
        this.etAddress.setCursorVisible(false);
        this.etAddress.setFocusable(false);
        this.etAddress.setFocusableInTouchMode(false);
    }


    private void loadData() {
        if (!TextUtils.isEmpty(this.updateCustomer.getPriceSystemId())) {
            String v0 = new PricesystemDAO().getPricesystemName(this.updateCustomer.getPriceSystemId());
            if (!TextUtils.isEmpty(v0)) {
                this.etPriceSystem.setText(v0);
            }
        }
        if (!TextUtils.isEmpty(this.updateCustomer.getPromotionId())) {
            String v1 = new PromotionDAO().getPromotionName(this.updateCustomer.getPromotionId());
            if (!TextUtils.isEmpty(v1)) {
                this.etPromotion.setText(v1);
            }
        }
        this.etCusId.setText(this.updateCustomer.getId());
        this.etCusName.setText(this.updateCustomer.getName());
        this.etContact.setText(this.updateCustomer.getContact());
        this.etContactMoblie.setText(this.updateCustomer.getContactMoblie());
        this.etTelephone.setText(this.updateCustomer.getTelephone());
        this.etAddress.setText(this.updateCustomer.getAddress());
    }


    @Override
    public void initData() {

    }

    public void setActionBarText() {
        setTitle("客户");
    }
}
