package com.sunwuyou.swymcx.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;

/**
 * Created by admin on
 * 2018/7/31.
 * content
 */
public class DocTypeListAct extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private String[] doctypes;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        doctypes = new String[]{"收款单", "其他收入单", "费用支出单"};
        ListView listView = this.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctypes);
        listView.setAdapter(localArrayAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setActionBarText() {
        super.setActionBarText();
        setTitle("结算单类型");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0: {
                intent.putExtra("doctype", "63");
                break;
            }
            case 1: {
                intent.putExtra("doctype", "64");
                break;
            }
            case 2: {
                intent.putExtra("doctype", "62");
                break;
            }
            default: {
                intent.putExtra("doctype", "63");
                break;
            }
        }
        intent.putExtra("doctypename", this.doctypes[position]);
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        setTitle("结算单类型");
    }
}
