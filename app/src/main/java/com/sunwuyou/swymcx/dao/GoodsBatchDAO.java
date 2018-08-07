package com.sunwuyou.swymcx.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.GoodsBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin
 * 2018/8/4.
 * content
 */

public class GoodsBatchDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public GoodsBatchDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public List<GoodsBatch> queryGoodsBatch(String arg9, boolean arg10) {
        this.db = this.helper.getReadableDatabase();
        try {
            String v4 = arg10 ? "select goodsid,batch,productiondate,stocknumber,bigstocknumber from kf_goodsbatch where goodsid = ? and stocknumber is not null and stocknumber != 0 order by productiondate" : "select goodsid,batch,productiondate,stocknumber,bigstocknumber from kf_goodsbatch where goodsid = ? order by productiondate";
            cursor = this.db.rawQuery(v4, new String[]{arg9});
            ArrayList<GoodsBatch> arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                GoodsBatch v2 = new GoodsBatch();
                v2.setGoodsid(cursor.getString(0));
                v2.setBatch(cursor.getString(1));
                v2.setProductiondate(cursor.getString(2));
                v2.setStocknumber(cursor.getDouble(3));
                v2.setBigstocknumber(cursor.getString(4));
                arrayList.add(v2);
            }
            return arrayList;
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


    public GoodsBatch queryGoodsBatch(String arg10, String arg11) {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select goodsid,batch,productiondate,stocknumber,bigstocknumber from kf_goodsbatch where goodsid = ? and batch=? order by productiondate";
            cursor = this.db.rawQuery(sql, new String[]{arg10, arg11});
            if (cursor.moveToNext()) {
                GoodsBatch goodsBatch = new GoodsBatch();
                goodsBatch.setGoodsid(cursor.getString(0));
                goodsBatch.setBatch(cursor.getString(1));
                goodsBatch.setProductiondate(cursor.getString(2));
                goodsBatch.setStocknumber(cursor.getDouble(3));
                goodsBatch.setBigstocknumber(cursor.getString(4));
                return goodsBatch;
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
        return null;
    }

    public double queryGoodStock(String goodsid) {
        this.db = this.helper.getReadableDatabase();
        try {
            String sql = "select total(stocknumber) from kf_goodsbatch where goodsid=? order by productiondate";
            cursor = this.db.rawQuery(sql, new String[]{goodsid});
            if (cursor.moveToNext()) {
                return cursor.getDouble(0);
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
        return 0;
    }

}
