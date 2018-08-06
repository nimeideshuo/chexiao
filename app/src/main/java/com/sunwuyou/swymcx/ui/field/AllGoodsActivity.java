package com.sunwuyou.swymcx.ui.field;

import android.app.ProgressDialog;
import android.content.Context;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.view.BounceListView;
import com.sunwuyou.swymcx.view.MyLetterListView;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class AllGoodsActivity extends BaseHeadActivity {

    private ProgressDialog progressDialog;
    private AccountPreference ap;
    private BounceListView bounceListView;
    private MyLetterListView myLetterListView;

    @Override
    public int getLayoutID() {
        return R.layout.act_field_all_goods;
    }

    @Override
    public void initView() {
        bounceListView = this.findViewById(R.id.listView);
        myLetterListView = this.findViewById(R.id.myLetterListView);
        this.myLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener(this, null));
        this.myLetterListView.setChooseChar("#");
        this.atvSearch = this.findViewById(2131296409);
        this.atvSearch.setOnTextChangeListener(this.changeListener);
        progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setCancelable(false);
        ap = new AccountPreference();
    }

    @Override
    public void initData() {

    }

    @Override
    public void setActionBarText() {
        super.setActionBarText();
        setTitle("产品手册");
    }
}
