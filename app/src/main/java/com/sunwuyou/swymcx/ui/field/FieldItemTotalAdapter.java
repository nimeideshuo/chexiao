package com.sunwuyou.swymcx.ui.field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.model.FieldSaleItemBatchEx;
import com.sunwuyou.swymcx.model.FieldSaleItemTotal;
import com.sunwuyou.swymcx.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/29.
 * content
 */

public class FieldItemTotalAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<FieldSaleItemTotal> listItems = new ArrayList<>();

    public FieldItemTotalAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        if (this.listItems == null) {
            return 0;
        }
        return this.listItems.size();
    }

    public void setData(List<FieldSaleItemTotal> listItems) {
        this.listItems.clear();
        this.listItems.addAll(listItems);
        notifyDataSetChanged();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItems.get(groupPosition).getItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Group v0;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_fielditem_total, null);
            v0 = new Group(convertView);
            convertView.setTag(v0);
        } else {
            v0 = (Group) convertView.getTag();
        }
//        convertView.setTag(2131296315, groupPosition);
        v0.setValue(this.listItems.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Child v0_1;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_fielditem_total_child, null);
            v0_1 = new Child(convertView);
            convertView.setTag(v0_1);
        } else {
            v0_1 = (Child) convertView.getTag();
        }
//        convertView.setTag(R.id.type_1, groupPosition);
        v0_1.setValue(this.listItems.get(groupPosition).getItems().get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class Child {
        private TextView tvBatch;
        private TextView tvNum;
        private TextView tvType;

        public Child(View view) {
            this.tvType = view.findViewById(R.id.tvType);
            this.tvBatch = view.findViewById(R.id.tvBatch);
            this.tvNum = view.findViewById(R.id.tvNum);
        }

        public void setValue(FieldSaleItemBatchEx batchEx) {
            TextView v1 = this.tvType;
            String v0 = batchEx.getIsout() ? "出库" : "入库";
            v1.setText(v0);
            this.tvBatch.setText(batchEx.getBatch());
            this.tvNum.setText(new GoodsUnitDAO().getBigNum(batchEx.getGoodsid(), batchEx.getUnitid(), batchEx.getNum()));
        }
    }

    class Group {
        private TextView tvBarcode;
        private TextView tvInNum;
        private TextView tvName;
        private TextView tvNoUseBatch;
        private TextView tvOutNum;

        public Group(View view) {
            super();
            this.tvName = view.findViewById(R.id.tvName);
            this.tvBarcode = view.findViewById(R.id.tvBarcode);
            this.tvNoUseBatch = view.findViewById(R.id.tvNoUseBatch);
            this.tvOutNum = view.findViewById(R.id.tvOutNum);
            this.tvInNum = view.findViewById(R.id.tvInNum);
        }

        public void setValue(FieldSaleItemTotal itemTotal) {
            String v6 = null;
            this.tvName.setText(itemTotal.getGoodsname());
            this.tvBarcode.setText(itemTotal.getBarcode());
            if (itemTotal.getIsusebatch()) {
                this.tvNoUseBatch.setVisibility(View.GONE);
            } else {
                this.tvNoUseBatch.setVisibility(View.VISIBLE);
            }

            String v1 = new GoodsUnitDAO().getBigNum(itemTotal.getGoodsid(), v6, itemTotal.getOutbasicnum());
            if (TextUtils.isEmpty(v1)) {
                v1 = "无";
            }

            String v0 = new GoodsUnitDAO().getBigNum(itemTotal.getGoodsid(), v6, itemTotal.getInbasicnum());
            if (TextUtils.isEmpty(v0)) {
                v0 = "无";
            }

            this.tvOutNum.setText("出库：" + v1);
            this.tvInNum.setText("入库：" + v0);
        }
    }
}
