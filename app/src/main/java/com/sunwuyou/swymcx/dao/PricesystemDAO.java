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

    public int getCount() {
        this.db = this.helper.getReadableDatabase();
        cursor = this.db.rawQuery("select count(*) from sz_pricesystem where isavailable = \'1\'", null);
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
        return 0;
    }

    public String getPricesystemName(String arg8) {
        this.db = this.helper.getReadableDatabase();
        Cursor v0 = this.db.rawQuery("select psname from sz_pricesystem where psid=?", new String[]{arg8});
        if (v0.moveToNext()) {
            return v0.getString(0);
        }
        v0.close();
        db.close();
        return "";
    }
}
