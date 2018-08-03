package com.sunwuyou.swymcx.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.DepartmentDAO;
import com.sunwuyou.swymcx.model.Department;

import java.util.List;

/**
 * Created by admin on
 * 2018/7/31.
 * content
 */
public class DepartmentSearchAct extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private List<Department> departments;
    private ListView listview;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        departments = new DepartmentDAO().getAllDepartment();
        listview = this.findViewById(R.id.listview);
        this.listview.setOnItemClickListener(this);
        if (this.departments != null && this.departments.size() != 0) {
            String[] strings = new String[this.departments.size()];
            int v1;
            for (v1 = 0; v1 < this.departments.size(); ++v1) {
                strings[v1] = this.departments.get(v1).getDname();
            }
            ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
            listview.setAdapter(localArrayAdapter);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void setActionBarText() {
        super.setActionBarText();
        setTitle("部门");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("department", this.departments.get(position));
        this.setResult(RESULT_OK, intent);
        this.finish();
    }
}
