package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.FieldSalePayType;

/**
 * Created by admin
 * 2018/7/24.
 * content
 */

public class FieldSalePayTypeDAO {

    private SQLiteDatabase db;
    private DBOpenHelper helper = new DBOpenHelper();
    private Cursor cursor;


    public long save(FieldSalePayType arg6) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v1 = new ContentValues();
        v1.put("fieldsaleid", Long.valueOf(arg6.getFieldsaleid()));
        v1.put("paytypeid", arg6.getPaytypeid());
        v1.put("paytypename", arg6.getPaytypename());
        v1.put("amount", Double.valueOf(arg6.getAmount()));
        try {
            return db.insert("kf_fieldsalepaytype", null, v1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    public boolean isHasPay(long arg9) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select 1 from kf_fieldsalepaytype where fieldsaleid=? and amount!=0";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg9)});
            if (cursor.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }


    public double getPayTypeAmount(long arg10) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select sum(amount) from kf_fieldsalepaytype where fieldsaleid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{String.valueOf(arg10)});
            if (cursor.moveToNext()) {
                return cursor.getDouble(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

}
