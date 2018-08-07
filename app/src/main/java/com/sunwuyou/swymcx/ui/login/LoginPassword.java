package com.sunwuyou.swymcx.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.request.ReqCommonPara;
import com.sunwuyou.swymcx.request.ReqUsrCheckAuthority;
import com.sunwuyou.swymcx.ui.FieldMainAct;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.LockPatternView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class LoginPassword extends BaseActivity implements LockPatternView.OnPatternChangeListener {
    @BindView(R.id.tv_passWord_name) TextView tvPassWordName;
    @BindView(R.id.login_toast) TextView login_toast;
    @BindView(R.id.mLocusPassWordView) LockPatternView lpwv;
    boolean open = true;
    int i = 4;
    private AccountPreference ap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        tvPassWordName.setText(SystemState.getUser().getName());
        lpwv.setOnPatternChangeListener(this);
        ap = new AccountPreference();
        String passWord = ap.getValue("passWord", "");
        if (passWord.length() == 0) {
            login_toast.setText("请输入初始化密码");
        } else {
            login_toast.setText("请输入密码");
            open = false;
        }
    }

    @Override
    public void onPatternChange(String patternPassword) {
        String passWord = ap.getValue("passWord", "");
        if (open) {
            if (patternPassword == null) {
                login_toast.setText("至少4个点");
            } else if (passWord.length() == 0) {
                login_toast.setText("请再次输入密码");
                ap.setValue("passWord", patternPassword);
            } else if (passWord.equals(patternPassword)) {
                startActivity(new Intent(LoginPassword.this, FieldMainAct.class));
                finish();
            } else if (!passWord.equals(patternPassword)) {
                ap.setValue("passWord", "");
                login_toast.setText("请输入初始化密码");
                Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (passWord.equals(patternPassword)) {
                Intent intent = new Intent(LoginPassword.this, FieldMainAct.class);
                // 打开新的Activity
                startActivity(intent);
                finish();
                return;
            }
            if (patternPassword == null) {
                login_toast.setText("至少4个点");
            } else if (i == 0) {
                ap.setValue("passWord", "");
                login_toast.setTextColor(getResources().getColor(android.R.color.black));
                login_toast.setText("请输入初始化密码");
                open = true;
            } else {
                login_toast.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                login_toast.setText("手势错误，你还可以输入" + i + "次");
                i--;
            }
        }
        lpwv.clear();
    }

    @Override
    public void onPatternStarted(boolean isStarted) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        getNet();
    }

    private void getNet() {
        HashMap<String, String> map = new HashMap<>();
        ReqUsrCheckAuthority authority = new ReqUsrCheckAuthority();
        authority.setUserid(SystemState.getUser().getId());
        authority.setAuthority("AllowChangeSalePrice,ViewKCStockBrowse");
        map.put("parameter", JSON.toJSONString(authority));
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                String[] infor = ((String) JSONUtil.parse2Map(response).get("info")).split(",");
                if ("permit".equals(infor[0])) {
                    ap.setValue("ViewKCStockBrowse", "1");
                } else {
                    ap.setValue("ViewKCStockBrowse", "0");
                }
                if ("permit".equals(infor[1])) {
                    ap.setValue("ViewChangeprice", "1");
                } else {
                    ap.setValue("ViewChangeprice", "0");
                }
                getBiz();
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.USER_GETAUTHORITYS), this, map, null, null, false, 0);
    }
    // 获取服务器基本设置
    private void getBiz() {
        HashMap<String, String> map = new HashMap<>();
        ReqCommonPara commonPara = new ReqCommonPara();
        commonPara.setStringValue("'intPricePrecision','intSubtotalPrecision','intReceivablePrecision','intOutDocUnit','intTransferDocUnit','isUseCustomerPrice','strPriceSystemCheXiao','companyname','isUseCurrentPrice','isTuiHuanHuoSamePrice','AllowChangeSalePrice','isCheXiaoAllowModifyPrinted','isUploadLocation','isDownloadCustomerByVisitLine','intGenerateBatch','strBatchPrefix','strBatchSuffix'");
        map.put("parameter", JSON.toJSONString(commonPara));
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                if (ap.saveBizInfo(response)) {
                    Utils.init();
                    ap.setValue("bizinfo", response);
                }
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SYSTEM_GETBIZPARAMETER), this, map, null, null, true, 0);
    }
}
