package com.sunwuyou.swymcx.ui.transfer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.dao.WarehouseDAO;
import com.sunwuyou.swymcx.response.RespGoodsBatchEntity;
import com.sunwuyou.swymcx.service.ServiceGoods;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;

import java.util.List;

/**
 * Created by admin
 * 2018/8/5.
 * content
 */

public class GoodsBatchSearchAct extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private ListView listview;
    private String warehousename;
    private GoodsBatchAdapter adapter;
    private List<RespGoodsBatchEntity> listGoodsBatch;

    @Override
    public int getLayoutID() {
        return R.layout.act_customer_search;
    }

    @Override
    public void initView() {
        this.findViewById(R.id.etSearch).setVisibility(View.GONE);
        this.findViewById(R.id.tvTop).setVisibility(View.GONE);
        listview = this.findViewById(R.id.listview);
        this.listview.setOnItemClickListener(this);
        String v1 = this.getIntent().getStringExtra("warehouseid");
        String v0 = this.getIntent().getStringExtra("goodsid");
        if (!TextUtils.isEmptyS(v1)) {
            warehousename = new WarehouseDAO().getWarehouse(v1).getName();
        }
        adapter = new GoodsBatchAdapter(this);
        this.listview.setAdapter(this.adapter);
        this.loadData(v1, v0);
    }

    private void loadData(final String warehouseid, final String goodsid) {
        PDH.show(this, "正在获取批次...", new PDH.ProgressCallBack() {
            public void action() {
                String v0 = new ServiceGoods().gds_GetGoodsBatch(warehouseid, goodsid);
                if (RequestHelper.isSuccess(v0)) {
                    handlerGetGoodsBatch.sendMessage(handlerGetGoodsBatch.obtainMessage(0, v0));
                } else {
                    handlerGetGoodsBatch.sendMessage(handlerGetGoodsBatch.obtainMessage(1, v0));
                }
            }
        });
    }

    @SuppressLint("HandlerLeak") private Handler handlerGetGoodsBatch = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    listGoodsBatch = JSONUtil.str2list(msg.obj.toString(), RespGoodsBatchEntity.class);
                    adapter.setData(listGoodsBatch);
                    if (listGoodsBatch.size() == 0) {
                        PDH.showMessage("无可用批次");
                    }
                    break;
                case 1:
                    PDH.showFail("批次读取失败");
                    break;
            }

        }
    };

    @Override
    public void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("batch", this.listGoodsBatch.get(position).getBatch());
        intent.putExtra("productiondate", this.listGoodsBatch.get(position).getProductiondate());
        intent.putExtra("warehouseid", this.listGoodsBatch.get(position).getWarehouseId());
        intent.putExtra("warehousename", this.listGoodsBatch.get(position).getWarehouseName());
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    public void setActionBarText() {
        if (TextUtils.isEmptyS(this.warehousename)) {
            setTitle("商品批次");
        } else {
            setTitle("商品批次-【" + this.warehousename + "】");
        }
    }

    class GoodsBatchAdapter extends BaseAdapter {
        private Context context;
        private List<RespGoodsBatchEntity> items;
        private View.OnClickListener onClickListener;

        public class Holder {
            TextView tvBatch;
            TextView tvStockNum;

            Holder(View arg3) {
                this.tvBatch = arg3.findViewById(R.id.tvBatch);
                this.tvStockNum = arg3.findViewById(R.id.tvStockNum);
            }

            public void setValue(RespGoodsBatchEntity arg5) {
                this.tvBatch.setText(arg5.getBatch());
                if (!"1".equals(new AccountPreference().getValue("ViewKCStockBrowse", "0"))) {
                    this.tvStockNum.setVisibility(View.GONE);
                } else {
                    this.tvStockNum.setText(arg5.getBigstocknum());
                }
            }
        }

        GoodsBatchAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.items == null ? 0 : this.items.size();
        }

        public List<RespGoodsBatchEntity> getData() {
            return this.items;
        }

        @Override
        public RespGoodsBatchEntity getItem(int position) {
            return this.items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_goodsbatch, null);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.setValue(this.items.get(position));
            return convertView;
        }

        public void setData(List<RespGoodsBatchEntity> items) {
            this.items = items;
            this.notifyDataSetChanged();
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
    }
}
