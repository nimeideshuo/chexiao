package com.sunwuyou.swymcx.ui.transfer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.dao.TransferItemDAO;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.model.TransferItem;
import com.sunwuyou.swymcx.model.TransferItemSource;
import com.sunwuyou.swymcx.model.Warehouse;
import com.sunwuyou.swymcx.request.ReqStrGetGoodsPrice;
import com.sunwuyou.swymcx.service.ServiceGoods;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.EditButtonView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/8/5.
 * content
 */

public class TransferAddMoreGoodsAct extends BaseHeadActivity {

    private List<GoodsThin> goodsThins;
    private ListView listView;
    private TransferDoc transferDoc;
    private TransferAddMoreAdapter adapter;
    private ArrayList<TransferItemSource> listItems;
    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setData(listItems);
                    break;
                case 1:
                    PDH.showFail(msg.obj.toString());
                    break;
                case 2:
                    if (TextUtils.isEmptyS(msg.obj.toString())) {
                        PDH.showSuccess("操作成功");
                    } else {
                        PDH.showMessage(msg.obj.toString());
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_add_more_goods;

    }

    @Override
    public void initView() {
        setTitleRight("保存", null);
        String v0 = this.getIntent().getStringExtra("goods");
        this.transferDoc = (TransferDoc) this.getIntent().getSerializableExtra("transferdoc");
        goodsThins = JSONUtil.str2list(v0, GoodsThin.class);
        listView = this.findViewById(R.id.listView);
        adapter = new TransferAddMoreAdapter(this);
        this.listView.setAdapter(this.adapter);
        listItems = new ArrayList<>();
        this.dealGoods();
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        PDH.show(this, new PDH.ProgressCallBack() {

            @Override
            public void action() {
                TransferItemDAO v9 = new TransferItemDAO();
                GoodsDAO v0 = new GoodsDAO();
                List<TransferItemSource> v5 = adapter.getData();
                ArrayList<ReqStrGetGoodsPrice> v6 = new ArrayList<>();
                for (int i = 0; i < v5.size(); i++) {
                    ReqStrGetGoodsPrice v1 = new ReqStrGetGoodsPrice();
                    v1.setCustomerid(null);
                    v1.setWarehouseid(null);
                    v1.setGoodsid(v5.get(i).getGoodsid());
                    v1.setUnitid(v5.get(i).getUnitid());
                    v1.setType(0);
                    v1.setPrice(0);
                    v1.setIsdiscount(false);
                    v1.setBatch(null);
                    v1.setProductiondate(null);
                    v6.add(v1);
                }

                String v7 = new ServiceGoods().gds_GetMultiGoodsPrice(v6, true, true);
                if (RequestHelper.isSuccess(v7)) {
                    int v3 = 0;
                    List<ReqStrGetGoodsPrice> v6_1 = JSONUtil.str2list(v7, ReqStrGetGoodsPrice.class);
                    Warehouse warehouse = SystemState.getWarehouse();
                    for (int i = 0; i < v6_1.size(); i++) {
                        ReqStrGetGoodsPrice v1_1 = v6_1.get(i);
                        TransferItemSource v4 = v5.get(i);
                        //修改调拨单仓库
//                        v4.setWarehouseid(v1_1.getWarehouseid());
                        v4.setWarehouseid(warehouse.getId());
                        v4.setWarehousename(warehouse.getName());
                        v4.setBatch(v1_1.getBatch());
                        v4.setProductiondate(v1_1.getProductiondate());
                        if ((v0.getGoodsThin(v4.getGoodsid()).getIsusebatch()) && (TextUtils.isEmptyS(v4.getBatch()))) {
                            v3 = 1;
                        }

                        v9.saveTransferItem(v4);
                    }
                    if (v3 != 0) {
                        handler.sendMessage(handler.obtainMessage(2, "存在部分商品在指定仓库无库存"));
                        return;
                    }
                    handler.sendMessage(handler.obtainMessage(2, ""));

                } else {
                    handler.sendMessage(handler.obtainMessage(1, v7));
                }
            }
        });

    }

