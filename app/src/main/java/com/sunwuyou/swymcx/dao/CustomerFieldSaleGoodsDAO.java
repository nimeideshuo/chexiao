package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.CustomerFieldSaleGoods;

import java.util.ArrayList;
import java.util.List;

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
            if (cursor != null) {
                cursor.close();
            }
            if (this.db != null) {
                this.db.close();
            }
        }
        return null;
    }

    public List<CustomerFieldSaleGoods> getCustomerfieldsalegoods(String customerid, long fieldsaleid, int type) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select cg.customerid,cg.goodsid,g.name as goodsname,g.barcode as barcode,cg.unitid,u.name as unitname,cg.price,cg.goodsthirdclassid,cg.issale,cg.ispass from cu_customerfieldsalegoods cg inner join sz_goods g on g.id=cg.goodsid left join sz_unit u on u.id=cg.unitid where cg.customerid=? and cg.goodsid not in  (select item.goodsid from kf_fieldsaleitem item where item.fieldsaleid=?)";
        if (type == 0) {
            sql = String.valueOf(sql) + " and cg.issale=\'true\' and (cg.ispass is null or cg.ispass!=\'1\')";
        } else if (type == 1) {
            sql = String.valueOf(sql) + " and cg.issale=\'false\' and (cg.ispass is null or cg.ispass!=\'1\')";
        } else if (type == 2) {
            sql = String.valueOf(sql) + " and cg.ispass is not null and cg.ispass=\'1\'";
        }
        try {
            cursor = db.rawQuery(sql, new String[]{customerid, String.valueOf(fieldsaleid)});
            ArrayList<CustomerFieldSaleGoods> v3 = new ArrayList<>();
            while (cursor.moveToNext()) {
                CustomerFieldSaleGoods v2 = new CustomerFieldSaleGoods();
                v2.setCustomerid(cursor.getString(0));
                v2.setGoodsid(cursor.getString(1));
                v2.setGoodsname(cursor.getString(2));
                v2.setBarcode(cursor.getString(3));
                v2.setUnitid(cursor.getString(4));
                v2.setUnitname(cursor.getString(5));
                v2.setPrice(cursor.getDouble(6));
                v2.setGoodsthirdclassid(cursor.getString(7));
                v2.setIssale("true".equals(cursor.getString(8)));
                v2.setIspass(cursor.getInt(9) == 1);
                v3.add(v2);
            }
            return v3;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (this.db != null) {
                this.db.close();
            }
        }

        return new ArrayList<>();

    }

    public boolean update(String arg8, String arg9, String arg10, String arg11) {
        boolean v1 = true;
        this.db = this.helper.getWritableDatabase();
        ContentValues v0 = new ContentValues();
        v0.put(arg10, arg11);
        if (this.db.update("cu_customerfieldsalegoods", v0, "goodsid=? and customerid=?", new String[]{arg8, arg9}) != 1) {
            v1 = false;
        }
        return v1;
    }
}
