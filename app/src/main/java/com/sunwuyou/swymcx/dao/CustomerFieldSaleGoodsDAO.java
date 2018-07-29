package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.CustomerFieldSaleGoods;

/**
 * Created by admin
 * 2018/7/29.
 * content
 */

public class CustomerFieldSaleGoodsDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public CustomerFieldSaleGoodsDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public CustomerFieldSaleGoods getCusGoods(String arg10, String arg11) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select cg.customerid,cg.goodsid,g.name as goodsname,g.barcode as barcode,cg.unitid,u.name as unitname,cg.price,cg.goodsthirdclassid,cg.issale,cg.ispass from cu_customerfieldsalegoods cg left join sz_unit u on u.id=cg.unitid left join sz_goods g on g.id=cg.goodsid where cg.customerid=? and cg.goodsid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg10, arg11});
            if (cursor.moveToNext()) {
                CustomerFieldSaleGoods saleGoods = new CustomerFieldSaleGoods();
                saleGoods.setCustomerid(cursor.getString(0));
                saleGoods.setGoodsid(cursor.getString(1));
                saleGoods.setGoodsname(cursor.getString(2));
                saleGoods.setBarcode(cursor.getString(3));
                saleGoods.setUnitid(cursor.getString(4));
                saleGoods.setUnitname(cursor.getString(5));
                saleGoods.setPrice(cursor.getDouble(6));
                saleGoods.setGoodsthirdclassid(cursor.getString(7));
                saleGoods.setIssale("true".equals(cursor.getString(8)));
                saleGoods.setIspass(cursor.getInt(9) == 1);
                return saleGoods;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (this.db != null)
                this.db.close();
        }
        return null;
    }


}
