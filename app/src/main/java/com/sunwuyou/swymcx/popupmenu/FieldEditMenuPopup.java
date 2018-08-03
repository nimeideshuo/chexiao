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
import com.sunwuyou.swymcx.dao.GoodsDAO;
import com.sunwuyou.swymcx.gps.GPS;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItemBatchEx;
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
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.MessageDialog;

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
                    FieldEditMenuPopup.this.activity.loadDoc();
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
        switch (v.getId()) {
            case R.id.btnOut:
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
                int v13;
                for (v13 = 0; v13 < v17.size(); ++v13) {
                    FieldSaleItemBatchEx v14 = v17.get(v13);
                    if ((v14.getIsusebatch()) && v14.getNum() * v14.getRatio() > v14.getStocknumber()) {
                        handler.sendMessage(handler.obtainMessage(3, new GoodsDAO().getGoodsThin(v14.getGoodsid()).getName()));
                        return;
                    }
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

    public void show(View paramView, long paramLong) {
        this.fieldSale = new FieldSaleDAO().getFieldsale(paramLong);
        int v7 = View.VISIBLE;
        int v5 = View.GONE;
        if (this.fieldSale.getStatus() == 0) {
            this.btnOut.setText("库存处理");
            this.btnBatchDetail.setVisibility(View.GONE);
            this.btnPrint.setVisibility(View.GONE);
            if (this.fieldSale.getLatitude() == v7) {
                this.btnLocation.setVisibility(View.VISIBLE);
            } else {
                this.btnLocation.setVisibility(View.GONE);
            }

            if (TextUtils.isEmptyS(this.fieldSale.getCustomerid())) {
                this.btnSale.setVisibility(v5);
            }

        } else {
            if (this.fieldSale.getStatus() == 1) {
                this.btnOut.setText("撤销库存");
                this.btnDelete.setVisibility(v5);
                if (this.fieldSale.getPrintnum() > v7 && !Utils.IS_ALLOWEDMODIFY_PRINTED) {
                    this.btnOut.setVisibility(v5);
                }

                if (this.fieldSale.getLatitude() == v7) {
                    this.btnLocation.setVisibility(View.VISIBLE);
                } else {
                    this.btnLocation.setVisibility(v5);
                }

                this.btnSale.setVisibility(v5);
                if (this.activity.getItems().size() == 0) {
                    this.btnSettle.setVisibility(v5);
                    this.btnPrint.setVisibility(v5);
                    this.btnBatchDetail.setVisibility(v5);
                    this.btnWritePad.setVisibility(v5);
                }
            }

            if (this.fieldSale.getStatus() != 2) {
                this.btnOut.setVisibility(v5);
                this.btnWritePad.setVisibility(v5);
                this.btnSale.setVisibility(v5);
                this.btnLocation.setVisibility(v5);
            }


            if (this.activity.getItems().size() == 0) {
                this.btnSettle.setVisibility(v5);
                this.btnPrint.setVisibility(v5);
                this.btnBatchDetail.setVisibility(v5);
                this.root.findViewById(R.id.linear_top).setVisibility(v5);
                DisplayMetrics v1 = new DisplayMetrics();
                this.activity.getWindowManager().getDefaultDisplay().getMetrics(v1);
                int v2 = v1.widthPixels;
                int v0 = v1.heightPixels;
                this.setWidth(v2);
                this.setHeight(v0 / 12);
            } else {
                DisplayMetrics v1 = new DisplayMetrics();
                this.activity.getWindowManager().getDefaultDisplay().getMetrics(v1);
                int v2 = v1.widthPixels;
                int v0 = v1.heightPixels;
                this.setWidth(v2);
                this.setHeight(v0 / 6);
            }
        }
        super.showAtLocation(paramView, 80, 0, 0);
    }
}
