package com.sunwuyou.swymcx.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.service.ServiceCustomer;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.AutoTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class CustomerSearchOnLineAct extends BaseHeadActivity implements AutoTextView.OnTextChangeListener, AdapterView.OnItemClickListener {
    @BindView(R.id.etSearch)
    AutoTextView etSearch;
    @BindView(R.id.listview)
    ListView listView;
    private List<Customer> sourceData;
    private String text;
    private List<Customer> showData;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String v5 = msg.obj.toString();
            if (RequestHelper.isSuccess(v5)) {
                sourceData = JSONUtil.str2list(v5, Customer.class);
                if (sourceData != null && sourceData.size() != 0) {
                    if (text.length() == 1) {
                        showData.clear();
                        showData.addAll(sourceData);
                    } else {
                        String[] v3 = Utils.CUSTOMER_CHECK_SELECT.split(",");
                        for (int i = 0; i < sourceData.size(); i++) {
                            String v6 = "";
                            for (String s : v3) {
                                switch (s) {
                                    case "id":
                                        v6 = sourceData.get(i).getId();
                                        break;
                                    case "name":
                                        v6 = sourceData.get(i).getName();
                                        break;
                                    case "pinyin":
                                        v6 = sourceData.get(i).getPinyin();
                                        break;
                                }
                                if (!TextUtils.isEmptyS(v6) && ((v6.contains(text)) || (v6.toLowerCase().contains(text)))) {
                                    showData.add(sourceData.get(i));
                                    break;
                                }
                            }
                        }
                        String[] items = new String[showData.size()];
                        for (int i = 0; i < showData.size(); i++) {
                            items[i] = showData.get(i).getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CustomerSearchOnLineAct.this, android.R.layout.simple_list_item_1, items);
                        listView.setAdapter(adapter);
                    }
                }
            } else {
                RequestHelper.showError(v5);
            }

        }
    };

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
        listView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        this.sourceData = new ArrayList<>();
        this.showData = new ArrayList<>();
    }

    @Override
    public void onChanged(View v, final String text) {
        if (TextUtils.isEmptyS(text)) {
            this.showData.clear();
            this.sourceData.clear();
            this.listView.setAdapter(null);
        } else {
            this.text = text;
            this.showData.clear();
            if (!TextUtils.isEmptyS(this.etSearch.getBeforeTextChange()) && (text.subSequence(0, 1).equals(this.etSearch.getBeforeTextChange().subSequence(0, 1)))) {

                String[] strings = Utils.CUSTOMER_CHECK_SELECT.split(",");
                for (int i = 0; i < sourceData.size(); i++) {
                    String v6 = "";
                    for (String string : strings) {
                        switch (string) {
                            case "id":
                                v6 = sourceData.get(i).getId();
                                break;
                            case "name":
                                v6 = sourceData.get(i).getName();
                                break;
                            case "pinyin":
                                v6 = sourceData.get(i).getPinyin();
                                break;
                        }
                        if (!TextUtils.isEmptyS(v6) && ((v6.contains(text)) || (v6.toLowerCase().contains(text)))) {
                            showData.add(sourceData.get(i));
                            break;
                        }
                    }
                }
                String[] items = new String[showData.size()];
                for (int i = 0; i < showData.size(); i++) {
                    items[i] = showData.get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CustomerSearchOnLineAct.this, android.R.layout.simple_list_item_1, items);
                listView.setAdapter(adapter);
            }
            new Thread() {
                public void run() {
                    handler.sendMessage(handler.obtainMessage(0, new ServiceCustomer().cu_FilterCustomer(text.substring(0, 1))));
                }
            }.start();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("customer", showData.get(position));
        this.setResult(2, intent);
        this.finish();
    }

    public void setActionBarText() {
        setTitle("客户检索");
    }
}
