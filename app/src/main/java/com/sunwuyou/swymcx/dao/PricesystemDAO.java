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

}
