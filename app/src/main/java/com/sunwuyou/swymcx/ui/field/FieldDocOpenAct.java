package com.sunwuyou.swymcx.ui.field;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.gps.GPS;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.ui.CustomerSearchAct;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin
 * 2018/7/17.
 * content
 */

public class FieldDocOpenAct extends BaseHeadActivity implements GPS.onLocationCallBack {

    private static final int INTENT_CODE_CUSTOMER = 300;
    @BindView(R.id.btnCustomer) Button btnCustomer;
    @BindView(R.id.etRemark) EditText etRemark;
    @BindView(R.id.tvGetLocation) TextView tvGetLocation;
    @BindView(R.id.btnOpenFieldDoc) Button btnOpenFieldDoc;
    Customer customer;
    private AMapLocation mLocation;
    private GPS mGps;
    private FieldSale fieldSale;

    @Override
    public int getLayoutID() {
        return R.layout.dia_field_opendoc;
    }

    @Override
    public void initView() {
        setTitle("销售开单");
        this.tvGetLocation.setText("正在获取位置...");
    }

    @Override
    public void initData() {
        this.customer = new CustomerDAO().getNextVisitCustomer();
        if (this.customer != null) {
            this.btnCustomer.setText(this.customer.getName());
            this.btnCustomer.setTag(this.customer.getId());
        }
        mGps = new GPS(this.getApplicationContext(), this);
    }

    private FieldSale makeFieldsaleForm() {
        fieldSale = new FieldSale();
        Department v4 = SystemState.getDepartment();
        User user = SystemState.getObject("cu_user", User.class);
        fieldSale.setShowid(new FieldSaleDAO().makeShowId());
        fieldSale.setDepartmentid(v4.getDid());
        fieldSale.setDepartmentname(v4.getDname());
        fieldSale.setPreference(0);
        fieldSale.setStatus(0);
        fieldSale.setBuildtime(Utils.formatDate(Utils.getCurrentTime(true)));
        fieldSale.setBuilderid(user.getId());
        fieldSale.setBuildername(user.getName());
        fieldSale.setRemark(this.etRemark.getText().toString());
        fieldSale.setLongitude(this.mLocation.getLongitude());
        fieldSale.setLatitude(this.mLocation.getLatitude());
        fieldSale.setAddress(this.mLocation.getAddress());
        return fieldSale;
    }


    @OnClick({R.id.btnCustomer, R.id.btnOpenFieldDoc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCustomer:
                startActivityForResult(new Intent(this, CustomerSearchAct.class), INTENT_CODE_CUSTOMER);
                break;
            case R.id.btnOpenFieldDoc:

                openDoc();


                break;
        }
    }

    private void openDoc() {
        FieldSale localFieldSale = FieldDocOpenAct.this.makeFieldsaleForm();
        long l = new FieldSaleDAO().addFieldsale(localFieldSale);
        FieldDocOpenAct.this.handler.sendMessage(FieldDocOpenAct.this.handler.obtainMessage(0, Long.valueOf(l)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INTENT_CODE_CUSTOMER) {
            Customer customer = (Customer) data.getSerializableExtra("customer");
            fieldSale.setCustomerid(customer.getId());
            fieldSale.setCustomername(customer.getName());
            fieldSale.setIsnewcustomer(customer.getIsNew());
            fieldSale.setPromotionid(TextUtils.isEmptyS(customer.getPromotionId()) ? null : customer.getPromotionId());
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLocation = aMapLocation;
        if (aMapLocation.getLatitude() != 0) {
            mGps.stop();
            FieldSale fieldSale = makeFieldsaleForm();
            tvGetLocation.setText(mGps.getmAddress());
        }
    }

}
