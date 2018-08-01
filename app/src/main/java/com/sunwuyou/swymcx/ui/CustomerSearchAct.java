package com.sunwuyou.swymcx.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.AutoTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin
 * 2018/7/18.
 * content 客户检索
 */

public class CustomerSearchAct extends BaseHeadActivity implements AutoTextView.OnTextChangeListener, AdapterView.OnItemClickListener {
    @BindView(R.id.etSearch)
    AutoTextView etSearch;
    @BindView(R.id.tvTop)
    TextView tvTop;
    @BindView(R.id.listview)
    ListView listview;
    List<Customer> temp;
    private List<Customer> customers;
    private ArrayList<Customer> list;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        setTitle("客户");
    }

    @Override
    public void initData() {
        this.etSearch.setOnTextChangeListener(this);
        this.listview.setOnItemClickListener(this);
        etSearch.setVisibility(View.VISIBLE);
        tvTop.setVisibility(View.VISIBLE);
        loadData();
    }

    private void loadData() {
        list = new ArrayList<Customer>();
        temp = new ArrayList<Customer>();
        customers = new CustomerDAO().queryAllCustomer();

        this.temp.addAll(this.customers);

        if ((this.customers == null) || (this.customers.size() == 0)) {
            return;
        }
        String[] arrayOfString = new String[customers.size()];
        for (int i = 0; i < customers.size(); i++) {
            arrayOfString[i] = ((Customer) this.customers.get(i)).getName();
        }
        ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(CustomerSearchAct.this,
                android.R.layout.simple_list_item_1, arrayOfString);
        listview.setAdapter(localArrayAdapter);
    }

    @Override
    public void onChanged(View v, String str) {
        temp.clear();
        list.clear();
        if (str.length() == 0) {
            loadData();
        }
        if (!TextUtils.isEmptyS(str)) {
            this.temp.addAll(customers);
            for (int k = 0; k < temp.size(); k++) {

                String localString = "";
                int i = 0;
                String[] arrayOfString1 = null;
                if (i < this.customers.size()) {
                    arrayOfString1 = Utils.CUSTOMER_CHECK_SELECT.split(",");
                }
                assert arrayOfString1 != null;
                for (int j = 0; j < arrayOfString1.length; j++) {
                    if (arrayOfString1[j].equals("id")) {
                        localString = (customers.get(k)).getId();
                    } else if (arrayOfString1[j].equals("name")) {
                        localString = (customers.get(k)).getName();
                    } else if (arrayOfString1[j].equals("pinyin")) {
                        localString = (customers.get(k)).getPinyin();
                    }
                    boolean contains = localString.contains(str);
                    boolean contains2 = (localString.toLowerCase()).contains(str);
                    if (contains && contains2) {
                        list.add(customers.get(k));
                        break;
                    }
                }
            }
            String[] items = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                items[i] = list.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Customer customerThin = null;
        if (list.size() == 0) {
            customerThin = temp.get(position);
        } else {
            customerThin = list.get(position);
        }
        Intent localIntent = new Intent();
        localIntent.putExtra("customer", customerThin);
        setResult(RESULT_OK, localIntent);
        finish();
    }
}
