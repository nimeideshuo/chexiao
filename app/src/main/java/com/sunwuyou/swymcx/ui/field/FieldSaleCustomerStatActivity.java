package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.model.FieldSaleMoneyStat;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.List;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class FieldSaleCustomerStatActivity extends BaseHeadActivity {

    private ListView listView;
    private FieldSaleCustomerStatAdapter adapter;
    private List<FieldSaleMoneyStat> listItems;
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

    private void itemInfoShow() {
        double v5 = 0;
        double v7 = 0;
        double v9 = 0;
        double v11 = 0;
        int v2;
        for (v2 = 0; v2 < this.listItems.size(); ++v2) {
            FieldSaleMoneyStat v3 = this.listItems.get(v2);
            v5 += v3.getNetsaleamount();
            v7 += v3.getPreference();
            v9 += v3.getReceivable();
            v11 += v3.getReceived();
        }
        String v4 = String.valueOf(String.valueOf(String.valueOf(String.valueOf("\n") + "合计：" + Utils.getRecvableMoney(v5) + "元\n\n") + "优惠：" + Utils.getRecvableMoney(v7) + "元\n\n") + "应收：" + Utils.getRecvableMoney(v9) + "元\n\n") + "已收：" + Utils.getRecvableMoney(v11) + "元\n\n";
        TextView v13 = new TextView(this);
        v13.setTextSize(16f);
        v13.setPadding(50, 0, 0, 0);
        v13.setText(v4);
        AlertDialog.Builder v1 = new AlertDialog.Builder(this);
        v1.setView(v13);
        v1.setTitle("合计");
        v1.create().show();
    }

    @Override
    public void initView() {
        setTitleRight("合计", null);
        listView = this.findViewById(R.id.listView);
        adapter = new FieldSaleCustomerStatAdapter(this);
    }

    protected void onResume() {
        super.onResume();
        this.loadData();
    }

    private void loadData() {
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                listItems = new FieldSaleDAO().querySaleMoneyStat();
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (this.listItems.size() > 0) {
            this.itemInfoShow();
        } else {
            PDH.showMessage("本地没有已库存处理销售记录");
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        setTitle("客户销售统计");
    }

    class FieldSaleCustomerStatAdapter extends BaseAdapter {
        Context context;
        private List<FieldSaleMoneyStat> items;

        public FieldSaleCustomerStatAdapter(Context context) {
            this.context = context;
        }

        public List<FieldSaleMoneyStat> getData() {
            return this.items;
        }

        @Override
        public int getCount() {
            return this.items == null ? 0 : this.items.size();
        }

        @Override
        public FieldSaleMoneyStat getItem(int position) {
            return this.items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Handler handler;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_fieldcustomerstat, null);
                handler = new Handler(convertView);
                convertView.setTag(handler);
            } else {
                handler = (Handler) convertView.getTag();
            }

            handler.setValue(this.items.get(position));
            return convertView;
        }

        public void setData(List<FieldSaleMoneyStat> items) {
            this.items = items;
            this.notifyDataSetChanged();
        }

        public void setStatus(int arg1) {
            this.notifyDataSetChanged();
        }

        public class Handler {
            public TextView tvCustomerName;
            public TextView tvNetSaleAmout;
            public TextView tvPreference;
            public TextView tvReceivable;
            public TextView tvReceived;

            public Handler(View arg3) {
                super();
                this.tvCustomerName = arg3.findViewById(R.id.tvCustomerName);
                this.tvNetSaleAmout = arg3.findViewById(R.id.tvNetSaleAmout);
                this.tvPreference = arg3.findViewById(R.id.tvPreference);
                this.tvReceivable = arg3.findViewById(R.id.tvReceivable);
                this.tvReceived = arg3.findViewById(R.id.tvReceived);
            }

            @SuppressLint("SetTextI18n")
            public void setValue(FieldSaleMoneyStat moneyStat) {
                this.tvCustomerName.setText(moneyStat.getCustomername());
                this.tvNetSaleAmout.setText("合计：" + moneyStat.getNetsaleamount() + "元");
                this.tvPreference.setText("优惠：" + moneyStat.getPreference() + "元");
                this.tvReceivable.setText("应收：" + moneyStat.getReceivable() + "元");
                this.tvReceived.setText("已收：" + moneyStat.getReceived() + "元");
            }
        }
    }
}
