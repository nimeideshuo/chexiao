package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.GoodsPriceDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.dao.PromotionGoodsDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItemSource;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.model.PromotionGoods;
import com.sunwuyou.swymcx.model.Warehouse;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/28.
 * content
 */

public class FieldAddMoreGoodsAct extends BaseHeadActivity {

    private FieldSale fieldSale;
    private List<GoodsThin> goodsThins;
    private ListView listView;
    private FieldAddMoreAdapter adapter;
    private List<FieldSaleItemSource> listItems;
    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                adapter.setData(listItems);
            } else if (msg.what == 1) {
                finish();
            }
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_add_more_goods;
    }

    @Override
    public void initView() {
        setTitle("商品添加");
        setTitleRight("保存", null);
        String v0 = this.getIntent().getStringExtra("goods");
        fieldSale = (FieldSale) getIntent().getSerializableExtra("fieldsale");
        goodsThins = JSONUtil.str2list(v0, GoodsThin.class);
        listView = findViewById(R.id.listView);
        adapter = new FieldAddMoreAdapter(this);
        this.listView.setAdapter(this.adapter);
        this.listItems = new ArrayList<>();
        this.dealGoods();
    }

    private void dealGoods() {
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                int v1;
                for (v1 = 0; v1 < FieldAddMoreGoodsAct.this.goodsThins.size(); ++v1) {
                    GoodsUnit v0 = Utils.DEFAULT_UNIT == 0 ? new GoodsUnitDAO().getBasicUnit(goodsThins.get(v1).getId()) : new GoodsUnitDAO().getBigUnit(goodsThins.get(v1).getId());
                    int v2 = 0;
                    if (!TextUtils.isEmptyS(fieldSale.getPromotionid()) && new PromotionGoodsDAO().getPromotiongoods(fieldSale.getPromotionid(), goodsThins.get(v1).getId()) != null) {
                        v2 = 1;
                    }
                    Warehouse v5 = SystemState.getWarehouse();
                    FieldSaleItemSource v3 = new FieldSaleItemSource();
                    v3.setFieldsaleid(FieldAddMoreGoodsAct.this.fieldSale.getId());
                    v3.setGoodsid(FieldAddMoreGoodsAct.this.goodsThins.get(v1).getId());
                    v3.setGoodsname(FieldAddMoreGoodsAct.this.goodsThins.get(v1).getName());
                    v3.setBarcode(FieldAddMoreGoodsAct.this.goodsThins.get(v1).getBarcode());
                    v3.setSpecification(FieldAddMoreGoodsAct.this.goodsThins.get(v1).getSpecification());
                    v3.setSaleunitid(v0.getUnitid());
                    v3.setSaleunitname(v0.getUnitname());
                    v3.setGiveunitid(v0.getUnitid());
                    v3.setWarehouseid(v5.getId());
                    if (v2 != 0) {
                        v3.setIspromotion(true);
                        v3.setPromotiontype(0);
                    } else {
                        v3.setIspromotion(false);
                        v3.setPromotiontype(-1);
                    }

                    listItems.add(v3);
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        PDH.show(this, new PDH.ProgressCallBack() {
            public void action() {
                List<FieldSaleItemSource> v9 = adapter.getData();
                int v8;
                for (v8 = 0; v8 < v9.size(); ++v8) {
                    if (v9.get(v8).getSalenum() > 0) {
                        if (v9.get(v8).isIspromotion()) {
                            PromotionGoods v12 = new PromotionGoodsDAO().getPromotiongoods(fieldSale.getPromotionid(), v9.get(v8).getGoodsid());
                            if (v12 == null) {
                                v9.get(v8).setSaleprice(0);
                                return;
                            } else {
                                v9.get(v8).setSaleprice(v12.getPrice() * new GoodsUnitDAO().getGoodsUnitRatio(v9.get(v8).getGoodsid(), v9.get(v8).getSaleunitid()) / new GoodsUnitDAO().getGoodsUnitRatio(v9.get(v8).getGoodsid(), v12.getUnitid()));
                            }
                        } else {
                            v9.get(v8).setSaleprice(new GoodsPriceDAO().queryGoodsPrice(v9.get(v8).getGoodsid(), v9.get(v8).getSaleunitid(), FieldAddMoreGoodsAct.this.fieldSale.getCustomerid(), FieldAddMoreGoodsAct.this.fieldSale.isIsnewcustomer(), FieldAddMoreGoodsAct.this.fieldSale.getPricesystemid()));
                        }
                        new FieldSaleItemDAO().saveFieldSaleItem(v9.get(v8));
                    }
                }
                handler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    public void initData() {

    }
}
