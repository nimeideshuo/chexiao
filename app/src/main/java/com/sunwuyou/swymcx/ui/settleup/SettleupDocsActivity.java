package com.sunwuyou.swymcx.ui.settleup;

import android.widget.ListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.model.SettleUp;

/**
 * Created by liupiao on
 * 2018/7/31.
 * content
 */
public class SettleupDocsActivity extends BaseHeadActivity {

    private ListView listView;
    private SettleUp settleUp;

    @Override
    public int getLayoutID() {
        return R.layout.act_settleup_docs;
    }

    @Override
    public void initView() {
        listView = this.findViewById(R.id.listView);
        settleUp = new SettleUpDAO().getSettleUp(this.getIntent().getLongExtra("settleupid", -1));
        this.adapter = new ItemAdapter(this, ((Context)this));
        this.listView.setAdapter(this.adapter);
        this.tv_sumamount = this.findViewById(2131296473);
        this.tv_settle_shidhou = this.findViewById(2131296366);
        this.tv_settle_jiesuan = this.findViewById(2131296367);
        this.tv_preference = this.findViewById(2131296474);
        if(this.settleUp.getIsSubmit()) {
            PDH.showMessage("该单据已经上传到服务器");
            this.isModify = false;
        }

        this.listView.setOnItemClickListener(this.onItemClickListener);
    }

    @Override
    public void initData() {

    }
}
