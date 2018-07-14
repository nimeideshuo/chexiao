package com.sunwuyou.swymcx.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class SettingActivity extends BaseHeadActivity {
    @Override
    public int getLayoutID() {
        return R.layout.act_setting;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.setting_detault_updata, R.id.setting_about_app})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_detault_updata:
                break;
            case R.id.setting_about_app:
                toast("点击");
                break;
        }
    }
}
