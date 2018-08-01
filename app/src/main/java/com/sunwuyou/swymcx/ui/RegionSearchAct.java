package com.sunwuyou.swymcx.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.immo.libcomm.utils.TextUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.RegionDAO;
import com.sunwuyou.swymcx.model.Region;
import com.sunwuyou.swymcx.view.AutoTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class RegionSearchAct extends BaseHeadActivity implements AutoTextView.OnTextChangeListener, AdapterView.OnItemClickListener {

    private AutoTextView etSearch;
    private ListView listview;
    private List<Region> types;
    private List<Region> showGoods;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        listview = this.findViewById(R.id.listview);
        etSearch = this.findViewById(R.id.etSearch);
        this.etSearch.setVisibility(View.VISIBLE);
        this.etSearch.setOnTextChangeListener(this);
        this.listview.setOnItemClickListener(this);
        this.etSearch.setHint("地区检索");
        types = new RegionDAO().getAllRegions();
        if (types.isEmpty()) {
            this.finish();
            return;
        }
        // 赋值
        showGoods = new ArrayList<>();
        showGoods = types;
        String[] items = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            items[i] = types.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listview.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onChanged(View v, String str) {
        if (TextUtils.isEmptyS(str)) {
            this.showGoods = this.types;
        } else {
            this.showGoods.clear();
            for (int i = 0; i < this.types.size(); ++i) {
                if ((this.types.get(i).getId().contains(str)) || (this.types.get(i).getName().contains(str))) {
                    this.showGoods.add(this.types.get(i));
                }
            }
        }
        String[] items = new String[showGoods.size()];
        for (int i = 0; i < showGoods.size(); i++) {
            items[i] = showGoods.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listview.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("region", this.types.get(position));
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    public void setActionBarText() {
        setTitle("地区");
    }
}
