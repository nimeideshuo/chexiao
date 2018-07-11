package com.sunwuyou.swycx.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.AppUtils;
import com.sunwuyou.swycx.app.BaseActivity;
import com.sunwuyou.swycx.app.SystemState;
import com.sunwuyou.swycx.http.HttpConnect;
import com.sunwuyou.swycx.model.User;

import butterknife.BindView;
import swymcx.sunwuyou.com.R;
import swymcx.sunwuyou.com.base.BasePath;

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

    }

    private void getNet() {
//        new HttpConnect().jsonPost();
    }

}
