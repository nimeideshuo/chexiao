package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.Customertype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class CustomerTypeDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public CustomerTypeDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public String getCTPriceSystemID(String id) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select pricesystemid from cu_customertype where id=?", new String[]{id});
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

    public List<Customertype> queryAllcuCustomertypes() {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select id,name,pricesystemid from cu_customertype where isavailable = \'1\'", null);
            ArrayList<Customertype> v2 = new ArrayList<>();
            while (cursor.moveToNext()) {
                Customertype v1 = new Customertype();
                v1.setId(cursor.getString(0));
                v1.setName(cursor.getString(1));
                v1.setPricesystemid(cursor.getString(2));
                v2.add(v1);
            }
            return v2;
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
