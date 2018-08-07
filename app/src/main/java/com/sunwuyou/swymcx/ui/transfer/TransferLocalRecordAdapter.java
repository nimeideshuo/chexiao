package com.sunwuyou.swymcx.ui.transfer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class TransferLocalRecordAdapter extends BaseAdapter {
    private Context context;
    private List<TransferDoc> listItems;
    private boolean multichoice = false;
    private HashMap<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();

    public TransferLocalRecordAdapter(Context context) {
        this.context = context;
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

    public void setData(List<TransferDoc> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

    public void setMultiChoice(boolean multichoice) {
        this.multichoice = multichoice;
        notifyDataSetChanged();
    }

    public void setCheckePosition(int position) {
        if (this.statusMap.get(position) == null) {
            this.statusMap.put(position, true);
        } else {
            statusMap.put(position, !statusMap.get(position));
        }
        this.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RemoteLocalItem item;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_transfer_local_record, null);
            item = new RemoteLocalItem(convertView);
            convertView.setTag(item);
        } else {
            item = (RemoteLocalItem) convertView.getTag();
        }
        if (this.statusMap.get(position) == null) {
            item.cbTV.setChecked(false);
        } else {
            item.cbTV.setChecked(statusMap.get(position));
        }
        item.tvId.setText(String.valueOf(this.getCount() - position));
        item.setValue(this.listItems.get(position));
        return convertView;
    }

    public class RemoteLocalItem {
        public TextView tvDate;
        public TextView tvId;
        public TextView tvWarehouse;
        private CheckedTextView cbTV;

        public RemoteLocalItem(View view) {
            this.tvId = view.findViewById(R.id.tvId);
            this.tvWarehouse = view.findViewById(R.id.tvWarehouse);
            this.tvDate = view.findViewById(R.id.tvDate);
            this.cbTV = view.findViewById(R.id.cbTV);
        }

        @SuppressLint("SetTextI18n")
        public void setValue(TransferDoc arg5) {
            this.tvWarehouse.setText("调拨单");
            this.tvDate.setText("开单：" + Utils.formatDate(arg5.getBuildtime(), "MM-dd HH:mm"));
            if (arg5.isIsupload()) {
                this.tvWarehouse.setTextColor(-65536);
            } else {
                this.tvWarehouse.setTextColor(-16777216);
            }

            if (multichoice) {
                this.cbTV.setVisibility(View.VISIBLE);
            } else {
                statusMap.clear();
                this.cbTV.setVisibility(View.GONE);
            }
        }
    }
}
