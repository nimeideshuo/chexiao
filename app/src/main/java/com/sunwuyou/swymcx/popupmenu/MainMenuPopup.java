package com.sunwuyou.swymcx.popupmenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.dou361.dialogui.DialogUIUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.ui.center.SettingActivity;
import com.sunwuyou.swymcx.view.MessageDialog;
import com.sunwuyou.swymcx.view.ServerIpDialog;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class MainMenuPopup extends PopupWindow implements View.OnClickListener {
    private final View root;
    private Activity act;
    private ServerIpDialog seviceDialog;

    public MainMenuPopup(Activity context) {
        super(context);
        this.act = context;
        root = LayoutInflater.from(context).inflate(R.layout.popup_menu_main, null);
        setContentView(this.root);
        Button btnClear = this.root.findViewById(R.id.btnClear);
        Button btnExsit = this.root.findViewById(R.id.btnExsit);
        Button btnSetting = this.root.findViewById(R.id.btnSetting);
        Button btnIp = this.root.findViewById(R.id.btnIp);
        btnClear.setOnClickListener(this);
        btnExsit.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnIp.setOnClickListener(this);
        setAnimationStyle(R.style.buttom_in_out);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        int j = localDisplayMetrics.heightPixels;
        setWidth(i);
        setHeight(j / 11);
        setBackgroundDrawable(null);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        WindowManager.LayoutParams params = act.getWindow().getAttributes();
        params.alpha = 1.0F;
        this.act.getWindow().setAttributes(params);
        switch (v.getId()) {
            case R.id.btnClear:
                //注销用户
                new MessageDialog(act).showDialog("提示", "确定注销当前用户?", null, null, new MessageDialog.CallBack() {
                    @Override
                    public void btnOk(View view) {
                        SystemState.setValue("cu_user", null);
                        MyApplication.getInstance().splash();
                    }

                    @Override
                    public void btnCancel(View view) {

                    }
                });
                break;
            case R.id.btnExsit:
                new MessageDialog(act).showDialog("提示", "确定退出当前程序?", null, null, new MessageDialog.CallBack() {
                    @Override
                    public void btnOk(View view) {
                        act.finish();
                    }

                    @Override
                    public void btnCancel(View view) {

                    }
                });
                break;
            case R.id.btnSetting:
                act.startActivity(new Intent(act, SettingActivity.class));
                break;
            case R.id.btnIp:
                final AccountPreference ap = new AccountPreference();
                //验证是否正确
                seviceDialog = new ServerIpDialog(act).showDialog("服务器地址", ap.getServerIp(), null, null, new ServerIpDialog.CallBack() {
                    @Override
                    public void btnOk(View view) {
                        String input = seviceDialog.getmInput();
                        //验证是否正确
                        ap.setServerIp(input);
                        BaseUrl.setUrl(input);
                        DialogUIUtils.showToast("程序即将重新启动");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MyApplication.getInstance().splash();
                            }
                        }, 1000);
                    }

                    @Override
                    public void btnCancel(View view) {

                    }
                });
                break;
        }

    }
}
