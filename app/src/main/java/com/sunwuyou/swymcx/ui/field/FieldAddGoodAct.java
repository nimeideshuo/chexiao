package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemBatchDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.GoodsPriceDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.dao.PromotionGoodsDAO;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItemBatchEx;
import com.sunwuyou.swymcx.model.FieldSaleItemSource;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.model.PromotionGoods;
import com.sunwuyou.swymcx.model.Warehouse;
import com.sunwuyou.swymcx.ui.GoodsSearchAct;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.Date;
import java.util.List;

/**
 * Created by admin
 * 2018/7/27.
 * content
 */

public class FieldAddGoodAct extends BaseHeadActivity {

    private ItemCancelBatchAdapter adapter;
    private AccountPreference ap;
    private GoodsUnit baseUnit;
    private Button btnGiveUnit;
    private Button btnProGoods;
    private Button btnPromotionUnit;
    private Button btnSaleUnit;
    private CheckBox cbIsexhibition;
    private EditText etCancelRemark;
    private EditText etGiftRemark;
    private EditText etGiveNum;
    private EditText etGiveRemark;
    private EditText etPromotionNum;
    private EditText etSaleNum;
    private EditText etSalePrice;
    private TextView etSalePriceName;
    private EditText etSaleRemark;
    private FieldSale fieldSale;
    private List<GoodsUnit> goodsUnits;
    private FieldSaleItemSource itemFS;
    private ListView listView;
    private String[] names;
    private PromotionGoods promotionGoods;
    View.OnClickListener unitOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSaleUnit:
                    saleUnitSelect();
                    break;
                case R.id.btnGiveUnit:
                    giveUnitSelect();
                    break;
                case R.id.btnPromotionUnit:
                    if (TextUtils.isEmptyS(btnProGoods.getTag().toString())) {
                        PDH.showMessage("请先选择搭赠商品");
                        return;
                    }
                    giftUnitSelect();
                    break;
            }
        }


    };
    private TextView tvBarcode;
    private TextView tvPromotion;
    private TextView tvSpecification;
    private TextView tvStock;
    @SuppressLint("HandlerLeak") private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (Boolean.parseBoolean(msg.obj.toString())) {
                    finish();
                } else {
                    PDH.showFail("保存失败");
                }
            }
        }
    };

    private void giveUnitSelect() {
        AlertDialog.Builder v0 = new AlertDialog.Builder(this);
        v0.setTitle("赠送单位");
        v0.setItems(this.names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg4, int arg5) {
                arg4.dismiss();
                GoodsUnit v0 = goodsUnits.get(arg5);
                btnGiveUnit.setText(v0.getUnitname());
                btnGiveUnit.setTag(v0.getUnitid());
            }
        });
        v0.show();
    }

    private void giftUnitSelect() {
        AlertDialog.Builder v0 = new AlertDialog.Builder(this);
        v0.setTitle("赠送单位");
        v0.setItems(this.names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg4, int arg5) {
                arg4.dismiss();
                GoodsUnit v0 = goodsUnits.get(arg5);
                btnGiveUnit.setText(v0.getUnitname());
                btnGiveUnit.setTag(v0.getUnitid());
            }
        });
        v0.show();
    }

    private void saleUnitSelect() {
        AlertDialog.Builder v0 = new AlertDialog.Builder(this);
        v0.setTitle("销售单位");
        v0.setItems(this.names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg16, int arg17) {
                arg16.dismiss();
                GoodsUnit v7 = goodsUnits.get(arg17);
                if (!v7.getUnitid().equals(String.valueOf(btnSaleUnit.getTag()))) {
                    if (!Utils.isUseCurrentPrice && (promotionGoods == null || promotionGoods.getType() != 0)) {
                        etSalePrice.setText(String.valueOf(new GoodsPriceDAO().queryGoodsPrice(itemFS.getGoodsid(), v7.getUnitid(), fieldSale.getCustomerid(), fieldSale.isIsnewcustomer(), fieldSale.getPricesystemid())));
                        btnSaleUnit.setText(v7.getUnitname());
                        btnSaleUnit.setTag(v7.getUnitid());
                    }

                    double v13 = Utils.getDouble(etSalePrice.getText().toString());
                    if (v13 == 0) {
                        btnSaleUnit.setText(v7.getUnitname());
                        btnSaleUnit.setTag(v7.getUnitid());
                    }
                    etSalePrice.setText(Utils.getPrice(v7.getRatio() * v13 / new GoodsUnitDAO().getGoodsUnit(itemFS.getGoodsid(), btnSaleUnit.getTag().toString()).getRatio()));
                }


            }
        });
        v0.show();
    }

    @Override
    public int getLayoutID() {
        return R.layout.act_field_add_goods;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();

        if (Utils.getDouble(this.etSaleNum.getText().toString()) != 0 && Utils.getDouble(this.etSalePrice.getText().toString()) == 0) {
            PDH.showMessage("销售价格不能为0");
            return;
        }
        int v8 = this.adapter.getCount();
        for (int i = 0; i < v8; i++) {
            FieldSaleItemBatchEx v16 = this.adapter.getData().get(i);
            if (v16.getNum() > 0 && (v16.getIsusebatch())) {
                if (TextUtils.isEmptyS(v16.getBatch())) {
                    PDH.showMessage("退货批次不能为空");
                    return;
                } else {
                    for (int j = i + 1; j < v8; j++) {
                        FieldSaleItemBatchEx v17 = this.adapter.getData().get(j);
                        if (v17.getNum() > 0 && v16.getBatch() == v17.getBatch()) {
                            PDH.showMessage("退货批次不允许重复");
                            return;
                        }
                    }

                }
            }
        }
        makeForm();
        if (this.itemFS.getSalenum() == 0 && this.itemFS.getGivenum() == 0 && this.itemFS.getCancelbasenum() == 0) {
            PDH.showMessage("销售、退货、赠送数量必需至少有一项大于0");
            return;
        }
        boolean isOk;
        if (this.itemFS.getSerialid() != -1) {
            isOk = new FieldSaleItemDAO().updateFieldSaleItem(this.itemFS);
        } else isOk = new FieldSaleItemDAO().saveFieldSaleItem(this.itemFS) != -1;
        new FieldSaleItemBatchDAO().dealCancelItemBatchs(this.fieldSale.getId(), this.itemFS.getGoodsid(), this.adapter.getData());
        this.handler.sendMessage(this.handler.obtainMessage(0, isOk));
    }

    @Override
    protected void onRightClick1() {
        super.onRightClick1();
        FieldSaleItemBatchEx v15 = new FieldSaleItemBatchEx();
        if (this.itemFS.getIsusebatch()) {
            long v9 = new Date().getTime();
            v15.setProductiondate(Utils.formatDate(v9));
            if (Utils.intGenerateBatch == 0) {
                v15.setBatch("");
            } else {
                v15.setBatch(Utils.generateBatch(v9));
            }
        } else {
            v15.setProductiondate(null);
            v15.setBatch(null);
        }
        double v19 = new GoodsPriceDAO().queryGoodsPrice(this.itemFS.getGoodsid(), this.baseUnit.getUnitid(), this.fieldSale.getCustomerid(), this.fieldSale.isIsnewcustomer(), this.fieldSale.getPricesystemid());
        v15.setFieldsaleid(this.fieldSale.getId());
        v15.setGoodsid(this.itemFS.getGoodsid());
        v15.setIsusebatch(this.itemFS.getIsusebatch());
        v15.setIsout(false);
        v15.setNum(0);
        v15.setPrice(v19);
        v15.setUnitid(this.baseUnit.getUnitid());
        v15.setUnitname(this.baseUnit.getUnitname());
        this.adapter.addItem(v15);
        this.setListViewHeightBasedOnChildren();
    }

    private void makeForm() {
        double v13 = 0;
        Warehouse v5 = SystemState.getWarehouse();
        this.itemFS.setSaleunitid(String.valueOf(this.btnSaleUnit.getTag()));
        this.itemFS.setSaleunitname(String.valueOf(this.btnSaleUnit.getText()));
        this.itemFS.setSalenum(Utils.getDouble(this.etSaleNum.getText().toString()));
        this.itemFS.setSaleprice(Utils.getDouble(this.etSalePrice.getText().toString()));
        this.itemFS.setGiveunitid(String.valueOf(this.btnGiveUnit.getTag()));
        this.itemFS.setGiveunitname(String.valueOf(this.btnGiveUnit.getText()));
        this.itemFS.setGivenum(Utils.getDouble(this.etGiveNum.getText().toString()));
        if (Utils.getDouble(this.etGiveNum.getText().toString()) > v13) {
            this.itemFS.setIsexhibition(this.cbIsexhibition.isChecked());
        } else {
            this.itemFS.setIsexhibition(false);
        }
        if (v5 != null) {
            this.itemFS.setWarehouseid(v5.getId());
        }
        double v0 = 0;
        List<FieldSaleItemBatchEx> v2 = this.adapter.getData();
        for (int i = 0; i < v2.size(); i++) {
            v0 += new GoodsUnitDAO().getGoodsUnit(this.itemFS.getGoodsid(), v2.get(i).getUnitid()).getRatio() * v2.get(i).getNum();
        }
        this.itemFS.setCancelbasenum(v0);
        this.itemFS.setSaleremark(this.etSaleRemark.getText().toString());
        this.itemFS.setGiftremark(this.etGiftRemark.getText().toString());
        this.itemFS.setCancelremark(this.etCancelRemark.getText().toString());
        this.itemFS.setGiveremark(this.etGiveRemark.getText().toString());
        if (Utils.getDouble(this.etPromotionNum.getText().toString()) <= v13 || (TextUtils.isEmptyS(this.btnProGoods.getTag().toString()))) {
            if (this.promotionGoods != null && this.promotionGoods.getType() == 0) {
                this.itemFS.setIspromotion(true);
                this.itemFS.setPromotiontype(0);
                return;
            }
            this.itemFS.setIspromotion(false);
            this.itemFS.setPromotiontype(-1);
        } else {
            this.itemFS.setGiftgoodsid(this.btnProGoods.getTag().toString());
            this.itemFS.setGiftgoodsname(this.btnProGoods.getText().toString());
            this.itemFS.setGiftunitid(String.valueOf(this.btnPromotionUnit.getTag()));
            this.itemFS.setGiftunitname(String.valueOf(this.btnPromotionUnit.getText()));
            this.itemFS.setGiftnum(Utils.getDouble(this.etPromotionNum.getText().toString()));
            this.itemFS.setIspromotion(true);
            this.itemFS.setPromotiontype(1);
        }
    }

    @Override
    public void initView() {
        setTitleRight("保存", null);
        setTitleRight1("退货");
        fieldSale = new FieldSaleDAO().getFieldsale(getIntent().getLongExtra("fieldsaleid", -1L));
        int v5 = (int) getIntent().getLongExtra("itemid", -1L);
        GoodsThin goodsThin = (GoodsThin) getIntent().getSerializableExtra("goods");
        ap = new AccountPreference();
        listView = findViewById(R.id.listView);
        if (v5 == -1) {
            this.itemFS = new FieldSaleItemSource();
            this.itemFS.setFieldsaleid(this.fieldSale.getId());
            this.itemFS.setSerialid(-1);
            this.itemFS.setGoodsid(goodsThin.getId());
            this.itemFS.setGoodsname(goodsThin.getName());
            this.itemFS.setBarcode(goodsThin.getBarcode());
            this.itemFS.setSpecification(goodsThin.getSpecification());
            this.itemFS.setIsusebatch(goodsThin.getIsusebatch());
            adapter = new ItemCancelBatchAdapter(this, goodsThin.getId());
            this.listView.setAdapter(this.adapter);
        } else {
            this.itemFS = new FieldSaleItemDAO().getFieldSaleItem(v5);
            List<FieldSaleItemBatchEx> v4 = new FieldSaleItemBatchDAO().queryItemBatchs(this.fieldSale.getId(), this.itemFS.getGoodsid(), false);
            this.adapter = new ItemCancelBatchAdapter(this, this.itemFS.getGoodsid());
            this.listView.setAdapter(this.adapter);
            this.adapter.setData(v4);
            this.setListViewHeightBasedOnChildren();
        }

        this.adapter.setOnLongClicklistener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                final int position = (int) v.getTag(R.id.llDate);
                new MessageDialog(FieldAddGoodAct.this).showDialog("提示", "确定删除该项？", null, null, new MessageDialog.CallBack() {
                    @Override
                    public void btnOk(View view) {
                        adapter.getData().remove(position);
                        adapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren();
                    }

                    @Override
                    public void btnCancel(View view) {

                    }
                });
                return false;
            }
        });
        if (!TextUtils.isEmptyS(this.fieldSale.getPromotionid())) {
            promotionGoods = new PromotionGoodsDAO().getPromotiongoods(this.fieldSale.getPromotionid(), this.itemFS.getGoodsid());
        }
        baseUnit = new GoodsUnitDAO().getBasicUnit(this.itemFS.getGoodsid());
        goodsUnits = new GoodsUnitDAO().queryGoodsUnits(this.itemFS.getGoodsid());
        if (this.goodsUnits.size() == 0) {
            PDH.showMessage("本地无该商品的单位信息");
            this.finish();
        }
        names = new String[this.goodsUnits.size()];
        for (int i = 0; i < goodsUnits.size(); i++) {
            names[i] = this.goodsUnits.get(i).getUnitname();
        }
    }

    private void initValue() {
        double v12 = 0;
        this.tvStock.setText(new GoodsDAO().queryGoodsBigStockNumber(this.itemFS.getGoodsid()));
        if (this.promotionGoods != null) {
            this.tvPromotion.setText(this.promotionGoods.getSummary());
            this.tvPromotion.setVisibility(View.VISIBLE);
        } else {
            this.tvPromotion.setVisibility(View.GONE);
        }
        GoodsUnitDAO v8 = new GoodsUnitDAO();
        GoodsUnit v7 = Utils.DEFAULT_UNIT == 0 ? v8.queryBaseUnit(this.itemFS.getGoodsid()) : v8.queryBigUnit(this.itemFS.getGoodsid());
        double v9 = new GoodsPriceDAO().queryGoodsPrice(this.itemFS.getGoodsid(), v7.getUnitid(), this.fieldSale.getCustomerid(), this.fieldSale.isIsnewcustomer(), this.fieldSale.getPricesystemid());
        if (this.itemFS.getSerialid() == -1) {
            this.btnGiveUnit.setText(v7.getUnitname());
            this.btnGiveUnit.setTag(v7.getUnitid());
            if (this.promotionGoods != null) {
                GoodsUnit v11 = v8.getGoodsUnit(this.promotionGoods.getGoodsid(), this.promotionGoods.getUnitid());
                this.btnSaleUnit.setText(v11.getUnitname());
                this.btnSaleUnit.setTag(v11.getUnitid());
                if (this.promotionGoods.getType() != 0) {
                    GoodsUnit v6 = v8.getGoodsUnit(this.promotionGoods.getGiftgoodsid(), this.promotionGoods.getGiftunitid());
                    this.btnPromotionUnit.setText(v6.getUnitname());
                    this.btnPromotionUnit.setTag(v6.getUnitid());
                }
                if (this.promotionGoods.getType() == 0) {
                    this.etSalePrice.setText(String.valueOf(this.promotionGoods.getPrice()));
                } else {
                    this.etSalePrice.setText(String.valueOf(v9));
                }
            } else {
                this.btnSaleUnit.setText(v7.getUnitname());
                this.btnSaleUnit.setTag(v7.getUnitid());
                this.etSalePrice.setText(String.valueOf(v9));
            }
        } else {
            this.btnSaleUnit.setText(this.itemFS.getSaleunitname());
            this.btnGiveUnit.setText(this.itemFS.getGiveunitname());
            this.btnPromotionUnit.setText(this.itemFS.getGiftunitname());
            this.cbIsexhibition.setChecked(this.itemFS.isIsexhibition());
            this.btnSaleUnit.setTag(this.itemFS.getSaleunitid());
            this.btnGiveUnit.setTag(this.itemFS.getGiveunitid());
            this.btnPromotionUnit.setTag(this.itemFS.getGiftunitid());
            this.btnProGoods.setTag(this.itemFS.getGiftgoodsid());
            this.btnProGoods.setText(this.itemFS.getGiftgoodsname());
            etPromotionNum.setText(this.itemFS.getGiftnum() == v12 ? "" : String.valueOf(this.itemFS.getGiftnum()));
            etSaleNum.setText(this.itemFS.getSalenum() == v12 ? "" : String.valueOf(this.itemFS.getSalenum()));
            this.etSalePrice.setText(String.valueOf(this.itemFS.getSaleprice()));
            etGiveNum.setText(this.itemFS.getGivenum() == v12 ? "" : String.valueOf(this.itemFS.getGivenum()));
            this.etSaleRemark.setText(this.itemFS.getSaleremark());
            this.etGiftRemark.setText(this.itemFS.getGiftremark());
            this.etCancelRemark.setText(this.itemFS.getCancelremark());
            this.etGiveRemark.setText(this.itemFS.getGiveremark());
        }


    }

    private void setListViewHeightBasedOnChildren() {
        int v3 = 0;
        int v0;
        for (v0 = 0; v0 < this.adapter.getCount(); ++v0) {
            View v1 = this.adapter.getView(v0, null, this.listView);
            v1.measure(0, 0);
            v3 += v1.getMeasuredHeight();
        }
        ViewGroup.LayoutParams v2 = this.listView.getLayoutParams();
        v2.height = this.listView.getDividerHeight() * (this.listView.getAdapter().getCount() - 1) + v3;
        this.listView.setLayoutParams(v2);
    }

    @Override
    public void initData() {
        setTitle(itemFS.getGoodsname());
        this.tvPromotion = this.findViewById(R.id.tvPromotion);
        this.etSalePriceName = this.findViewById(R.id.etSalePriceName);
        this.tvBarcode = this.findViewById(R.id.tvBarcode);
        this.tvBarcode.setText(this.itemFS.getBarcode());
        this.tvSpecification = this.findViewById(R.id.tvSpecification);
        this.tvSpecification.setText(this.itemFS.getSpecification());
        this.tvStock = this.findViewById(R.id.tvStock);
        this.etSaleRemark = this.findViewById(R.id.etSaleRemark);
        this.etCancelRemark = this.findViewById(R.id.etCancelRemark);
        this.etGiftRemark = this.findViewById(R.id.etGiftRemark);
        this.etGiveRemark = this.findViewById(R.id.etGiveRemark);
        this.btnSaleUnit = this.findViewById(R.id.btnSaleUnit);
        this.etSaleNum = this.findViewById(R.id.etSaleNum);
//        this.etSaleNum.setDecNum(Utils.NUMBER_DEC);
        this.etSalePrice = this.findViewById(R.id.etSalePrice);
//        this.etSalePrice.setDecNum(Utils.PRICE_DEC_NUM);
        this.cbIsexhibition = this.findViewById(R.id.cbIsexhibition);
        if (this.promotionGoods != null && this.promotionGoods.getType() == 0) {
            this.etSalePrice.setEnabled(false);
            this.etSalePriceName.setText("特价");
            this.etSalePriceName.setTextColor(-65536);
            this.findViewById(R.id.linearPro).setVisibility(View.GONE);
        }
        this.btnProGoods = this.findViewById(R.id.btnProGoods);
        this.btnProGoods.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg4) {
                Intent v0 = new Intent();
                v0.setClass(FieldAddGoodAct.this, GoodsSearchAct.class);
                v0.putExtra("isonlyhasstock", true);
                startActivityForResult(v0, 8);
            }
        });
        this.btnPromotionUnit = this.findViewById(R.id.btnPromotionUnit);
        this.etPromotionNum = this.findViewById(R.id.etPromotionNum);
//        this.etPromotionNum.setDecNum(Utils.NUMBER_DEC);
        if (this.etSalePrice.isEnabled()) {
            if (!"1".equals(this.ap.getValue("AllowChangeSalePrice", "1"))) {
                this.etSalePrice.setEnabled(false);
            } else {
                this.etSalePrice.setEnabled(true);
            }
        }
        this.btnGiveUnit = this.findViewById(R.id.btnGiveUnit);
        this.etGiveNum = this.findViewById(R.id.etGiveNum);
//        this.etGiveNum.setDecNum(Utils.NUMBER_DEC);
        this.btnSaleUnit.setOnClickListener(this.unitOnClickListener);
        this.btnGiveUnit.setOnClickListener(this.unitOnClickListener);
        this.btnPromotionUnit.setOnClickListener(this.unitOnClickListener);
        initValue();
    }
}
