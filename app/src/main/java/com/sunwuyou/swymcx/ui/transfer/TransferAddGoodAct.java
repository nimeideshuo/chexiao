package com.sunwuyou.swymcx.ui.transfer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.app.BaseHeadActivity;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.dao.TransferDocDAO;
import com.sunwuyou.swymcx.dao.TransferItemDAO;
import com.sunwuyou.swymcx.dao.WarehouseDAO;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.model.TransferItemSource;
import com.sunwuyou.swymcx.request.ReqStrGetGoodsPrice;
import com.sunwuyou.swymcx.service.ServiceGoods;
import com.sunwuyou.swymcx.ui.GoodsWarehouseSearchAct;
import com.sunwuyou.swymcx.utils.ClickUtils;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.EditButtonView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/8/5.
 * content
 */

public class TransferAddGoodAct extends BaseHeadActivity implements View.OnClickListener {
    private Button btnBatch;
    private Button btnUnit;
    private Button btnWarehouse;
    private EditButtonView etNum;
    private EditText etRemark;
    private TransferDoc transferDoc;
    private TransferItemSource transferitem;
    private TextView tvBarcode;
    private TextView tvSpecification;
    @SuppressLint("HandlerLeak") private Handler handlerGetGoodsBatch = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                ReqStrGetGoodsPrice v0 = JSONUtil.str2list(msg.obj.toString(), ReqStrGetGoodsPrice.class).get(0);
                transferitem.setWarehouseid(v0.getWarehouseid());
                if (!TextUtils.isEmptyS(transferitem.getWarehouseid())) {
                    transferitem.setWarehousename(new WarehouseDAO().getWarehouse(transferitem.getWarehouseid()).getName());
                }
                transferitem.setBatch(v0.getBatch());
                transferitem.setProductiondate(v0.getProductiondate());
                btnWarehouse.setText(transferitem.getWarehousename());
                btnWarehouse.setTag(transferitem.getWarehouseid());
                btnBatch.setText(transferitem.getBatch());
                btnBatch.setTag(transferitem.getProductiondate());
                if (!TextUtils.isEmptyS(transferitem.getBatch())) {
                    return;
                }
                PDH.showMessage("指定仓库无可用批次");
            } else {
                PDH.showFail(msg.obj.toString());
            }
        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.act_transfer_add_goods;
    }

    @Override
    public void initView() {
        setTitleRight("保存", null);
        this.tvBarcode = this.findViewById(R.id.tvBarcode);
        this.tvSpecification = this.findViewById(R.id.tvSpecification);
        this.btnUnit = this.findViewById(R.id.btnUnit);
        this.btnWarehouse = this.findViewById(R.id.btnWarehouse);
        this.btnBatch = this.findViewById(R.id.btnBatch);
        this.etNum = this.findViewById(R.id.etNum);
        this.etRemark = this.findViewById(R.id.etRemark);
        this.btnWarehouse.setOnClickListener(this);
        this.btnBatch.setOnClickListener(this);
        this.btnUnit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg2) {
                unitSelect();
            }
        });
        this.transferDoc = new TransferDocDAO().getTransferDoc(this.getIntent().getLongExtra("transferdocid", -1));
        long itemid = ((long) (((int) this.getIntent().getLongExtra("itemid", -1))));
        GoodsThin v0 = (GoodsThin) this.getIntent().getSerializableExtra("goods");
        if (itemid == -1) {
            transferitem = new TransferItemSource();
            this.transferitem.setSerialid(-1);
            this.transferitem.setTransferdocid(this.transferDoc.getId());
            this.transferitem.setGoodsid(v0.getId());
            this.transferitem.setGoodsname(v0.getName());
            this.transferitem.setBarcode(v0.getBarcode());
            this.transferitem.setSpecification(v0.getSpecification());
            this.transferitem.setIsusebatch(v0.getIsusebatch());
            this.transferitem.setNum(Utils.getDouble(this.etNum.getText().toString()));
            this.transferitem.setRemark(null);
            GoodsUnit goodsUnit = Utils.TRANSFER_DEFAULT_UNIT == 0 ? new GoodsUnitDAO().queryBaseUnit(this.transferitem.getGoodsid()) : new GoodsUnitDAO().queryBigUnit(this.transferitem.getGoodsid());
            this.transferitem.setUnitid(goodsUnit.getUnitid());
            this.transferitem.setUnitname(goodsUnit.getUnitname());
            if (!v0.getIsusebatch()) {
                this.tvBarcode.setText(this.transferitem.getBarcode());
                this.tvSpecification.setText(this.transferitem.getSpecification());
                this.btnUnit.setText(this.transferitem.getUnitname());
                this.btnUnit.setTag(this.transferitem.getUnitid());
                this.btnWarehouse.setText(this.transferitem.getWarehousename());
                this.btnWarehouse.setTag(this.transferitem.getWarehouseid());
                this.btnBatch.setText(this.transferitem.getBatch());
                this.btnBatch.setTag(this.transferitem.getProductiondate());
                this.etNum.setText(String.valueOf(this.transferitem.getNum()));
                this.etRemark.setText(this.transferitem.getRemark());
                if (!this.transferitem.getIsusebatch()) {
                    this.findViewById(R.id.llBatch).setVisibility(View.GONE);
                }

                PDH.show(this, "正在获取仓库...", new PDH.ProgressCallBack() {

                    @Override
                    public void action() {
                        ReqStrGetGoodsPrice goodsPrice = new ReqStrGetGoodsPrice();
                        goodsPrice.setCustomerid(null);
                        goodsPrice.setWarehouseid(null);
                        goodsPrice.setGoodsid(TransferAddGoodAct.this.transferitem.getGoodsid());
                        goodsPrice.setUnitid(TransferAddGoodAct.this.transferitem.getUnitid());
                        goodsPrice.setType(0);
                        goodsPrice.setPrice(0);
                        goodsPrice.setIsdiscount(false);
                        goodsPrice.setBatch(null);
                        goodsPrice.setProductiondate(null);
                        ArrayList<ReqStrGetGoodsPrice> priceArrayList = new ArrayList<>();
                        priceArrayList.add(goodsPrice);
                        String v2 = new ServiceGoods().gds_GetMultiGoodsPrice(priceArrayList, true, true);
                        if (RequestHelper.isSuccess(v2)) {
                            handlerGetGoodsBatch.sendMessage(handlerGetGoodsBatch.obtainMessage(0, v2));
                        } else {
                            handlerGetGoodsBatch.sendMessage(handlerGetGoodsBatch.obtainMessage(1, v2));
                        }
                    }
                });
            }
        } else {
            this.transferitem = new TransferItemDAO().getTransferItem(itemid);
            this.tvBarcode.setText(this.transferitem.getBarcode());
            this.tvSpecification.setText(this.transferitem.getSpecification());
            this.btnUnit.setText(this.transferitem.getUnitname());
            this.btnUnit.setTag(this.transferitem.getUnitid());
            this.btnWarehouse.setText(this.transferitem.getWarehousename());
            this.btnWarehouse.setTag(this.transferitem.getWarehouseid());
            this.btnBatch.setText(this.transferitem.getBatch());
            this.btnBatch.setTag(this.transferitem.getProductiondate());
            this.etNum.setText(String.valueOf(this.transferitem.getNum()));
            this.etRemark.setText(this.transferitem.getRemark());
            if (!this.transferitem.getIsusebatch()) {
                this.findViewById(R.id.llBatch).setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        long v8 = -1;
        if (!ClickUtils.isFastDoubleClick()) {
            if (TextUtils.isEmpty(btnWarehouse.getText().toString())) {
                PDH.showMessage("仓库不能为空");
                return;
            }
            if (this.transferitem.getIsusebatch() && TextUtils.isEmpty(btnBatch.getText().toString())) {
                PDH.showMessage("批次不能为空");
                return;
            }
            if (Utils.getDouble(this.etNum.getText().toString()) == 0) {
                PDH.showMessage("数量不能为0");
                return;
            }
            this.makeForm();
            boolean v0;
            if (this.transferitem.getSerialid() != v8) {
                v0 = new TransferItemDAO().updateTransferItem(this.transferitem);
            } else if (new TransferItemDAO().saveTransferItem(this.transferitem) != v8) {
                v0 = true;
            } else {
                v0 = false;
            }
            if (v0) {
                this.finish();
            } else {
                PDH.showFail("保存失败");
            }
        }
    }

    private void makeForm() {
        this.transferitem.setUnitid(String.valueOf(this.btnUnit.getTag()));
        this.transferitem.setUnitname(String.valueOf(this.btnUnit.getText()));
        this.transferitem.setNum(Utils.getDouble(this.etNum.getText().toString()));
        this.transferitem.setBatch(this.btnBatch.getText().toString());
        this.transferitem.setProductiondate(this.btnBatch.getTag() == null ? null : this.btnBatch.getTag().toString());
        if (TextUtils.isEmptyS(this.transferitem.getProductiondate())) {
            this.transferitem.setProductiondate(null);
        }
        this.transferitem.setWarehouseid(this.btnWarehouse.getTag() != null ? this.btnWarehouse.getTag().toString() : null);
        this.transferitem.setWarehousename(this.btnWarehouse.getText().toString());
        this.transferitem.setRemark(String.valueOf(this.etRemark.getText()));
    }


    private void unitSelect() {
        final List<GoodsUnit> goodsUnits = new GoodsUnitDAO().queryGoodsUnits(this.transferitem.getGoodsid());
        String[] v3 = new String[goodsUnits.size()];

        for (int i = 0; i < goodsUnits.size(); i++) {
            v3[i] = goodsUnits.get(i).getUnitname();

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("赠送单位");
        builder.setItems(v3, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg4, int arg5) {
                arg4.dismiss();
                GoodsUnit v0 = goodsUnits.get(arg5);
                btnUnit.setText(v0.getUnitname());
                btnUnit.setTag(v0.getUnitid());
            }
        });
        builder.show();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWarehouse:
                this.startActivityForResult(new Intent().setClass(this, GoodsWarehouseSearchAct.class).putExtra("inwarehouseid", this.transferDoc.getInwarehouseid()).putExtra("goodsid", this.transferitem.getGoodsid()).putExtra("isgetbatch", this.transferitem.getIsusebatch()), 12);
                break;
            case R.id.btnBatch:
                if (!TextUtils.isEmptyS(btnWarehouse.getTag().toString())) {
                    this.startActivityForResult(new Intent(this, GoodsBatchSearchAct.class).putExtra("warehouseid", this.btnWarehouse.getTag().toString()).putExtra("goodsid", this.transferitem.getGoodsid()), 1);
                    return;
                }
                PDH.showMessage("请先选择仓库");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    GoodsUnit v0 = (GoodsUnit) data.getSerializableExtra("unit");
                    this.btnUnit.setText(v0.getUnitname());
                    this.btnUnit.setTag(v0.getUnitid());
                    break;
                case 1:
                    this.btnBatch.setText(data.getStringExtra("batch"));
                    this.btnBatch.setTag(data.getStringExtra("productiondate"));
                    break;
                case 12:
                    this.btnWarehouse.setText(data.getStringExtra("warehousename"));
                    this.btnWarehouse.setTag(data.getStringExtra("warehouseid"));
                    if (this.transferitem.getIsusebatch()) {
                        this.btnBatch.setText(data.getStringExtra("batch"));
                        this.btnBatch.setTag(data.getStringExtra("productiondate"));
                    }
                    break;
            }

        }

    }

    public void setActionBarText() {
        setTitle(this.transferitem.getGoodsname());
    }
}
