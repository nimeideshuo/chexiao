package com.sunwuyou.swymcx.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.dao.CustomerTypeDAO;
import com.sunwuyou.swymcx.dao.RegionDAO;
import com.sunwuyou.swymcx.dao.VisitLineDAO;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.Customertype;
import com.sunwuyou.swymcx.model.Region;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.model.VisitLine;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.PinYin4JUtils;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.List;

/**
 * Created by admin
 * 2018/7/23.
 * content
 */

public class NewCustomerAddAct extends BaseHeadActivity implements View.OnClickListener {

    private Customer updateCustomer;
    private Button btnCusRegion;
    private Button btnCusType;
    private Button btnCusVisitLine;
    private String cusRegionId;
    private String cusVisitLineId;
    private Customer customer;
    private Customertype customertype;
    private EditText etAddress;
    private EditText etCusBankAccount;
    private EditText etCusContactMobile;
    private EditText etCusId;
    private EditText etCusName;
    private EditText etCusRemark;
    private EditText etCusTelephone;
    private boolean isAppointedVisitLine;
    private AutoCompleteTextView actvCusBank;
    private Button btnAddCustomer;
    private List<Customertype> mapTypes;
    private List<Region> mapRegions;
    private List<VisitLine> mapVisitLines;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (Boolean.parseBoolean(msg.obj.toString())) {
                PDH.showSuccess("操作成功");
                if (updateCustomer != null) {
                    new CustomerDAO().updateCustomerDocs(updateCustomer.getId(), customer.getId());
                }
                Intent intent = new Intent();
                intent.putExtra("newcustomer", customer);
                intent.putExtra("refresh", true);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                PDH.showFail("操作失败");
            }
        }
    };
    private View.OnClickListener btnAddCustomerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String v0 = etCusId.getText().toString();
            if (TextUtils.isEmpty(v0)) {
                PDH.showError("客户编号为必填");
            } else if (TextUtils.isEmpty(etCusName.getText().toString())) {
                PDH.showError("客户姓名为必填! ");
            } else if (TextUtils.isEmpty(etCusContactMobile.getText().toString())) {
                PDH.showError("客户电话必填! ");
            } else if (TextUtils.isEmpty(etAddress.getText().toString())) {
                PDH.showError("客户地址必填! ");
            } else {
                if (new CustomerDAO().isExists(v0)) {
                    if (updateCustomer == null) {
                        PDH.showFail("当前客户编号已经存在");
                        return;
                    } else if (!v0.equals(updateCustomer.getId())) {
                        PDH.showFail("当前客户编号已经存在");
                        return;
                    }
                }
                customer = makeForm();
                PDH.show(NewCustomerAddAct.this, "正在执行操作...", new PDH.ProgressCallBack() {
                    public void action() {
                        customer.setIsNew(true);
                        if (updateCustomer == null) {
                            customer.setIsFinish(false);
                            customer.setOrderNo(new CustomerDAO().getMaxOrderNo() + 1);
                            new CustomerDAO().addCustomer(customer);
                            handler.sendMessage(handler.obtainMessage(0, true));
                        } else {
                            customer.setIsFinish(updateCustomer.getIsFinish());
                            customer.setIsNew(updateCustomer.getIsNew());
                            customer.setOrderNo(updateCustomer.getOrderNo());
                            new CustomerDAO().updateCustomer(updateCustomer.getId(), customer);
                            handler.sendMessage(handler.obtainMessage(0, true));
                        }
                    }
                });
            }
        }
    };

    private Customer makeForm() {
        Customer v0 = new Customer();
        v0.setName(this.etCusName.getText().toString().trim());
        v0.setId(this.etCusId.getText().toString().trim());
        v0.setPinyin(PinYin4JUtils.getPinYinHeadChar(this.etCusName.getText().toString().trim()));
        v0.setContactMoblie(this.etCusContactMobile.getText().toString().trim());
        v0.setTelephone(this.etCusTelephone.getText().toString().trim());
        v0.setCustomerTypeId(this.customertype.getId());
        v0.setPriceSystemId(new CustomerTypeDAO().getCTPriceSystemID(v0.getCustomerTypeId()));
        v0.setRegionId(this.cusRegionId);
        v0.setVisitLineId(this.cusVisitLineId);
        v0.setDepositBank(this.actvCusBank.getText().toString());
        v0.setBankingAccount(this.etCusBankAccount.getText().toString());
        v0.setRemark(this.etCusRemark.getText().toString());
        v0.setAddress(this.etAddress.getText().toString());
        v0.setPriceSystemId(this.customertype.getPricesystemid());
        v0.setIsusecustomerprice(Utils.USE_CUSTOMER_PRICE);
        return v0;
    }

    @Override
    public int getLayoutID() {
        return R.layout.add_customer_dialog;
    }

    @Override
    public void initView() {
        setTitle("新增客户");
        String v0 = this.getIntent().getStringExtra("updatecustomerid");
        if (!TextUtils.isEmptyS(v0)) {
            updateCustomer = new CustomerDAO().getCustomer(v0, true);
        }
        if ((Utils.isDownloadCustomerByVisitLine) && !TextUtils.isEmpty(SystemState.getObject("cu_user", User.class).getVisitLineId())) {
            this.isAppointedVisitLine = true;
        }
        init();
        this.loadData();
    }

    private void loadData() {
        mapTypes = new CustomerTypeDAO().queryAllcuCustomertypes();
        if (mapTypes.isEmpty()) {
            PDH.showFail("客户分类信息不存在");
            this.finish();
        } else {
            if (this.updateCustomer != null) {
                for (int i = 0; i < mapTypes.size(); i++) {
                    if (this.mapTypes.get(i).getId().equals(this.updateCustomer.getCustomerTypeId())) {
                        this.customertype = this.mapTypes.get(i);
                        break;
                    }
                }
            } else {
                this.customertype = this.mapTypes.get(0);
            }
            this.btnCusType.setText(this.customertype.getName());
            this.mapRegions = new RegionDAO().getAllRegions();
            Region v1_1 = null;
            if (this.mapRegions != null && this.mapRegions.size() != 0) {
                if (this.updateCustomer != null) {
                    for (int i = 0; i < mapRegions.size(); i++) {
                        if (this.mapRegions.get(i).getId().equals(this.updateCustomer.getRegionId())) {
                            v1_1 = this.mapRegions.get(i);
                            break;
                        }
                    }
                } else {
                    v1_1 = this.mapRegions.get(0);
                }
                assert v1_1 != null;
                this.cusRegionId = v1_1.getId();
                this.btnCusRegion.setText(v1_1.getName());
                if (!this.isAppointedVisitLine) {
                    mapVisitLines = new VisitLineDAO().getAllVisitLines();
                    VisitLine v3_1 = null;
                    if (!mapVisitLines.isEmpty()) {
                        if (this.updateCustomer != null) {
                            for (int i = 0; i < mapVisitLines.size(); i++) {
                                if (mapVisitLines.get(i).getId().equals(this.updateCustomer.getVisitLineId())) {
                                    v3_1 = mapVisitLines.get(i);
                                }
                            }
                        } else {
                            v3_1 = mapVisitLines.get(0);
                        }

                        assert v3_1 != null;
                        this.cusVisitLineId = v3_1.getId();
                        this.btnCusVisitLine.setText(v3_1.getName());
                    }
                } else {
                    this.cusVisitLineId = SystemState.getObject("cu_user", User.class).getVisitLineId();
                    if (mapVisitLines == null) {
                        return;
                    }
                    if (this.mapVisitLines.size() <= 0) {
                        return;
                    }
                    if (this.updateCustomer == null) {
                        return;
                    }
                    for (int i = 0; i < this.mapVisitLines.size(); i++) {
                        if (this.cusVisitLineId.equals(this.mapVisitLines.get(i).getId())) {
                            this.btnCusVisitLine.setText(this.mapVisitLines.get(i).getName());
                            break;
                        }
                    }
                }
                //TODO 代码有问题 等待调试
                this.setText();
                //                PDH.showFail("地区信息不存在");
                //                this.finish();
            }
        }
    }

    private void init() {
        this.btnCusRegion = this.findViewById(R.id.btnCusRegion);
        this.btnCusType = this.findViewById(R.id.btnCusType);
        this.btnCusVisitLine = this.findViewById(R.id.btnCusVisitLine);
        this.btnCusRegion.setOnClickListener(this);
        this.btnCusType.setOnClickListener(this);
        this.btnCusVisitLine.setOnClickListener(this);
        this.etCusName = this.findViewById(R.id.etCusName);
        this.etCusId = this.findViewById(R.id.etCusId);
        this.etCusTelephone = this.findViewById(R.id.etCusTelephone);
        this.etCusContactMobile = this.findViewById(R.id.etCusContactMobile);
        this.etCusRemark = this.findViewById(R.id.etCusRemark);
        this.etCusBankAccount = this.findViewById(R.id.etCusBankAccount);
        this.etAddress = this.findViewById(R.id.etAddress);
        actvCusBank = this.findViewById(R.id.actvCusBank);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.getResources().getStringArray(R.array.banks));
        actvCusBank.setAdapter(adapter);
        this.actvCusBank.setThreshold(1);
        btnAddCustomer = this.findViewById(R.id.btnAddCustomer);
        this.btnAddCustomer.setOnClickListener(this.btnAddCustomerListener);
        if (this.isAppointedVisitLine) {
            this.findViewById(R.id.llCusVisitLine).setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCusRegion:
                //地区
                this.startActivityForResult(new Intent().setClass(this, RegionSearchAct.class), 1);
                break;
            case R.id.btnCusType:
                //客户
                this.startActivityForResult(new Intent().setClass(this, CustomerTypeSearchAct.class), 0);
                break;
            case R.id.btnCusVisitLine:
                this.startActivityForResult(new Intent().setClass(this, VisitLineSearchAct.class), 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    this.customertype = (Customertype) data.getSerializableExtra("customertype");
                    this.btnCusType.setText(this.customertype.getName());
                    break;
                case 1:
                    Region region = (Region) data.getSerializableExtra("region");
                    this.cusRegionId = region.getId();
                    this.btnCusRegion.setText(region.getName());
                    break;
                case 2:
                    VisitLine visitline = (VisitLine) data.getSerializableExtra("visitline");
                    this.cusVisitLineId = visitline.getId();
                    this.btnCusVisitLine.setText(visitline.getName());
                    break;
            }

        }
    }

    private void setText() {
        this.etCusId.setText(this.updateCustomer.getId());
        this.etCusName.setText(this.updateCustomer.getName());
        this.etCusContactMobile.setText(this.updateCustomer.getContactMoblie());
        this.etCusTelephone.setText(this.updateCustomer.getTelephone());
        this.etCusBankAccount.setText(this.updateCustomer.getBankingAccount());
        this.actvCusBank.setText(this.updateCustomer.getDepositBank());
        this.etCusRemark.setText(this.updateCustomer.getRemark());
        this.etAddress.setText(this.updateCustomer.getAddress());
    }

    public void setActionBarText() {
        setTitle("新增客户");
    }
}
