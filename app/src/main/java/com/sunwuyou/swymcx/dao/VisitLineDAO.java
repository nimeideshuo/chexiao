package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class VisitLineDAO {

    private final DBOpenHelper helper;
    private SQLiteDatabase db;

    public VisitLineDAO() {
        super();
        helper = new DBOpenHelper();
    }

    public String getVisitLineName(String id) {
        db = this.helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String sql = "select name from sz_visitline where id=?";
            cursor = this.db.rawQuery(sql, new String[]{id});
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
