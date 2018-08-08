package com.sunwuyou.swymcx.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.SystemState;
//import com.sunwuyou.swymcx.bmob.molder.BUser;
//import com.sunwuyou.swymcx.bmob.service.UserDao;
import com.sunwuyou.swymcx.bmob.molder.BUser;
import com.sunwuyou.swymcx.bmob.service.UserDao;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpErrorConnnet;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.request.ReqUsrUserLogin;
import com.sunwuyou.swymcx.request.RespUserEntity;
import com.sunwuyou.swymcx.request.TerminalEntity;
import com.sunwuyou.swymcx.ui.login.LoginAct;
import com.sunwuyou.swymcx.ui.login.LoginPassword;
import com.sunwuyou.swymcx.utils.MLog;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.view.AccountSelectDialog;
import com.sunwuyou.swymcx.view.ServerIpDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by admin
 * 2018/7/13.
 * content
 */

public class SplashAct extends Activity {
    private static final int REQUEST_CODE_PERMISSION_MULTI = 101;
    public static int height;
    public static int width;
    @BindView(R.id.loading)
    TextView loading;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.edition)
    TextView edition;
    @BindView(R.id.copyright)
    TextView copyright;
    private ServerIpDialog seviceDialog;
    private AccountPreference ap;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
        DisplayMetrics v0 = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(v0);
        width = v0.widthPixels;
        height = v0.heightPixels;
    }

    private void initView() {
        //设置版本版本名
        version.setText(AppUtils.getAppVersionName());
        getPerMission(this);
        ap = new AccountPreference();
        String serverIp = ap.getServerIp();
        if (serverIp.isEmpty()) {
            seviceDialog = new ServerIpDialog(this).showDialog("服务器地址", "", null, null, new ServerIpDialog.CallBack() {
                @Override
                public void btnOk(View view) {
                    String input = seviceDialog.getmInput();
                    //验证是否正确
                    ap.setServerIp(input);
                    BaseUrl.setUrl(input);
                    checkRegisterNet();
                }

                @Override
                public void btnCancel(View view) {

                }
            });
            return;
        }
        checkRegisterNet();
    }

    private void checkRegisterNet() {
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
                    if (SystemState.getAccountSet() == null) {

                        new AccountSelectDialog(SplashAct.this).showDialog("工作账套");
                        return;
                    }
                    if (SystemState.getUser() == null) {
                        //跳转到登陆模块
                        startActivity(new Intent(SplashAct.this, LoginAct.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashAct.this, LoginPassword.class));
                        finish();
                    }
                }
            }
        }, new HttpErrorConnnet() {
            @Override
            public void loadHttpError(int i) {
                loading.setText("无网络链接");
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SYSTEM_CHECKREGISTER), this, map, null, null, true, 0);


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
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WAKE_LOCK
        ).callback(this).rationale(new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                AndPermission.rationaleDialog(baseActivity, rationale).show();
            }
        }).start();
    }

    @OnClick(R.id.loading)
    public void onViewClicked() {
        seviceDialog = new ServerIpDialog(this).showDialog("服务器地址", ap.getServerIp(), null, null, new ServerIpDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                String input = seviceDialog.getmInput();
                //验证是否正确
                ap.setServerIp(input);
                BaseUrl.setUrl(input);
                checkRegisterNet();
            }

            @Override
            public void btnCancel(View view) {

            }
        });
    }
}
