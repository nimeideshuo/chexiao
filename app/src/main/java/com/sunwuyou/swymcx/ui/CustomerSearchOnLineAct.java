package com.sunwuyou.swymcx.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.view.AutoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class CustomerSearchOnLineAct extends BaseHeadActivity implements AutoTextView.OnTextChangeListener, AdapterView.OnItemClickListener {
    @BindView(R.id.etSearch) AutoTextView etSearch;
    @BindView(R.id.tvTop) TextView tvTop;
    @BindView(R.id.listview) ListView listview;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        setTitle("客户检索");
        this.etSearch.setVisibility(View.VISIBLE);
        findViewById(R.id.tvTop).setVisibility(View.VISIBLE);
        this.etSearch.setOnTextChangeListener(this);
        this.etSearch.setHint("请输入客户编号、名称、拼音");
        listview.setOnItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onChanged(View v, String str) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
