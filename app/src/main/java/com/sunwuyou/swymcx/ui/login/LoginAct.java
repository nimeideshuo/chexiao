package com.sunwuyou.swymcx.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.request.ReqUsrUserLogin;
import com.sunwuyou.swymcx.request.RespUserEntity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin
 * 2018/7/14.
 * content 登陆模块
 */

public class LoginAct extends BaseHeadActivity {


    private static final String TAG = LoginAct.class.getSimpleName();
    @BindView(R.id.et_username) EditText etUsername;
    @BindView(R.id.et_password) EditText etPassword;

    @Override
    public int getLayoutID() {
        return R.layout.act_login;
    }

    @Override
    public void initView() {
        setBackVisibility(false);
        setTitle("登陆");
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.button1)
    public void onViewClicked() {
        if (etUsername.getText().toString().isEmpty()) {
            toast("请输入用户名!");
            return;
        }
        if (etPassword.getText().toString().isEmpty()) {
            toast("请输入密码!");
            return;
        }
        //  登陆验证
        checkUserNet();
    }

    private void checkUserNet() {
        HashMap<String, String> map = new HashMap<>();
        ReqUsrUserLogin ben = new ReqUsrUserLogin(BaseUrl.BASE_NAME, etPassword.getText().toString(), etUsername.getText().toString());
        map.put("parameter", JSON.toJSONString(ben));
        new HttpConnect(new HttpListener() {
            @Override
            public void loadHttp(Object object, String response) {
                if (object instanceof RespUserEntity) {
                    RespUserEntity userRntity = (RespUserEntity) object;
                    User user = new User();
                    user.setId(userRntity.getId());
                    user.setIsaccountmanager(userRntity.isaccountmanager());
                    user.setName(userRntity.getName());
                    user.setVisitlineid(userRntity.getVisitlineid());
                    user.setGoodsclassid(userRntity.getGoodsclassid());
                    user.setPassword("");
                    user.setOfflinepassword("");
                    SystemState.saveObject("cu_user", user);
                    SystemState.saveObject("gpsinterval", userRntity.getGpsinterval());
                    //跳转到 九宫格密码验证
                    startActivity(new Intent(LoginAct.this, LoginPassword.class));
                    finish();
                }
            }
        }).jsonPost(BaseUrl.getUrl(BaseUrl.USER_USERLOGIN), this, map, RespUserEntity.class, null, true, 0);


    }
}
