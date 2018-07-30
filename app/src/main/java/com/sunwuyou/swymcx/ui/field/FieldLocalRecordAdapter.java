package com.sunwuyou.swymcx.ui.field;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sunwuyou.swymcx.model.FieldSaleThin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class FieldLocalRecordAdapter extends BaseAdapter {
    private Context context;
    private List<FieldSaleThin> listItems;
    private boolean multichoice = false;
    private HashMap<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();

    public FieldLocalRecordAdapter(Context context) {
        this.context = context;
    }

    public int getSelectCount() {
        int v0 = 0;
        if (this.statusMap != null) {
            //            Iterator v3 = this.statusMap.keySet().iterator();
            //            while(v3.hasNext()) {
            //                if(!this.statusMap.get(v3.next())) {
            //                    continue;
            //                }
            //                ++v0;
            //            }
            return statusMap.size();
        }
        return v0;
    }

    public List<FieldSaleThin> getSelectList() {
        ArrayList<FieldSaleThin> v1 = new ArrayList<FieldSaleThin>();
        for (int i = 0; i < listItems.size(); i++) {
            if (this.statusMap.get(i) != null && (this.statusMap.get(i))) {
                v1.add(this.listItems.get(i));
            }
        }
        return v1;
    }


    @Override
    public int getCount() {
        if (this.listItems == null) {
            return 0;
        }
        return this.listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
