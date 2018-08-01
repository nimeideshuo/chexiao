package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.model.FieldSaleStat;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.List;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class FieldSaleGoodsStatActivity extends BaseHeadActivity {
    private FieldSaleGoodsStatAdapter adapter;
    private List<FieldSaleStat> listItems;
    private ListView listView;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.setData(listItems);
            listView.setAdapter(adapter);
            //refreshUI();
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_fieldgoodsstat;
    }

    @Override
    public void initView() {
        setTitleRight("合计", null);
        listView = this.findViewById(R.id.listView);
        adapter = new FieldSaleGoodsStatAdapter(this);


    }

    protected void onResume() {
        super.onResume();
        this.loadData();
    }

    private void loadData() {
        PDH.show(this, new PDH.ProgressCallBack() {

            public void action() {
                listItems = new FieldSaleDAO().queryGoodsSaleStat();
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (listItems.size() > 0) {
            this.itemInfoShow();
        } else {
            PDH.showMessage("本地没有已库存处理销售记录");
        }

    }

    private void itemInfoShow() {
        double v8 = 0;
        double v1 = 0;
        double v6 = 0;
        int v3;
        for (v3 = 0; v3 < this.listItems.size(); ++v3) {
            Object v4 = this.listItems.get(v3);
            v8 += ((FieldSaleStat) v4).getSaleamount();
            v1 += ((FieldSaleStat) v4).getCancelamount();
            v6 += ((FieldSaleStat) v4).getNetsaleamount();
        }
        String v5 = String.valueOf(String.valueOf(String.valueOf("\n") + "销售金额：" + Utils.getRecvable(v8) + "元\n\n") + "退货金额：" + Utils.getRecvable(v1) + "元\n\n") + "净销售额：" + Utils.getRecvable(v6) + "元\n\n";
        TextView v10 = new TextView(this);
        v10.setTextSize(16f);
        v10.setPadding(50, 0, 0, 0);
        v10.setText(v5);
        AlertDialog.Builder v0 = new AlertDialog.Builder(this);
        v0.setView(v10);
        v0.setTitle("合计");
        v0.create().show();
    }

    @Override
    public void initData() {

    }

    public class FieldSaleGoodsStatAdapter extends BaseAdapter {
        private Context context;
        private GoodsUnitDAO goodsUnitDao;
        private List<FieldSaleStat> items;

        public FieldSaleGoodsStatAdapter(Context context) {
            super();
            this.context = context;
            this.goodsUnitDao = new GoodsUnitDAO();
        }

        public void setData(List<FieldSaleStat> items) {
            this.items = items;
            this.notifyDataSetChanged();
        }

        public void setStatus(int arg1) {
            this.notifyDataSetChanged();
        }

        public List<FieldSaleStat> getData() {
            return this.items;
        }

        @Override
        public int getCount() {
            return this.items == null ? 0 : this.items.size();
        }

        @Override
        public Object getItem(int position) {
            return this.items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Handler handler = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_fieldgoodsstat, null);
                handler = new Handler(convertView);
                convertView.setTag(handler);
            } else {
                handler = (Handler) convertView.getTag();
            }
            handler.setValue(items.get(position));
            return convertView;
        }

        public class Handler {
            public TextView tvBarcode;
            public TextView tvGoodsName;
            public TextView tvSaleAmount;
            public TextView tvSaleNum;

            public Handler(View arg3) {
                super();
                this.tvGoodsName = arg3.findViewById(R.id.tvGoodsName);
                this.tvBarcode = arg3.findViewById(R.id.tvBarcode);
                this.tvSaleNum = arg3.findViewById(R.id.tvSaleNum);
                this.tvSaleAmount = arg3.findViewById(R.id.tvSaleAmount);
            }

            @SuppressLint("SetTextI18n")
            public void setValue(FieldSaleStat arg10) {
                this.tvGoodsName.setText(arg10.getGoodsname());
                this.tvBarcode.setText(arg10.getBarcode());
                TextView v1 = this.tvSaleNum;
                StringBuilder v2 = new StringBuilder("数量：销");
                String v0 = arg10.getSalebasenum() != 0 ? FieldSaleGoodsStatAdapter.this.goodsUnitDao.getBigNum(arg10.getGoodsid(), null, arg10.getSalebasenum()) : "0";
                v2 = v2.append(v0).append("，退");
                v0 = arg10.getCancelbasenum() != 0 ? FieldSaleGoodsStatAdapter.this.goodsUnitDao.getBigNum(arg10.getGoodsid(), null, arg10.getCancelbasenum()) : "0";
                v2 = v2.append(v0).append("，净销");
                v0 = arg10.getNetsalebasenum() != 0 ? FieldSaleGoodsStatAdapter.this.goodsUnitDao.getBigNum(arg10.getGoodsid(), null, arg10.getNetsalebasenum()) : "0";
                v1.setText(v2.append(v0).toString());
                this.tvSaleAmount.setText("金额：销" + Utils.getRecvableMoney(arg10.getSaleamount()) + "元" + "，退" + Utils.getRecvableMoney(arg10.getCancelamount()) + "元" + "，净销" + Utils.getRecvableMoney(arg10.getNetsaleamount()) + "元");
            }
        }
    }
}
