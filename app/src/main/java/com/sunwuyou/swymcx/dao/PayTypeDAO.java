package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.PayType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/7/24.
 * content
 */

public class PayTypeDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public PayTypeDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<PayType> getPaytypes(boolean arg8) {
        this.db = this.helper.getWritableDatabase();
        String v4 = "select id, name from sz_paytype where isavailable = \'1\'";
        if (arg8) {
            v4 = String.valueOf(v4) + " and id != \'100\' ";
        }
        try {
            cursor = this.db.rawQuery(v4, null);
            ArrayList<PayType> v2 = new ArrayList<>();
            while (cursor.moveToNext()) {
                PayType payType = new PayType();
                payType.setId(cursor.getString(0));
                payType.setName(cursor.getString(1));
                v2.add(payType);
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
