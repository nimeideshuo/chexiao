package com.sunwuyou.swymcx.ui.field;

import android.widget.ExpandableListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItemBatchEx;
import com.sunwuyou.swymcx.model.FieldSaleItemTotal;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.utils.PDH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class FieldItemTotalAct extends BaseHeadActivity {

    HashMap map;
    private FieldSale fieldSale;
    private ExpandableListView listView;
    private FieldItemTotalAdapter adapter;
    private GoodsUnitDAO goodsUnitDAO;
    private GoodsDAO goodsDAO;

    @Override
    public int getLayoutID() {
        return R.layout.act_item_total;
    }

    @Override
    public void initView() {
        this.map = new HashMap();
        fieldSale = new FieldSaleDAO().getFieldsale(this.getIntent().getLongExtra("fieldsaleid", -1));
        listView = this.findViewById(R.id.listView);
        adapter = new FieldItemTotalAdapter(this);
        goodsUnitDAO = new GoodsUnitDAO();
        goodsDAO = new GoodsDAO();
        loadData();

    }

    private void loadData() {
//        PDH.show(this, new PDH.ProgressCallBack() {
//            public void action() {
//                ArrayList v5;
//                FieldSaleItemBatchEx v10;
//                FieldItemTotalAct v18 = FieldItemTotalAct.this;
//                FieldSaleItemDAO v19 = new FieldSaleItemDAO();
//                long v20 = fieldSale.getId();
//                boolean v17 = fieldSale.getStatus() != 0;
//                v18.listItemTotal = v19.queryFieldItemTotal(v20, v17);
//                if (FieldItemTotalAct.this.fieldSale.getStatus() != 0) {
//                    FieldItemTotalAct.this.handler.sendEmptyMessage(0);
//                } else {
//                    int v9;
//                    for (v9 = 0; v9 < FieldItemTotalAct.this.listItemTotal.size(); ++v9) {
//                        Object v11 = FieldItemTotalAct.this.listItemTotal.get(v9);
//                        GoodsUnit v4 = FieldItemTotalAct.this.goodsUnitDAO.getBasicUnit(((FieldSaleItemTotal) v11).getGoodsid());
//                        ArrayList v14 = new ArrayList();
//                        if (((FieldSaleItemTotal) v11).getIsusebatch()) {
//                            double v15 = ((FieldSaleItemTotal) v11).getOutbasicnum();
//                            List v7 = new GoodsBatchDAO().queryGoodsBatch(((FieldSaleItemTotal) v11).getGoodsid(), true);
//                            int v12;
//                            for (v12 = 0; v12 < v7.size(); ++v12) {
//                                Object v6 = v7.get(v12);
//                                v10 = new FieldSaleItemBatchEx();
//                                v10.setFieldsaleid(FieldItemTotalAct.this.fieldSale.getId());
//                                v10.setGoodsid(((FieldSaleItemTotal) v11).getGoodsid());
//                                v10.setIsusebatch(((FieldSaleItemTotal) v11).getIsusebatch());
//                                v10.setBatch(((GoodsBatch) v6).getBatch());
//                                v10.setProductiondate(((GoodsBatch) v6).getProductiondate());
//                                v10.setUnitid(v4.getUnitid());
//                                v10.setUnitname(v4.getUnitname());
//                                v10.setPrice(0);
//                                v10.setRatio(1);
//                                v10.setIsout(true);
//                                if (v15 == 0) {
//                                    v10.setNum(0);
//                                } else if (((GoodsBatch) v6).getStocknumber() >= v15) {
//                                    v10.setNum(v15);
//                                    v15 = 0;
//                                } else {
//                                    v10.setNum(((GoodsBatch) v6).getStocknumber());
//                                    v15 -= ((GoodsBatch) v6).getStocknumber();
//                                }
//
//                                v10.setStocknumber(((GoodsBatch) v6).getStocknumber());
//                                v10.setBigstocknumber(((GoodsBatch) v6).getBigstocknumber());
//                                ((List) v14).add(v10);
//                                FieldItemTotalAct.this.map.put(v10.getGoodsid(), v14);
//                            }
//
//                            if (v15 > 0) {
//                                FieldItemTotalAct.this.handler.sendMessage(FieldItemTotalAct.this.handler.obtainMessage(3, ((FieldSaleItemTotal) v11).getGoodsname()));
//                                return;
//                            }
//
//                            v5 = new ArrayList();
//                            int v13;
//                            for (v13 = 0; v13 < ((List) v14).size(); ++v13) {
//                                if (((List) v14).get(v13).getNum() > 0) {
//                                    ((List) v5).add(((List) v14).get(v13));
//                                }
//                            }
//
//                            ((List) v5).addAll(new FieldSaleItemBatchDAO().getGoodsCancelItemBatch(FieldItemTotalAct.this.fieldSale.getId(), ((FieldSaleItemTotal) v11).getGoodsid()));
//                            ((FieldSaleItemTotal) v11).setItems(((List) v5));
//                        } else {
//                            v5 = new ArrayList();
//                            if (((FieldSaleItemTotal) v11).getOutbasicnum() > 0) {
//                                GoodsThin v8 = FieldItemTotalAct.this.goodsDAO.getGoodsThin(((FieldSaleItemTotal) v11).getGoodsid());
//                                if (((FieldSaleItemTotal) v11).getOutbasicnum() > v8.getStocknumber()) {
//                                    FieldItemTotalAct.this.handler.sendMessage(FieldItemTotalAct.this.handler.obtainMessage(4, ((FieldSaleItemTotal) v11).getGoodsname()));
//                                    return;
//                                } else {
//                                    v10 = new FieldSaleItemBatchEx();
//                                    v10.setFieldsaleid(FieldItemTotalAct.this.fieldSale.getId());
//                                    v10.setGoodsid(((FieldSaleItemTotal) v11).getGoodsid());
//                                    v10.setIsusebatch(((FieldSaleItemTotal) v11).getIsusebatch());
//                                    v10.setBatch(null);
//                                    v10.setProductiondate(null);
//                                    v10.setUnitid(v4.getUnitid());
//                                    v10.setUnitname(v4.getUnitname());
//                                    v10.setPrice(0);
//                                    v10.setRatio(1);
//                                    v10.setIsout(true);
//                                    v10.setNum(((FieldSaleItemTotal) v11).getOutbasicnum());
//                                    v10.setStocknumber(v8.getStocknumber());
//                                    v10.setBigstocknumber(v8.getBigstocknumber());
//                                    ((List) v5).add(v10);
//                                }
//                            }
//
//                            ((List) v5).addAll(new FieldSaleItemBatchDAO().getGoodsCancelItemBatch(FieldItemTotalAct.this.fieldSale.getId(), ((FieldSaleItemTotal) v11).getGoodsid()));
//                            ((FieldSaleItemTotal) v11).setItems(((List) v5));
//                        }
//                    }
//
//                    FieldItemTotalAct.this.handler.sendEmptyMessage(0);
//                }
//            }
//        });
    }

    @Override
    public void initData() {

    }

    public void setActionBarText() {
        if (this.fieldSale == null || this.fieldSale.getStatus() != 0) {
            setTitle("批次明细");
        } else {
            setTitle("库存处理");
        }
    }
}
