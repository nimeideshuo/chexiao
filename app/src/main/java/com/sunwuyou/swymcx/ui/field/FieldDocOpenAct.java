package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.sunwuyou.swymcx.dao.PromotionDAO;
import com.sunwuyou.swymcx.gps.GPS;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.Promotion;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.ui.CustomerSearchAct;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin
 * 2018/7/17.
 * content
 */

public class FieldDocOpenAct extends BaseHeadActivity implements GPS.onLocationCallBack {

    private static final int INTENT_CODE_CUSTOMER = 300;
    @BindView(R.id.btnCustomer)
    Button btnCustomer;
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.tvGetLocation)
    TextView tvGetLocation;
    @BindView(R.id.btnOpenFieldDoc)
    Button btnOpenFieldDoc;
    Customer customer;
    private AMapLocation mLocation;
    private GPS mGps;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            long l = Long.parseLong(message.obj.toString());
            if (l != -1L) {
                Intent intent = new Intent(FieldDocOpenAct.this, FieldEditActivity.class);
                intent.putExtra("fieldsaleid", l);
                startActivity(intent);
                if ((customer != null) && (!TextUtils.isEmptyS(customer.getId()))) {
                    new CustomerDAO().updateCustomerValue(customer.getId(), "isfinish", "1");
                }
                finish();
                return;
            }
            toast("操作失败");
        }
    };

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
        mGps = new GPS(getApplicationContext(), this);
    }

    private FieldSale makeFieldsaleForm() {
        FieldSale fieldSale = new FieldSale();
        if (this.customer != null) {
            fieldSale.setCustomerid(this.customer.getId());
            fieldSale.setCustomername(this.customer.getName());
            fieldSale.setIsnewcustomer(this.customer.getIsNew());
            if (!TextUtils.isEmptyS(this.customer.getPromotionId())) {
                Promotion v9 = new PromotionDAO().getPromotion(this.customer.getPromotionId());
                try {
                    if (v9 == null) {
                        fieldSale.setPromotionid(null);
                    } else {
                        long v0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(v9.getBeginTime()).getTime();
                        long v7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(v9.getEndtime()).getTime();
                        long v2 = Utils.getCurrentTime(false);
                        if (v2 > v0 && v2 < v7) {
                            fieldSale.setPromotionid(this.customer.getPromotionId());
                        } else {
                            fieldSale.setPromotionid(null);
                        }
                    }
                } catch (ParseException v6) {
                    v6.printStackTrace();
                }
            }
        }
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
        if (this.mLocation != null) {
            fieldSale.setLongitude(this.mLocation.getLongitude());
            fieldSale.setLatitude(this.mLocation.getLatitude());
            fieldSale.setAddress(this.mLocation.getAddress());
        } else {
            fieldSale.setLongitude(0);
            fieldSale.setLatitude(0);
            fieldSale.setAddress(null);
        }
        fieldSale.setRemark(this.etRemark.getText().toString());
        return fieldSale;
    }

    @OnClick({R.id.btnCustomer, R.id.btnOpenFieldDoc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCustomer:
                startActivityForResult(new Intent(this, CustomerSearchAct.class), INTENT_CODE_CUSTOMER);
                break;
            case R.id.btnOpenFieldDoc:
                PDH.show(this, new PDH.ProgressCallBack() {
                    @Override
                    public void action() {
                        FieldSale localFieldSale = makeFieldsaleForm();
                        long l = new FieldSaleDAO().addFieldsale(localFieldSale);
                        handler.sendMessage(handler.obtainMessage(0, l));
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INTENT_CODE_CUSTOMER) {
            customer = (Customer) data.getSerializableExtra("customer");
            if (customer == null) {
                this.btnCustomer.setText("客户");
            } else {
                this.btnCustomer.setText(customer.getName());
                this.btnCustomer.setTag(customer.getId());
            }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLocation = aMapLocation;
        tvGetLocation.setText(mGps.getmAddress());
    }

}
