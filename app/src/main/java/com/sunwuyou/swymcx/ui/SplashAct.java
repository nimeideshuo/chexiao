package com.sunwuyou.swymcx.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.immo.libcomm.utils.TextUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseActivity;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.model.AccountSetEntity;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.request.TerminalEntity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin
 * 2018/7/9.
 * content
 */
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
//        SystemState.setValue()
//        this.user = ((User) SystemState.getObject("cu_user", User.class));
        String uniqueCode = MyApplication.getInstance().getUniqueCode();
    }

    @Override
    public void initData() {
//        if(user != null&&!TextUtils.isEmptyS(user.getOfflinepassword())){
//
//
//        }
//        ap = new AccountPreference();

        getNet();
    }

    private void getNet() {
        HashMap<String, Object> map = new HashMap<>();
        TerminalEntity terminalEntity = new TerminalEntity();
        terminalEntity.setIdentifier(MyApplication.getInstance().getUniqueCode());
        terminalEntity.setVersionKey("mchexiaoban");
        map.put("parameter", terminalEntity);
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
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("parameter", new TerminalEntity());
//        new HttpConnect(new HttpListener() {
//            @Override
//            public void loadHttp(Object object, String response) {
//                if (RequestHelper.isSuccess(response)) {//已经注册去登陆
//                    List<AccountSetEntity> entityList = JSON.parseArray(response, AccountSetEntity.class);
//                    showAccountDialog(entityList);
//
//
//
//                } else {
//                    loading.setText("无网络连接");
//                }
//            }
//        }).jsonPost(BaseUrl.getUrl(BaseUrl.SYSTEM_CHECKREGISTER), this, null, AccountSetEntity.class, null, true, 0);
    }

    private void showAccountDialog(List<AccountSetEntity> entityList) {
//        View view = View.inflate(this, R.layout.dia_servicer_ip, null);
//        Dialog show = DialogUIUtils.showCustomAlert(this, view).show();

    }
}
