package com.sunwuyou.swymcx.ui.settleup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.dao.SettleUpItemDAO;
import com.sunwuyou.swymcx.dao.SettleUpPayTypeDAO;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.model.SettleUpItem;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.List;

/**
 * Created by admin on
 * 2018/7/31.
 * content
 */
public class SettleupDocsActivity extends BaseHeadActivity {

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (SettleupDocsActivity.this.isModify) {
                receiveMoney(position);
            }
        }
    };
    private ListView listView;
    private SettleUp settleUp;
    private ItemAdapter adapter;
    private TextView tv_preference;
    private TextView tv_settle_jiesuan;
    private TextView tv_settle_shidhou;
    private TextView tv_sumamount;
    private List<SettleUpItem> listItems;

    @Override
    public int getLayoutID() {
        return R.layout.act_settleup_docs;
    }

    //TODO 未写完
    protected void receiveMoney(int position) {
        //        Object v1 = this.listItems.get(arg6);
        //        SettleupReceivedDialog v0 = new SettleupReceivedDialog(((Activity)this));
        //        v0.setConfirmDo(new ConfirmDo(((SettleUpItem)v1), arg6) {
        //            public void received(double arg8) {
        //                if(new SettleUpItemDAO().updateSettleUpItem(this.val$item.getSerialid(), "thisamount", new StringBuilder(String.valueOf(arg8)).toString())) {
        //                    this.val$item.setThisamount(arg8);
        //                    SettleupDocsActivity.this.listItems.set(this.val$position, this.val$item);
        //                    SettleupDocsActivity.this.adapter.setData(SettleupDocsActivity.this.listItems);
        //                    SettleupDocsActivity.this.refreshUI();
        //                }
        //                else {
        //                    PDH.showFail("操作失败，请重试");
        //                }
        //            }
        //        });
        //        v0.setDefaultMoney(Utils.getRecvableMoney(((SettleUpItem)v1).getLeftamount()));
        //        v0.show();
    }

    @Override
    public void initView() {
        listView = this.findViewById(R.id.listView);
        settleUp = new SettleUpDAO().getSettleUp(this.getIntent().getLongExtra("settleupid", -1));
        adapter = new ItemAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.tv_sumamount = this.findViewById(R.id.tv_sumamount);
        this.tv_settle_shidhou = this.findViewById(R.id.tv_settle_shidhou);
        this.tv_settle_jiesuan = this.findViewById(R.id.tv_settle_jiesuan);
        this.tv_preference = this.findViewById(R.id.tv_preference);
        if (this.settleUp.getIsSubmit()) {
            PDH.showMessage("该单据已经上传到服务器");
            this.isModify = false;
        }
        this.listView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void initData() {

    }

    protected void onResume() {
        super.onResume();
        this.loadData();
    }

    private void loadData() {
        listItems = new SettleUpItemDAO().getItems(this.settleUp.getId());
        this.adapter.setData(this.listItems);
        this.refreshUI();
    }

    @SuppressLint("SetTextI18n")
    public void refreshUI() {
        this.settleUp = new SettleUpDAO().getSettleUp(this.settleUp.getId());
        double v0 = new SettleUpItemDAO().getSettleupAmount(this.settleUp.getId());
        double v4 = new SettleUpPayTypeDAO().getSettleupPaysAmount(this.settleUp.getId());
        double v2 = this.settleUp.getPreference();
        this.tv_sumamount.setText("合计：" + Utils.getRecvableMoney(v0));
        this.tv_preference.setText("优惠：" + Utils.getRecvableMoney(v2));
        this.tv_settle_shidhou.setText("应收：" + Utils.getRecvableMoney(v0 - v2));
        this.tv_settle_jiesuan.setText("已收：" + Utils.getRecvableMoney(v4));
    }

    public void operationResult(final boolean b) {
        this.mHandler.post(new Runnable() {
            public void run() {
                if (b) {
                    PDH.showSuccess("操作成功");
                    refreshUI();
                    return;
                }
                PDH.showFail("操作失败");
            }
        });
    }

    public void settlepay(View view) {
        if (!ClickUtils.isFastDoubleClick()) {
            this.startActivity(new Intent().setClass(this, SettleupPayActivity.class).putExtra("settleupid", this.settleUp.getId()));
        }
    }

    public void setActionBarText() {
        setTitle("收款【" + this.settleUp.getObjectName() + "】");
    }

    private class ItemAdapter extends BaseAdapter {
        private List<SettleUpItem> listItems;

        public ItemAdapter(Context context) {
        }

        @Override
        public int getCount() {
            return listItems == null ? 0 : listItems.size();
        }

        @Override
        public SettleUpItem getItem(int position) {
            return listItems.get(position);
        }

        public void setData(List<SettleUpItem> listItems) {
            this.listItems = listItems;
            this.notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item;
            if (convertView == null) {
                convertView = SettleupDocsActivity.this.getLayoutInflater().inflate(R.layout.item_settleup_doc, null);
                item = new Item(convertView);
                convertView.setTag(item);
            } else {
                item = (Item) convertView.getTag();
            }
            item.setValue(this.listItems.get(position));
            return convertView;
        }

        class Item {
            private TextView tvDocShowid;
            private TextView tvDocTime;
            private TextView tvReceiveablePrice;
            private TextView tvReceiveedLeftPrice;
            private TextView tvthisTrueReceivedPrice;

            public Item(View view) {
                super();
                this.tvDocTime = view.findViewById(R.id.tvDocTime);
                this.tvDocShowid = view.findViewById(R.id.tvDocShowid);
                this.tvReceiveablePrice = view.findViewById(R.id.tvReceiveablePrice);
                this.tvReceiveedLeftPrice = view.findViewById(R.id.tvReceivedLeftPrice);
                this.tvthisTrueReceivedPrice = view.findViewById(R.id.tvthisTrueReceivedPrice);
            }

            private String getName(String arg2) {
                String v0;
                if ("13".equals(arg2)) {
                    v0 = "销售单";
                } else if ("14".equals(arg2)) {
                    v0 = "销售退货单";
                } else if ("15".equals(arg2)) {
                    v0 = "销售换货单";
                } else if ("03".equals(arg2)) {
                    v0 = "采购单";
                } else if ("04".equals(arg2)) {
                    v0 = "采购单";
                } else if ("05".equals(arg2)) {
                    v0 = "采购单";
                } else if ("81".equals(arg2)) {
                    v0 = "期初";
                } else {
                    v0 = "";
                }

                return v0;
            }

            @SuppressLint("SetTextI18n")
            public void setValue(SettleUpItem arg5) {
                if (!TextUtils.isEmptyS(arg5.getDoctime())) {
                    this.tvDocTime.setText("日期：" + Utils.formatDate(arg5.getDoctime(), "yyyy-MM-dd"));
                } else {
                    this.tvDocTime.setText("日期：");
                }

                if ("81".equals(arg5.getDoctype())) {
                    this.tvDocShowid.setText("期初");
                } else {
                    this.tvDocShowid.setText(String.valueOf(this.getName(arg5.getDoctype())) + " 【" + arg5.getDocshowid() + "】");
                }

                this.tvthisTrueReceivedPrice.setText("本次收款：" + Utils.getRecvableMoney(arg5.getThisamount()));
                this.tvReceiveablePrice.setText("优惠后应收：" + Utils.getRecvableMoney(arg5.getReceivableamount()));
                this.tvReceiveedLeftPrice.setText("待收：" + Utils.getRecvableMoney(arg5.getLeftamount()));
            }
        }
    }

}
