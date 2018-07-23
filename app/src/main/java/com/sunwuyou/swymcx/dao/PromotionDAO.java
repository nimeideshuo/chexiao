package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by admin
 * 2018/7/22.
 * content
 */

public class PromotionDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;

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

}
