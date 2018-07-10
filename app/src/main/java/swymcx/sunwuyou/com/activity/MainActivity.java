package swymcx.sunwuyou.com.activity;

import android.view.View;

import com.immo.libcomm.base.BaseHeadActivity;
import com.immo.libcomm.http.HttpConnect;
import com.immo.libcomm.http.HttpErrorConnnet;

import swymcx.sunwuyou.com.R;

public class MainActivity extends BaseHeadActivity {

    @Override
    public View getLayoutID() {
        return View.inflate(this, R.layout.activity_main, null);
    }

    @Override
    public void initView() {
        setTitle("main");
    }

    @Override
    public void initData() {

    }

    public void start(View view) {
        toast("点击了");
        getNet();
    }

    public void getNet() {
        String url="http://192.168.1.5:9682/system/checkregister";
        String json="parameter={\"Identifier\":\"92E1C0A93A5D75D7862915032546356862915037950835\",\"IsPC\":false,\"LoginName\":null,\"Mac\":null,\"Model\":null,\"Owner\":null,\"RegistrationCode\":null,\"VersionKey\":\"mchexiaoban\",\"mac\":null}";
        new HttpConnect(new HttpErrorConnnet() {
            @Override
            public void loadHttpError(int i) {

            }
        }).jsonPost(url,this,json,String.class,null,true,0);

    }
}
