package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.model.CustomerFieldSaleGoods;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin
 * 2018/7/29.
 * content
 */

public class GoodsPriceDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public GoodsPriceDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public double queryGoodsPrice(String arg10, String arg11, String arg12, boolean arg13, String arg14) {
        double v3 = 0;
        GoodsUnitDAO v2 = new GoodsUnitDAO();
        if (!arg13 && (Utils.USE_CUSTOMER_PRICE) && !TextUtils.isEmptyS(arg12)) {
            Customer v1 = new CustomerDAO().getCustomer(arg12, false);
            if (v1 != null && (v1.getIsusecustomerprice())) {
                CustomerFieldSaleGoods v0 = new CustomerFieldSaleGoodsDAO().getCusGoods(arg12, arg10);
                if (v0 != null && v0.getPrice() > 0) {
                    v3 = v0.getPrice();
                    if (!arg11.equals(v0.getUnitid())) {
                        v3 = v2.getGoodsUnitRatio(arg10, arg11) * v3 / v2.getGoodsUnitRatio(arg10, v0.getUnitid());
                    }
                }
            }
        }

        if (v3 <= 0) {
            v3 = this.getGoodsUnitPrice(arg10, arg11, arg14);
        }

        if (v3 <= 0 && !v2.isBasicUnit(arg10, arg11)) {
            v3 = this.getBasicUnitPrice(arg10, arg14) * v2.getGoodsUnitRatio(arg10, arg11);
        }

        return Utils.normalize(v3, Utils.PRICE_DEC_NUM);
    }

    public double getGoodsUnitPrice(String arg9, String arg10, String arg11) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select price from sz_goodsprice where goodsid=? and unitid=? and pricesystemid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg9, arg10, arg11});
            if (cursor.moveToNext()) {
                return cursor.getDouble(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return 0;
    }

    public double getBasicUnitPrice(String arg9, String arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select gp.price from sz_goodsprice as gp, sz_goodsunit as gu where gp.goodsid = gu.goodsid and gp.unitid = gu.unitid and gu.isbasic = \'1\' and gu.goodsid=? and gp.pricesystemid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg9, arg10});
            if (cursor.moveToNext()) {
                return cursor.getDouble(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return 0;
    }
}
