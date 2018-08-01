package com.sunwuyou.swymcx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunwuyou.swymcx.model.SettleUpItem;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class SettleUpItemDAO {
    private SQLiteDatabase db;
    private DBOpenHelper helper;
    private Cursor cursor;

    public SettleUpItemDAO() {
        super();
        this.helper = new DBOpenHelper();
    }

    public double getSettleupAmount(long arg10) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select sum(thisamount) from cw_settleupitem where settleupid=?", new String[]{String.valueOf(arg10)});
            if (cursor.moveToNext()) {
                return Utils.getSubtotal(cursor.getDouble(0));
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
        return 0;
    }

    public long AddSettleUpItem(SettleUpItem settleUpItem) {
        this.db = this.helper.getWritableDatabase();
        ContentValues v2 = new ContentValues();
        v2.put("settleupid", settleUpItem.getSettleupid());
        v2.put("docid", settleUpItem.getDocid());
        v2.put("docshowid", settleUpItem.getDocshowid());
        v2.put("doctype", settleUpItem.getDoctype());
        v2.put("doctypename", settleUpItem.getDoctypename());
        v2.put("receivableamount", settleUpItem.getReceivableamount());
        v2.put("receivedamount", settleUpItem.getReceivedamount());
        v2.put("thisamount", settleUpItem.getThisamount());
        v2.put("leftamount", settleUpItem.getLeftamount());
        v2.put("doctime", settleUpItem.getDoctime());
        long v0 = this.db.insert("cw_settleupitem", null, v2);
        if (this.db != null) {
            this.db.close();
        }
        return v0;
    }

    public List<SettleUpItem> getItems(long arg10) {
        this.db = this.helper.getReadableDatabase();
        try {
            cursor = this.db.rawQuery("select serialid,settleupid,docid,docshowid,doctype,doctypename,receivableamount,receivedamount,thisamount,leftamount,doctime,doctypename from cw_settleupitem where settleupid=?", new String[]{String.valueOf(arg10)});
            ArrayList<SettleUpItem> arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                SettleUpItem upItem = new SettleUpItem();
                upItem.setSerialid(cursor.getLong(0));
                upItem.setSettleupid(cursor.getLong(1));
                upItem.setDocid(cursor.getLong(2));
                upItem.setDocshowid(cursor.getString(3));
                upItem.setDoctype(cursor.getString(4));
                upItem.setDoctypename(cursor.getString(5));
                upItem.setReceivableamount(cursor.getDouble(6));
                upItem.setReceivedamount(cursor.getDouble(7));
                upItem.setThisamount(cursor.getDouble(8));
                upItem.setLeftamount(cursor.getDouble(9));
                upItem.setDoctime(cursor.getString(10));
                upItem.setDoctypename(cursor.getString(11));
                arrayList.add(upItem);
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

}
