package swymcx.sunwuyou.com.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.AppUtils;
import com.immo.libcomm.base.BaseActivity;

import butterknife.BindView;
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

    @Override
    public View getLayoutID() {
        return View.inflate(this, R.layout.activity_splash, null);
    }

    @Override
    public void initView() {
        //设置版本版本名
        version.setText(AppUtils.getAppVersionName());
    }

    @Override
    public void initData() {

    }


}
