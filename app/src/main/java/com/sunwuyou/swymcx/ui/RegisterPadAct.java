package com.sunwuyou.swymcx.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dou361.dialogui.DialogUIUtils;
import com.immo.libcomm.utils.TextUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.request.TerminalEntity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin
 * 2018/7/13.
 * content
 */

public class RegisterPadAct extends BaseHeadActivity {
    @BindView(R.id.etRegisterId)
    EditText etRegisterId;
    @BindView(R.id.etPadUser)
    EditText etPadUser;

    @Override
    public int getLayoutID() {
        return R.layout.dia_register;
    }

    @Override
    public void initView() {
        setTitle("注册");
    }

    @Override
    public void initData() {


    }


    @OnClick({R.id.btnRegister, R.id.btnExit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                String registerId = etRegisterId.getText().toString();
                String registerName = etPadUser.getText().toString();
                if (TextUtils.isEmpty(registerId)) {
                    toast("请输入注册码");
                    return;
                }
                if (TextUtils.isEmpty(registerName)) {
                    toast("请输入注册人");
                    return;
                }
                //注册
                registerUser(registerId, registerName);
                break;
            case R.id.btnExit:
                finish();
                break;
        }
    }

    private void registerUser(String registerId, String registerName) {
        TerminalEntity bean = new TerminalEntity(MyApplication.getInstance().getUniqueCode(), "mchexiaoban");
        bean.setRegistrationCode(registerId);
        bean.setIdentifier(MyApplication.getInstance().getUniqueCode());
        bean.setModel(Build.MODEL);
        bean.setMac(MyApplication.getInstance().getMac());
        bean.setOwner(registerName);
        bean.setLoginName(null);
        bean.setPC(false);
        bean.setVersionKey("mchexiaoban");
        HashMap<String, String> map = new HashMap<>();
        map.put("parameter", JSON.toJSONString(bean));
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                startActivity(new Intent(RegisterPadAct.this, SplashAct.class));
                finish();
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SYSTEM_REGISTER), this, map, null, null, true, 0);
    }
}
