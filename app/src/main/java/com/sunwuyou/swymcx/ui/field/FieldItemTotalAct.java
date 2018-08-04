package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.ExpandableListView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemBatchDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.GoodsBatchDAO;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItemBatchEx;
import com.sunwuyou.swymcx.model.FieldSaleItemTotal;
import com.sunwuyou.swymcx.model.GoodsBatch;
import com.sunwuyou.swymcx.model.GoodsBatchForUpdate;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class FieldItemTotalAct extends BaseHeadActivity {

    HashMap<String, Object> map;
    private FieldSale fieldSale;
    private ExpandableListView listView;
    private FieldItemTotalAdapter adapter;
    private GoodsUnitDAO goodsUnitDAO;
    private GoodsDAO goodsDAO;
    private boolean isallexpanded;

    @Override
    public int getLayoutID() {
        return R.layout.act_item_total;
    }

    @Override
    public void initView() {
        this.map = new HashMap<>();
        fieldSale = new FieldSaleDAO().getFieldsale(this.getIntent().getLongExtra("fieldsaleid", -1));
        listView = this.findViewById(R.id.listView);
        adapter = new FieldItemTotalAdapter(this);
        goodsUnitDAO = new GoodsUnitDAO();
        goodsDAO = new GoodsDAO();
        if (this.fieldSale.getStatus() == 0) {
            setTitleRight("执行", null);
        }
        if (this.isallexpanded) {
            setTitleRight1("收起");
        } else {
            setTitleRight1("展开");
        }
        loadData();

    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        //执行
        PDH.show(this, "正在执行库存处理...", new PDH.ProgressCallBack() {
            @Override
            public void action() {
                for (int i = 0; i < listItemTotal.size(); i++) {
                    FieldSaleItemTotal v12 = listItemTotal.get(i);
                    if (v12.getIsusebatch()) {
                        for (int j = 0; j < v12.getItems().size(); j++) {
                            FieldSaleItemBatchEx v11 = v12.getItems().get(j);
                            if ((v11.getIsout()) && v11.getNum() * v11.getRatio() > v11.getStocknumber()) {
                                handler.sendMessage(handler.obtainMessage(5, new GoodsDAO().getGoodsThin(v11.getGoodsid()).getName()));
                                return;
                            }
                        }
                    }
                }
                GoodsUnitDAO v7 = new GoodsUnitDAO();
                GoodsBatchDAO v5 = new GoodsBatchDAO();
                List<HashMap<String, String>> v16 = new ArrayList<>();
                ArrayList<GoodsBatchForUpdate> v15 = new ArrayList<>();
                for (int i = 0; i < listItemTotal.size(); i++) {
                    FieldSaleItemTotal v12 = listItemTotal.get(i);
                    if (v12.getIsusebatch()) {
                        for (int j = 0; j < v12.getItems().size(); j++) {
                            FieldSaleItemBatchEx v11 = v12.getItems().get(j);
                            if (TextUtils.isEmptyS(v11.getProductiondate())) {
                                v11.setProductiondate(null);
                            }
                            if ((v11.getIsout()) && v11.getNum() != 0) {
                                String v25 = " insert into kf_fieldsaleitembatch(fieldsaleid,goodsid,batch,productiondate,unitid,price,num,isout)  values(%s,\'%s\',\'%s\',\'%s\',\'%s\',%s,%s,%s) ";
                                Object[] v26 = new Object[8];
                                v26[0] = v11.getFieldsaleid();
                                v26[1] = v11.getGoodsid();
                                v26[2] = v11.getBatch();
                                v26[3] = v11.getProductiondate();
                                v26[4] = v11.getUnitid();
                                v26[5] = v11.getPrice();
                                v26[6] = v11.getNum();
                                v26[7] = v11.getIsout() ? "1" : "0";
                                HashMap<String, String> v19 = new HashMap<>();
                                v19.put("sql", String.format(v25, v26));
                                v16.add(v19);
                            }
                            if (v11.getIsout()) {
                                GoodsBatchForUpdate v6 = new GoodsBatchForUpdate();
                                v6.setGoodsid(v11.getGoodsid());
                                v6.setBatch(v11.getBatch());
                                v6.setProductiondate(v11.getProductiondate());
                                v6.setStocknumber(v11.getStocknumber() - v11.getNum() * v11.getRatio());
                                v15.add(v6);
                            } else {
                                GoodsBatchForUpdate v6_1 = null;
                                for (int k = 0; k < v15.size(); k++) {
                                    GoodsBatchForUpdate v23 = v15.get(k);
                                    if ((v23.getGoodsid().equals(v11.getGoodsid())) && (v23.getBatch().equals(v11.getBatch()))) {
                                        v6_1 = v23;
                                        break;
                                    }
                                }
                                if (v6_1 != null) {
                                    v6_1.setStocknumber(v6_1.getStocknumber() + v11.getNum() * v11.getRatio());
                                }
                                GoodsBatchForUpdate v6 = new GoodsBatchForUpdate();
                                v6.setGoodsid(v11.getGoodsid());
                                v6.setBatch(v11.getBatch());
                                v6.setProductiondate(v11.getProductiondate());
                                GoodsBatch v8 = new GoodsBatchDAO().queryGoodsBatch(v11.getGoodsid(), v11.getBatch());
                                if (v8 == null) {
                                    v6.setStocknumber(v11.getNum() * v11.getRatio());
                                } else {
                                    v6.setStocknumber(v11.getNum() * v11.getRatio() + v8.getStocknumber());
                                }
                                v15.add(v6);
                            }
                        }
                    } else {
                        if (v12.getOutbasicnum() > 0) {
                            for (int j = 0; j < v12.getItems().size(); j++) {
                                FieldSaleItemBatchEx v11 = v12.getItems().get(j);
                                if (v11.getIsout()) {
                                    String v25 = " insert into kf_fieldsaleitembatch(fieldsaleid,goodsid,unitid,price,num,isout)  values(%s,\'%s\',\'%s\',%s,%s,%s) ";
                                    Object[] v26 = new Object[6];
                                    v26[0] = v11.getFieldsaleid();
                                    v26[1] = v11.getGoodsid();
                                    v26[2] = v11.getUnitid();
                                    v26[3] = v11.getPrice();
                                    v26[4] = v11.getNum();
                                    v26[5] = v11.getIsout() ? "1" : "0";
                                    HashMap<String, String> v19 = new HashMap<>();
                                    v19.put("sql", String.format(v25, v26));
                                    v16.add(v19);
                                }
                            }
                            double v21 = goodsDAO.getGoodsThin(v12.getGoodsid()).getStocknumber() - (v12.getOutbasicnum() - v12.getInbasicnum());
                            String v20 = String.format(" update sz_goods set stocknumber = %s, bigstocknumber = \'%s\' where id = \'%s\' ", Double.valueOf(v21), v7.getBigNum(((FieldSaleItemTotal) v12).getGoodsid(), null, v21), ((FieldSaleItemTotal) v12).getGoodsid());
                            HashMap<String, String> v19 = new HashMap<>();
                            v19.put("sql", v20);
                            v16.add(v19);
                        }
                    }

                    for (int j = 0; j < v15.size(); j++) {
                        GoodsBatchForUpdate v6_1 = v15.get(j);
                        String v20 = String.format(" replace into kf_goodsbatch(goodsid,batch,productiondate,stocknumber,bigstocknumber)  values(\'%s\',\'%s\',\'%s\',%s,\'%s\') ", ((GoodsBatchForUpdate) v6_1).getGoodsid(), ((GoodsBatchForUpdate) v6_1).getBatch(), ((GoodsBatchForUpdate) v6_1).getProductiondate(), Double.valueOf(((GoodsBatchForUpdate) v6_1).getStocknumber()), v7.getBigNum(((GoodsBatchForUpdate) v6_1).getGoodsid(), null, ((GoodsBatchForUpdate) v6_1).getStocknumber()));
                        HashMap<String, String> v19 = new HashMap<>();
                        v19.put("sql", v20);
                        v16.add(v19);
                    }
                    if (v16.size() > 0) {
                        String v20 = String.format(" update kf_fieldsale set status = %s where id = %s ", "1", Long.valueOf(FieldItemTotalAct.this.fieldSale.getId()));
                        HashMap<String, String> v19 = new HashMap<>();
                        v19.put("sql", v20);
                        v16.add(v19);
                        if (new UpdateUtils().saveToLocalDB(v16)) {
                            v16 = new ArrayList<>();
                        } else {
                            FieldItemTotalAct.this.handler.sendEmptyMessage(2);
                            return;
                        }

                        for (int j = 0; j < listItemTotal.size(); j++) {
                            FieldSaleItemTotal v12s = listItemTotal.get(j);
                            if (v12s.getIsusebatch()) {
                                double v21 = v5.queryGoodStock(v12.getGoodsid());
                                v20 = String.format(" update sz_goods set stocknumber = %s, bigstocknumber = \'%s\' where id = \'%s\' ", Double.valueOf(v21), v7.getBigNum(((FieldSaleItemTotal) v12).getGoodsid(), null, v21), ((FieldSaleItemTotal) v12).getGoodsid());
                                HashMap<String, String> v19s = new HashMap<>();
                                v19s.put("sql", v20);
                                v16.add(v19s);
                            }
                        }
                        new UpdateUtils().saveToLocalDB(v16);
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }

    @Override
    protected void onRightClick1() {
        super.onRightClick1();

        //展开
        if (isallexpanded) {
            setTitleRight1("展开");
        } else {
            setTitleRight1("收起");
        }
        int groupCount = adapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            if (this.isallexpanded) {
                this.listView.collapseGroup(i);
            } else {
                this.listView.expandGroup(i);
            }
        }

        if (this.isallexpanded) {
            this.isallexpanded = false;
        } else {
            this.isallexpanded = true;
        }

    }

    private List<FieldSaleItemTotal> listItemTotal;

    private void loadData() {
        PDH.show(this, new PDH.ProgressCallBack() {
            @Override
            public void action() {
                FieldItemTotalAct v18 = FieldItemTotalAct.this;
                FieldSaleItemDAO v19 = new FieldSaleItemDAO();
                long v20 = FieldItemTotalAct.this.fieldSale.getId();
                boolean v17 = fieldSale.getStatus() != 0;
                v18.listItemTotal = v19.queryFieldItemTotal(v20, v17);
                if (fieldSale.getStatus() != 0) {
                    handler.sendEmptyMessage(0);
                } else {

                    for (int i = 0; i < listItemTotal.size(); i++) {
                        FieldSaleItemTotal v11 = listItemTotal.get(i);
                        GoodsUnit v4 = goodsUnitDAO.getBasicUnit(v11.getGoodsid());
                        ArrayList<FieldSaleItemBatchEx> v14 = new ArrayList<>();
                        if (v11.getIsusebatch()) {
                            double v15 = v11.getOutbasicnum();
                            List<GoodsBatch> v7 = new GoodsBatchDAO().queryGoodsBatch(v11.getGoodsid(), true);
                            for (int j = 0; j < v7.size(); j++) {
                                GoodsBatch v6 = v7.get(j);
                                FieldSaleItemBatchEx v10 = new FieldSaleItemBatchEx();
                                v10.setFieldsaleid(fieldSale.getId());
                                v10.setGoodsid(v11.getGoodsid());
                                v10.setIsusebatch(v11.getIsusebatch());
                                v10.setBatch(v6.getBatch());
                                v10.setProductiondate(v6.getProductiondate());
                                v10.setUnitid(v4.getUnitid());
                                v10.setUnitname(v4.getUnitname());
                                v10.setPrice(0);
                                v10.setRatio(1);
                                v10.setIsout(true);
                                if (v15 == 0) {
                                    v10.setNum(0);
                                } else if (v6.getStocknumber() >= v15) {
                                    v10.setNum(v15);
                                    v15 = 0;
                                } else {
                                    v10.setNum(v6.getStocknumber());
                                    v15 -= v6.getStocknumber();
                                }
                                v10.setStocknumber(v6.getStocknumber());
                                v10.setBigstocknumber(v6.getBigstocknumber());
                                v14.add(v10);
                                map.put(v10.getGoodsid(), v14);
                            }
                            if (v15 > 0) {
                                handler.sendMessage(handler.obtainMessage(3, v11.getGoodsname()));
                                return;
                            }
                            ArrayList<FieldSaleItemBatchEx> v5 = new ArrayList<>();
                            for (int j = 0; j < v14.size(); j++) {
                                if (v14.get(j).getNum() > 0) {
                                    v5.add((v14).get(j));
                                }
                            }
                            v5.addAll(new FieldSaleItemBatchDAO().getGoodsCancelItemBatch(fieldSale.getId(), v11.getGoodsid()));
                            v11.setItems(v5);
                        } else {
                            ArrayList<FieldSaleItemBatchEx> v5 = new ArrayList<>();
                            if (v11.getOutbasicnum() > 0) {
                                GoodsThin v8 = goodsDAO.getGoodsThin(v11.getGoodsid());
                                if (v11.getOutbasicnum() > v8.getStocknumber()) {
                                    handler.sendMessage(handler.obtainMessage(4, v11.getGoodsname()));
                                    return;
                                } else {
                                    FieldSaleItemBatchEx v10 = new FieldSaleItemBatchEx();
                                    v10.setFieldsaleid(FieldItemTotalAct.this.fieldSale.getId());
                                    v10.setGoodsid(v11.getGoodsid());
                                    v10.setIsusebatch(v11.getIsusebatch());
                                    v10.setBatch(null);
                                    v10.setProductiondate(null);
                                    v10.setUnitid(v4.getUnitid());
                                    v10.setUnitname(v4.getUnitname());
                                    v10.setPrice(0);
                                    v10.setRatio(1);
                                    v10.setIsout(true);
                                    v10.setNum(v11.getOutbasicnum());
                                    v10.setStocknumber(v8.getStocknumber());
                                    v10.setBigstocknumber(v8.getBigstocknumber());
                                    v5.add(v10);
                                }
                            }
                            v5.addAll(new FieldSaleItemBatchDAO().getGoodsCancelItemBatch(fieldSale.getId(), v11.getGoodsid()));
                            v11.setItems(v5);
                        }
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        });

    }

    Menu menu;
    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setData(listItemTotal);
                    listView.setAdapter(adapter);
                    break;
                case 1:
                    PDH.showSuccess("库存处理成功");
                    finish();
                    break;
                case 2:
                    PDH.showFail("库存处理失败");
                    break;
                case 3:
                    menu.clear();
                    PDH.showFail("库存处理失败，【" + msg.obj.toString() + "】批次库存不足");
                    finish();
                    break;
                case 4:
                    FieldItemTotalAct.this.menu.clear();
                    PDH.showFail("库存处理失败，【" + msg.obj.toString() + "】剩余库存不足");
                    FieldItemTotalAct.this.finish();
                    break;
                case 5:
                    FieldItemTotalAct.this.menu.clear();
                    PDH.showFail("库存处理失败，【" + msg.obj.toString() + "】批次库存不足");
                    break;
                case 6:
                    FieldItemTotalAct.this.menu.clear();
                    PDH.showFail("库存处理失败，【" + msg.obj.toString() + "】剩余库存不足");
                    break;
            }

        }
    };

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
