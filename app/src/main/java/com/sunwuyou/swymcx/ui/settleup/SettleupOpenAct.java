package com.sunwuyou.swymcx.ui.settleup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.DepartmentDAO;
import com.sunwuyou.swymcx.dao.ServerDocDAO;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.dao.SettleUpItemDAO;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.model.ServerDoc;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.model.SettleUpItem;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.ui.CustomerSearchAct;
import com.sunwuyou.swymcx.ui.DepartmentSearchAct;
import com.sunwuyou.swymcx.ui.DocTypeListAct;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.List;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class SettleupOpenAct extends BaseHeadActivity implements View.OnClickListener {
    private Button btnCustomer;
    private Button btnDepartment;
    private Button btnDocType;
    private Button btnOpenSettleupDoc;
    private Customer customer;
    private Department department;
    private String docType;
    private EditText etObjectPeople;
    private String docTypeNmae = "收款单";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (docType.equals("63")) {
                    startActivity(new Intent(SettleupOpenAct.this, SettleupDocsActivity.class).putExtra("settleupid", msg.obj.toString()));
                } else {
                    startActivity(new Intent(SettleupOpenAct.this, OtherSettleupDocActivity.class).putExtra("settleupid", msg.obj.toString()));
                }

                finish();
            } else {
                PDH.showFail(msg.obj.toString());
                btnOpenSettleupDoc.setOnClickListener(onClickListener);
            }
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btnOpenSettleupDoc.setOnClickListener(null);
            if (customer == null) {
                PDH.showMessage("请选择客户");
                btnOpenSettleupDoc.setOnClickListener(onClickListener);
            } else {
                PDH.show(SettleupOpenAct.this, "正在开单...", new PDH.ProgressCallBack() {
                    @Override
                    public void action() {
                        List<ServerDoc> v5 = null;
                        if (docType.equals("63")) {
                            v5 = new ServerDocDAO().getCusServerdocs(customer.getId());
                            if (v5.size() == 0) {
                                handler.sendMessage(handler.obtainMessage(1, "此客户不存在可结算的业务单据"));
                                btnOpenSettleupDoc.setOnClickListener(onClickListener);
                            } else {
                                long v1 = new SettleUpDAO().AddSettleUp(makeForm());
                                if (v1 == -1) {
                                    handler.sendMessage(handler.obtainMessage(1, "开单失败"));
                                    btnOpenSettleupDoc.setOnClickListener(onClickListener);
                                } else {
                                    SettleUpItemDAO v7 = new SettleUpItemDAO();
                                    for (int i = 0; i < v5.size(); i++) {
                                        ServerDoc serverDoc = v5.get(i);
                                        SettleUpItem upItem = new SettleUpItem();
                                        upItem.setDocid(serverDoc.getDocid());
                                        upItem.setDocshowid(serverDoc.getDocshowid());
                                        upItem.setDoctime(serverDoc.getDoctime());
                                        upItem.setDoctype(serverDoc.getDoctype());
                                        upItem.setDoctypename(serverDoc.getDoctypename());
                                        upItem.setReceivableamount(serverDoc.getReceivableamount());
                                        upItem.setReceivedamount(serverDoc.getReceivedamount());
                                        upItem.setThisamount(serverDoc.getLeftamount());
                                        upItem.setLeftamount(serverDoc.getLeftamount());
                                        upItem.setSettleupid(v1);
                                        v7.AddSettleUpItem(upItem);
                                    }
                                    handler.sendMessage(handler.obtainMessage(0, v1));
                                }
                            }
                        }
                    }
                });
            }
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.dia_settleup_open;
    }

    @Override
    public void initView() {
        this.btnDepartment = this.findViewById(R.id.btnDepartment);
        this.btnDocType = this.findViewById(R.id.btnDocType);
        this.btnCustomer = this.findViewById(R.id.btnCustomer);
        this.btnOpenSettleupDoc = this.findViewById(R.id.btnOpenSettleupDoc);
        this.btnDepartment.setOnClickListener(this);
        this.btnDocType.setOnClickListener(this);
        this.btnCustomer.setOnClickListener(this);
        this.btnOpenSettleupDoc.setOnClickListener(this.onClickListener);
        this.etObjectPeople = this.findViewById(R.id.etObjectPeople);
        if (!new DepartmentDAO().isExist()) {
            PDH.showFail("部门不存在，请同步数据");
            this.finish();
        } else {
            this.department = SystemState.getDepartment();
            this.btnDepartment.setText(this.department.getDname());
            this.btnDocType.setText(this.docTypeNmae);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void setActionBarText() {
        super.setActionBarText();
        setTitle("结算");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDepartment:
                this.startActivityForResult(new Intent(this, DepartmentSearchAct.class), 11);
                break;
            case R.id.btnDocType:
                this.startActivityForResult(new Intent(this, DocTypeListAct.class), 0);
                break;
            case R.id.btnCustomer:
                this.startActivityForResult(new Intent(this, CustomerSearchAct.class).putExtra("showfirst", 0), 10);
                break;
        }
    }

    private SettleUp makeForm() {
        User v1 = SystemState.getObject("cu_user", User.class);
        SettleUp v0 = new SettleUp();
        v0.set0bjectId(this.customer.getId());
        v0.setObjectName(this.customer.getName());
        v0.setBuilderId(((User) v1).getId());
        v0.setBuilderName(((User) v1).getName());
        v0.setBuildTime(Utils.formatDate(Utils.getCurrentTime(true)));
        v0.setDepartmentId(this.department.getDid());
        v0.setDepartmentName(this.department.getDname());
        v0.setType(this.docType);
        v0.setObjectOperator(this.etObjectPeople.getText().toString());
        v0.setPreference(0);
        v0.setRemark("");
        v0.setIsSubmit(false);
        return v0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    this.docType = data.getStringExtra("doctype");
                    this.docTypeNmae = data.getStringExtra("doctypename");
                    this.btnDocType.setText(this.docTypeNmae);
                    break;
                case 10:
                    this.customer = (Customer) data.getSerializableExtra("customer");
                    this.btnCustomer.setText(this.customer.getName());
                    break;
                case 11:
                    this.department = (Department) data.getSerializableExtra("department");
                    this.btnDepartment.setText(this.department.getDname());
                    break;
            }
        }
    }
}
