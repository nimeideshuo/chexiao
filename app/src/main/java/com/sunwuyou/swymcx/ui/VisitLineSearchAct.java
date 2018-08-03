package com.sunwuyou.swymcx.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.VisitLineDAO;
import com.sunwuyou.swymcx.model.VisitLine;

import java.util.List;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class VisitLineSearchAct extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private List<VisitLine> visitlines;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        visitlines = new VisitLineDAO().getAllVisitLines();
        ListView listview = this.findViewById(R.id.listview);
        listview.setOnItemClickListener(this);
        if (!visitlines.isEmpty()) {
            String items[] = new String[visitlines.size()];
            for (int i = 0; i < visitlines.size(); i++) {
                items[i] = visitlines.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            listview.setAdapter(adapter);
        }
    }

    @Override
    public void initData() {

    }

    public void setActionBarText() {
        setTitle("路线");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("visitline", this.visitlines.get(position));
        this.setResult(RESULT_OK, intent);
        this.finish();
    }
}
