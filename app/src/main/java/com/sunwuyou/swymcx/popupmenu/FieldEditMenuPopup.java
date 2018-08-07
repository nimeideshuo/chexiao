package com.sunwuyou.swymcx.popupmenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocation;
import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.CustomerDAO;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemBatchDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.FieldSalePayTypeDAO;
import com.sunwuyou.swymcx.dao.GoodsBatchDAO;
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.gps.GPS;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItemBatchEx;
import com.sunwuyou.swymcx.model.FieldSaleItemTotal;
import com.sunwuyou.swymcx.model.GoodsBatchForUpdate;
import com.sunwuyou.swymcx.ui.LoadingDialog;
import com.sunwuyou.swymcx.ui.WritePadAct;
import com.sunwuyou.swymcx.ui.field.CustomerGoodsActivity;
import com.sunwuyou.swymcx.ui.field.DocRemarkDialog;
import com.sunwuyou.swymcx.ui.field.FieldDocOpenAct;
import com.sunwuyou.swymcx.ui.field.FieldEditActivity;
import com.sunwuyou.swymcx.ui.field.FieldItemTotalAct;
import com.sunwuyou.swymcx.ui.field.FieldPayTypeAct;
import com.sunwuyou.swymcx.ui.field.PicturesActivity;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.UpdateUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.MessageDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class FieldEditMenuPopup extends PopupWindow implements View.OnClickListener, View.OnTouchListener {
    private FieldEditActivity activity;
    private Button btnBatchDetail;
    private LinearLayout btnDelete;
    private LinearLayout btnDocInfo;
    private LinearLayout btnLocation;
    private Button btnOut;
    private LinearLayout btnPicture;
    private LinearLayout btnPrint;
    private LinearLayout btnSale;
    private LinearLayout btnSettle;
    private LinearLayout btnWritePad;
    private FieldSale fieldSale;
    private FieldSaleDAO fieldsaleDAO;
    private LoadingDialog loadingDialog;
    //    private DocRemarkDialog remarkDialog;
    private View root;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1: {
                    PDH.showSuccess("撤销库存成功");
                    new FieldSaleDAO().updateDocValue(FieldEditMenuPopup.this.fieldSale.getId(), "status", "0");
                    activity.loadDoc();
                    break;
                }
                case 2: {
                    PDH.showFail("撤销库存失败");
                    break;
                }
                case 3: {
                    PDH.showFail("撤销库存失败，【" + msg.obj + "】批次库存不足");
                    break;
                }
                case 4: {
                    PDH.showFail("撤销库存失败，【" + msg.obj + "】剩余库存不足");
                    break;
                }
            }
        }
    };
    private DocRemarkDialog remarkDialog;
    @SuppressLint("HandlerLeak")
    private Handler handlerGetLocation = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AMapLocation obj = (AMapLocation) msg.obj;
            loadingDialog.dismiss();
            if (obj == null || TextUtils.isEmptyS(obj.getAddress())) {
                PDH.showFail("定位失败");
            } else {
                PDH.showSuccess(obj.getAddress());
                new FieldSaleDAO().updateLocation(fieldSale.getId(), obj);
                btnLocation.setOnClickListener(null);
            }

        }
    };

    public FieldEditMenuPopup(FieldEditActivity activity) {
        super(activity);
        this.activity = activity;
        this.root = LayoutInflater.from(activity).inflate(R.layout.popup_menu_fieldedit, null);
        setContentView(this.root);
        init();
        this.fieldsaleDAO = new FieldSaleDAO();
        setAnimationStyle(R.style.buttom_in_out);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        int j = localDisplayMetrics.heightPixels;
        setWidth(i);
        setHeight(j / 6);
        setBackgroundDrawable(null);
    }

    private void init() {
        this.loadingDialog = new LoadingDialog(this.activity);
        this.btnOut = this.root.findViewById(R.id.btnOut);
        this.btnSettle = this.root.findViewById(R.id.btnSettle);
        this.btnPrint = this.root.findViewById(R.id.btnPrint);
        this.btnSale = this.root.findViewById(R.id.btnSale);
        this.btnWritePad = this.root.findViewById(R.id.btnWritePad);
        this.btnDelete = this.root.findViewById(R.id.btnDelete);
        this.btnPicture = this.root.findViewById(R.id.btnPicture);
        this.btnLocation = this.root.findViewById(R.id.btnLocation);
        this.btnDocInfo = this.root.findViewById(R.id.btnDocInfo);
        this.btnBatchDetail = this.root.findViewById(R.id.btnBatchDetail);
        this.btnOut.setOnClickListener(this);
        this.btnSettle.setOnTouchListener(this);
        this.btnPrint.setOnTouchListener(this);
        this.btnSale.setOnClickListener(this);
        this.btnWritePad.setOnTouchListener(this);
        this.btnDelete.setOnTouchListener(this);
        this.btnPicture.setOnTouchListener(this);
        this.btnLocation.setOnTouchListener(this);
        this.btnBatchDetail.setOnTouchListener(this);
        this.btnDocInfo.setOnTouchListener(this);
    }

    private void delete() {
        new MessageDialog(activity).showDialog("提示", "是否删除单据？", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                fieldsaleDAO.deleteFieldsale(fieldSale.getId());
                PDH.showSuccess("删除完成");
                if (!TextUtils.isEmptyS(fieldSale.getCustomerid())) {
                    new CustomerDAO().updateCustomerValue(fieldSale.getCustomerid(), "isfinish", "0");
                }
                activity.finish();
            }

            @Override
            public void btnCancel(View view) {

            }
        }).show();
    }

    @Override
    public void onClick(View v) {
        dismiss();
        WindowManager.LayoutParams attributes = this.activity.getWindow().getAttributes();
        attributes.alpha = 1f;
        this.activity.getWindow().setAttributes(attributes);
        switch (v.getId()) {
            case R.id.btnOut:
                //库存处理
                if (this.fieldSale.getStatus() == 0) {
                    if (new FieldSaleItemDAO().getFieldSaleItems(this.fieldSale.getId()).size() == 0) {
                        new MessageDialog(activity).showDialog("提示", "当前单据为空，是否置为已处理状态？", null, null, new MessageDialog.CallBack() {
                            @Override
                            public void btnOk(View view) {
                                int v0 = 0;
                                if (new FieldSaleItemDAO().getCount(fieldSale.getId()) == 0) {
                                    v0 = 1;
                                }
                                if (v0 == 0 || fieldSale.getPreference() == 0) {
                                    if (v0 != 0 && (new FieldSalePayTypeDAO().isHasPay(fieldSale.getId()))) {
                                        PDH.showMessage("空单据不允许收款，请清除收款记录");
                                        return;
                                    }

                                    new FieldSaleDAO().updateDocValue(fieldSale.getId(), "status", "1");
                                    PDH.showSuccess("处理成功");
                                    if (v0 != 0) {
                                        activity.finish();
                                        return;
                                    }
                                    activity.loadDoc();
                                } else {
                                    PDH.showMessage("空单据不允许优惠，请清除优惠");
                                }


                            }

                            @Override
                            public void btnCancel(View view) {

                            }
                        }).show();
                        return;
                    }
                    this.activity.startActivity(new Intent().setClass(this.activity, FieldItemTotalAct.class).putExtra("fieldsaleid", this.fieldSale.getId()));
                    return;
                }

                if (this.fieldSale.getStatus() == 1) {
                    if (new FieldSaleItemDAO().getFieldSaleItems(this.fieldSale.getId()).size() == 0) {
                        new FieldSaleDAO().updateDocValue(this.fieldSale.getId(), "status", "0");
                        this.activity.loadDoc();
                        PDH.showSuccess("操作成功");
                        return;
                    }

                    this.reverseStock();
                    return;
                }
                PDH.showMessage("单据已上传");
                break;
            case R.id.btnSale:
                //客史
                if (!TextUtils.isEmptyS(this.fieldSale.getCustomerid()) && !this.fieldSale.isIsnewcustomer()) {
                    this.activity.startActivity(new Intent().setClass(this.activity, CustomerGoodsActivity.class).putExtra("fieldsaleid", this.fieldSale.getId()));
                    return;
                }
                PDH.showMessage("临时客户无客史记录");
                break;
        }
    }

    private void reverseStock() {
        new MessageDialog(activity).showDialog("提示", "确定撤销库存？", null, null, new MessageDialog.CallBack() {
            @Override
            public void btnOk(View view) {
                FieldSaleItemBatchDAO v5 = new FieldSaleItemBatchDAO();
                List<FieldSaleItemBatchEx> v17 = v5.queryFieldSaleBatchs(fieldSale.getId(), false);

                for (int i = 0; i < v17.size(); i++) {
                    FieldSaleItemBatchEx v14 = v17.get(i);
                    if ((v14.getIsusebatch()) && v14.getNum() * v14.getRatio() > v14.getStocknumber()) {
                        handler.sendMessage(handler.obtainMessage(3, new GoodsDAO().getGoodsThin(v14.getGoodsid()).getName()));
                        return;
                    }
                }
                GoodsDAO v8 = new GoodsDAO();
                List<FieldSaleItemTotal> v19 = new FieldSaleItemDAO().queryFieldItemTotal(fieldSale.getId(), false);
                ArrayList<FieldSaleItemTotal> v20 = new ArrayList<>();
                for (int i = 0; i < v19.size(); i++) {
                    FieldSaleItemTotal v15 = v19.get(i);
                    if (!v15.getIsusebatch()) {
                        if (v15.getInbasicnum() > v8.getGoodsThin(v15.getGoodsid()).getStocknumber()) {
                            handler.sendMessage(handler.obtainMessage(4, v15.getGoodsname()));
                            return;
                        } else {
                            v20.add(v15);
                        }
                    }
                }
                ArrayList<GoodsBatchForUpdate> v18 = new ArrayList<>();
                List<FieldSaleItemBatchEx> v17s = v5.queryFieldSaleBatchs(fieldSale.getId(), true);
                for (int i = 0; i < v17s.size(); i++) {
                    FieldSaleItemBatchEx batchEx = v17s.get(i);
                    if (batchEx.getIsusebatch()) {
                        GoodsBatchForUpdate v7 = new GoodsBatchForUpdate();
                        v7.setGoodsid(batchEx.getGoodsid());
                        v7.setBatch(batchEx.getBatch());
                        v7.setProductiondate(batchEx.getProductiondate());
                        v7.setStocknumber(batchEx.getStocknumber() + batchEx.getNum() * batchEx.getRatio());
                        v18.add(v7);
                    }
                }

                List<FieldSaleItemBatchEx> v17g = v5.queryFieldSaleBatchs(fieldSale.getId(), false);

                for (int i = 0; i < v17g.size(); i++) {
                    FieldSaleItemBatchEx v14 = v17g.get(i);
                    if (v14.getIsusebatch()) {
                        GoodsBatchForUpdate v7_1 = null;
                        for (int j = 0; j < v18.size(); j++) {
                            GoodsBatchForUpdate v30 = v18.get(j);
                            if ((v30.getGoodsid().equals(v14.getGoodsid())) && (v30.getBatch().equals(v14.getBatch()))) {
                                v7_1 = v30;
                                break;
                            }
                        }
                        if (v7_1 != null) {
                            v7_1.setStocknumber(v7_1.getStocknumber() - v14.getNum() * v14.getRatio());
                        }
                    }


                }
                ArrayList<HashMap<String, String>> v21 = new ArrayList<>();
                GoodsUnitDAO v10 = new GoodsUnitDAO();
                for (int i = 0; i < v18.size(); i++) {
                    GoodsBatchForUpdate forUpdate = v18.get(i);
                    String v25 = String.format("update kf_goodsbatch set stocknumber=%s,bigstocknumber=\'%s\' where goodsid=\'%s\' and batch=\'%s\'", forUpdate.getStocknumber(), v10.getBigNum(forUpdate.getGoodsid(), null, forUpdate.getStocknumber()), forUpdate.getGoodsid(), forUpdate.getBatch());
                    HashMap<String, String> v24 = new HashMap<>();
                    v24.put("sql", v25);
                    v21.add(v24);
                }

                for (int i = 0; i < v20.size(); i++) {
                    FieldSaleItemTotal v15 = v20.get(i);
                    double v28 = v8.getGoodsThin(v15.getGoodsid()).getStocknumber() + (v15.getOutbasicnum() - v15.getInbasicnum());
                    String v25 = String.format(" update sz_goods set stocknumber = %s, bigstocknumber = \'%s\' where id = \'%s\' ", v28, v10.getBigNum(v15.getGoodsid(), null, v28), v15.getGoodsid());
                    HashMap<String, String> v24 = new HashMap<>();
                    v24.put("sql", v25);
                    v21.add(v24);
                }
                if (v21.size() > 0) {
                    List<String> v9 = v5.getUseBatchGoodsIds(fieldSale.getId());
                    String v25 = String.format(" delete from kf_fieldsaleitembatch where fieldsaleid = %s and isout = \'1\' ", fieldSale.getId());
                    HashMap<String, String> v24 = new HashMap<>();
                    v24.put("sql", v25);
                    v21.add(v24);

                    String v25s = String.format(" update kf_fieldsale set status = %s where id = %s ", "0", fieldSale.getId());
                    HashMap<String, String> v24s = new HashMap<>();
                    v24s.put("sql", v25s);
                    v21.add(v24s);
                    if (!new UpdateUtils().saveToLocalDB(v21)) {
                        handler.sendEmptyMessage(2);
                        return;
                    }
                    GoodsBatchDAO v6 = new GoodsBatchDAO();
                    for (int i = 0; i < v9.size(); i++) {
                        String v11 = v9.get(i);
                        double v28 = v6.queryGoodStock(v11);
                        String v30 = String.format(" update sz_goods set stocknumber = %s, bigstocknumber = \'%s\' where id = \'%s\' ", v28, v10.getBigNum(v11, null, v28), v11);
                        HashMap<String, String> v30s = new HashMap<>();
                        v30s.put("sql", v30);
                        v21.add(v30s);
                    }
                    new UpdateUtils().saveToLocalDB(v21);
                    handler.sendEmptyMessage(1);
                }

            }

            @Override
            public void btnCancel(View view) {

            }
        }).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.dismiss();
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.alpha = 1f;
        activity.getWindow().setAttributes(attributes);
        switch (v.getId()) {
            case R.id.btnDelete:
                //删除
                if (this.fieldSale.getStatus() == 1) {
                    PDH.showMessage("已处理的单据不允许删除");
                    return true;
                }
                this.delete();
                break;
            case R.id.btnPrint:
                //打印
                if (this.fieldSale.getStatus() == 0) {
                    PDH.showMessage("单据未处理，不允许打印");
                    return true;
                }
                this.activity.print();
                break;
            case R.id.btnSettle:
                //收款
                this.activity.startActivity(new Intent(activity, FieldPayTypeAct.class).putExtra("fieldsaleid", this.fieldSale.getId()));
                break;
            case R.id.btnDocInfo:
                //签名
                if (remarkDialog == null) {
                    remarkDialog = new DocRemarkDialog(this.activity);
                }
                this.remarkDialog.show();
                break;
            case R.id.btnWritePad:
                //签名
                this.activity.startActivity(new Intent().setClass(this.activity, WritePadAct.class).putExtra("fieldsaleid", this.fieldSale.getId()));
                break;
            case R.id.btnBatchDetail:
                // 库存处理  批次明细
                this.activity.startActivity(new Intent().setClass(this.activity, FieldItemTotalAct.class).putExtra("fieldsaleid", this.fieldSale.getId()));
                break;
            case R.id.btnPicture:
                //照片
                this.activity.startActivity(new Intent(this.activity, PicturesActivity.class).putExtra("fieldsaleid", this.fieldSale.getId()));
                break;
            case R.id.btnLocation:
                this.loadingDialog.show("正在获取位置信息");
                new GPS(activity, new GPS.onLocationCallBack() {
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {
                        handlerGetLocation.sendMessage(handlerGetLocation.obtainMessage(0, aMapLocation));
                    }
                });
                break;
        }
        return false;
    }

    public void show(View paramView, long id) {
        this.fieldSale = new FieldSaleDAO().getFieldsale(id);
        switch (this.fieldSale.getStatus()) {
            case 0:
                this.btnOut.setText("库存处理");
                this.btnBatchDetail.setVisibility(View.GONE);
                this.btnPrint.setVisibility(View.GONE);
                if (this.fieldSale.getLatitude() == 0) {
                    this.btnLocation.setVisibility(View.VISIBLE);
                } else {
                    this.btnLocation.setVisibility(View.GONE);
                }

                if (TextUtils.isEmptyS(this.fieldSale.getCustomerid())) {
                    this.btnSale.setVisibility(View.GONE);
                }
                break;
            case 1:
                this.btnOut.setText("撤销库存");
                this.btnDelete.setVisibility(View.GONE);
                if (this.fieldSale.getPrintnum() > 0 && !Utils.IS_ALLOWEDMODIFY_PRINTED) {
                    this.btnOut.setVisibility(View.GONE);
                }

                if (this.fieldSale.getLatitude() == 0) {
                    this.btnLocation.setVisibility(View.VISIBLE);
                } else {
                    this.btnLocation.setVisibility(View.GONE);
                }
                this.btnSale.setVisibility(View.GONE);
                this.btnPrint.setVisibility(View.VISIBLE);
                break;
            case 2:
                this.btnOut.setVisibility(View.GONE);
                this.btnWritePad.setVisibility(View.GONE);
                this.btnSale.setVisibility(View.GONE);
                this.btnLocation.setVisibility(View.GONE);
                break;
        }
        super.showAtLocation(paramView, 80, 0, 0);
    }
}
