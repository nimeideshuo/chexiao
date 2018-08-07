package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.ServerDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
 * 2018/7/31.
 * content
 */
public class ServerDocDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public ServerDocDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<ServerDoc> getCusServerdocs(String arg9) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select docid,docshowid,customerid,customername,doctype,doctypename,receivableamount,receivedamount,leftamount,doctime from kf_serverdoc where customerid=?", new String[]{arg9});
            ArrayList<ServerDoc> docArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                ServerDoc serverDoc = new ServerDoc();
                serverDoc.setDocid(cursor.getLong(0));
                serverDoc.setDocshowid(cursor.getString(1));
                serverDoc.setCustomerid(cursor.getString(2));
                serverDoc.setCustmername(cursor.getString(3));
                serverDoc.setDoctype(cursor.getString(4));
                serverDoc.setDoctypename(cursor.getString(5));
                serverDoc.setReceivableamount(cursor.getDouble(6));
                serverDoc.setReceivedamount(cursor.getDouble(7));
                serverDoc.setLeftamount(cursor.getDouble(8));
                serverDoc.setDoctime(cursor.getString(9));
                docArrayList.add(serverDoc);
            }
            return docArrayList;
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
