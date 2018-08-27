package com.sunwuyou.swymcx.ui.center;

import android.content.Intent;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.print.BTPrinter;
import com.sunwuyou.swymcx.ui.PrefsFragment;
import com.sunwuyou.swymcx.utils.TextUtils;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class SettingActivity extends BaseHeadActivity {

    private PrefsFragment pref;
    private AccountPreference ap;

    @Override
    public int getLayoutID() {
        return R.layout.act_setting_top;
    }

    @Override
    public void initView() {
        pref = new PrefsFragment();
        getFragmentManager().beginTransaction().replace(R.id.base_fragment, pref).commit();
        ap = new AccountPreference();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            BTPrinter v0 = this.ap.getPrinter();
            if (v0 != null) {
                this.pref.getMyPreference(R.string.default_printer).setSummary(String.valueOf(v0.getName()) + "[" + v0.getAddress() + "]");
            } else {
                this.pref.getMyPreference(R.string.default_printer).setSummary("无打印机");
            }
        }
    }

    protected void onResume() {
        super.onResume();
        if(this.pref == null) {
            this.pref = new PrefsFragment();
        }

        this.pref.getCustomerPre().setSummary(TextUtils.setTextStyle("上次同步时间：", this.ap.getValue("customer_data_updateime", "未同步")));
    }

    public void setActionBarText() {
        setTitle("设置");
    }

}
