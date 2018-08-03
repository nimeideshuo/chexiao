package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.CustomerFieldSaleGoodsDAO;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.in.EmptyDo;
import com.sunwuyou.swymcx.model.CustomerFieldSaleGoods;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.GoodsThin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class CustomerGoodsActivity extends BaseHeadActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView tvOnSale;
    private TextView tvStopSale;
    private TextView tvIsPass;
    private ListView listView;
    private CustomerFieldSaleGoodsDAO fsGoodsDAO;
    private FieldSale fieldSale;
    private CustomerGoodsAdapter adapter;
    private List<CustomerFieldSaleGoods> listItems;
    private int type;
    ActionMode mMode;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.setData(listItems);
            listView.setAdapter(adapter);
        }
    };
    private GoodsSelectMoreDialog moreDialog;
    AbsListView.MultiChoiceModeListener multichoicelistener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            adapter.setCheckePosition(position);
            mode.setSubtitle("选中" + listView.getCheckedItemCount() + "条");
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mMode = mode;
            int v3 = 2;
            adapter.setMultiChoice(true);
            mode.setTitle("选择");
            menu.add(0, 0, 0, "添加").setShowAsAction(v3);
            if (type != v3) {
                menu.add(0, 1, 0, "跳过").setShowAsAction(v3);
            }
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            List<CustomerFieldSaleGoods> saleGoodsList = adapter.getSelectList();
            if (item.getItemId() == 0) {
                ArrayList<GoodsThin> goodsThins = new ArrayList<>();
                for (int i = 0; i < goodsThins.size(); i++) {
                    GoodsThin goodsThin = new GoodsThin();
                    goodsThin.setId(saleGoodsList.get(i).getGoodsid());
                    goodsThin.setName(saleGoodsList.get(i).getGoodsname());
                    goodsThin.setBarcode(saleGoodsList.get(i).getBarcode());
                    goodsThins.add(goodsThin);
                }
                if (moreDialog == null) {
                    moreDialog = new GoodsSelectMoreDialog(CustomerGoodsActivity.this);
                }
                moreDialog.setAction(new EmptyDo() {
                    public void doAction() {
                        loadData();
                    }
                });
                moreDialog.show(goodsThins, fieldSale.getId());
            } else {
                mode.finish();
                passCustomerGoods(saleGoodsList);
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.setMultiChoice(false);
        }
    };

    private void passCustomerGoods(List<CustomerFieldSaleGoods> saleGoodsList) {
        for (int i = 0; i < saleGoodsList.size(); i++) {
            new CustomerFieldSaleGoodsDAO().update(saleGoodsList.get(i).getGoodsid(), saleGoodsList.get(i).getCustomerid(), "ispass", "1");
        }
        this.loadData();
    }

    @Override
    public int getLayoutID() {
        return R.layout.act_field_customer_goods;
    }

    @Override
    public void initView() {
        tvOnSale = this.findViewById(R.id.tvOnSale);
        tvStopSale = this.findViewById(R.id.tvStopSale);
        tvIsPass = this.findViewById(R.id.tvIsPass);
        listView = this.findViewById(R.id.listView);
        this.tvOnSale.setOnClickListener(this);
        this.tvStopSale.setOnClickListener(this);
        this.tvIsPass.setOnClickListener(this);
        fsGoodsDAO = new CustomerFieldSaleGoodsDAO();
        fieldSale = new FieldSaleDAO().getFieldsale(this.getIntent().getLongExtra("fieldsaleid", -1));
        adapter = new CustomerGoodsAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(this);
        this.listView.setChoiceMode(3);
        this.listView.setMultiChoiceModeListener(multichoicelistener);
    }

    @Override
    public void initData() {

    }

    protected void onResume() {
        super.onResume();
        if (mMode != null) {
            mMode.finish();
        }
        this.loadData();
    }

    private void loadData() {
        new Thread() {
            public void run() {
                listItems = fsGoodsDAO.getCustomerfieldsalegoods(fieldSale.getCustomerid(), fieldSale.getId(), type);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        int v4 = -3355444;
        int v3 = -16777216;
        int v2 = -7829368;
        if (!this.adapter.getMultiChoice()) {
            switch (v.getId()) {
                case R.id.tvOnSale:
                    this.type = 0;
                    this.tvOnSale.setTextColor(v3);
                    this.findViewById(R.id.btnOnSaleline).setBackgroundColor(v4);
                    this.tvStopSale.setTextColor(v2);
                    this.findViewById(R.id.btnStopSaleline).setBackgroundColor(0);
                    this.tvIsPass.setTextColor(v2);
                    this.findViewById(R.id.btnIsPassline).setBackgroundColor(0);
                    break;
                case R.id.tvStopSale:
                    this.type = 1;
                    this.tvOnSale.setTextColor(v2);
                    this.findViewById(R.id.btnOnSaleline).setBackgroundColor(0);
                    this.tvStopSale.setTextColor(v3);
                    this.findViewById(R.id.btnStopSaleline).setBackgroundColor(v4);
                    this.tvIsPass.setTextColor(v2);
                    this.findViewById(R.id.btnIsPassline).setBackgroundColor(0);
                    break;
                case R.id.tvIsPass:
                    this.type = 2;
                    this.tvOnSale.setTextColor(v2);
                    this.findViewById(R.id.btnOnSaleline).setBackgroundColor(0);

                    this.tvStopSale.setTextColor(v2);
                    this.findViewById(R.id.btnStopSaleline).setBackgroundColor(0);

                    this.tvIsPass.setTextColor(v3);
                    this.findViewById(R.id.btnIsPassline).setBackgroundColor(v4);

                    break;
            }
            loadData();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CustomerFieldSaleGoods saleGoods = this.listItems.get(position);
        GoodsThin goodsThin = new GoodsThin();
        goodsThin.setId(saleGoods.getGoodsid());
        goodsThin.setName(saleGoods.getGoodsname());
        goodsThin.setBarcode(saleGoods.getBarcode());
        Intent intent = new Intent(this, FieldAddGoodAct.class);
        intent.putExtra("goods", goodsThin);
        intent.putExtra("fieldsaleid", this.fieldSale.getId());
        startActivity(intent);
    }

    public void setActionBarText() {
        setTitle("客史商品");
    }

    class CustomerGoodsAdapter extends BaseAdapter {
        private Context context;
        private boolean ismultichoice;
        private List<CustomerFieldSaleGoods> listItems;
        private HashMap<Integer, Boolean> status;

        public CustomerGoodsAdapter(Context context) {
            this.ismultichoice = false;
            this.context = context;
            this.status = new HashMap<>();
        }

        @Override
        public int getCount() {
            return this.listItems == null ? 0 : this.listItems.size();
        }

        @Override
        public CustomerFieldSaleGoods getItem(int position) {
            return this.listItems.get(position);
        }

        public boolean getMultiChoice() {
            return this.ismultichoice;
        }

        public List<CustomerFieldSaleGoods> getSelectList() {
            ArrayList<CustomerFieldSaleGoods> listItems = new ArrayList<>();
            for (int i = 0; i < listItems.size(); i++) {
                if (this.status.get(i) != null && (this.status.get(i))) {
                    listItems.add(this.listItems.get(i));
                }
            }
            return listItems;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_field_customer_goods, null);
                item = new Item(convertView);
                convertView.setTag(item);
            } else {
                item = (Item) convertView.getTag();
            }
            if (this.status.get(position) == null) {
                item.cbTv.setChecked(false);
            } else {
                item.cbTv.setChecked(this.status.get(position));
            }
            item.tvSerialid.setText(String.valueOf(position + 1));
            item.setValue(this.listItems.get(position));
            return convertView;
        }

        public void setCheckePosition(int position) {
            if (this.status.get(position) == null) {
                this.status.put(position, true);
            } else {
                HashMap<Integer, Boolean> v2 = this.status;
                Integer v3 = position;
                boolean v0 = !this.status.get(position);
                v2.put(v3, v0);
            }
            this.notifyDataSetChanged();
        }

        public void setData(List<CustomerFieldSaleGoods> listItems) {
            this.listItems = listItems;
            this.status.clear();
            this.notifyDataSetChanged();
        }

        public void setMultiChoice(boolean ismultichoice) {
            this.ismultichoice = ismultichoice;
            this.notifyDataSetChanged();
        }

        class Item {
            private CheckedTextView cbTv;
            private TextView tvBarcode;
            private TextView tvGoodsName;
            private TextView tvSalePrice;
            private TextView tvSerialid;

            public Item(View view) {
                super();
                this.tvSerialid = view.findViewById(R.id.tvSerialid);
                this.tvGoodsName = view.findViewById(R.id.tvGoodsName);
                this.tvBarcode = view.findViewById(R.id.tvBarcode);
                this.tvSalePrice = view.findViewById(R.id.tvSalePrice);
                this.cbTv = view.findViewById(R.id.cbTV);
            }

            @SuppressLint("SetTextI18n")
            public void setValue(CustomerFieldSaleGoods arg5) {
                this.tvGoodsName.setText(arg5.getGoodsname());
                this.tvBarcode.setText(arg5.getBarcode());
                this.tvSalePrice.setText(String.valueOf(arg5.getPrice()) + "/" + arg5.getUnitname());
                if (ismultichoice) {
                    this.cbTv.setVisibility(View.VISIBLE);
                } else {
                    status.clear();
                    this.cbTv.setVisibility(View.GONE);
                }
            }
        }
    }
}
