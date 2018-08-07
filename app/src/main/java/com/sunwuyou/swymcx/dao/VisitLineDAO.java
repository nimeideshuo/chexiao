package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.VisitLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/20.
 * content
 */

public class VisitLineDAO {

    private final DBOpenHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public VisitLineDAO() {
        super();
        helper = new DBOpenHelper();
    }

    public String getVisitLineName(String id) {
        db = this.helper.getReadableDatabase();
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

    public List<VisitLine> getAllVisitLines() {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select id,name from sz_visitline where isavailable = \'1\'", null);
            ArrayList<VisitLine> v3 = new ArrayList<>();
            while (cursor.moveToNext()) {
                VisitLine v2 = new VisitLine();
                v2.setId(cursor.getString(0));
                v2.setName(cursor.getString(1));
                v3.add(v2);
            }
            return v3;
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
        return new ArrayList<>();
    }
}
