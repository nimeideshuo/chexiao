package com.sunwuyou.swymcx.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.CustomerTypeDAO;
import com.sunwuyou.swymcx.model.Customertype;

import java.util.List;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class CustomerTypeSearchAct extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private List<Customertype> types;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        types = new CustomerTypeDAO().queryAllcuCustomertypes();
        ListView listview = this.findViewById(R.id.listview);
        listview.setOnItemClickListener(this);
        if (!types.isEmpty()) {
            String items[] = new String[types.size()];
            for (int i = 0; i < types.size(); i++) {
                items[i] = types.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            listview.setAdapter(adapter);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("customertype", types.get(position));
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    public void setActionBarText() {
        setTitle("客户分类");
    }
}
