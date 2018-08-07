package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.Promotion;

/**
 * Created by admin
 * 2018/7/22.
 * content
 */

public class PromotionDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public PromotionDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public boolean isExists(String arg8) {
        this.db = this.helper.getWritableDatabase();
        String sql = "select 1 from sz_promotion where id=?";
        Cursor v0 = this.db.rawQuery(sql, new String[]{arg8});
        if (!v0.moveToNext()) {
            return true;
        }
        return false;
    }

    public Promotion getPromotion(String arg9) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select id, name, begintime, endtime from sz_promotion where id=?", new String[]{arg9});
            if (cursor.moveToNext()) {
                Promotion v3 = new Promotion();
                v3.setId(cursor.getString(0));
                v3.setName(cursor.getString(1));
                v3.setBeginTime(cursor.getString(2));
                v3.setEndtime(cursor.getString(3));
                return v3;
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

    public String getPromotionName(String arg8) {
        this.db = this.helper.getReadableDatabase();
        Cursor v0 = this.db.rawQuery("select name from sz_promotion where id=?", new String[]{arg8});
        if (v0.moveToNext()) {
            return v0.getString(0);
        }
        v0.close();
        db.close();
        return "";
    }
}
