package com.sunwuyou.swymcx.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.model.AccountSetEntity;
import com.sunwuyou.swymcx.request.TerminalEntity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * 2018/7/13.
 * content
 */

public class SplashAct extends Activity {
    private static final int REQUEST_CODE_PERMISSION_MULTI = 101;
    @BindView(R.id.loading)
    TextView loading;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.edition)
    TextView edition;
    @BindView(R.id.copyright)
    TextView copyright;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
        getNet();
    }

    private void initView() {
        //设置版本版本名
        version.setText(AppUtils.getAppVersionName());
        getPerMission(this);
        loading.setText(AppUtils.getAppName());
    }

    private void getNet() {
        HashMap<String, String> map = new HashMap<>();
        TerminalEntity terminalEntity = new TerminalEntity();
        terminalEntity.setIdentifier(MyApplication.getInstance().getUniqueCode());
        terminalEntity.setVersionKey("mchexiaoban");
        map.put("parameter", JSON.toJSONString(terminalEntity));
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                if (response.contains("register")) {
                    //未注册去注册
                    startActivity(new Intent(SplashAct.this, RegisterPadAct.class));
                    finish();
                } else {
                    querySaccountNet();
                }
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SYSTEM_CHECKREGISTER), this, map, null, null, true, 0);
    }

    //查询工作账套
    private void querySaccountNet() {
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                List<AccountSetEntity> entityList = JSON.parseArray(response, AccountSetEntity.class);


            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SUPPORT_QUERYSACCOUNTSET), this, null, null, null, true, 0);

    }

    /**
     * 获取权限
     */
    public void getPerMission(final Activity baseActivity) {
        AndPermission.with(baseActivity)
                .requestCode(REQUEST_CODE_PERMISSION_MULTI).permission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WAKE_LOCK
        ).callback(this).rationale(new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                AndPermission.rationaleDialog(baseActivity, rationale).show();
            }
        }).start();
    }
}
