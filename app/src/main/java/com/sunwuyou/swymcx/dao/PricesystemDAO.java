package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class PricesystemDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public PricesystemDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public String queryAvailablePricesystem() {
        this.db = this.helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = this.db.rawQuery("select psid from sz_pricesystem limit 1 offset 0", null);
            if (cursor.moveToNext()) {
                return cursor.getString(0);
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
        return "";
    }

    public boolean isPricesystemidExists(String arg8) {
        this.db = this.helper.getReadableDatabase();
        String sql = "select 1 from sz_pricesystem where psid=?";
        try {
            cursor = this.db.rawQuery(sql, new String[]{arg8});
            if (cursor.getCount() == 1) {
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


}
