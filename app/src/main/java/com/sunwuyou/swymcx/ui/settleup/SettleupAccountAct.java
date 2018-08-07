package com.sunwuyou.swymcx.ui.settleup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.AccountDAO;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.model.Account;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.ui.CustomerSearchAct;

import java.util.List;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class SettleupAccountAct extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private SettleUp settleUp;
    private List<Account> items;
    private ListView listview;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        settleUp = new SettleUpDAO().getSettleUp(this.getIntent().getLongExtra("settleupid", -1));
        AccountDAO accountDAO = new AccountDAO();
        items = accountDAO.getAccounts(settleUp.getType().equals("64") ? "404" : "504");
        listview = this.findViewById(R.id.listview);
        this.listview.setOnItemClickListener(this);
        if (this.items != null && this.items.size() != 0) {
            String[] strings = new String[this.items.size()];
            for (int i = 0; i < items.size(); i++) {
                strings[i] = this.items.get(i).getAname();
            }
            ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
            listview.setAdapter(localArrayAdapter);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("account", items.get(position));
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        setTitle("项目");
    }
}
