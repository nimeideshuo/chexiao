package com.sunwuyou.swymcx.ui.field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.sunwuyou.swymcx.R;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.GoodsPriceDAO;
import com.sunwuyou.swymcx.dao.GoodsUnitDAO;
import com.sunwuyou.swymcx.dao.PromotionGoodsDAO;
import com.sunwuyou.swymcx.in.EmptyDo;
import com.sunwuyou.swymcx.model.FieldSale;
import com.sunwuyou.swymcx.model.FieldSaleItem;
import com.sunwuyou.swymcx.model.GoodsThin;
import com.sunwuyou.swymcx.model.GoodsUnit;
import com.sunwuyou.swymcx.model.PromotionGoods;
import com.sunwuyou.swymcx.ui.TitleDialog;
import com.sunwuyou.swymcx.utils.PDH;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.view.EditButtonView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on
 * 2018/8/3.
 * content
 */
public class GoodsSelectMoreDialog extends TitleDialog implements View.OnClickListener {

    private final GoodsUnitDAO goodsunitDAO;
    private final GoodsPriceDAO goodspriceDAO;
    private final EditButtonView etNum;
    private final FieldSaleItemDAO fieldsaleitemDAO;
    private FieldSale fieldsale;
    private List<GoodsThin> goods;
    private EmptyDo actionDo;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (TextUtils.isEmptyS(msg.obj.toString())) {
                dismiss();
                if (actionDo != null) {
                    actionDo.doAction();
                }
            } else {
                PDH.showFail(msg.obj.toString());
            }
        }
    };

    @SuppressLint("WrongViewCast")
    public GoodsSelectMoreDialog(Activity activity) {
        super(activity);
        this.setView(R.layout.dia_field_goods_select_more);
        this.setTitleText("商品添加");
        etNum = findViewById(R.id.etNum);
        goodsunitDAO = new GoodsUnitDAO();
        goodspriceDAO = new GoodsPriceDAO();
        fieldsaleitemDAO = new FieldSaleItemDAO();
        this.etNum.setDecNum(Utils.NUMBER_DEC);
        this.setConfirmButton(this);
        this.setCancelButton(null);
    }

    public void setAction(EmptyDo actionDo) {
        this.actionDo = actionDo;
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(this.etNum.getText().toString())) {
            PDH.showMessage("请输入商品数量");
        } else if (Utils.getDouble(this.etNum.getText().toString()) == 0) {
            PDH.showMessage("销售数量不能为0");
        } else {
            PDH.show(this.mContext, "正在添加商品...", new PDH.ProgressCallBack() {

                @Override
                public void action() {
                    String name = "";
                    for (int i = 0; i < goods.size(); i++) {
                        if (fieldsaleitemDAO.goodsIsAdded(fieldsale.getId(), goods.get(i).getId())) {
                            name = String.valueOf(i) + goods.get(i).getName();
                        }
                        PromotionGoods v9 = null;
                        if (!TextUtils.isEmptyS(fieldsale.getPromotionid())) {
                            v9 = new PromotionGoodsDAO().getPromotiongoods(fieldsale.getPromotionid(), goods.get(i).getId());
                        }
                        GoodsUnit v13 = Utils.DEFAULT_UNIT == 0 ? goodsunitDAO.queryBaseUnit(goods.get(i).getId()) : goodsunitDAO.queryBigUnit(goods.get(i).getId());
                        FieldSaleItem fieldSaleItem = new FieldSaleItem();
                        double v11 = v9 == null || v9.getType() != 0 ? goodspriceDAO.queryGoodsPrice(goods.get(i).getId(), v13.getUnitid(), fieldsale.getCustomerid(), fieldsale.isIsnewcustomer(), fieldsale.getPricesystemid()) : v13.getRatio() / new GoodsUnitDAO().getGoodsUnit(goods.get(i).getId(), v9.getUnitid()).getRatio() * v9.getPrice();
                        fieldSaleItem.setFieldsaleid(fieldsale.getId());
                        fieldSaleItem.setGoodsid(goods.get(i).getId());
                        fieldSaleItem.setSaleunitid(v13.getUnitid());
                        fieldSaleItem.setGiveunitid(v13.getUnitid());
                        fieldSaleItem.setSalenum(Utils.getDouble(etNum.getText().toString()));
                        fieldSaleItem.setSaleprice(v11);
                        fieldsaleitemDAO.saveFieldSaleItem(fieldSaleItem);
                    }
                    handler.sendMessage(handler.obtainMessage(0, name));
                }
            });
        }
    }

    public void show(List<GoodsThin> goods, long arg6) {
        super.show();
        this.goods = goods;
        fieldsale = new FieldSaleDAO().getFieldsale(arg6);
        new Timer().schedule(new TimerTask() {
            public void run() {
                ((InputMethodManager) etNum.getContext().getSystemService("input_method")).showSoftInput(etNum, 0);
            }
        }, 300);
    }
}
