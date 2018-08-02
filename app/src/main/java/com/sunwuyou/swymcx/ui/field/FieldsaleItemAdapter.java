package com.sunwuyou.swymcx.ui.field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.model.FieldSaleItemThin;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.List;

/**
 * Created by admin
 * 2018/7/26.
 * content
 */

public class FieldsaleItemAdapter extends BaseAdapter {
    private Context context;
    private List<FieldSaleItemThin> items;

    public FieldsaleItemAdapter(Context context) {
        super();
        this.context = context;
    }

    public int getCount() {
        int v0 = this.items == null ? 0 : this.items.size();
        return v0;
    }

    public List getData() {
        return this.items;
    }

    public void setData(List arg1) {
        this.items = arg1;
        this.notifyDataSetChanged();
    }

    public Object getItem(int arg2) {
        return this.items.get(arg2);
    }

    public long getItemId(int arg3) {
        return this.items.get(arg3).getSerialid();
    }

    @Override
    public View getView(int arg5, View arg6, ViewGroup arg7) {
        FieldGoodsItem v0;
        if (arg6 == null) {
            arg6 = LayoutInflater.from(this.context).inflate(R.layout.item_field_goods, null);
            v0 = new FieldGoodsItem(this, arg6);
            arg6.setTag(v0);
        } else {
            v0 = (FieldGoodsItem) arg6.getTag();
        }
        v0.setValue(this.items.get(arg5));
        v0.tvSerialid.setText(String.valueOf(arg5 + 1));
        return arg6;
    }

    public class FieldGoodsItem {
        public TextView tvBarcode;
        public TextView tvInfo;
        public TextView tvName;
        public TextView tvSaleNum;
        public TextView tvSerialid;
        public TextView tvSubtotal;

        public FieldGoodsItem(FieldsaleItemAdapter arg2, View arg3) {
            super();
            this.tvSerialid = arg3.findViewById(R.id.tvSerialid);
            this.tvInfo = arg3.findViewById(R.id.tvInfo);
            this.tvName = arg3.findViewById(R.id.tvGoodsName);
            this.tvBarcode = arg3.findViewById(R.id.tvBarcode);
            this.tvSubtotal = arg3.findViewById(R.id.tvGoodsSubtotal);
            this.tvSaleNum = arg3.findViewById(R.id.tvSaleNum);
        }

        public void setValue(FieldSaleItemThin arg10) {
            double v7 = 0;
            String v6 = null;
            if (arg10.getIspromotion()) {
                this.tvInfo.setVisibility(View.VISIBLE);
            } else {
                this.tvInfo.setVisibility(View.GONE);
            }

            this.tvName.setText(arg10.getGoodsname());
            if (!TextUtils.isEmptyS(arg10.getBarcode())) {
                this.tvBarcode.setText(arg10.getBarcode());
            }

            GoodsUnitDAO v0 = new GoodsUnitDAO();
            String v1 = "";
            if (arg10.getSalebasenum() > v7) {
                v1 = "销" + v0.getBigNum(arg10.getGoodsid(), v6, arg10.getSalebasenum());
            }

            if (arg10.getGivebasenum() > v7) {
                v1 = v1.length() > 0 ? String.valueOf(v1) + "；赠" + v0.getBigNum(arg10.getGoodsid(), v6, arg10.getGivebasenum()) : "赠" + v0.getBigNum(arg10.getGoodsid(), v6, arg10.getGivebasenum());
            }

            if (arg10.getCancelbasenum() > v7) {
                v1 = v1.length() > 0 ? String.valueOf(v1) + "；退" + v0.getBigNum(arg10.getGoodsid(), v6, arg10.getCancelbasenum()) : "退" + v0.getBigNum(arg10.getGoodsid(), v6, arg10.getCancelbasenum());
            }

            if (arg10.getSalebasenum() > v7 && (arg10.getIspromotion()) && arg10.getGiftbasenum() > v7) {
                v1 = v1.length() > 0 ? String.valueOf(v1) + "；搭赠【" + arg10.getGiftgoodsname() + "】" + v0.getBigNum(arg10.getGiftgoodsid(), v6, arg10.getGiftbasenum()) : "搭赠【" + arg10.getGiftgoodsname() + "】" + v0.getBigNum(arg10.getGiftgoodsid(), v6, arg10.getGiftbasenum());
            }

            this.tvSaleNum.setText(v1);
            this.tvSubtotal.setText(String.valueOf(Utils.getSubtotalMoney(arg10.getSubtotal())) + "元");
        }
    }
}
