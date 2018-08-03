package com.sunwuyou.swymcx.ui.settleup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.OtherSettleUpItemDAO;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.dao.SettleUpPayTypeDAO;
import com.sunwuyou.swymcx.model.OtherSettleUpItem;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.List;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class OtherSettleupDocActivity extends BaseHeadActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private SettleUp settleUp;
    private SwipeMenuListView listView;
    private TextView tvYiShou;
    private TextView tvYingShou;
    private OtherSettleupDocAdapter adapter;
    private List<OtherSettleUpItem> listItems;

    @Override
    public int getLayoutID() {
        return R.layout.act_othersettleup_docs;
    }

    @Override
    public void initView() {
        settleUp = new SettleUpDAO().getSettleUp(this.getIntent().getLongExtra("settleupid", -1));
        listView = this.findViewById(R.id.listView);
        this.listView.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu arg5) {
                SwipeMenuItem v0 = new SwipeMenuItem(OtherSettleupDocActivity.this.getApplicationContext());
                v0.setTitle("删除");
                v0.setTitleSize(14);
                v0.setTitleColor(-16777216);
                v0.setBackground(new ColorDrawable(Color.rgb(201, 201, 206)));
                v0.setWidth(100);
                arg5.addMenuItem(v0);
            }
        });
        this.listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(int arg3, SwipeMenu menu, int index) {
                delete(listItems.get(index));
                return false;
            }
        });
        this.listView.setOnItemClickListener(this);
        this.tvYingShou = this.findViewById(R.id.tvYingshou);
        this.tvYiShou = this.findViewById(R.id.tvYiShou);
        Button btnAccount = this.findViewById(R.id.btnAccount);
        Button btnSettleup = this.findViewById(R.id.btnSettleup);
        btnSettleup.setOnClickListener(this);
        btnAccount.setOnClickListener(this);
        if (this.settleUp.getType().equals("64")) {
            btnSettleup.setText("收款");
        } else {
            btnSettleup.setText("付款");
        }

        if (this.settleUp.getIsSubmit()) {
            this.isModify = false;
        }

        if (!this.isModify) {
            btnAccount.setVisibility(View.GONE);
        }

        if (!this.isModify) {
            this.listView.setItemSwipe(false);
        } else {
            this.listView.setItemSwipe(true);
        }
        adapter = new OtherSettleupDocAdapter(this);
        this.listView.setAdapter(this.adapter);
    }

    @Override
    public void initData() {

    }

    protected void onResume() {
        super.onResume();
        this.loadData();
    }

    private void loadData() {
        listItems = new OtherSettleUpItemDAO().getItems(this.settleUp.getId());
        this.adapter.setData(this.listItems);
        this.listView.setAdapter(this.adapter);
        this.refreshUI();
    }

    @SuppressLint("SetTextI18n")
    public void refreshUI() {
        double v2 = new OtherSettleUpItemDAO().getOtherSettleupAmount(this.settleUp.getId());
        double v0 = new SettleUpPayTypeDAO().getSettleupPaysAmount(this.settleUp.getId());
        if (this.settleUp.getType().equals("64")) {
            this.tvYingShou.setText("应收：" + v2);
            this.tvYiShou.setText("已收：" + v0);
        } else {
            this.tvYingShou.setText("应付：" + v2);
            this.tvYiShou.setText("已付：" + v0);
        }
    }


    private void delete(final OtherSettleUpItem item) {

        new MessageDialog(this).showDialog("提示", "确定删除吗？", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                if (new OtherSettleUpItemDAO().delete(item.getSerialid())) {
                    listItems.remove(item);
                    adapter.setData(listItems);
                    adapter.notifyDataSetChanged();
                    refreshUI();
                } else {
                    PDH.showFail("删除失败");
                }
            }

            @Override
            public void btnCancel(View view) {

            }
        }).showDialog();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.isModify) {
            this.startActivity(new Intent(this, SettleupAccountSelectAct.class).putExtra("othersettleupitem", this.listItems.get(position)).putExtra("settleupid", this.settleUp.getId()));
        }
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtils.isFastDoubleClick()) {
            if (v.getId() == R.id.btnAccount) {
                if (this.isModify) {
                    this.startActivity(new Intent().setClass(this, SettleupAccountSelectAct.class).putExtra("settleupid", this.settleUp.getId()));
                }
            } else if (v.getId() == R.id.btnSettleup) {
                this.startActivity(new Intent().setClass(this, SettleupPayActivity.class).putExtra("settleupid", this.settleUp.getId()));
            }
        }
    }

    class OtherSettleupDocAdapter extends BaseAdapter {
        private Context context;
        private List<OtherSettleUpItem> listItems;

        OtherSettleupDocAdapter(Context context) {
            super();
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.listItems == null ? 0 : this.listItems.size();
        }

        @Override
        public OtherSettleUpItem getItem(int position) {
            return this.listItems.get(position);
        }

        public void setData(List<OtherSettleUpItem> listItems) {
            this.listItems = listItems;
            this.notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_other_settleup_doc, null);
                item = new Item(convertView);
                convertView.setTag(item);
            } else {
                item = (Item) convertView.getTag();
            }

            item.setValue(this.listItems.get(position));
            return convertView;
        }

        public class Item {
            private TextView tvAccountname;
            private TextView tvMoney;

            Item(View view) {
                super();
                this.tvMoney = view.findViewById(R.id.tvMoney);
                this.tvAccountname = view.findViewById(R.id.tvAccountname);
            }

            @SuppressLint("SetTextI18n")
            public void setValue(OtherSettleUpItem upItem) {
                this.tvAccountname.setText(upItem.getAccountname());
                this.tvMoney.setText(String.valueOf(upItem.getAmount()) + "元");
            }
        }
    }

}
