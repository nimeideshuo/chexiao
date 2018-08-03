package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.immo.libcomm.utils.TextUtils;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.model.FieldSaleThin;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class FieldLocalRecordAdapter extends BaseAdapter {
    private Context context;
    private List<FieldSaleThin> listItems;
    private boolean multichoice = false;
    private HashMap<Integer, Boolean> statusMap = new HashMap<>();

    public FieldLocalRecordAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<FieldSaleThin> listItems) {
        this.listItems = listItems;
        this.notifyDataSetChanged();
    }

    public void setCheckePosition(int arg6) {
        if (this.statusMap.get(arg6) == null) {
            this.statusMap.put(arg6, Boolean.TRUE);
        } else {
            HashMap<Integer, Boolean> v2 = this.statusMap;
            Integer v3 = arg6;
            boolean v0 = !this.statusMap.get(arg6);
            v2.put(v3, v0);
        }
        this.notifyDataSetChanged();
    }

    public void setSelectAll() {
        this.statusMap.clear();
        for (int i = 0; i < listItems.size(); i++) {
            this.statusMap.put(i, true);
        }
        this.notifyDataSetChanged();
    }

    public void setMultiChoice(boolean multichoice) {
        this.multichoice = multichoice;
        this.notifyDataSetChanged();
    }

    public int getSelectCount() {
        return statusMap==null?0:statusMap.size();
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
        RemoteLocalItem v0;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_field_local_record, null);
            v0 = new RemoteLocalItem(convertView);
            convertView.setTag(v0);
        } else {
            v0 = (RemoteLocalItem) convertView.getTag();
        }

        if (this.statusMap.get(position) == null) {
            v0.cbTV.setChecked(false);
        } else {
            v0.cbTV.setChecked(this.statusMap.get(position));
        }

        v0.tvId.setText(String.valueOf(this.getCount() - position));
        v0.setValue(this.listItems.get(position));
        return convertView;
    }


    public class RemoteLocalItem {
        public TextView tvCustomer;
        public TextView tvDate;
        public TextView tvId;
        public TextView tvReceivable;
        public TextView tvReceived;
        public TextView tvStatus;
        private CheckedTextView cbTV;

        public RemoteLocalItem(View arg3) {
            super();
            this.tvId = arg3.findViewById(R.id.tvId);
            this.tvCustomer = arg3.findViewById(R.id.tvCustomer);
            this.tvDate = arg3.findViewById(R.id.tvDate);
            this.tvStatus = arg3.findViewById(R.id.tvStatus);
            this.tvReceivable = arg3.findViewById(R.id.tvReceivable);
            this.tvReceived = arg3.findViewById(R.id.tvReceived);
            this.cbTV = arg3.findViewById(R.id.cbTV);
        }


        @SuppressLint("SetTextI18n")
        public void setValue(FieldSaleThin arg8) {
            String v0 = arg8.getCustomername();
            if (TextUtils.isEmptyS(v0)) {
                v0 = "客户";
            }
            this.tvCustomer.setText(v0);
            this.tvDate.setText("开单：" + Utils.formatDate(arg8.getBuildtime(), "MM-dd HH:mm"));
            if (arg8.getStatus() == 0) {
                this.tvStatus.setText("未处理");
            } else {
                this.tvStatus.setText("已处理");
            }
            if (arg8.getStatus() == 2) {
                this.tvCustomer.setTextColor(-65536);
            } else {
                this.tvCustomer.setTextColor(-16777216);
            }

            this.tvReceivable.setText("应收：" + Utils.getRecvableMoney(arg8.getSaleamount() - arg8.getCancelamount() - arg8.getPreference()));
            this.tvReceived.setText("已收：" + arg8.getReceivedamount());
            if (multichoice) {
                this.cbTV.setVisibility(View.VISIBLE);
            } else {
                statusMap.clear();
                this.cbTV.setVisibility(View.GONE);
            }
        }
    }


}