    private void dealGoods() {
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                for (int i = 0; i < goodsThins.size(); i++) {
                    GoodsThin v0 = goodsThins.get(i);
                    GoodsUnit v1 = Utils.DEFAULT_UNIT == 0 ? new GoodsUnitDAO().getBasicUnit(v0.getId()) : new GoodsUnitDAO().getBigUnit(((GoodsThin) v0).getId());
                    TransferItemSource v3 = new TransferItemSource();
                    v3.setTransferdocid(transferDoc.getId());
                    v3.setGoodsid(v0.getId());
                    v3.setGoodsname(v0.getName());
                    v3.setBarcode(v0.getBarcode());
                    v3.setSpecification(v0.getSpecification());
                    v3.setIsusebatch(v0.getIsusebatch());
                    v3.setUnitid(v1.getUnitid());
                    v3.setUnitname(v1.getUnitname());
                    v3.setNum(0);
                    listItems.add(v3);
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    public void initData() {

    }

    public void setActionBarText() {
        setTitle("商品添加");
    }

    class TransferAddMoreAdapter extends BaseAdapter {
        private final ArrayList<TransferItemSource> listItems;
        private Context context;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button btn = (Button) v;
                int v4 = Integer.parseInt(v.getTag().toString());
                final List<GoodsUnit> goodsUnits = new GoodsUnitDAO().queryGoodsUnits(listItems.get(v4).getGoodsid());
                final String[] goodsNames = new String[goodsUnits.size()];
                for (int i = 0; i < goodsUnits.size(); i++) {
                    goodsNames[i] = goodsUnits.get(i).getUnitname();
                }
                AlertDialog.Builder v6 = new AlertDialog.Builder(TransferAddMoreAdapter.this.context);
                v6.setTitle("单位选择");
                v6.setItems(goodsNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg3, int position) {
                        btn.setText(goodsNames[position]);
                        listItems.get(position).setUnitid(goodsUnits.get(position).getUnitid());
                        listItems.get(position).setUnitname(goodsUnits.get(position).getUnitname());
                    }
                });
                v6.create().show();
            }
        };

        TransferAddMoreAdapter(Context context) {
            this.context = context;
            listItems = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        public List<TransferItemSource> getData() {
            return this.listItems;
        }

        public void setData(List<TransferItemSource> arg2) {
            this.listItems.clear();
            this.listItems.addAll(arg2);
            this.notifyDataSetChanged();
        }

        @Override
        public TransferItemSource getItem(int position) {
            return this.listItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.context).inflate(R.layout.item_add_more_goods, null);
                item = new Item(convertView);
                convertView.setTag(item);
            } else {
                item = (Item) convertView.getTag();
            }
            item.btnUnit.setTag(position);
            item.btnUnit.setOnClickListener(this.onClickListener);
            item.etNum.setTag(position);
            item.etNum.addTextChangedListener(new NumWatcher(item));
            item.setValue(this.listItems.get(position));
            return convertView;
        }

        class NumWatcher implements TextWatcher {
            Item item;

            public NumWatcher(Item item) {
                this.item = item;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listItems.get(Integer.parseInt(item.etNum.getTag().toString())).setNum(Utils.getDouble(s.toString()));
            }
        }

        class Item {
            private Button btnUnit;
            private EditButtonView etNum;
            private TextView tvBarcode;
            private TextView tvName;

            Item(View arg3) {
                this.tvName = arg3.findViewById(R.id.tvName);
                this.tvBarcode = arg3.findViewById(R.id.tvBarcode);
                this.btnUnit = arg3.findViewById(R.id.btnUnit);
                this.etNum = arg3.findViewById(R.id.etNum);
            }

            public void setValue(TransferItemSource arg7) {
                this.tvName.setText(arg7.getGoodsname());
                this.tvBarcode.setText(arg7.getBarcode());
                this.btnUnit.setText(arg7.getUnitname());
                String v0 = arg7.getNum() == 0 ? "" : String.valueOf(arg7.getNum());
                this.etNum.setText(v0);
            }
        }
    }
}
