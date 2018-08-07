package com.sunwuyou.swymcx.ui;

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

public class GoodsWarehouseSearchAct extends BaseHeadActivity implements AdapterView.OnItemClickListener {

    private ListView listview;
    private GoodsWarehouseAdapter adapter;
    private String inwarehouseid;
    private boolean isGetBatch;
    private List<RespGoodsBatchEntity> goodsWarehouses;

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
        adapter = new GoodsWarehouseAdapter(this);
        this.listview.setAdapter(this.adapter);
        this.inwarehouseid = this.getIntent().getStringExtra("inwarehouseid");
        String v0 = this.getIntent().getStringExtra("goodsid");
        this.isGetBatch = this.getIntent().getBooleanExtra("isgetbatch", false);
        this.loadData(v0, this.isGetBatch);
    }

    private void loadData(final String goodsid, final boolean isGetBatch) {
        PDH.show(this, "正在查询仓库...", new PDH.ProgressCallBack() {
            public void action() {
                String v0 = new ServiceGoods().gds_GetGoodsWarehouses(goodsid, isGetBatch);
                if (RequestHelper.isSuccess(v0)) {
                    handler.sendMessage(handler.obtainMessage(0, v0));
                } else {
                    handler.sendMessage(handler.obtainMessage(1, v0));
                }
            }
        });
    }

    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                goodsWarehouses = JSONUtil.str2list(msg.obj.toString(), RespGoodsBatchEntity.class);
                if (goodsWarehouses.size() == 0) {
                    PDH.showMessage("无可用仓库");
                    return;
                }
                if (!TextUtils.isEmptyS(inwarehouseid)) {
                    for (int i = 0; i < goodsWarehouses.size(); i++) {
                        if (goodsWarehouses.get(i).getWarehouseId().equals(inwarehouseid)) {
                            goodsWarehouses.remove(i);
                        }
                    }
                    adapter.setData(goodsWarehouses);
                }
            } else {
                PDH.showFail("仓库读取失败");
            }
        }
    };

    @Override
    public void initData() {

    }

    class GoodsWarehouseAdapter extends BaseAdapter {
        private boolean isShowStockNum;
        private List<RespGoodsBatchEntity> items;
        private Context context;

        GoodsWarehouseAdapter(Context context) {
            this.isShowStockNum = true;
            this.context = context;
            this.isShowStockNum = "1".equals(new AccountPreference().getValue("ViewKCStockBrowse", "0"));
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
            Holder v0_1;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_goodswarehouse, null);
                v0_1 = new Holder(convertView);
                convertView.setTag(v0_1);
            } else {
                v0_1 = (Holder) convertView.getTag();
            }
            v0_1.setValue(this.items.get(position));
            return convertView;
        }

        public class Holder {
            public TextView tvStockNum;
            public TextView tvWarehouse;

            public Holder(View arg3) {
                this.tvWarehouse = arg3.findViewById(R.id.tvWarehouse);
                this.tvStockNum = arg3.findViewById(R.id.tvStockNum);
            }

            public void setValue(RespGoodsBatchEntity arg3) {
                this.tvWarehouse.setText(arg3.getWarehouseName());
                if (!isShowStockNum) {
                    this.tvStockNum.setVisibility(View.GONE);
                } else {
                    this.tvStockNum.setText(arg3.getBigstocknum());
                }
            }
        }

        public void setData(List<RespGoodsBatchEntity> items) {
            this.items = items;
            this.notifyDataSetChanged();
        }

        private View.OnClickListener onClickListener;

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RespGoodsBatchEntity entity = this.goodsWarehouses.get(position);
        Intent intent = new Intent();
        intent.putExtra("warehouseid", entity.getWarehouseId());
        intent.putExtra("warehousename", entity.getWarehouseName());
        if (this.isGetBatch) {
            intent.putExtra("batch", entity.getBatch());
            intent.putExtra("productiondate", entity.getProductiondate());
        }
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    public void setActionBarText() {
        setTitle("仓库");
    }
}
