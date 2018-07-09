package swymcx.sunwuyou.com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.immo.libcomm.base.BaseActivity;

import swymcx.sunwuyou.com.R;
import swymcx.sunwuyou.com.base.BasePath;
import swymcx.sunwuyou.com.utils.NetUtils;

/**
 * Created by admin
 * 2018/7/9.
 * content
 */
@Route(path = BasePath.activity_splash)
public class SplashAct extends BaseActivity {

    @butterknife.BindView(R.id.imageView)
    ImageView imageView;
    @butterknife.BindView(R.id.loading)
    TextView loading;
    @butterknife.BindView(R.id.version)
    TextView version;
    @butterknife.BindView(R.id.edition)
    TextView edition;
    @butterknife.BindView(R.id.copyright)
    TextView copyright;

    @Override
    public View getLayoutID() {
        return View.inflate(this, R.layout.activity_splash, null);
    }

    @Override
    public void initView() {
        version.setText("eeeeeeeeeeeee");
        loading.setText("eeeeeeeeeeeee");
    }

    @Override
    public void initData() {
        if (!NetUtils.isNetConnect(this)) {
            return;
        }
    }
}
