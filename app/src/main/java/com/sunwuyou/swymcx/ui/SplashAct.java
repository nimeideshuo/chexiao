package com.sunwuyou.swymcx.ui;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.dou361.dialogui.DialogUIUtils;
import com.immo.libcomm.utils.TextUtils;
import com.sunwuyou.swycx.R;
import com.sunwuyou.swycx.app.AccountPreference;
import com.sunwuyou.swycx.app.BaseActivity;
import com.sunwuyou.swycx.app.BasePath;
import com.sunwuyou.swycx.app.RequestHelper;
import com.sunwuyou.swycx.app.SystemState;
import com.sunwuyou.swycx.http.BaseUrl;
import com.sunwuyou.swycx.http.HttpConnect;
import com.sunwuyou.swycx.http.HttpListener;
import com.sunwuyou.swycx.model.AccountSetEntity;
import com.sunwuyou.swycx.model.User;
import com.sunwuyou.swycx.request.TerminalEntity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin
 * 2018/7/9.
 * content
 */
@Route(path = BasePath.activity_splash)
public class SplashAct extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.loading)
    TextView loading;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.edition)
    TextView edition;
    @BindView(R.id.copyright)
    TextView copyright;
    User user;
    AccountPreference ap;

    @Override
    public View getLayoutID() {
        return View.inflate(this, R.layout.activity_splash, null);
    }

    @Override
    public void initView() {
        //设置版本版本名
        version.setText(AppUtils.getAppVersionName());
        this.user = ((User) SystemState.getObject("cu_user", User.class));
    }

    @Override
    public void initData() {
        if(user != null&&!TextUtils.isEmptyS(user.getOfflinepassword())){


        }
        ap = new AccountPreference();

        getNet();
    }

    private void getNet() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("parameter", new TerminalEntity());
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                if (RequestHelper.isSuccess(response)) {//已经注册去登陆
                    querySaccountSet();
                } else {
                    loading.setText("无网络连接");
                }
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SYSTEM_CHECKREGISTER), this, JSON.toJSONString(map), TerminalEntity.class, null, true, 0);
    }

    //查询工作账套
    private void querySaccountSet() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("parameter", new TerminalEntity());
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                if (RequestHelper.isSuccess(response)) {//已经注册去登陆
                    List<AccountSetEntity> entityList = JSON.parseArray(response, AccountSetEntity.class);
                    showAccountDialog(entityList);



                } else {
                    loading.setText("无网络连接");
                }
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.SYSTEM_CHECKREGISTER), this, null, AccountSetEntity.class, null, true, 0);
    }

    private void showAccountDialog(List<AccountSetEntity> entityList) {
//        View view = View.inflate(this, R.layout.dia_servicer_ip, null);
//        Dialog show = DialogUIUtils.showCustomAlert(this, view).show();

    }
}
